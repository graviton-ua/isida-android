package ua.isida.preferences

import com.russhwolf.settings.ObservableSettings
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn

expect interface PreferencesPlatformComponent

@ContributesTo(AppScope::class)
interface PreferencesComponent : PreferencesPlatformComponent {

    @Provides @SingleIn(AppScope::class)
    fun provideAppPreferences(
        settings: Lazy<ObservableSettings>,
    ): AppPreferences = AppPreferencesImpl(settings)
}