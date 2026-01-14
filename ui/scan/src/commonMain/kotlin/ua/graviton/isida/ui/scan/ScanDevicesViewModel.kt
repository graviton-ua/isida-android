package ua.graviton.isida.ui.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whoppah.metrox.viewmodel.ViewModelKey
import com.whoppah.metrox.viewmodel.ViewModelScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ua.graviton.isida.data.bluetooth.DeviceScanner

@Inject
@ViewModelKey(ScanDevicesViewModel::class)
@ContributesIntoMap(ViewModelScope::class)
class ScanDevicesViewModel(
    private val scanner: DeviceScanner,
) : ViewModel() {
    private val pendingActions = MutableSharedFlow<ScanDevicesAction>()

    val state: StateFlow<ScanDevicesViewState> = combine(
        scanner.pairedDevices,
        scanner.foundDevices,
        scanner.isScanning
    ) { paired, found, scanning ->
        ScanDevicesViewState(
            paired = paired,
            found = found,
            isLoading = scanning,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ScanDevicesViewState.Empty,
    )

    init {
        // Auto-start scan if on JVM, or wait for permission on Android?
        // Usually better to let the UI trigger it after permission checks.

        viewModelScope.launch {
            pendingActions.collect { action ->
                when (action) {
                    is ScanDevicesAction.StartScanClicked -> scanner.startScan()
                    is ScanDevicesAction.StopScanClicked -> scanner.stopScan()
                    // OnDeviceClicked and NavigateUp are handled by the Screen/Navigation
                    else -> Unit
                }
            }
        }
    }

    override fun onCleared() {
        scanner.stopScan()
        super.onCleared()
    }

    fun submitAction(action: ScanDevicesAction) {
        viewModelScope.launch { pendingActions.emit(action) }
    }
}