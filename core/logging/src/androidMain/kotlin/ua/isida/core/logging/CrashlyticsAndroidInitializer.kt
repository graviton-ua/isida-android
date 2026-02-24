package ua.isida.core.logging

import co.touchlab.kermit.Logger
import ua.isida.appinitializers.AppInitializer

internal object CrashlyticsAndroidInitializer : AppInitializer {

    override fun init() {
        //enableCrashlytics()

        // Add Crashlytics log writer
        Logger.addLogWriter(CrashlyticsLoggerWriter())
    }
}