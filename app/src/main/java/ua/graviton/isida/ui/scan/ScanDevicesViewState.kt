package ua.graviton.isida.ui.scan

import android.bluetooth.BluetoothDevice

data class ScanDevicesViewState(
    val paired: List<BluetoothDevice> = emptyList(),
    val found: List<BluetoothDevice> = emptyList(),
    val isLoading: Boolean = false
) {
    companion object {
        val Empty = ScanDevicesViewState()
        val Preview = ScanDevicesViewState()
    }
}