package ua.graviton.isida.data.bluetooth

/**
 * Represents a found Bluetooth device or Serial port.
 * @property name User-friendly name of the device.
 * @property address Unique identifier (MAC or Port).
 * @property isPaired True if the device is bonded/paired.
 */
data class DiscoveredDevice(
    val name: String,
    val address: DeviceAddress, // Use the value class we defined in step 1
    val isPaired: Boolean = false // Android has pairing, Serial ports don't (usually)
)