package ua.graviton.isida.ui.setprop

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ua.graviton.isida.domain.models.DeviceProperty
import ua.graviton.isida.domain.models.getProperty
import ua.graviton.isida.domain.observers.ObserveDeviceData
import ua.graviton.isida.ui.setprop.SetPropAction.UpdateState
import ua.graviton.isida.ui.setprop.SetPropViewState.InputState
import javax.inject.Inject

@HiltViewModel
class SetPropViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    observeDeviceData: ObserveDeviceData,
) : ViewModel() {
    private val _events = MutableSharedFlow<SetPropEvent>()
    val events = _events.asSharedFlow()
    private val pendingActions = MutableSharedFlow<SetPropAction>()

    private val _id = MutableStateFlow<String>(savedStateHandle["id"]!!)

    private val deviceData = observeDeviceData.flow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = null,
    )
    private val property = combine(_id, deviceData) { id, data -> data?.getProperty(id) }.take(1).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = null,
    )

    private val _inputState = MutableStateFlow<InputState>(InputState.Unknown)
    private val inputState = combine(property, _inputState) { property, state ->
        when (state) {
            is InputState.Unknown -> property?.toInputState() ?: state
            else -> state
        }
    }

    val state: StateFlow<SetPropViewState> = combine(property, inputState) { property, input ->
        if (property == null) return@combine SetPropViewState.NoData
        when (property) {
            DeviceProperty.Unknown -> SetPropViewState.NotFound
            else -> SetPropViewState.Success(
                propId = property.id,
                inputState = input
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SetPropViewState.Init,
    )

    init {
        // Listen actions
        viewModelScope.launch {
            pendingActions.collect { action ->
                when (action) {
                    is SetPropAction.Update -> update(action.state).join()
                    is SetPropAction.Send -> Unit
                    else -> Unit
                }
            }
        }
    }

    fun submitAction(action: SetPropAction) {
        viewModelScope.launch { pendingActions.emit(action) }
    }


    private fun <T> DeviceProperty<T>.toInputState(): InputState {
        return when (value) {
            is Int -> InputState.NumberInput(value.toString())
            is Float -> InputState.NumberInput(value.toString())
            else -> InputState.Unknown
        }
    }

    private fun CoroutineScope.update(updateState: UpdateState) = launch(Dispatchers.Default) {
        _inputState.update {
            when (updateState) {
                is UpdateState.NumberInput -> InputState.NumberInput(value = updateState.value)
            }
        }
    }

    private fun CoroutineScope.send() = launch(Dispatchers.Default) {
        //Here we should build and send command to device
    }
}