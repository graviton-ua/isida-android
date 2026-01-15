package ua.graviton.isida.data.bluetooth

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import co.touchlab.kermit.Logger
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.Named
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@SuppressLint("MissingPermission")
@Inject
@ContributesBinding(AppScope::class)
class AndroidDeviceScanner(
    @param:Named("APPLICATION_CONTEXT") private val context: Context,
    private val adapter: BluetoothAdapter? = null,
) : DeviceScanner {
    private val logger by lazy { Logger.withTag("AndroidDeviceScanner") }

    private val _isScanning = MutableStateFlow(false)
    override val isScanning = _isScanning.asStateFlow()

    private val _pairedDevices = MutableStateFlow<List<DiscoveredDevice>>(emptyList())
    override val pairedDevices = _pairedDevices.asStateFlow()

    private val _foundDevices = MutableStateFlow<List<DiscoveredDevice>>(emptyList())
    override val foundDevices = _foundDevices.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    override val error = _error.asStateFlow()

    private val _isBluetoothEnabled = MutableStateFlow(adapter?.isEnabled == true)
    override val isBluetoothEnabled = _isBluetoothEnabled.asStateFlow()

    private var isReceiverRegistered = false

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                    if (device != null && device.bondState != BluetoothDevice.BOND_BONDED) {
                        val newDevice = device.toDomain()
                        _foundDevices.update { current ->
                            if (current.any { it.address == newDevice.address }) current else current + newDevice
                        }
                    }
                }

                BluetoothAdapter.ACTION_DISCOVERY_STARTED -> _isScanning.value = true
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> _isScanning.value = false
                BluetoothAdapter.ACTION_STATE_CHANGED -> {
                    val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
                    _isBluetoothEnabled.value = (state == BluetoothAdapter.STATE_ON)
                    if (state == BluetoothAdapter.STATE_ON) refreshPairedDevices()
                }
            }
        }
    }

    init {
        // Lifecycle Start: Register immediately upon creation (ViewModel init)
        try {
            val filter = IntentFilter().apply {
                addAction(BluetoothDevice.ACTION_FOUND)
                addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
                addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
                addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
            }
            context.registerReceiver(receiver, filter)
            isReceiverRegistered = true
            refreshPairedDevices()
        } catch (e: Exception) {
            logger.e(e) { "Failed to register receiver in init" }
        }
    }

    override fun startScan() {
        logger.d { "Requesting startScan" }
        _error.value = null // Clear previous errors

        // 1. Check if Adapter exists and is enabled
        if (adapter == null) {
            _error.value = "Bluetooth not supported on this device"
            return
        }
        if (!adapter.isEnabled) {
            _error.value = "Bluetooth is disabled"
            return
        }

        // 2. Check Runtime Permissions
        if (!hasScanPermission()) {
            _error.value = "Missing Bluetooth permissions"
            logger.e { "startScan failed: Missing Runtime Permissions" }
            return
        }

        // 3. Check Location Services (GPS) - CRITICAL for returning TRUE
        // On Android 12+, if we have BLUETOOTH_SCAN, we usually don't need GPS strictly for discovery
        // depending on "neverForLocation", but for Classic discovery or older Androids, we do.
        if (!isLocationServiceEnabled()) {
            _error.value = "Location Services (GPS) must be enabled to scan"
            logger.e { "startScan failed: Location Services disabled" }
            return
        }

        // 4. Safe Start
        if (_isScanning.value) return

        // Cancel any previous discovery to be safe
        try {
            if (adapter.isDiscovering) adapter.cancelDiscovery()
        } catch (e: SecurityException) { /* no-op */
        }

        refreshPairedDevices()
        _foundDevices.value = emptyList()

        try {
            val success = adapter.startDiscovery()
            logger.d { "adapter.startDiscovery() returned: $success" }

            if (!success) {
                _error.value = "System refused to start scan (Unknown error)"
                _isScanning.value = false
            }
        } catch (e: SecurityException) {
            _error.value = "Security Exception: ${e.message}"
            logger.e(e) { "Security Exception during startDiscovery" }
        }
    }

    override fun stopScan() {
        try {
            if (hasScanPermission()) {
                adapter?.cancelDiscovery()
            }
        } catch (e: Exception) {
            logger.w(e) { "Error cancelling discovery" }
        }
    }

    /**
     * Lifecycle End: Called by ViewModel.onCleared()
     */
    override fun cleanup() {
        stopScan()
        if (isReceiverRegistered) {
            try {
                context.unregisterReceiver(receiver)
                isReceiverRegistered = false
            } catch (e: IllegalArgumentException) {
                // Ignore if already unregistered
            }
        }
    }

    private fun refreshPairedDevices() {
        if (!hasScanPermission()) return
        try {
            _pairedDevices.value = adapter?.bondedDevices?.map { it.toDomain(isPaired = true) } ?: emptyList()
        } catch (e: SecurityException) {
            logger.w(e) { "Permission denied getting bonded devices" }
        }
    }

    /**
     * Helper to check permissions based on Android Version
     */
    private fun hasScanPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            context.checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED &&
                    context.checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED
        } else {
            context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        }
    }

    /**
     * Helper to check if GPS/Location is enabled
     */
    private fun isLocationServiceEnabled(): Boolean {
        // On Android 12+ (S), if you have BLUETOOTH_SCAN, you theoretically don't need location enabled
        // for "finding devices", but many manufacturers still enforce it for Classic Bluetooth.
        // It is safer to require it if scanning fails.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // You can try returning true here to relax requirements for Android 12+,
            // but if startDiscovery returns false, revert to checking location.
        }

        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
        return locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true ||
                locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) == true
    }

    private fun BluetoothDevice.toDomain(isPaired: Boolean = false): DiscoveredDevice {
        return DiscoveredDevice(
            name = this.name ?: this.address,
            address = DeviceAddress(this.address),
            isPaired = isPaired
        )
    }
}