package ua.graviton.isida.ui.scan

import androidx.compose.runtime.Immutable
import ua.graviton.isida.data.bluetooth.DiscoveredDevice

@Immutable
data class ScanDevicesViewState(
    val paired: List<DiscoveredDevice> = emptyList(),
    val found: List<DiscoveredDevice> = emptyList(),
    val isLoading: Boolean = false
) {
    companion object {
        val Empty = ScanDevicesViewState()
    }
}

sealed class ScanDevicesAction {
    object NavigateUp : ScanDevicesAction()
    object StartScanClicked : ScanDevicesAction()
    object StopScanClicked : ScanDevicesAction()

    // We pass the generic DiscoveredDevice, not the Android BluetoothDevice
    data class OnDeviceClicked(val device: DiscoveredDevice) : ScanDevicesAction()
}