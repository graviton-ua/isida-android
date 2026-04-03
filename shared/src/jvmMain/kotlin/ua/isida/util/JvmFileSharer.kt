package ua.isida.util

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding
import kotlinx.io.files.Path
import java.awt.Desktop
import java.io.File

/**
 * JVM implementation of FileSharer that opens the file directory or the file itself.
 */
@Inject @ContributesBinding(scope = AppScope::class, binding = binding<FileSharer>())
class JvmFileSharer : FileSharer {
    override fun shareFile(filePath: Path, title: String) {
        val file = File(filePath.toString())
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

    override fun shareFiles(filePaths: List<Path>, title: String) {
        if (filePaths.isEmpty()) return
        // On JVM we just open the directory containing the files
        val firstFile = File(filePaths[0].toString())
        val parentDir = firstFile.parentFile ?: File(".")

        if (!Desktop.isDesktopSupported()) return
        val desktop = Desktop.getDesktop()
        try {
            if (desktop.isSupported(Desktop.Action.OPEN)) {
                desktop.open(parentDir)
            }
        } catch (ignore: Exception) {
        }
    }
}
