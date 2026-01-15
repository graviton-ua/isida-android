package ua.graviton.isida.ui.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whoppah.metrox.viewmodel.ViewModelKey
import com.whoppah.metrox.viewmodel.ViewModelScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import ua.graviton.isida.data.bluetooth.DeviceScanner

@Inject
@ViewModelKey(ScanDevicesViewModel::class)
@ContributesIntoMap(ViewModelScope::class)
class ScanDevicesViewModel(
    private val scanner: DeviceScanner,
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
}