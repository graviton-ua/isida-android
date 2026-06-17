package ua.isida.domain.services

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import co.touchlab.kermit.Logger
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.getString
import ua.isida.App
import ua.isida.BuildConfig
import ua.isida.R
import ua.isida.common.ui.resources.Res
import ua.isida.common.ui.resources.btn_stop
import ua.isida.common.ui.resources.notification_connected_to
import ua.isida.common.ui.resources.notification_title
import ua.isida.intentMain
import ua.isida.shared.BluetoothStateService

fun Context.intentBLConnectionService() = Intent(this, BluetoothConnectionService::class.java)

fun Context.intentBLServiceDisconnectDevice() = intentBLConnectionService().apply { action = BluetoothConnectionService.Action.DISCONNECT.name }

class BluetoothConnectionService : BluetoothStateService() {
    private val logger by lazy { Logger.withTag("BluetoothConnectionService") }

    override fun onCreate() {
        val appGraph = (application as App).appGraph
        appGraph.inject(this)
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        val action = intent?.action?.let { Action.valueOf(it) } ?: return START_NOT_STICKY
        logger.d("onStartCommand: $intent")

        when (action) {
            Action.DISCONNECT -> scope.launch { connectionManager.disconnect() }
            else -> Unit
        }
        return START_STICKY
    }

    override fun startForegroundAndShowNotification() {
        val notification = runBlocking { notificationCountDown("Device").build() }
        ServiceCompat.startForeground(
            this,
            NOTIFICATION_ID,
            notification,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                ServiceInfo.FOREGROUND_SERVICE_TYPE_CONNECTED_DEVICE
            } else {
                0
            },
        )
    }


    private suspend fun notificationCountDown(name: String?): NotificationCompat.Builder {
        return NotificationCompat.Builder(this, BuildConfig.NOTIFICATION_CHANNEL_ID_GENERAL)
            .setContentTitle(getString(Res.string.notification_title))
            .setContentText(getString(Res.string.notification_connected_to, name ?: ""))
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntentOpenApp)
            .addAction(R.drawable.ic_stop, getString(Res.string.btn_stop), pendingIntentDisconnect)
            .setOnlyAlertOnce(true)
            .setOngoing(true)
    }


    private val pendingIntentOpenApp: PendingIntent
        get() = PendingIntent.getActivity(this, 0, intentMain(), PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

    private val pendingIntentDisconnect: PendingIntent
        get() = PendingIntent.getService(this, 0, intentBLServiceDisconnectDevice(), PendingIntent.FLAG_IMMUTABLE)


    enum class Action {
        DISCONNECT, UNKNOWN
    }

    companion object {
        private const val NOTIFICATION_ID = 123
    }
}