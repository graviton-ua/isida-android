package ua.graviton.isida.data.bluetooth

import kotlinx.coroutines.flow.StateFlow

/**
 * Handles discovery of Bluetooth devices or Serial ports.
 * Provides streams for found devices and scanning status.
 */
interface DeviceScanner {
    /** Indicates if scanning is currently in progress. */
    val isScanning: StateFlow<Boolean>
    
    /** List of devices already paired or known to the system. */
    val pairedDevices: StateFlow<List<DiscoveredDevice>>
    
    /** List of new devices found during the current scan. */
    val foundDevices: StateFlow<List<DiscoveredDevice>>
    
    /** Latest error message, if any. Null if no error. */
    val error: StateFlow<String?>
    
    /** Indicates if the Bluetooth adapter is enabled and ready. */
    val isBluetoothEnabled: StateFlow<Boolean>

    /**
     * Starts the discovery process (Inquiry).
     * Example: `scanner.startScan()`
     */
    fun startScan()

    /**
     * Stops the discovery process.
     * Example: `scanner.stopScan()`
     */
    fun stopScan()

    /**
     * Called when the owner (ViewModel) is cleared.
     * Implementation should unregister receivers and cancel internal scopes.
     */
    fun cleanup()
}