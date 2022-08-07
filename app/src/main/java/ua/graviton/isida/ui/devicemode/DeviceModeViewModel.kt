package ua.graviton.isida.ui.devicemode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ua.graviton.isida.data.bl.IsidaCommands
import ua.graviton.isida.data.bl.IsidaCommands.DeviceMode
import ua.graviton.isida.data.bl.IsidaCommands.DeviceModeExtra
import ua.graviton.isida.domain.observers.ObserveDeviceData
import javax.inject.Inject

@HiltViewModel
class DeviceModeViewModel @Inject constructor(
    observeDeviceData: ObserveDeviceData
) : ViewModel() {
    val events = MutableSharedFlow<DeviceModeEvent>()
    private val pendingActions = MutableSharedFlow<DeviceModeAction>()

    private val deviceId = MutableStateFlow<Int?>(null)
    private val mode = MutableStateFlow<DeviceMode?>(null)
    private val modeExtras = MutableStateFlow<List<DeviceModeExtra>>(emptyList())

    val state: StateFlow<DeviceModeViewState> = combine(
        deviceId, mode, modeExtras
    ) { id, mode, extras ->
        DeviceModeViewState(
            deviceId = id,
            mode = mode,
            extras = extras,
            applyEnabled = id != null,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DeviceModeViewState.Empty,
    )

    init {
        // Fetch device cell number
        viewModelScope.launch {
            observeDeviceData.flow.mapNotNull { it?.cellId }.take(1).collect { deviceId.value = it }
        }

        // Fetch device state/mode/extras
        viewModelScope.launch {
            observeDeviceData.flow.filterNotNull().take(1)
                .map {
                    val state = it.state
                    val mode = when (state) {
                        state or DeviceMode.ENABLE.code -> DeviceMode.ENABLE
                        state or DeviceMode.ONLY_ROTATION.code -> DeviceMode.ONLY_ROTATION
                        else -> DeviceMode.DISABLE
                    }
                    val extras = if (mode == DeviceMode.ENABLE) {
                        val result = mutableListOf<DeviceModeExtra>()
                        if (state == state or DeviceModeExtra.EXTRA_1.code) result.add(DeviceModeExtra.EXTRA_1)
                        if (state == state or DeviceModeExtra.EXTRA_2.code) result.add(DeviceModeExtra.EXTRA_2)
                        if (state == state or DeviceModeExtra.EXTRA_3.code) result.add(DeviceModeExtra.EXTRA_3)
                        if (state == state or DeviceModeExtra.EXTRA_4.code) result.add(DeviceModeExtra.EXTRA_4)
                        result
                    } else {
                        emptyList()
                    }
                    mode to extras
                }
                .collect {
                    mode.value = it.first
                    modeExtras.value = it.second
                }
        }

        // Listen actions
        viewModelScope.launch {
            pendingActions.collect { action ->
                when (action) {
                    is DeviceModeAction.SelectMode -> {
                        mode.value = action.mode
                    }
                    is DeviceModeAction.ToggleExtra -> {
                        val current = modeExtras.value
                        modeExtras.value = when (current.contains(action.extra)) {
                            true -> current.filterNot { it == action.extra }
                            false -> current.toMutableList().apply { add(action.extra) }
                        }
                    }

                    is DeviceModeAction.ApplyMode -> send().also { it.join() }
                    else -> Unit
                }
            }
        }
    }

    fun submitAction(action: DeviceModeAction) {
        viewModelScope.launch { pendingActions.emit(action) }
    }


    private fun CoroutineScope.send() = launch {
        val device = deviceId.value ?: return@launch
        val mode = mode.value ?: return@launch
        events.emit(
            DeviceModeEvent.Send(
                command = IsidaCommands.deviceMode(device, mode, *modeExtras.value.toTypedArray())
            ) as DeviceModeEvent
        )
    }
}