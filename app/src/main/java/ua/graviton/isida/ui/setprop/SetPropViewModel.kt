package ua.graviton.isida.ui.setprop

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ua.graviton.isida.data.bl.IsidaCommands
import ua.graviton.isida.domain.models.DeviceProperty
import ua.graviton.isida.domain.models.getProperty
import ua.graviton.isida.domain.observers.ObserveDeviceData
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
    private val updatedProperty = MutableStateFlow<DeviceProperty<*>?>(null)

    val state: StateFlow<SetPropViewState> = property.map { property ->
        if (property == null) return@map SetPropViewState.NoData
        when (property) {
            DeviceProperty.Unknown -> SetPropViewState.NotFound
            else -> SetPropViewState.Success(
                property = property
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
                    is SetPropAction.UpdateProperty -> with(updatedProperty) { value = action.property }
                    is SetPropAction.Send -> send()
                    else -> Unit
                }
            }
        }
    }

    fun submitAction(action: SetPropAction) {
        viewModelScope.launch { pendingActions.emit(action) }
    }


    private fun CoroutineScope.send() = launch(Dispatchers.Default) {
        //Here we should build and send command to device
        val dataSnapshot = deviceData.value ?: return@launch
        val device = dataSnapshot.deviceNumber
        val prop = updatedProperty.value ?: property.value ?: return@launch
        _events.emit(
            SetPropEvent.Send(
                command = IsidaCommands.updateProperties(device, dataSnapshot, prop)
            )
        )
    }
}