package ua.isida.domain.services

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import co.touchlab.kermit.Logger
import kotlinx.coroutines.launch
import ua.isida.App
import ua.isida.BuildConfig
import ua.isida.R
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

    @SuppressLint("ForegroundServiceType")
    override fun startForegroundAndShowNotification() {
        startForeground(NOTIFICATION_ID, notificationCountDown("Device").build())
    }


    private fun notificationCountDown(name: String?): NotificationCompat.Builder {
        return NotificationCompat.Builder(this, BuildConfig.NOTIFICATION_CHANNEL_ID_GENERAL)
            .setContentTitle("ISIDA Connected")
            .setContentText("We successfully connected to: $name")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntentOpenApp)
            .addAction(R.drawable.ic_stop, "Stop", pendingIntentDisconnect)
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