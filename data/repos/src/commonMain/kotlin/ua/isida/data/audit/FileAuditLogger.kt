@file:Suppress("DEPRECATION")

package ua.isida.data.audit

import co.touchlab.kermit.Logger
import kotlin.time.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import ua.isida.util.AuditLogger
import ua.isida.util.PathProvider
import dev.zacsweers.metro.Inject
import kotlinx.io.*
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem

/**
 * Implementation of AuditLogger that logs to Kermit and a local file.
 * Automatically cleans up entries older than 30 days on initialization.
 */
@Inject
class FileAuditLogger(
    private val pathProvider: PathProvider,
    private val sharer: ua.isida.util.FileSharer
) : AuditLogger {
    private val logger = Logger.withTag("BlackBox")
    private val logsDir = "logs"
    private val logFileName = "audit.log"
    private val logPath = Path(pathProvider.filesPath, logsDir, logFileName)

    init {
        cleanupOldLogs()
    }

    override fun exportLogs() {
        sharer.shareFile(logFileName, logsDir, "Audit Log Export")
    }

    private fun cleanupOldLogs() {
        try {
            if (SystemFileSystem.metadataOrNull(logPath) == null) {
                return
            }
            
            val now = Clock.System.now()
            val today = now.toLocalDateTime(TimeZone.currentSystemDefault()).date
            val thirtyDaysAgo = today.minus(30, DateTimeUnit.DAY)

            val remainingLines = mutableListOf<String>()
            
            SystemFileSystem.source(logPath).buffered().use { source ->
                while (!source.exhausted()) {
                    val line = source.readLine() ?: break
                    if (line.isEmpty()) continue
                    
                    if (line.startsWith("[")) {
                        val dateStr = line.substringAfter('[').substringBefore(' ')
                        try {
                            val logDate = LocalDate.parse(dateStr)
                            if (logDate >= thirtyDaysAgo) {
                                remainingLines.add(line)
                            }
                        } catch (e: Exception) {
                            remainingLines.add(line)
                        }
                    } else {
                        remainingLines.add(line)
                    }
                }
            }

            SystemFileSystem.sink(logPath, append = false).buffered().use { sink ->
                remainingLines.forEach { line -> 
                    sink.writeString(line)
                    sink.writeString("\n")
                }
            }
        } catch (e: Exception) {
            logger.e(e) { "Failed to cleanup old logs: $logPath" }
        }
    }

    override fun logAction(
        screen: String, 
        action: String, 
        details: String, 
        devicePrefix: String?,
        consoleLog: String?
    ) {
        try {
            // Ensure directory exists before writing
            val parentDir = logPath.parent ?: Path(pathProvider.filesPath)
            if (SystemFileSystem.metadataOrNull(parentDir) == null) {
                SystemFileSystem.createDirectories(parentDir)
            }

            val now = Clock.System.now()
            val localNow = now.toLocalDateTime(TimeZone.currentSystemDefault())
            val timestamp = "${localNow.date} ${localNow.time.toString().substringBefore('.')}"
            
            val logEntry = if (devicePrefix != null) {
                "[$timestamp] $devicePrefix [$screen] $action: $details"
            } else {
                "[$timestamp] [$screen] $action: $details"
            }
            
            // Output technical/english version to terminal (Kermit/Logcat)
            // We use consoleLog as a full replacement if provided to avoid encoding issues
            logger.i { consoleLog ?: logEntry }
            
            // Write localized logEntry to the audit.log file
            SystemFileSystem.sink(logPath, append = true).buffered().use { sink ->
                sink.writeString(logEntry)
                sink.writeString("\n")
            }
        } catch (e: Exception) {
            logger.e(e) { "Failed to write to audit file: $logPath" }
        }
    }
}
