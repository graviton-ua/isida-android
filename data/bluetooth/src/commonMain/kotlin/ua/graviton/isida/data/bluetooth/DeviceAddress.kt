package ua.graviton.isida.data.bluetooth

/**
 * Platform-agnostic identifier.
 * - Android: MAC Address (e.g., "00:11:22:33:44:55")
 * - Desktop: Port Name (e.g., "COM3" or "/dev/ttyUSB0")
 */
@JvmInline
value class DeviceAddress(val value: String)

fun String.asDeviceAddress() = DeviceAddress(this)