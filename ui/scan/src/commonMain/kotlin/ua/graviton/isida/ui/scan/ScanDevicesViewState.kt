package ua.graviton.isida.ui.scan

import androidx.compose.runtime.Immutable
import ua.isida.data.bluetooth.DiscoveredDevice

@Immutable
data class ScanDevicesViewState(
    val isBluetoothEnabled: Boolean = false,
    val paired: List<DiscoveredDevice> = emptyList(),
    val found: List<DiscoveredDevice> = emptyList(),
    val isScanning: Boolean = false,
) {
    companion object {
        val Empty = ScanDevicesViewState()
    }
}