package ua.graviton.isida.data.bluetooth

/**
 * Represents the connection status of the Bluetooth client.
 */
enum class ConnectionState {
    /** Client is not connected to any device. */
    DISCONNECTED,
    /** Client is currently establishing a connection. */
    CONNECTING,
    /** Client is successfully connected and ready to transfer data. */
    CONNECTED,
    /** Connection failed or was lost due to an error. */
    ERROR
}