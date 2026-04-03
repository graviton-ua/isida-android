package ua.isida.util

import kotlinx.io.files.Path

/**
 * Interface to share or open a file on the device.
 */
interface FileSharer {
    /**
     * Share a file from the internal storage.
     * 
     * @param filePath Full path to the file.
     * @param title Title for the share dialog.
     */
    fun shareFile(filePath: Path, title: String = "Share Log File")
}
