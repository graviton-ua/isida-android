package com.whoppah.core.logging

import co.touchlab.crashkios.crashlytics.enableCrashlytics
import co.touchlab.crashkios.crashlytics.setCrashlyticsUnhandledExceptionHook
import co.touchlab.kermit.Logger
import com.whoppah.appinitializers.AppInitializer

internal object CrashlyticsIosInitializer : AppInitializer {

    override fun init() {
        // https://crashkios.touchlab.co/docs/crashlytics#step-2---add-crashkios
        enableCrashlytics()
        setCrashlyticsUnhandledExceptionHook()

        // Add Crashlytics log writer
        Logger.addLogWriter(CrashlyticsLoggerWriter())
    }
}