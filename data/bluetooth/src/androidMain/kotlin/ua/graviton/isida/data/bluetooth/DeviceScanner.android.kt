package ua.graviton.isida.data.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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
        logger.d { "Starting scan" }
        if (adapter == null || !adapter.isEnabled) {
            _error.value = "Bluetooth disabled or unavailable"
            return
        }

        // FIX: Guard clause. If we are already scanning, do nothing.
        if (_isScanning.value) return

        // ALTERNATIVE FIX (More robust):
        // Unregister any existing receiver to ensure a clean slate before registering again.
        // Since stopScan() handles the try-catch for unregistering, we can just call it.
        stopScan()

        // Safety: If already scanning, restart involves cancelling first
        if (adapter.isDiscovering) adapter.cancelDiscovery()

        refreshPairedDevices()
        _foundDevices.value = emptyList()

        val success = adapter.startDiscovery()
        if (!success) {
            // Usually indicates GPS is Off or Permission Missing
            logger.e { "startDiscovery returned false" }
            _error.value = "Scan failed to start. Check GPS."
            _isScanning.value = false
        }

    }

    override fun stopScan() {
        try {
            adapter?.cancelDiscovery()
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
        try {
            _pairedDevices.value = adapter?.bondedDevices?.map { it.toDomain(isPaired = true) } ?: emptyList()
        } catch (e: Exception) {
            logger.w(e) { "Can't get bonded devices" }
        }
    }

    private fun BluetoothDevice.toDomain(isPaired: Boolean = false): DiscoveredDevice {
        return DiscoveredDevice(
            name = this.name ?: this.address,
            address = DeviceAddress(this.address),
            isPaired = isPaired
        )
    }
}