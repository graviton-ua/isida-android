package ua.graviton.isida.data.bluetooth

import kotlinx.coroutines.flow.StateFlow

interface DeviceScanner {
    val isScanning: StateFlow<Boolean>
    val pairedDevices: StateFlow<List<DiscoveredDevice>>
    val foundDevices: StateFlow<List<DiscoveredDevice>>
    val error: StateFlow<String?>
    val isBluetoothEnabled: StateFlow<Boolean>

    /**
     * Starts the discovery process (Inquiry).
     */
    fun startScan()

    /**
     * Stops the discovery process.
     */
    fun stopScan()

    /**
     * Called when the owner (ViewModel) is cleared.
     * Implementation should unregister receivers and cancel internal scopes.
     */
    fun cleanup()
}