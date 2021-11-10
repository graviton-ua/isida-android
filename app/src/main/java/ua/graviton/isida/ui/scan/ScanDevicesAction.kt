package ua.graviton.isida.ui.scan

import android.bluetooth.BluetoothDevice

sealed class ScanDevicesAction {
    object NavigateUp : ScanDevicesAction()
    object StartScanClicked : ScanDevicesAction()
    object StopScanClicked : ScanDevicesAction()
    data class OnDeviceClicked(val device: BluetoothDevice) : ScanDevicesAction()

    object ScanningStarted : ScanDevicesAction()
    object ScanningStopped : ScanDevicesAction()

    object ClearDeviceList : ScanDevicesAction()
    data class AlreadyPairedDevices(val devices: List<BluetoothDevice>) : ScanDevicesAction()
    data class DeviceFound(val device: BluetoothDevice) : ScanDevicesAction()
}