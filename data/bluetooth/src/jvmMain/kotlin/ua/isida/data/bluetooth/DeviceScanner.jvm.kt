package ua.isida.data.bluetooth

import com.fazecast.jSerialComm.SerialPort
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ua.isida.data.bluetooth.DeviceAddress
import ua.isida.data.bluetooth.DeviceScanner
import ua.isida.data.bluetooth.DiscoveredDevice
import ua.isida.util.AppCoroutineDispatchers

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
/**
 * JVM/Desktop implementation of [DeviceScanner].
 * Scans for available Serial ports (COM ports) rather than strictly Bluetooth devices.
 * Emulates "pairing" by listing all found ports as "paired".
 */
class JvmDeviceScanner(
    dispatchers: AppCoroutineDispatchers,
) : DeviceScanner {

    private val _isScanning = MutableStateFlow(false)
    override val isScanning = _isScanning.asStateFlow()

    private val _pairedDevices = MutableStateFlow<List<DiscoveredDevice>>(emptyList())
    override val pairedDevices = _pairedDevices.asStateFlow()

    override val foundDevices = MutableStateFlow(emptyList<DiscoveredDevice>())
    override val error = MutableStateFlow<String?>(null)
    override val isBluetoothEnabled = MutableStateFlow(true)

    // Scope tied to this instance
    private val scope = CoroutineScope(dispatchers.io + SupervisorJob())
    private var scanJob: Job? = null

    /**
     * Starts polling for available COM ports.
     * Runs for 10 seconds, refreshing the list every 2 seconds.
     */
    override fun startScan() {
        stopScan()
        _isScanning.value = true

        scanJob = scope.launch {
            try {
                // Poll ports for 10 seconds
                val endTime = System.currentTimeMillis() + 10_000
                while (isActive && System.currentTimeMillis() < endTime) {
                    val ports = SerialPort.getCommPorts()
                    val devices = ports.map { port ->
                        DiscoveredDevice(
                            name = port.descriptivePortName ?: port.systemPortName,
                            address = DeviceAddress(port.systemPortName),
                            isPaired = true
                        )
                    }
                    _pairedDevices.value = devices
                    delay(2000)
                }
            } finally {
                _isScanning.value = false
            }
        }
    }

    /**
     * Stops the active polling job.
     */
    override fun stopScan() {
        scanJob?.cancel()
        _isScanning.value = false
    }

    /**
     * Cancels the scope and stops scanning.
     */
    override fun cleanup() {
        stopScan()
        scope.cancel() // Kill the scope when ViewModel dies
    }
}