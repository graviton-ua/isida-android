package com.whoppah.core.logging

import com.whoppah.appinitializers.AppInitializer
import com.whoppah.base.PlatformConfig
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