package ua.isida.core.logging

fun interface SetCrashReportingEnabledAction {
    operator fun invoke(enabled: Boolean)
}