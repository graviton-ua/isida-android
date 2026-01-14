package ua.graviton.isida.data.bluetooth

data class DiscoveredDevice(
    val name: String,
    val address: DeviceAddress, // Use the value class we defined in step 1
    val isPaired: Boolean = false // Android has pairing, Serial ports don't (usually)
)