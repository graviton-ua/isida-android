package ua.graviton.isida.domain.bl

object BluetoothConstants {
    // Message types sent from the BluetoothChatService Handler
    //const val MESSAGE_STATE_CHANGE = 1
    const val MESSAGE_READ = 2
    const val MESSAGE_WRITE = 3
    const val MESSAGE_DEVICE_NAME = 4
    const val MESSAGE_TOAST = 5

    // Key names received from the BluetoothChatService Handler
    const val DEVICE_NAME = "device_name"
    const val DEVICE_ADDRESS = "device_address"

    const val DEVICE_ANDROID = true
    const val DEVICE_OTHER = false

    // Return Intent extra
    const val EXTRA_DEVICE_ADDRESS = "device_address"
}

enum class BluetoothState {
    IDLE,           // we're doing nothing
    CONNECTING,     // now initiating an outgoing connection
    CONNECTED;      // now connected to a remote device
}