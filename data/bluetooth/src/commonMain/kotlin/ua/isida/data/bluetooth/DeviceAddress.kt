package ua.isida.data.bluetooth

/**
 * Platform-agnostic identifier for a Bluetooth device or Serial port.
 * @property value The string representation of the address (MAC e.g. "00:11:22:33:44:55" or Port name e.g. "COM3").
 * Example: `DeviceAddress("00:11:22:33:44:55")`
 */
@JvmInline
value class DeviceAddress(val value: String)

/**
 * Extension to convert a String directly to a DeviceAddress.
 * Example: `"00:11:22:33:44:55".asDeviceAddress()`
 */
fun String.asDeviceAddress() = DeviceAddress(this)