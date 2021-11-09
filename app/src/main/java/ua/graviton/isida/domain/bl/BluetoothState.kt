package ua.graviton.isida.domain.bl

class BluetoothState {
    // Constants that indicate the current connection state
    companion object {
        const val STATE_IDLE = 0          // we're doing nothing
        const val STATE_CONNECTING = 2    // now initiating an outgoing connection
        const val STATE_CONNECTED = 3     // now connected to a remote device

        // Message types sent from the BluetoothChatService Handler
        const val MESSAGE_STATE_CHANGE = 1
        const val MESSAGE_READ = 2
        const val MESSAGE_WRITE = 3
        const val MESSAGE_DEVICE_NAME = 4
        const val MESSAGE_TOAST = 5

        // Intent request codes
        const val REQUEST_CONNECT_DEVICE = 384
        const val REQUEST_ENABLE_BT = 385

        // Key names received from the BluetoothChatService Handler
        const val DEVICE_NAME = "device_name"
        const val DEVICE_ADDRESS = "device_address"
        const val TOAST = "toast"

        const val DEVICE_ANDROID = true
        const val DEVICE_OTHER = false

        // Return Intent extra
        const val EXTRA_DEVICE_ADDRESS = "device_address"
    }
}