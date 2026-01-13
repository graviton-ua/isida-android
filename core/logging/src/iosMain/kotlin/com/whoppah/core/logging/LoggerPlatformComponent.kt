package com.whoppah.core.logging

import com.whoppah.appinitializers.AppInitializer
import dev.zacsweers.metro.IntoSet
import dev.zacsweers.metro.Provides

actual interface LoggerPlatformComponent {

    @Provides @IntoSet
    fun provideCrashlyticsAndroidInitializer(): AppInitializer = CrashlyticsIosInitializer

    @Provides
    fun bindSetCrashReportingEnabledAction(): SetCrashReportingEnabledAction = NoopSetCrashReportingEnabledAction
}

private object NoopSetCrashReportingEnabledAction : SetCrashReportingEnabledAction {
    override fun invoke(enabled: Boolean) {}
}