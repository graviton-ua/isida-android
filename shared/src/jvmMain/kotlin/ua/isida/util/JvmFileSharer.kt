package ua.isida.util

import dev.zacsweers.metro.Inject
import java.awt.Desktop
import java.io.File

/**
 * JVM implementation of FileSharer that opens the file directory or the file itself.
 */
@Inject
class JvmFileSharer : FileSharer {
    override fun shareFile(fileName: String, title: String) {
        val file = File(".", fileName)
        if (!file.exists() || !Desktop.isDesktopSupported()) return

        val desktop = Desktop.getDesktop()
        try {
            // Check if browseFileDirectory is supported (Java 9+)
            if (desktop.isSupported(Desktop.Action.BROWSE_FILE_DIR)) {
                desktop.browseFileDirectory(file)
            } else if (desktop.isSupported(Desktop.Action.OPEN)) {
                // Fallback: Open the parent directory
                val parentDir = file.parentFile ?: File(".")
                desktop.open(parentDir)
            }
        } catch (e: Exception) {
            // Last resort: try to open the file itself (might open in text editor)
            try {
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    desktop.open(file)
                }
            } catch (ignore: Exception) {
                // Silently fail if nothing works
            }
        }
    }
}
