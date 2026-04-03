package ua.isida.util

import kotlinx.io.files.Path

/**
 * Interface to provide the application's internal storage path for each platform.
 * 
 * This interface abstracts the platform-specific filesystem locations to ensure that
 * sensitive data (like logs) is stored in the appropriate directory for each OS.
 */
interface PathProvider {
    /**
     * Path to the directory where logs are stored.
     *
     * ### Platform Specific Placements:
     * 
     * **Android:**
     * - **Path:** `/data/user/0/<package_name>/files/logs`
     * - **Behavior:** Internal app storage. Erased by the OS when the app is uninstalled.
     * 
     * **JVM (Desktop):**
     * - **Path (Unix):** `~/Isida/logs`
     * - **Path (Windows):** `C:\Users\<user>\Isida\logs`
     * - **Behavior:** Persistent user-home directory. Remains after app removal.
     */
    val logsPath: Path
}
