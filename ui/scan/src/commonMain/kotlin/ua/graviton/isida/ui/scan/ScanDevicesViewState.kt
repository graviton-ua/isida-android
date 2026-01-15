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