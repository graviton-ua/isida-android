package ua.isida.core.logging

import dev.zacsweers.metro.Inject
import ua.isida.appinitializers.AppInitializer
import ua.isida.base.PlatformConfig

@Inject
class CrashReportingInitializer(
    private val platform: PlatformConfig,
    private val action: SetCrashReportingEnabledAction,
) : AppInitializer {

    override fun init() {
        action(platform.crashReporting)
    }
}