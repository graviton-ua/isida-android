package ua.graviton.isida

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // Init custom debug tree for logger
        Timber.plant(Timber.DebugTree())
    }
}