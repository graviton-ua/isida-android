package ua.crypto.shared.inject

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import ua.crypto.core.app.ApplicationInfo
import ua.crypto.core.app.Flavor
import java.io.File
import java.util.prefs.Preferences

actual interface SharedPlatformApplicationComponent {
    @SingleIn(AppScope::class)
    @Provides
    fun provideApplicationId(
        flavor: Flavor,
    ): ApplicationInfo = ApplicationInfo(
        packageName = "ua.cybergateway",
        debugBuild = true,
        flavor = flavor,
        versionName = "1.0.0",
        versionCode = 1,
        cachePath = { getCacheDir().absolutePath },
    )

    @SingleIn(AppScope::class)
    @Provides
    fun providePreferences(): Preferences = Preferences.userRoot().node("ua.cybergateway")
}

private fun getCacheDir(): File = when (currentOperatingSystem) {
    OperatingSystem.Windows -> File(System.getenv("AppData"), "cybergateway/cache")
    OperatingSystem.Linux -> File(System.getProperty("user.home"), ".cache/cybergateway")
    OperatingSystem.MacOS -> File(System.getProperty("user.home"), "Library/Caches/cybergateway")
    else -> throw IllegalStateException("Unsupported operating system")
}

internal enum class OperatingSystem {
    Windows,
    Linux,
    MacOS,
    Unknown,
}

private val currentOperatingSystem: OperatingSystem
    get() {
        val os = System.getProperty("os.name").lowercase()
        return when {
            os.contains("win") -> OperatingSystem.Windows
            os.contains("nix") || os.contains("nux") || os.contains("aix") -> {
                OperatingSystem.Linux
            }

            os.contains("mac") -> OperatingSystem.MacOS
            else -> OperatingSystem.Unknown
        }
    }
