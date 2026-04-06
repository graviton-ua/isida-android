package ua.isida.ui.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ua.isida.data.bluetooth.DeviceAddress
import ua.isida.data.bluetooth.DeviceScanner
import ua.isida.domain.bluetooth.DeviceConnectionManager

@Inject
@ViewModelKey(ScanDevicesViewModel::class)
@ContributesIntoMap(AppScope::class)
class ScanDevicesViewModel(
    private val scanner: DeviceScanner,
    private val connectionManager: DeviceConnectionManager,
) : ViewModel() {

    val state: StateFlow<ScanDevicesViewState> = combine(
        scanner.isBluetoothEnabled,
        scanner.pairedDevices,
        scanner.foundDevices,
        scanner.isScanning
    ) { isEnabled, paired, found, scanning ->
        ScanDevicesViewState(
            isBluetoothEnabled = isEnabled,
            paired = paired,
            found = found,
            isScanning = scanning,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ScanDevicesViewState.Empty,
    )

    override fun onCleared() {
        scanner.cleanup()
        super.onCleared()
    }

    fun startScan() = scanner.startScan()
    fun stopScan() = scanner.stopScan()

    fun selectDevice(address: DeviceAddress) {
        stopScan()
        viewModelScope.launch { connectionManager.connect(address) }
    }
}