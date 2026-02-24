package ua.isida.core.logging

import dev.zacsweers.metro.Provides

actual interface LoggerPlatformComponent {

    @Provides
    fun bindSetCrashReportingEnabledAction(): SetCrashReportingEnabledAction = NoopSetCrashReportingEnabledAction
}

private object NoopSetCrashReportingEnabledAction : SetCrashReportingEnabledAction {
    override fun invoke(enabled: Boolean) {}
}