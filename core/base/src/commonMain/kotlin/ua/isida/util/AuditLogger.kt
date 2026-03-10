package ua.isida.util

/**
 * Interface for logging user actions ("Black Box").
 */
interface AuditLogger {
    /**
     * Log a user action.
     * 
     * @param screen The name of the screen where the action occurred.
     * @param action The name of the action (e.g., "Apply Settings").
     * @param details Additional details about the change.
     * @param consoleLog Optional English/Technical string for terminal output.
     */
    fun logAction(screen: String, action: String, details: String, consoleLog: String? = null)

    /**
     * Export or share the audit log file.
     */
    fun exportLogs()
}
