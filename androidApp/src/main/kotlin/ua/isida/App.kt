package ua.isida

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import dev.zacsweers.metro.createGraphFactory
import ua.isida.base.PlatformConfig
import ua.isida.base.PlatformInfo
import ua.isida.shared.di.AndroidAppGraph

class App : Application() {
    private val config: PlatformConfig by lazy {
        PlatformConfig(
            isDebug = BuildConfig.DEBUG,
            isQaBuild = true,
            crashReporting = BuildConfig.CRASH_REPORTING,
            platformInfo = PlatformInfo(
                userDevice = "${Build.MANUFACTURER} ${Build.MODEL}",
                osVersion = Build.VERSION.RELEASE,
                appIdentifier = "android"
            ),
        )
    }
    val appGraph: AndroidAppGraph by lazy { createGraphFactory<AndroidAppGraph.Factory>().create(this, config) }

    override fun onCreate() {
        super.onCreate()

        appGraph.initializers.init()

        createGeneralChannel()
    }

    private fun createGeneralChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = BuildConfig.NOTIFICATION_CHANNEL_ID_GENERAL
            val descriptionText = "General notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel =
                NotificationChannel(BuildConfig.NOTIFICATION_CHANNEL_ID_GENERAL, name, importance)
                    .apply { description = descriptionText }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}