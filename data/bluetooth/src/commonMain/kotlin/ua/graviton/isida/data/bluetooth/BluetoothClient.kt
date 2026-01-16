package ua.graviton.isida.data.bluetooth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * Interface for managing Bluetooth/Serial connections and data transfer.
 * Provides mechanism to connect, disconnect, and send/receive data.
 */
interface BluetoothClient {
    /**
     * The current state of the connection.
     * Example: `client.state.collect { state -> ... }`
     */
    val state: StateFlow<ConnectionState>

    /**
     * Hot stream of incoming data packets.
     * Emits byte arrays received from the device.
     * Implementation guarantees IO thread execution.
     * Example: `client.incomingData.collect { packet -> println(packet) }`
     */
    val incomingData: Flow<ByteArray>

    /**
     * Connects to the specific device.
     * @param address The target device address (MAC or COM port).
     * Example: `client.connect(DeviceAddress("00:11:22:33:44:55"))`
     */
    suspend fun connect(address: DeviceAddress)

    /**
     * Closes the connection and releases resources.
     * Example: `client.disconnect()`
     */
    suspend fun disconnect()

    /**
     * Writes data to the output stream.
     * @param data The byte array to send.
     * Example: `client.send(byteArrayOf(0x01, 0x02))`
     */
    suspend fun send(data: ByteArray)
}