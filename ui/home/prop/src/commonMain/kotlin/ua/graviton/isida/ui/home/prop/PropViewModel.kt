package ua.graviton.isida.ui.home.prop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whoppah.common.resources.*
import com.whoppah.common.resources.ComposableString.Companion.composableString
import com.whoppah.metrox.viewmodel.ViewModelKey
import com.whoppah.metrox.viewmodel.ViewModelScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.*
import org.jetbrains.compose.resources.stringResource
import ua.graviton.isida.data.protocol.packets.v1.StatusPacketV1
import ua.graviton.isida.domain.observers.ObserveStatus
import java.util.Locale

@Inject
@ViewModelKey(PropViewModel::class)
@ContributesIntoMap(ViewModelScope::class)
class PropViewModel(
    observeStatus: ObserveStatus,
) : ViewModel() {
    private val packets = observeStatus.flow.stateIn(
        scope = viewModelScope, started = SharingStarted.WhileSubscribed(0), initialValue = null,
    )

    private val uiItems = packets.mapNotNull { packet ->
        when (packet) {
            is StatusPacketV1 -> packet.toItems()
            else -> null
        }
    }.onStart { emit(emptyList()) }

    val state: StateFlow<PropViewState> = uiItems.map { items ->
        PropViewState(
            items = items,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PropViewState.Init,
    )
}

private fun Float.format(): String = String.format(Locale.getDefault(), "%.1f", this)
private const val EMPTY_PLACEHOLDER = "--"

private fun StatusPacketV1?.toItems(): List<PropItem> = buildProps {
    item(
        id = "spT0",
        title = composableString { stringResource(Res.string.prop_spT0_lb) },
        value = composableString(this@toItems?.spT0) {
            this@toItems?.spT0?.format()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER
        }
    )
    item(
        id = "spT1",
        title = composableString { stringResource(Res.string.prop_spT1_lb) },
        value = composableString(this@toItems?.spT1) {
            this@toItems?.spT1?.format()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER
        }
    )
    item(
        id = "permission",
        title = composableString { stringResource(Res.string.prop_Rh_lb) },
        value = composableString(this@toItems?.permission) {
            this@toItems?.permission?.toString() ?: EMPTY_PLACEHOLDER
        }
    )
    item(
        id = "spRh1",
        title = composableString { stringResource(Res.string.prop_spRh1_lb) },
        value = composableString(this@toItems?.spRh1) {
            this@toItems?.spRh1?.format()?.let { stringResource(Res.string.prop_dimen_percent, it) } ?: EMPTY_PLACEHOLDER
        }
    )
    item(
        id = "spRh0",
        title = composableString { stringResource(Res.string.prop_spRh0_lb) },
        value = composableString(this@toItems?.spRh0) {
            this@toItems?.spRh0?.format()?.let { stringResource(Res.string.prop_dimen_percent, it) } ?: EMPTY_PLACEHOLDER
        }
    )
    item(
        id = "extendMode",
        title = composableString { stringResource(Res.string.prop_extMode_lb) },
        value = composableString(this@toItems?.extendMode) {
            this@toItems?.extendMode?.toString() ?: EMPTY_PLACEHOLDER
        }
    )
    item(
        id = "relayMode",
        title = composableString { stringResource(Res.string.prop_relMode_lb) },
        value = composableString(this@toItems?.relayMode) {
            this@toItems?.relayMode?.toString() ?: EMPTY_PLACEHOLDER
        }
    )
    item(
        id = "program",
        title = composableString { stringResource(Res.string.prop_program_lb) },
        value = composableString(this@toItems?.programm) {
            val programm = this@toItems?.programm
            when (programm) {
                1 -> "Tratat"
                2 -> "Tratat"
                3 -> "Tratat"
                null -> EMPTY_PLACEHOLDER
                else -> programm.toString()
            }
        }
    )
    item(
        id = "minRun",
        title = composableString { stringResource(Res.string.prop_minImpulse_lb) },
        value = composableString(this@toItems?.minRun) {
            this@toItems?.minRun?.toString()?.let { stringResource(Res.string.prop_dimen_sec, it) } ?: EMPTY_PLACEHOLDER
        }
    )
    item(
        id = "maxRun",
        title = composableString { stringResource(Res.string.prop_maxImpulse_lb) },
        value = composableString(this@toItems?.maxRun) {
            this@toItems?.maxRun?.toString()?.let { stringResource(Res.string.prop_dimen_sec, it) } ?: EMPTY_PLACEHOLDER
        }
    )
    item(
        id = "period",
        title = composableString { stringResource(Res.string.prop_repeatTime_lb) },
        value = composableString(this@toItems?.period) {
            this@toItems?.period?.toString()?.let { stringResource(Res.string.prop_dimen_sec, it) } ?: EMPTY_PLACEHOLDER
        }
    )
    item(
        id = "turnOff",
        title = composableString { stringResource(Res.string.prop_turnOff_lb) },
        value = composableString(this@toItems?.timer0) {
            this@toItems?.timer0?.toString()?.let { stringResource(Res.string.prop_dimen_min, it) } ?: EMPTY_PLACEHOLDER
        }
    )
    item(
        id = "turnOn",
        title = composableString { stringResource(Res.string.prop_turnOn_lb) },
        value = composableString(this@toItems?.timer1) {
            this@toItems?.timer1?.toString()?.let { stringResource(Res.string.prop_dimen_min, it) } ?: EMPTY_PLACEHOLDER
        }
    )
    item(
        id = "alarm0",
        title = composableString { stringResource(Res.string.prop_alarm0_lb) },
        value = composableString(this@toItems?.alarm0) {
            this@toItems?.alarm0?.toString()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER
        }
    )
    item(
        id = "alarm1",
        title = composableString { stringResource(Res.string.prop_alarm1_lb) },
        value = composableString(this@toItems?.alarm1) {
            this@toItems?.alarm1?.toString()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER
        }
    )
    item(
        id = "extOn0",
        title = composableString { stringResource(Res.string.prop_extOn0_lb) },
        value = composableString(this@toItems?.extOn0) {
            this@toItems?.extOn0?.toString()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER
        }
    )
    item(
        id = "extOn1",
        title = composableString { stringResource(Res.string.prop_extOn1_lb) },
        value = composableString(this@toItems?.extOn1) {
            this@toItems?.extOn1?.toString()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER
        }
    )
    item(
        id = "extOff0",
        title = composableString { stringResource(Res.string.prop_extOff0_lb) },
        value = composableString(this@toItems?.extOff0) {
            this@toItems?.extOff0?.toString()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER
        }
    )
    item(
        id = "extOff1",
        title = composableString { stringResource(Res.string.prop_extOff1_lb) },
        value = composableString(this@toItems?.extOff1) {
            this@toItems?.extOff1?.toString()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER
        }
    )
    item(
        id = "air0",
        title = composableString { stringResource(Res.string.prop_air0_lb) },
        value = composableString(this@toItems?.air0) {
            this@toItems?.air0?.toString()?.let { stringResource(Res.string.prop_dimen_min, it) } ?: EMPTY_PLACEHOLDER
        }
    )
    item(
        id = "air1",
        title = composableString { stringResource(Res.string.prop_air1_lb) },
        value = composableString(this@toItems?.air1) {
            this@toItems?.air1?.toString()?.let { stringResource(Res.string.prop_dimen_sec, it) } ?: EMPTY_PLACEHOLDER
        }
    )
    item(
        id = "spCO2",
        title = composableString { stringResource(Res.string.prop_CO2_lb) },
        value = composableString(this@toItems?.spCO2) {
            this@toItems?.spCO2?.toString()?.let { stringResource(Res.string.prop_dimen_ppm, it) } ?: EMPTY_PLACEHOLDER
        }
    )
    item(
        id = "koffCurr",
        title = composableString { stringResource(Res.string.prop_koffCurr_lb) },
        value = composableString(this@toItems?.koffCurr) {
            this@toItems?.koffCurr?.toString() ?: EMPTY_PLACEHOLDER
        }
    )
    item(
        id = "hysteresis",
        title = composableString { stringResource(Res.string.prop_Hysteresis_lb) },
        value = composableString(this@toItems?.hysteresis) {
            this@toItems?.hysteresis?.toString()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER
        }
    )
    // item(
    //     id = "zonaFlap",
    //     title = composableString { stringResource(Res.string.prop_zoneFlap_lb) },
    //     value = composableString(this@toItems?.zonaFlap) {
    //         this@toItems?.zonaFlap?.toString()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER
    //     }
    // )
    item(
        id = "turnTime",
        title = composableString { stringResource(Res.string.prop_turnTime_lb) },
        value = composableString(this@toItems?.turnTime) {
            this@toItems?.turnTime?.toString()?.let { stringResource(Res.string.prop_dimen_sec, it) } ?: EMPTY_PLACEHOLDER
        }
    )
    item(
        id = "waitCooling",
        title = composableString { stringResource(Res.string.prop_waitCooling_lb) },
        value = composableString(this@toItems?.waitCooling) {
            this@toItems?.waitCooling?.toString()?.let { stringResource(Res.string.prop_dimen_sec, it) } ?: EMPTY_PLACEHOLDER
        }
    )
    item(
        id = "pkoff0",
        title = composableString { stringResource(Res.string.prop_pkoff0_lb) },
        value = composableString(this@toItems?.pkoff0) {
            this@toItems?.pkoff0?.toString() ?: EMPTY_PLACEHOLDER
        }
    )
    item(
        id = "pkoff1",
        title = composableString { stringResource(Res.string.prop_pkoff1_lb) },
        value = composableString(this@toItems?.pkoff1) {
            this@toItems?.pkoff1?.toString() ?: EMPTY_PLACEHOLDER
        }
    )
    item(
        id = "ikoff0",
        title = composableString { stringResource(Res.string.prop_ikoff0_lb) },
        value = composableString(this@toItems?.ikoff0) {
            this@toItems?.ikoff0?.toString() ?: EMPTY_PLACEHOLDER
        }
    )
    item(
        id = "ikoff1",
        title = composableString { stringResource(Res.string.prop_ikoff1_lb) },
        value = composableString(this@toItems?.ikoff1) {
            this@toItems?.ikoff1?.toString() ?: EMPTY_PLACEHOLDER
        }
    )
    item(
        id = "identif",
        title = composableString { stringResource(Res.string.prop_identif_lb) },
        value = composableString(this@toItems?.node) {
            this@toItems?.node?.toString() ?: EMPTY_PLACEHOLDER
        }
    )
}