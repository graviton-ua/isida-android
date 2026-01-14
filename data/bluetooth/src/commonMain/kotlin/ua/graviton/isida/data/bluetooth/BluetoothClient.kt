package ua.graviton.isida.data.bluetooth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface BluetoothClient {
    /**
     * The current state of the connection.
     */
    val state: StateFlow<ConnectionState>

    /**
     * Hot stream of incoming data packets.
     * Implementation guarantees IO thread execution.
     */
    val incomingData: Flow<ByteArray>

    /**
     * Connects to the specific device.
     * @param address MAC (Android) or COM Port (Desktop)
     */
    suspend fun connect(address: DeviceAddress)

    /**
     * Closes the connection and releases resources.
     */
    suspend fun disconnect()

    /**
     * Writes data to the output stream.
     */
    suspend fun send(data: ByteArray)
}