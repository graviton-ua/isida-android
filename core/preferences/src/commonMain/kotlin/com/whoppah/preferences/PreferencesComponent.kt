package com.whoppah.preferences

import com.russhwolf.settings.ObservableSettings
import com.whoppah.util.AppCoroutineDispatchers
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn

expect interface PreferencesPlatformComponent

@ContributesTo(AppScope::class)
interface PreferencesComponent : PreferencesPlatformComponent {

    @Provides @SingleIn(AppScope::class)
    fun provideAppPreferences(
        settings: ObservableSettings,
        dispatchers: AppCoroutineDispatchers,
    ): AppPreferences = AppPreferencesImpl(settings, dispatchers)

    @Provides @SingleIn(AppScope::class)
    fun provideUserPreferences(
        settings: ObservableSettings,
        dispatchers: AppCoroutineDispatchers,
    ): UserPreferences = UserPreferencesImpl(settings, dispatchers)
}