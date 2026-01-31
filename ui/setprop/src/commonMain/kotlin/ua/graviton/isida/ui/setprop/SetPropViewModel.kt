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
import ua.graviton.isida.data.protocol.commands.v1.UpdateSettingsCommandV1
import ua.graviton.isida.data.protocol.packets.StatusPacket
import ua.graviton.isida.data.protocol.packets.v1.StatusPacketV1
import ua.graviton.isida.domain.interactors.SendCommand
import ua.graviton.isida.ui.setprop.models.DeviceProperty
import ua.graviton.isida.ui.setprop.models.getProperty
import ua.graviton.isida.domain.observers.ObserveStatus

@AssistedInject
class SetPropViewModel(
    @Assisted private val id: String,
    observeStatus: ObserveStatus,
    private val sendCommand: SendCommand,
) : ViewModel() {

    @AssistedFactory
    @ViewModelKey(SetPropViewModel::class)
    @ContributesIntoMap(ViewModelScope::class)
    interface Factory : ViewModelAssistedFactory {
        fun create(id: String): SetPropViewModel
    }

    private val logger by lazy { Logger.withTag("SetPropViewModel") }

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

        val cmd = prepareCommand(snapshot, prop)
            .onFailure { logger.w(it) { "Command preparation failed" } }
            .getOrNull() ?: return@launch

        sendCommand(cmd)
            .onSuccess { logger.d { "Command sent" } }
            .onFailure { logger.e(it) { "Command failed" } }
    }

    private fun prepareCommand(deviceDataSnapshot: StatusPacket, vararg props: DeviceProperty<*>): Result<UpdateSettingsCommandV1> {
        val snapshot = when (deviceDataSnapshot) {
            is StatusPacketV1 -> deviceDataSnapshot
            else -> return Result.failure(IllegalArgumentException("Unsupported packet type"))
        }

        return Result.success(
            UpdateSettingsCommandV1(
                spT0 = props.findIsInstance<DeviceProperty.SpT0>()?.value ?: snapshot.spT0,
                spT1 = props.findIsInstance<DeviceProperty.SpT1>()?.value ?: snapshot.spT1,
                spRh0 = props.findIsInstance<DeviceProperty.SpRh0>()?.value ?: snapshot.spRh0,
                spRh1 = props.findIsInstance<DeviceProperty.SpRh1>()?.value ?: snapshot.spRh1,
                state = props.findIsInstance<DeviceProperty.State>()?.value ?: snapshot.state,
                extendMode = props.findIsInstance<DeviceProperty.ExtendMode>()?.value ?: snapshot.extendMode,
                relayMode = props.findIsInstance<DeviceProperty.RelayMode>()?.value ?: snapshot.relayMode,
                programm = props.findIsInstance<DeviceProperty.Program>()?.value ?: snapshot.programm,

                minRun = props.findIsInstance<DeviceProperty.MinRun>()?.value ?: snapshot.minRun,
                maxRun = props.findIsInstance<DeviceProperty.MaxRun>()?.value ?: snapshot.maxRun,
                period = props.findIsInstance<DeviceProperty.Period>()?.value ?: snapshot.period,
                timer0 = props.findIsInstance<DeviceProperty.Timer0>()?.value ?: snapshot.timer0,
                timer1 = props.findIsInstance<DeviceProperty.Timer1>()?.value ?: snapshot.timer1,
                alarm0 = props.findIsInstance<DeviceProperty.Alarm0>()?.value ?: snapshot.alarm0,
                alarm1 = props.findIsInstance<DeviceProperty.Alarm1>()?.value ?: snapshot.alarm1,
                extOn0 = props.findIsInstance<DeviceProperty.ExtOn0>()?.value ?: snapshot.extOn0,
                extOn1 = props.findIsInstance<DeviceProperty.ExtOn1>()?.value ?: snapshot.extOn1,
                extOff0 = props.findIsInstance<DeviceProperty.ExtOff0>()?.value ?: snapshot.extOff0,
                extOff1 = props.findIsInstance<DeviceProperty.ExtOff1>()?.value ?: snapshot.extOff1,
                air0 = props.findIsInstance<DeviceProperty.Air0>()?.value ?: snapshot.air0,
                air1 = props.findIsInstance<DeviceProperty.Air1>()?.value ?: snapshot.air1,
                spCO2 = props.findIsInstance<DeviceProperty.SpCO2>()?.value ?: snapshot.spCO2,
                koffCurr = snapshot.koffCurr,
                hysteresis = props.findIsInstance<DeviceProperty.Hysteresis>()?.value ?: snapshot.hysteresis,
                zonaFlap = snapshot.zonaFlap,
                turnTime = props.findIsInstance<DeviceProperty.TurnTime>()?.value ?: snapshot.turnTime,
                waitCooling = snapshot.waitCooling,
                pkoff0 = props.findIsInstance<DeviceProperty.Pkoff0>()?.value ?: snapshot.pkoff0,
                pkoff1 = props.findIsInstance<DeviceProperty.Pkoff1>()?.value ?: snapshot.pkoff1,
                ikoff0 = props.findIsInstance<DeviceProperty.Ikoff0>()?.value ?: snapshot.ikoff0,
                ikoff1 = props.findIsInstance<DeviceProperty.Ikoff1>()?.value ?: snapshot.ikoff1,
                identif = props.findIsInstance<DeviceProperty.Identif>()?.value ?: snapshot.identif,
                ip0 = snapshot.ip0,
                ip1 = snapshot.ip1,
                ip2 = snapshot.ip2,
                ip3 = snapshot.ip3,
                nothing0 = snapshot.nothing0,
                nothing1 = snapshot.nothing1,
            )
        )
    }

    private inline fun <reified R> Array<*>.findIsInstance(): R? = filterIsInstance<R>().firstOrNull()
}