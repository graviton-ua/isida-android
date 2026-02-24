package ua.graviton.isida.ui.devicemode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ua.isida.metrox.viewmodel.ViewModelKey
import ua.isida.metrox.viewmodel.ViewModelScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ua.isida.data.protocol.DeviceMode
import ua.isida.data.protocol.DeviceModeExtra
import ua.isida.data.protocol.commands.v1.DeviceModeCommandV1
import ua.isida.data.protocol.packets.v1.StatusPacketV1
import ua.graviton.isida.domain.interactors.SendCommand
import ua.graviton.isida.domain.observers.ObserveStatus

@Inject
@ViewModelKey(DeviceModeViewModel::class)
@ContributesIntoMap(ViewModelScope::class)
class DeviceModeViewModel(
    observeStatus: ObserveStatus,
    private val sendCommand: SendCommand,
) : ViewModel() {
    private val _events = Channel<DeviceModeViewEvent>(Channel.BUFFERED)
    val events: Flow<DeviceModeViewEvent> = _events.receiveAsFlow()

    private val pendingActions = Channel<DeviceModeAction>(
        capacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

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
            observeStatus.flow.mapNotNull { packet ->
                when (packet) {
                    is StatusPacketV1 -> packet.node
                    else -> null
                }
            }.take(1).collect { deviceId.value = it }
        }

        // Fetch device state/mode/extras
        viewModelScope.launch {
            observeStatus.flow.filterNotNull().take(1)
                .mapNotNull { packet ->
                    when (packet) {
                        is StatusPacketV1 -> packet.state
                        else -> null
                    }
                }
                .map { state ->
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
            pendingActions.receiveAsFlow().collect { action ->
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
                }
            }
        }
    }

    fun submitAction(action: DeviceModeAction) {
        pendingActions.trySend(action)
    }


    private fun CoroutineScope.send() = launch {
        val mode = mode.value ?: return@launch

        val extras = modeExtras.value

        val commandValue = when (mode) {
            DeviceMode.ENABLE -> extras.map { it.code }.foldRight(initial = mode.code) { left, right -> left or right }
            else -> mode.code
        }

        sendCommand(DeviceModeCommandV1(mode = commandValue))
            .onSuccess { _events.send(DeviceModeViewEvent.OnApplied) }
    }
}