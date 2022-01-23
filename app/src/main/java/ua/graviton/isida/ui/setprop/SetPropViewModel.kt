package ua.graviton.isida.ui.setprop

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ua.graviton.isida.domain.models.DeviceProperty
import ua.graviton.isida.domain.models.getProperty
import ua.graviton.isida.domain.observers.ObserveDeviceData
import ua.graviton.isida.ui.setprop.SetPropViewState.InputState
import javax.inject.Inject

@HiltViewModel
class SetPropViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    observeDeviceData: ObserveDeviceData,
) : ViewModel() {
    val events = MutableSharedFlow<SetPropEvent>()
    private val pendingActions = MutableSharedFlow<SetPropAction>()

    private val _id = MutableStateFlow<String>(savedStateHandle["id"]!!)
    private val property = combine(_id, observeDeviceData.flow) { id, data -> data?.getProperty(id) }.distinctUntilChanged()
    private val _inputState = MutableStateFlow<InputState>(InputState.Unknown)
    private val inputState = combine(property, _inputState) { property, state ->
        when (state) {
            is InputState.Unknown -> property?.toInputState() ?: state
            else -> state
        }
    }

    val state: StateFlow<SetPropViewState> = combine(_id, property, inputState) { id, property, input ->
        if (property != null) {
            when (property) {
                DeviceProperty.Unknown -> SetPropViewState.NotFound
                else -> SetPropViewState.Success(
                    propId = property.info.id,
                    inputState = input
                )
            }
        } else SetPropViewState.NoData
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
            is Int -> InputState.IntProperty(value)
            is Float -> InputState.FloatProperty(value)
            else -> InputState.Unknown
        }
    }
}