package ua.graviton.isida.ui.scan

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ua.graviton.isida.ui.utils.ObservableLoadingCounter
import javax.inject.Inject

@HiltViewModel
class ScanDevicesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val loadingState = ObservableLoadingCounter()
    private val pendingActions = MutableSharedFlow<ScanDevicesAction>()

    private val scanning = MutableStateFlow(false)
    private val paired = MutableStateFlow(emptyList<BluetoothDevice>())
    private val found = MutableStateFlow(emptySet<BluetoothDevice>())

    val state: StateFlow<ScanDevicesViewState> = combine(
        paired, found, scanning, loadingState.observable
    ) { paired, found, scanning, loading ->
        ScanDevicesViewState(
            paired = paired,
            found = found.toList(),
            isLoading = loading || scanning,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ScanDevicesViewState.Empty,
    )

    init {
        viewModelScope.launch {
            pendingActions.collect { action ->
                when (action) {
                    is ScanDevicesAction.ScanningStarted -> with(scanning) { value = true }
                    is ScanDevicesAction.ScanningStopped -> with(scanning) { value = false }
                    is ScanDevicesAction.ClearDeviceList -> {
                        paired.value = emptyList()
                        found.value = emptySet()
                    }
                    is ScanDevicesAction.AlreadyPairedDevices -> with(paired) { value = action.devices }
                    is ScanDevicesAction.DeviceFound -> with(found) {
                        value = value.toMutableSet().apply { add(action.device) }
                    }
                    else -> Unit
                }
            }
        }
    }

    fun submitAction(action: ScanDevicesAction) {
        viewModelScope.launch { pendingActions.emit(action) }
    }
}