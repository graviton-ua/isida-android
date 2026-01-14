package ua.graviton.isida.data.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import co.touchlab.kermit.Logger
import dev.zacsweers.metro.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@SuppressLint("MissingPermission")
@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class AndroidDeviceScanner(
    @param:Named("APPLICATION_CONTEXT") private val context: Context,
    private val adapter: BluetoothAdapter? = null,
) : DeviceScanner {
    private val logger by lazy { Logger.withTag("AndroidDeviceScanner") }

    private val _isScanning = MutableStateFlow(false)
    override val isScanning: StateFlow<Boolean> = _isScanning.asStateFlow()

    private val _pairedDevices = MutableStateFlow<List<DiscoveredDevice>>(emptyList())
    override val pairedDevices: StateFlow<List<DiscoveredDevice>> = _pairedDevices.asStateFlow()

    private val _foundDevices = MutableStateFlow<List<DiscoveredDevice>>(emptyList())
    override val foundDevices: StateFlow<List<DiscoveredDevice>> = _foundDevices.asStateFlow()

    // Android BroadcastReceiver to listen for Found devices
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
            }
        }
    }

    init {
        refreshPairedDevices()
    }

    override fun startScan() {
        if (adapter == null) return

        // 1. Refresh paired
        refreshPairedDevices()

        // 2. Clear previous found
        _foundDevices.value = emptyList()

        // 3. Register Receiver
        try {
            val filter = IntentFilter().apply {
                addAction(BluetoothDevice.ACTION_FOUND)
                addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
                addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
            }
            context.registerReceiver(receiver, filter)

            // 4. Start Discovery
            if (adapter.isDiscovering) adapter.cancelDiscovery()
            adapter.startDiscovery()
        } catch (e: Exception) {
            logger.e(e) { "Failed to start scan" }
        }
    }

    override fun stopScan() {
        if (adapter == null) return
        try {
            adapter.cancelDiscovery()
            // We typically unregister in onCleared or similar, but for manual stop:
            try {
                context.unregisterReceiver(receiver)
            } catch (e: IllegalArgumentException) {
                // Receiver not registered
            }
        } catch (e: Exception) {
            logger.w(e) { "Error stopping scan" }
        }
    }

    private fun refreshPairedDevices() {
        adapter?.bondedDevices?.let { bonded ->
            _pairedDevices.value = bonded.map { it.toDomain(isPaired = true) }
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