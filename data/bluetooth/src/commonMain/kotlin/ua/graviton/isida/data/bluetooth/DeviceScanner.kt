package ua.graviton.isida.data.bluetooth

import kotlinx.coroutines.flow.StateFlow

interface DeviceScanner {
    /**
     * True if the system is currently looking for devices.
     */
    val isScanning: StateFlow<Boolean>

    /**
     * A list of devices known to the system (e.g., Paired on Android).
     * On JVM, this might be empty or a list of saved COM ports.
     */
    val pairedDevices: StateFlow<List<DiscoveredDevice>>

    /**
     * A list of new devices found during the current scan session.
     */
    val foundDevices: StateFlow<List<DiscoveredDevice>>

    /**
     * Starts the discovery process.
     */
    fun startScan()

    /**
     * Stops the discovery process.
     */
    fun stopScan()
}