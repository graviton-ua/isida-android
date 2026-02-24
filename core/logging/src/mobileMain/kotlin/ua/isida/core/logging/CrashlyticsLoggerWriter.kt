package ua.isida.core.logging

import co.touchlab.crashkios.crashlytics.CrashlyticsKotlin
import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Severity

internal class CrashlyticsLoggerWriter(
    private val minSeverity: Severity = Severity.Warn,
) : LogWriter() {

    override fun isLoggable(tag: String, severity: Severity): Boolean = severity >= minSeverity

    override fun log(severity: Severity, message: String, tag: String, throwable: Throwable?) {
        CrashlyticsKotlin.logMessage(message)
        if (throwable != null) {
            CrashlyticsKotlin.sendHandledException(throwable)
        }
    }
}