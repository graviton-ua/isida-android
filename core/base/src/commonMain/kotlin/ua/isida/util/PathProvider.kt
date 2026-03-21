package ua.isida.util

/**
 * Interface to provide the application's internal storage path for each platform.
 */
interface PathProvider {
    /**
     * Path to the internal files' directory.
     */
    val filesPath: String
}
