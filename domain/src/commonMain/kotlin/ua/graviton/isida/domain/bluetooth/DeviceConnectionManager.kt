@file:OptIn(ExperimentalUnsignedTypes::class)

package ua.graviton.isida.domain.bluetooth

import co.touchlab.kermit.Logger
import com.whoppah.util.AppCoroutineDispatchers
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import ua.graviton.isida.data.bluetooth.BluetoothClient
import ua.graviton.isida.data.bluetooth.ConnectionState
import ua.graviton.isida.data.bluetooth.DeviceAddress
import ua.graviton.isida.data.bluetooth.asDeviceAddress
import ua.graviton.isida.data.parsers.RootDecoder
import ua.graviton.isida.data.protocol.packets.IsidaPacket
import ua.graviton.isida.data.protocol.packets.StatusPacket

@Inject
@SingleIn(AppScope::class)
class DeviceConnectionManager(
    dispatchers: AppCoroutineDispatchers,
    private val client: BluetoothClient,
    appScope: CoroutineScope,
) {
    private val logger by lazy { Logger.withTag("DeviceConnectionManager") }

    val connectionState: StateFlow<ConnectionState> = client.state

    // val dataStream: SharedFlow<ByteArray> = client.incomingData
    //     .shareIn(appScope, SharingStarted.WhileSubscribed(), replay = 0)

    val packetStream: SharedFlow<IsidaPacket> = client.incomingData
        .mapNotNull { data ->
            RootDecoder.parse(data).fold(
                onSuccess = { it },
                onFailure = { e ->
                    logger.w(e) { "Failed to parse packet" }
                    null
                }
            )
        }
        .flowOn(dispatchers.computation)
        .shareIn(appScope, SharingStarted.WhileSubscribed(), replay = 0)

    val statusStream: StateFlow<StatusPacket?> = packetStream.filterIsInstance<StatusPacket>()
        .flowOn(dispatchers.computation)
        .stateIn(appScope, SharingStarted.WhileSubscribed(), null)


    suspend fun connect(address: DeviceAddress) = client.connect(address)
    suspend fun connect(address: String) = connect(address.asDeviceAddress())
    suspend fun disconnect() = client.disconnect()
    suspend fun sendCommand(cmd: ByteArray) = client.send(cmd)
}