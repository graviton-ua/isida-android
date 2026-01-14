package ua.graviton.isida.data.bluetooth

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class JvmDeviceScanner : DeviceScanner {

    private val _foundDevices = MutableStateFlow<List<DiscoveredDevice>>(emptyList())

    // Serial ports don't really have a "scanning" state like Bluetooth discovery
    override val isScanning = MutableStateFlow(false)
    override val pairedDevices = MutableStateFlow(emptyList<DiscoveredDevice>())
    override val foundDevices = _foundDevices.asStateFlow()

    override fun startScan() {
        isScanning.value = true
        // FUTURE: jSerialComm Logic
        // val ports = com.fazecast.jSerialComm.SerialPort.getCommPorts()
        // _foundDevices.value = ports.map { DiscoveredDevice(it.systemPortName, DeviceAddress(it.systemPortName)) }

        // Mock for now
        _foundDevices.value = listOf(
            DiscoveredDevice("COM3 - USB Serial", DeviceAddress("COM3")),
            DiscoveredDevice("/dev/ttyUSB0", DeviceAddress("/dev/ttyUSB0"))
        )
        isScanning.value = false
    }

    override fun stopScan() {
        isScanning.value = false
    }
}