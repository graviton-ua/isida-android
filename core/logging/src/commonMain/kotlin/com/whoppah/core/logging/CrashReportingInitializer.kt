package com.whoppah.core.logging

import com.whoppah.appinitializers.AppInitializer
import dev.zacsweers.metro.Inject

@Inject
class CrashReportingInitializer(
    private val platform: PlatformConfig,
    private val action: SetCrashReportingEnabledAction,
) : AppInitializer {

    override fun init() {
        action(platform.crashReporting)
    }
}