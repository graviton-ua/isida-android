package com.whoppah.core.logging

import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import com.whoppah.appinitializers.AppInitializer
import dev.zacsweers.metro.Inject

@Inject
class KermitInitializer(
    private val platform: PlatformConfig,
) : AppInitializer {

    override fun init() {
        Logger.setMinSeverity(
            when {
                platform.isDebug -> Severity.Debug
                platform.isQaBuild -> Severity.Debug
                else -> Severity.Error
            },
        )
    }
}