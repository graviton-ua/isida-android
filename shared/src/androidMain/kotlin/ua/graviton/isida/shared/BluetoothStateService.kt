package ua.graviton.isida.shared

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dev.zacsweers.metro.HasMemberInjections
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ua.isida.data.bluetooth.ConnectionState
import ua.graviton.isida.domain.bluetooth.DeviceConnectionManager

@HasMemberInjections
abstract class BluetoothStateService : Service() {
    @Inject lateinit var connectionManager: DeviceConnectionManager

    protected val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    override fun onCreate() {
        super.onCreate()

        // Observe the Shared Manager
        connectionManager.connectionState
            .onEach { state ->
                when (state) {
                    ConnectionState.CONNECTED -> {
                        // Requirement #2: Show Notification
                        //startForeground(NOTIFICATION_ID, createNotification("Connected"))
                        startForegroundAndShowNotification()
                    }

                    ConnectionState.DISCONNECTED -> {
                        stopForeground(STOP_FOREGROUND_REMOVE)
                        stopSelf() // Kill service if we aren't connected
                    }

                    else -> Unit // Handle connecting/error states
                }
            }
            .launchIn(scope)
    }

    // Standard Service lifecycle boilerplate...
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // If the service is started by the system, we might want to check if
        // we should actually be running (check manager state).
        if (connectionManager.connectionState.value == ConnectionState.DISCONNECTED) {
            stopSelf()
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null // We don't need binding anymore!

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }

    abstract fun startForegroundAndShowNotification()
}