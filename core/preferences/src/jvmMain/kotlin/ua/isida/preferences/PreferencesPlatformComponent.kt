package ua.isida.preferences

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.PreferencesSettings
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import java.util.prefs.Preferences

actual interface PreferencesPlatformComponent {

    @Provides @SingleIn(AppScope::class)
    fun provideSettings(delegate: Preferences): ObservableSettings = PreferencesSettings(delegate)

    @Provides @SingleIn(AppScope::class)
    fun providePreferences(): Preferences = Preferences.userRoot().node("ua.isida")
}
