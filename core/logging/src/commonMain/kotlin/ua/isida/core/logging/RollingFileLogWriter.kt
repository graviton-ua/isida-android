package ua.isida.core.logging

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Severity
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.datetime.*
import kotlinx.io.Sink
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.writeString
import ua.isida.util.AppCoroutineDispatchers
import ua.isida.util.PathProvider
import kotlin.time.Clock
import kotlin.time.Duration.Companion.seconds
import kotlin.time.Instant

/**
 * A specialized [LogWriter] implementation for the Kermit logging library that persists logs to local storage.
 *
 * Key features:
 * - **Daily Rotation:** Automatically creates a new log file for each calendar day (UTC).
 * - **Asynchronous Writing:** Log entries are queued and processed on a background IO dispatcher to avoid blocking the caller.
 * - **Automatic Cleanup:** Periodically removes log files older than a specified retention period.
 * - **Batch Processing:** Writes logs in batches to optimize disk I/O performance and reduce overhead.
 * - **Error Resilience:** Gracefully handles I/O failures (e.g., full disk or locked files) with retry logic.
 *
 * @param dispatchers Provides the execution context (IO dispatcher) for background operations.
 * @param pathProvider Supplies the base directory path where the "logs" folder will be created.
 * @param scope The coroutine scope that manages the lifecycle of the background logging process.
 * @param minSeverity The minimum [Severity] level required for a log to be written to disk. Defaults to [Severity.Info].
 * @param maxHistoryDays The number of days to retain log files before they are automatically deleted. Defaults to 30 days.
 */
@Inject @ContributesBinding(scope = AppScope::class, binding = binding<LogWriter>())
class RollingFileLogWriter(
    dispatchers: AppCoroutineDispatchers,
    pathProvider: PathProvider,
    scope: CoroutineScope,
    private val minSeverity: Severity = Severity.Info,
    private val maxHistoryDays: Int = 30,
) : LogWriter() {

    private val dispatcher = dispatchers.io

    /** The directory where all log files are stored. */
    private val logsDir = Path(pathProvider.filesPath, "logs")

    /**
     * Internal data container for raw log events. 
     * Formatting into strings is deferred until the background processing stage.
     */
    private class LogEntry(
        val instant: Instant,
        val severity: Severity,
        val tag: String,
        val message: String,
        val throwable: Throwable?
    )

    /**
     * A thread-safe channel used to queue log entries for the background worker.
     * Uses a buffer to handle bursts and drops the oldest logs if the buffer reaches capacity.
     */
    private val logChannel = Channel<LogEntry>(
        capacity = 2000,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        // Start the background log processing loop immediately upon initialization.
        scope.launch(dispatcher) { processLogs() }
    }

    /**
     * Determines if a log entry should be processed based on its severity.
     */
    override fun isLoggable(tag: String, severity: Severity): Boolean = severity >= minSeverity

    /**
     * Entry point for the Kermit library. Captures the log data and queues it for asynchronous writing.
     */
    override fun log(severity: Severity, message: String, tag: String, throwable: Throwable?) {
        val entry = LogEntry(Clock.System.now(), severity, tag, message, throwable)
        logChannel.trySend(entry)
    }

    /**
     * The main background loop that handles log file rotation, batching, and I/O operations.
     */
    private suspend fun CoroutineScope.processLogs() {
        var activeDate: LocalDate? = null
        var activeSink: Sink? = null

        // Initial setup: Ensure the logs directory exists and run an initial cleanup.
        try {
            if (!SystemFileSystem.exists(logsDir)) {
                SystemFileSystem.createDirectories(logsDir)
            }
            cleanupOldLogs(Clock.System.now().toLocalDateTime(TimeZone.UTC).date)
        } catch (e: Exception) {
            println("Logger: Failed to initialize logs directory: ${e.message}")
        }

        while (isActive) {
            try {
                // Wait until at least one log entry is available in the channel.
                val firstEntry = logChannel.receive()
                val logDate = firstEntry.instant.toLocalDateTime(TimeZone.UTC).date

                // Check if we need to rotate the file (e.g., date has changed).
                if (logDate != activeDate) {
                    activeSink?.flush()
                    activeSink?.close()

                    activeDate = logDate
                    val fileName = "logs_$logDate.log"
                    val filePath = Path(logsDir, fileName)

                    // Re-verify directory existence in case it was deleted by the OS or user.
                    if (!SystemFileSystem.exists(logsDir)) SystemFileSystem.createDirectories(logsDir)

                    activeSink = SystemFileSystem.sink(filePath, append = true).buffered()
                    cleanupOldLogs(logDate)
                }

                // Process the first entry.
                writeToSink(activeSink, firstEntry)

                // Batch processing: Drain additional logs from the channel up to MAX_BATCH_SIZE.
                var processedInBatch = 1
                while (processedInBatch < MAX_BATCH_SIZE) {
                    val nextEntry = logChannel.tryReceive().getOrNull() ?: break
                    writeToSink(activeSink, nextEntry)
                    processedInBatch++
                }

                // Ensure data is committed to the file system after each batch.
                activeSink?.flush()

            } catch (e: Exception) {
                // Error Resilience: Handle cases like disk full, file locked, or permission issues.
                println("Logger: IO Exception during batch write: ${e.message}")

                // Reset state to force a fresh file handle on the next attempt.
                try {
                    activeSink?.close()
                } catch (_: Exception) {
                    // Ignore errors during emergency close.
                }
                activeSink = null
                activeDate = null

                // Throttle retries to avoid CPU spin-locking during persistent I/O failures.
                delay(5.seconds)
            }
        }

        // Finalize: Ensure all buffered logs are written before the coroutine scope is cancelled.
        try {
            activeSink?.flush()
            activeSink?.close()
        } catch (_: Exception) {
            // Ignore errors during shutdown.
        }
    }

    /**
     * Formats a [LogEntry] into a human-readable string and writes it to the provided [Sink].
     * 
     * Output format: [Timestamp] [Severity] [Tag] Message
     */
    private fun writeToSink(sink: Sink?, entry: LogEntry) {
        if (sink == null) return
        try {
            val logString = buildString {
                append("[")
                append(entry.instant.toString())
                append("] [")
                append(entry.severity.name[0]) // Use single letter for severity (I, W, E, etc.)
                append("] [")
                append(entry.tag)
                append("] ")
                append(entry.message)
                if (entry.throwable != null) {
                    append('\n')
                    append(entry.throwable.stackTraceToString())
                }
                append('\n')
            }
            sink.writeString(logString)
        } catch (e: Exception) {
            // Propagate exception to the main loop for recovery handling.
            throw e
        }
    }

    /**
     * Scans the logs directory and deletes any file whose date is older than [maxHistoryDays].
     * 
     * Expects filename format: "logs_YYYY-MM-DD.log"
     */
    private fun cleanupOldLogs(currentDate: LocalDate) {
        try {
            if (!SystemFileSystem.exists(logsDir)) return

            val limitDate = currentDate.minus(maxHistoryDays, DateTimeUnit.DAY)

            SystemFileSystem.list(logsDir).forEach { path ->
                val fileName = path.name
                if (fileName.startsWith("logs_") && fileName.endsWith(".log")) {
                    val dateStr = fileName.substringAfter("logs_").substringBefore(".log")
                    try {
                        val fileDate = LocalDate.parse(dateStr)
                        if (fileDate < limitDate) {
                            SystemFileSystem.delete(path)
                        }
                    } catch (e: Exception) {
                        // Skip files with unrecognized date formats.
                    }
                }
            }
        } catch (e: Exception) {
            println("Logger: Failed to cleanup old logs: ${e.message}")
        }
    }

    companion object {
        /** Maximum number of log entries to process in a single I/O burst. */
        private const val MAX_BATCH_SIZE = 200
    }
}
