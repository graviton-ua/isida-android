@file:OptIn(ExperimentalUnsignedTypes::class)

package ua.graviton.isida.domain.bluetooth

import co.touchlab.kermit.Logger
import com.whoppah.util.AppCoroutineDispatchers
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import ua.graviton.isida.data.bluetooth.BluetoothClient
import ua.graviton.isida.data.bluetooth.ConnectionState
import ua.graviton.isida.data.bluetooth.DeviceAddress
import ua.graviton.isida.data.bluetooth.asDeviceAddress

@Inject
@SingleIn(AppScope::class)
class DeviceConnectionManager(
    dispatchers: AppCoroutineDispatchers,
    private val client: BluetoothClient, // Platform implementation injected here
    private val appScope: CoroutineScope, // Scope that lives as long as the app
) {
    private val logger by lazy { Logger.withTag("DeviceConnectionManager") }

    // 1. Expose State clearly to the rest of the app
    val connectionState: StateFlow<ConnectionState> = client.state

    // Expose data stream if UI needs raw data, or keep it internal
    val dataStream: SharedFlow<ByteArray> = client.incomingData
        .map { it } // TODO: Probably should parse ByteArray into IsidaPacket and return it as Result<IsidaPacket> (not sure)
        .flowOn(dispatchers.computation)
        .shareIn(appScope, SharingStarted.WhileSubscribed(), replay = 0)

    init {
        // 2. Start listening immediately when Manager is created
        observerDataStream()
    }

    private fun observerDataStream() {
        // client.incomingData
        //     .onEach { data ->
        //         //logger.d { "Incoming data: ${data.toUByteArray().joinToString(", ")}" }
        //         // Your logic from the old Service
        //         try {
        //             val result = DataPackageDto.parseData(data)
        //             //logger.d { "Result data: $result" }
        //         } catch (e: Exception) {
        //             logger.w(e) { "Parse failed" }
        //         }
        //     }
        //     .launchIn(appScope)
    }

    suspend fun connect(address: DeviceAddress) = client.connect(address)
    suspend fun connect(address: String) = connect(address.asDeviceAddress())
    suspend fun disconnect() = client.disconnect()
    suspend fun sendCommand(cmd: ByteArray) = client.send(cmd)
}