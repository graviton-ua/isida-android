package ua.graviton.isida.data.bluetooth

import com.fazecast.jSerialComm.SerialPort
import com.whoppah.util.AppCoroutineDispatchers
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class JvmDeviceScanner(
    dispatchers: AppCoroutineDispatchers,
) : DeviceScanner {

    private val _isScanning = MutableStateFlow(false)
    override val isScanning = _isScanning.asStateFlow()

    private val _pairedDevices = MutableStateFlow<List<DiscoveredDevice>>(emptyList())
    override val pairedDevices = _pairedDevices.asStateFlow() // On JVM, mapped ports act like paired devices

    // JVM jSerialComm doesn't really "find" new devices via radio, it only sees ports.
    // So foundDevices will always be empty, everything goes into pairedDevices (Mapped Ports).
    override val foundDevices = MutableStateFlow(emptyList<DiscoveredDevice>())

    override val error = MutableStateFlow<String?>(null)

    private var scanJob: Job? = null
    private val scope = CoroutineScope(dispatchers.io + SupervisorJob())

    override fun startScan() {
        stopScan()
        _isScanning.value = true

        // Emulate scanning behavior by polling ports periodically
        scanJob = scope.launch {
            try {
                // Poll for 10 seconds or until stopped
                repeat(5) {
                    val ports = SerialPort.getCommPorts()
                    val devices = ports.map { port ->
                        DiscoveredDevice(
                            name = port.descriptivePortName ?: port.systemPortName,
                            address = DeviceAddress(port.systemPortName),
                            isPaired = true // Treat OS mapped ports as paired
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

    override fun stopScan() {
        scanJob?.cancel()
        _isScanning.value = false
    }
}