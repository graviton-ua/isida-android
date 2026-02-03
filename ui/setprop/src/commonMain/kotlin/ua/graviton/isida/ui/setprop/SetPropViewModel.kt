package ua.graviton.isida.ui.setprop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.whoppah.metrox.viewmodel.ViewModelAssistedFactory
import com.whoppah.metrox.viewmodel.ViewModelKey
import com.whoppah.metrox.viewmodel.ViewModelScope
import com.whoppah.util.AppCoroutineDispatchers
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesIntoMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ua.graviton.isida.data.protocol.commands.IsidaCommand
import ua.graviton.isida.data.protocol.commands.v1.UpdateSettingsCommandV1
import ua.graviton.isida.data.protocol.packets.StatusPacket
import ua.graviton.isida.data.protocol.packets.v1.StatusPacketV1
import ua.graviton.isida.domain.interactors.SendCommand
import ua.graviton.isida.domain.observers.ObserveStatus
import ua.graviton.isida.ui.setprop.models.propertyFromId

@AssistedInject
class SetPropViewModel(
    @Assisted private val id: String,
    dispatchers: AppCoroutineDispatchers,
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

    private val property = propertyFromId(id)
    private val waitingForData = MutableStateFlow<Boolean>(true)

    private val packets = observeStatus.flow.stateIn(
        scope = viewModelScope, started = SharingStarted.WhileSubscribed(0), initialValue = null,
    )

    val state: StateFlow<SetPropViewState> = waitingForData.map { waiting ->
        SetPropViewState(
            property = property,
            waitingForData = waiting,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SetPropViewState(property = property, waitingForData = waitingForData.value),
    )

    init {
        // Update property value fom StatusPacket
        viewModelScope.launch {
            packets.filterNotNull().take(1)
                .collect { packet ->
                    property.readValue(packet)
                    waitingForData.value = false
                }
        }

        // Clear error state if input is updated
        viewModelScope.launch(dispatchers.computation) { property.clearErrorOnInputUpdate() }
    }


    fun send() = viewModelScope.launch(Dispatchers.Default) {
        // validate prop before we send anything
        val isValid = property.validate()

        // If it's not valid error already been shown on the UI, we can silently return
        if (!isValid) return@launch

        //Here we should build and send command to device
        val snapshot = packets.value ?: return@launch
        val modifiedSnapshot = property.copyAndUpdate(snapshot)

        val cmd = prepareCommand(modifiedSnapshot)
            .onFailure { logger.w(it) { "Command preparation failed" } }
            .getOrNull() ?: return@launch

        sendCommand(cmd)
            .onSuccess { logger.d { "Command sent" } }
            .onFailure { logger.e(it) { "Command failed" } }
    }

    private fun prepareCommand(snapshot: StatusPacket): Result<IsidaCommand> = when (snapshot) {
        is StatusPacketV1 -> Result.success(
            UpdateSettingsCommandV1(
                spT0 = snapshot.spT0, spT1 = snapshot.spT1,
                spRh0 = snapshot.spRh0, spRh1 = snapshot.spRh1,
                state = snapshot.state,
                extendMode = snapshot.extendMode,
                relayMode = snapshot.relayMode,
                programm = snapshot.programm,
                minRun = snapshot.minRun, maxRun = snapshot.maxRun,
                period = snapshot.period,
                timer0 = snapshot.timer0, timer1 = snapshot.timer1,
                alarm0 = snapshot.alarm0, alarm1 = snapshot.alarm1,
                extOn0 = snapshot.extOn0, extOn1 = snapshot.extOn1,
                extOff0 = snapshot.extOff0, extOff1 = snapshot.extOff1,
                air0 = snapshot.air0, air1 = snapshot.air1,
                spCO2 = snapshot.spCO2,
                koffCurr = snapshot.koffCurr,
                hysteresis = snapshot.hysteresis,
                permission = snapshot.permission,
                zonaFlap = snapshot.zonaFlap,
                turnTime = snapshot.turnTime,
                waitCooling = snapshot.waitCooling,
                pkoff0 = snapshot.pkoff0, pkoff1 = snapshot.pkoff1,
                ikoff0 = snapshot.ikoff0, ikoff1 = snapshot.ikoff1,
                identif = snapshot.identif,
                ip0 = snapshot.ip0, ip1 = snapshot.ip1,
                ip2 = snapshot.ip2, ip3 = snapshot.ip3,
                nothing0 = snapshot.nothing0,
                nothing1 = snapshot.nothing1,
            )
        )

        else -> Result.failure(IllegalArgumentException("Unsupported packet type"))
    }
}