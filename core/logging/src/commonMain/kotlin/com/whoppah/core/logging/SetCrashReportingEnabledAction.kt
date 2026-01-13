package com.whoppah.core.logging

fun interface SetCrashReportingEnabledAction {
    operator fun invoke(enabled: Boolean)
}