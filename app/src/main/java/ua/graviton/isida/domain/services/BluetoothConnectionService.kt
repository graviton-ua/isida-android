package ua.graviton.isida.domain.services

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import co.touchlab.kermit.Logger
import com.whoppah.extensions.enumValueOf
import com.whoppah.extensions.toHexString
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ua.graviton.isida.R
import ua.graviton.isida.data.models.SendPackageDto
import ua.graviton.isida.domain.DeviceConnectionHolder
import ua.graviton.isida.domain.bl.BluetoothSPP
import ua.graviton.isida.domain.interactors.SaveDataPackage
import ua.graviton.isida.ui.intentMain

fun Context.intentBLConnectionService() = Intent(this, BluetoothConnectionService::class.java)

fun Context.intentBLServiceConnectDevice(address: String) =
    intentBLConnectionService().apply {
        action = BluetoothConnectionService.Action.CONNECT.name
        putExtra("address", address)
    }

fun Context.intentBLServiceSendCommand(cmd: SendPackageDto) =
    intentBLConnectionService().apply {
        action = BluetoothConnectionService.Action.SEND_CMD.name
        //putExtra("command", cmd)
    }

fun Context.intentBLServiceDisconnectDevice() = intentBLConnectionService().apply { action = BluetoothConnectionService.Action.DISCONNECT.name }

class BluetoothConnectionService : Service() {
    private val logger by lazy { Logger.withTag("BluetoothConnectionService") }

    @Inject lateinit var saveDataPackage: SaveDataPackage
    private lateinit var bt: BluetoothSPP

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private val _isRecording = MutableStateFlow(false)


    override fun onCreate() {
        super.onCreate()
        logger.d("Service created")

        val bluetoothManager: BluetoothManager = getSystemService(BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter
        if (bluetoothAdapter == null) {
            stopSelf()
            return
        }
        logger.d("BluetoothSPP created")
        bt = BluetoothSPP(scope, bluetoothAdapter)

        bt.setOnDataReceivedListener(object : BluetoothSPP.OnDataReceivedListener {
            override fun onDataReceived(data: ByteArray, message: String) {
                logger.d("Device data received | ${data.toHexString(" ")}")
                scope.parseAndSave(data)
            }
        })

        bt.setBluetoothConnectionListener(object : BluetoothSPP.BluetoothConnectionListener {
            override fun onDeviceDisconnected() {
                DeviceConnectionHolder.isConnected.value = false
                // viewModel.submitStreamEnd()
                scope.parseAndSave(null)
                logger.d("Device disconnected")
                stopSelf()
            }

            override fun onDeviceConnectionFailed() {
                DeviceConnectionHolder.isConnected.value = false
                logger.w("Device connection failed")
                stopSelf()
            }

            @SuppressLint("ForegroundServiceType")
            override fun onDeviceConnected(name: String?, address: String?) {
                logger.d("Device connected \"$name\" [$address]")
                startForeground(NOTIFICATION_ID, notificationCountDown(name).build())
                DeviceConnectionHolder.isConnected.value = true
            }
        })
    }

    override fun onBind(intent: Intent?): IBinder = ConnectionBinder()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        logger.d("onStartCommand: $intent")
        when (intent?.action?.let { enumValueOf(it, Action.UNKNOWN) }) {
            Action.CONNECT -> {
                val address: String? = intent.getStringExtra("address")
                // val device: BluetoothDevice? = intent.getParcelableExtra("device")
                bt.connect(address)
            }

            Action.SEND_CMD -> {
                // TODO: Implement unified commands interface
                val command: SendPackageDto? = intent.getParcelableExtra("command")
                if (command != null) bt.send(command.asByteArray().also { logger.d("Send command: ${it.toHexString(" ")}") }, true)
            }

            Action.DISCONNECT -> {
                bt.disconnect()
                stopSelf()
            }

            else -> Unit
        }
        return START_STICKY
    }

    override fun onDestroy() {
        DeviceConnectionHolder.isConnected.value = false
        bt.disconnect()
        scope.cancel()
        super.onDestroy()
        stopForeground(true)
        logger.d("Service destroyed")
    }


    private fun CoroutineScope.parseAndSave(bytes: ByteArray?) = launch {
        try {
            saveDataPackage.executeSync(SaveDataPackage.Params(bytes))
        } catch (t: Throwable) {
            logger.w(t) { t.message ?: "Unknown error" }
        }
    }


    private fun notificationCountDown(name: String?): NotificationCompat.Builder {
        return NotificationCompat.Builder(this, "BuildConfig.NOTIFICATION_CHANNEL_ID_GENERAL")
            .setContentTitle("ISIDA Connected")
            .setContentText("We successfully connected to: $name")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntentOpenApp)
            .addAction(R.drawable.ic_stop, "Stop", pendingIntentDisconnect)
            .setOnlyAlertOnce(true)
            .setOngoing(true)
    }


    private val pendingIntentOpenApp: PendingIntent
        get() = PendingIntent.getActivity(this, 0, intentMain(), PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

    private val pendingIntentDisconnect: PendingIntent
        get() = PendingIntent.getService(this, 0, intentBLServiceDisconnectDevice(), PendingIntent.FLAG_IMMUTABLE)


    inner class ConnectionBinder : Binder() {
        fun isConnected(): StateFlow<Boolean> = _isRecording
    }

    enum class Action {
        CONNECT, SEND_CMD, DISCONNECT, UNKNOWN
    }

    companion object {
        private const val NOTIFICATION_ID = 123
    }
}