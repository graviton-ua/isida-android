package com.whoppah.core.logging

import co.touchlab.crashkios.crashlytics.enableCrashlytics
import co.touchlab.kermit.Logger
import com.whoppah.appinitializers.AppInitializer

internal object CrashlyticsAndroidInitializer : AppInitializer {

    override fun init() {
        //enableCrashlytics()

        // Add Crashlytics log writer
        Logger.addLogWriter(CrashlyticsLoggerWriter())
    }
}