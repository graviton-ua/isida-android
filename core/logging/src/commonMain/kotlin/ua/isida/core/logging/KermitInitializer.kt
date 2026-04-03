package ua.isida.core.logging

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import dev.zacsweers.metro.Inject
import ua.isida.appinitializers.AppInitializer
import ua.isida.base.PlatformConfig

@Inject
class KermitInitializer(
    private val platform: PlatformConfig,
    private val writers: Set<LogWriter>,
) : AppInitializer {

    override fun init() {
        Logger.setMinSeverity(
            when {
                platform.isDebug -> Severity.Debug
                platform.isQaBuild -> Severity.Debug
                else -> Severity.Info
            },
        )

        Logger.addLogWriter(*writers.toTypedArray())
    }
}