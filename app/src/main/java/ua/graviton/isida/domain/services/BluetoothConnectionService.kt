package ua.graviton.isida.domain.services

import android.app.PendingIntent
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import ua.graviton.isida.BuildConfig
import ua.graviton.isida.R
import ua.graviton.isida.data.bl.model.SendPackageDto
import ua.graviton.isida.domain.DeviceConnectionHolder
import ua.graviton.isida.domain.bl.BluetoothSPP
import ua.graviton.isida.domain.interactors.SaveDataPackage
import ua.graviton.isida.ui.intentMain
import ua.graviton.isida.utils.enumValueOf
import javax.inject.Inject

fun Context.intentBLConnectionService() = Intent(this, BluetoothConnectionService::class.java)

fun Context.intentBLServiceConnectDevice(address: String) =
    intentBLConnectionService().apply {
        action = BluetoothConnectionService.Action.CONNECT.name
        putExtra("address", address)
    }

fun Context.intentBLServiceSendCommand(cmd: SendPackageDto) =
    intentBLConnectionService().apply {
        action = BluetoothConnectionService.Action.SEND_CMD.name
        putExtra("command", cmd)
    }

fun Context.intentBLServiceDisconnectDevice() = intentBLConnectionService().apply { action = BluetoothConnectionService.Action.DISCONNECT.name }

@AndroidEntryPoint
class BluetoothConnectionService : Service() {
    @Inject lateinit var saveDataPackage: SaveDataPackage
    private lateinit var bt: BluetoothSPP

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private val _isRecording = MutableStateFlow(false)


    override fun onCreate() {
        super.onCreate()
        Timber.d("Service created")

        val bluetoothManager: BluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter
        if (bluetoothAdapter == null) {
            stopSelf()
            return
        }
        Timber.d("BluetoothSPP created")
        bt = BluetoothSPP(bluetoothAdapter)

        bt.setOnDataReceivedListener(object : BluetoothSPP.OnDataReceivedListener {
            override fun onDataReceived(data: ByteArray, message: String) {
                Timber.d("Device data received")
                scope.parseAndSave(data)
            }
        })

        bt.setBluetoothConnectionListener(object : BluetoothSPP.BluetoothConnectionListener {
            override fun onDeviceDisconnected() {
                DeviceConnectionHolder.isConnected.value = false
                //viewModel.submitStreamEnd()
                scope.parseAndSave(null)
                Timber.d("Device disconnected")
                stopSelf()
            }

            override fun onDeviceConnectionFailed() {
                DeviceConnectionHolder.isConnected.value = false
                Timber.w("Device connection failed")
                stopSelf()
            }

            override fun onDeviceConnected(name: String?, address: String?) {
                Timber.d("Device connected \"$name\" [$address]")
                startForeground(NOTIFICATION_ID, notificationCountDown(name).build())
                DeviceConnectionHolder.isConnected.value = true
            }
        })
    }

    override fun onBind(intent: Intent?): IBinder = ConnectionBinder()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Timber.d("onStartCommand: $intent")
        when (intent?.action?.let { enumValueOf(it, Action.UNKNOWN) }) {
            Action.CONNECT -> {
                val address: String? = intent.getStringExtra("address")
                //val device: BluetoothDevice? = intent.getParcelableExtra("device")
                bt.connect(address)
            }
            Action.SEND_CMD -> {
                //TODO: Implement unified commands interface
                val command: SendPackageDto? = intent.getParcelableExtra("command")
                if (command != null) bt.send(command.asByteArray(), false)
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
        Timber.d("Service destroyed")
    }


    private fun CoroutineScope.parseAndSave(bytes: ByteArray?) = launch {
        try {
            saveDataPackage.executeSync(SaveDataPackage.Params(bytes))
        } catch (t: Throwable) {
            Timber.w(t)
        }
    }


    private fun notificationCountDown(name: String?): NotificationCompat.Builder {
        return NotificationCompat.Builder(this, BuildConfig.NOTIFICATION_CHANNEL_ID_GENERAL)
            .setContentTitle("ISIDA Connected")
            .setContentText("We successfully connected to: $name")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntentOpenApp)
            .addAction(R.drawable.ic_stop, "Stop", pendingIntentDisconnect)
            .setOnlyAlertOnce(true)
            .setOngoing(true)
    }


    private val pendingIntentOpenApp: PendingIntent
        get() = PendingIntent.getActivity(this, 0, intentMain(), PendingIntent.FLAG_UPDATE_CURRENT)

    private val pendingIntentDisconnect: PendingIntent
        get() = PendingIntent.getService(this, 0, intentBLServiceDisconnectDevice(), 0)


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