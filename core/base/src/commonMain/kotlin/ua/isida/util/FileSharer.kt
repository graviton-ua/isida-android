package ua.isida.util

/**
 * Interface to share or open a file on the device.
 */
interface FileSharer {
    /**
     * Share a file from the internal storage.
     * 
     * @param fileName Name of the file in the app's internal storage.
     * @param title Title for the share dialog.
     */
    fun shareFile(fileName: String, title: String = "Share Log File")
}
