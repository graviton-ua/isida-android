package ua.isida.core.logging

import dev.zacsweers.metro.Inject
import kotlinx.io.buffered
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readLine
import ua.isida.util.FileSharer
import ua.isida.util.PathProvider

/**
 * Manages reading and exporting logs from the [RollingFileLogWriter] directory.
 */
@Inject
class FileLogManager(
    pathProvider: PathProvider,
    private val sharer: FileSharer
) {
    private val logsDir = pathProvider.logsPath

    /**
     * Reads the most recent logs across all log files, limited to [maxLines].
     */
    fun readRecentLogs(maxLines: Int = 1000): List<String> {
        val lines = mutableListOf<String>()
        try {
            if (SystemFileSystem.metadataOrNull(logsDir) == null) return emptyList()

            // Get log files sorted by date descending (newest first)
            val logFiles = SystemFileSystem.list(logsDir)
                .filter { it.name.startsWith("logs_") && it.name.endsWith(".log") }
                .sortedByDescending { it.name }

            for (file in logFiles) {
                if (lines.size >= maxLines) break

                val fileLines = mutableListOf<String>()
                SystemFileSystem.source(file).buffered().use { source ->
                    while (!source.exhausted()) {
                        val line = source.readLine() ?: break
                        if (line.isNotEmpty()) {
                            fileLines.add(line)
                        }
                    }
                }
                // Add the lines from this file (newest in file are at the bottom, 
                // but we want newest overall at the top)
                lines.addAll(fileLines.asReversed())
            }
        } catch (e: Exception) {
            println("Failed to read logs: ${e.message}")
        }
        return lines.take(maxLines)
    }

    /**
     * Shares the most recent log file.
     */
    fun shareLatestLog() {
        try {
            if (SystemFileSystem.metadataOrNull(logsDir) == null) return

            val latestFile = SystemFileSystem.list(logsDir)
                .filter { it.name.startsWith("logs_") && it.name.endsWith(".log") }
                .maxByOrNull { it.name } ?: return

            sharer.shareFile(latestFile, "App Log Export")
        } catch (e: Exception) {
            println("Failed to share log: ${e.message}")
        }
    }
}
