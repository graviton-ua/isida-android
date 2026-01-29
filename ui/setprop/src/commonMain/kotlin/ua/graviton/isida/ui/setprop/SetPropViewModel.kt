package ua.graviton.isida.ui.setprop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.whoppah.metrox.viewmodel.ViewModelAssistedFactory
import com.whoppah.metrox.viewmodel.ViewModelKey
import com.whoppah.metrox.viewmodel.ViewModelScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesIntoMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ua.graviton.isida.data.protocol.packets.v1.StatusPacketV1
import ua.graviton.isida.domain.interactors.SendUpdateSettingsCommand
import ua.graviton.isida.domain.models.DeviceProperty
import ua.graviton.isida.domain.models.getProperty
import ua.graviton.isida.domain.observers.ObserveStatus

@AssistedInject
class SetPropViewModel(
    @Assisted private val id: String,
    observeStatus: ObserveStatus,
    private val sendUpdateSettingsCommand: SendUpdateSettingsCommand,
) : ViewModel() {

    @AssistedFactory
    @ViewModelKey(SetPropViewModel::class)
    @ContributesIntoMap(ViewModelScope::class)
    interface Factory : ViewModelAssistedFactory {
        fun create(id: String): SetPropViewModel
    }

    private val logger by lazy { Logger.withTag("SetPropViewModel") }

    private val _events = MutableSharedFlow<SetPropEvent>()
    val events = _events.asSharedFlow()
    private val pendingActions = MutableSharedFlow<SetPropAction>()

    private val packets = observeStatus.flow.stateIn(
        scope = viewModelScope, started = SharingStarted.WhileSubscribed(0), initialValue = null,
    )
    private val property = packets.mapNotNull { packet ->
        when (packet) {
            is StatusPacketV1 -> packet.getProperty(id)
            else -> null
        }
    }.take(1).stateIn(
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
        logger.d { "id: $id" }
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
        val snapshot = packets.value ?: return@launch
        val prop = updatedProperty.value ?: property.value ?: return@launch

        sendUpdateSettingsCommand(snapshot, prop)
            .onSuccess { logger.d { "Command sent" } }
            .onFailure { logger.e(it) { "Command failed" } }
    }
}