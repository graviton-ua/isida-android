package ua.isida.core.logging

import ua.isida.appinitializers.AppInitializer
import dev.zacsweers.metro.IntoSet
import dev.zacsweers.metro.Provides

actual interface LoggerPlatformComponent {

    @Provides @IntoSet
    fun provideCrashlyticsAndroidInitializer(): AppInitializer = CrashlyticsAndroidInitializer

    @Provides
    fun bindSetCrashReportingEnabledAction(): SetCrashReportingEnabledAction = AndroidSetCrashReportingEnabledAction
}