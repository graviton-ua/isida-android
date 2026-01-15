package ua.graviton.isida.domain.bluetooth

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import ua.graviton.isida.data.bluetooth.BluetoothClient
import ua.graviton.isida.data.bluetooth.ConnectionState
import ua.graviton.isida.data.bluetooth.DeviceAddress
import ua.graviton.isida.data.bluetooth.asDeviceAddress
import ua.graviton.isida.domain.interactors.SaveDataPackage

@Inject
@SingleIn(AppScope::class)
class DeviceConnectionManager(
    private val client: BluetoothClient, // Platform implementation injected here
    private val saveDataPackage: SaveDataPackage,
    private val appScope: CoroutineScope, // Scope that lives as long as the app
) {
    // 1. Expose State clearly to the rest of the app
    val connectionState: StateFlow<ConnectionState> = client.state

    // Expose data stream if UI needs raw data, or keep it internal
    val dataStream: SharedFlow<ByteArray> = client.incomingData
        .shareIn(appScope, SharingStarted.WhileSubscribed(), replay = 0)

    init {
        // 2. Start listening immediately when Manager is created
        observerDataStream()
    }

    private fun observerDataStream() {
        client.incomingData
            .onEach { data ->
                // Your logic from the old Service
                try {
                    saveDataPackage.executeSync(SaveDataPackage.Params(data))
                } catch (e: Exception) {
                    // Log error
                }
            }
            .launchIn(appScope)
    }

    suspend fun connect(address: DeviceAddress) = client.connect(address)
    suspend fun connect(address: String) = connect(address.asDeviceAddress())
    suspend fun disconnect() = client.disconnect()
    suspend fun sendCommand(cmd: ByteArray) = client.send(cmd)
}