package ua.graviton.isida

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.whoppah.base.PlatformConfig
import com.whoppah.base.PlatformInfo
import dev.zacsweers.metro.createGraphFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import ua.graviton.isida.shared.di.AndroidAppGraph

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
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}