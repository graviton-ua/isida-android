package ua.isida.preferences

import com.russhwolf.settings.ObservableSettings
import kotlin.time.Clock

internal class AppPreferencesImpl(
    settings: Lazy<ObservableSettings>,
) : AppPreferences {
    private val settings: ObservableSettings by settings


    companion object {
        private const val FIRST_APP_LAUNCH_TIME_STAMP = "first_app_launch_time"
    }


    override var firstAppLaunchTimeStamp: Long?
        get() = settings.getLongOrNull(FIRST_APP_LAUNCH_TIME_STAMP)
        set(value) {
            val current = settings.getLongOrNull(FIRST_APP_LAUNCH_TIME_STAMP)
            if (current == null) settings.putLong(FIRST_APP_LAUNCH_TIME_STAMP, value ?: Clock.System.now().toEpochMilliseconds())
        }
}