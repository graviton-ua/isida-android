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

    /**
     * Share multiple files from the internal storage.
     *
     * @param filePaths List of full paths to the files.
     * @param title Title for the share dialog.
     */
    fun shareFiles(filePaths: List<Path>, title: String = "Share Log Files")
}
