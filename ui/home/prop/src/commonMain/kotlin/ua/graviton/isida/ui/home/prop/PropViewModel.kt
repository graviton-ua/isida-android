package ua.graviton.isida.ui.home.prop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whoppah.common.resources.*
import com.whoppah.metrox.viewmodel.ViewModelKey
import com.whoppah.metrox.viewmodel.ViewModelScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import ua.graviton.isida.data.protocol.packets.v1.StatusPacketV1
import ua.graviton.isida.domain.observers.ObserveStatus
import ua.graviton.isida.ui.home.prop.PropItem.Title
import ua.graviton.isida.ui.home.prop.PropItem.Value

@Inject
@ViewModelKey(PropViewModel::class)
@ContributesIntoMap(ViewModelScope::class)
class PropViewModel(
    observeStatus: ObserveStatus,
) : ViewModel() {
    private val pendingActions = MutableSharedFlow<PropAction>()

    private val packets = observeStatus.flow.stateIn(
        scope = viewModelScope, started = SharingStarted.WhileSubscribed(0), initialValue = null,
    )

    private val uiItems = packets.mapNotNull { packet ->
        when (packet) {
            is StatusPacketV1 -> packet.toItems()
            else -> null
        }
    }.onStart {
        // val testItems = listOf(
        //     PropItem(
        //         id = "spT0",
        //         title = Title.Text("[TEST] SpT0"),
        //         value = Value.Data(10f) { it?.format()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        //     ),
        //     PropItem(
        //         id = "spT1",
        //         title = Title.Text("[TEST] SpT1"),
        //         value = Value.Data(10f) { it?.format()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        //     ),
        //     PropItem(
        //         id = "spRh0",
        //         title = Title.Text("[TEST] spRh0"),
        //         value = Value.Data(10f) { it?.format()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        //     ),
        //     PropItem(
        //         id = "minRun",
        //         title = Title.Text("[TEST] Min Run"),
        //         value = Value.Data(10f) { it?.format()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        //     ),
        //     PropItem(
        //         id = "relayMode",
        //         title = Title.Text("[TEST] relayMode"),
        //         value = Value.Data(10f) { it?.format()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        //     ),
        // )
        // emit(testItems)//emptyList())
        emit(emptyList())
    }

    val state: StateFlow<PropViewState> = uiItems.map { items ->
        PropViewState(
            items = items,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PropViewState.Init,
    )

    init {
        viewModelScope.launch {
            pendingActions.collect { action ->
                when (action) {
                    //is PropAction.RefreshCart -> observeShopCart(Unit)
                    else -> Unit
                }
            }
        }
    }

    internal fun submitAction(action: PropAction) {
        viewModelScope.launch { pendingActions.emit(action) }
    }
}

private fun Float.format(): String = String.format("%.1f", this)
private const val EMPTY_PLACEHOLDER = "--"

private fun StatusPacketV1?.toItems(): List<PropItem> {
    return listOf(
        PropItem(
            id = "spT0",
            title = Title.ResId(Res.string.prop_spT0_lb),
            value = Value.Data(this?.spT0) { it?.format()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "spT1",
            title = Title.ResId(Res.string.prop_spT1_lb),
            value = Value.Data(this?.spT1) { it?.format()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "permission",
            title = Title.ResId(Res.string.prop_Rh_lb),
            value = Value.Data(this?.permission) { it?.toString() ?: EMPTY_PLACEHOLDER  },
        ),
        PropItem(
            id = "spRh1",
            title = Title.ResId(Res.string.prop_spRh1_lb),
            value = Value.Data(this?.spRh1) { it?.format()?.let { stringResource(Res.string.prop_dimen_percent, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "spRh0",
            title = Title.ResId(Res.string.prop_spRh0_lb),
            value = Value.Data(this?.spRh0) { it?.format()?.let { stringResource(Res.string.prop_dimen_percent, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "extendMode",
            title = Title.ResId(Res.string.prop_extMode_lb),
            value = Value.Data(this?.extendMode) { it?.toString() ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "relayMode",
            title = Title.ResId(Res.string.prop_relMode_lb),
            value = Value.Data(this?.relayMode) { it?.toString() ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "program",
            title = Title.ResId(Res.string.prop_program_lb),
            value = Value.Data(this?.programm) { it?.toString() ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "minRun",
            title = Title.ResId(Res.string.prop_minImpulse_lb),
            value = Value.Data(this?.minRun) { it?.toString()?.let { stringResource(Res.string.prop_dimen_sec, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "maxRun",
            title = Title.ResId(Res.string.prop_maxImpulse_lb),
            value = Value.Data(this?.maxRun) { it?.toString()?.let { stringResource(Res.string.prop_dimen_sec, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "period",
            title = Title.ResId(Res.string.prop_repeatTime_lb),
            value = Value.Data(this?.period) { it?.toString()?.let { stringResource(Res.string.prop_dimen_sec, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "turnOff",
            title = Title.ResId(Res.string.prop_turnOff_lb),
            value = Value.Data(this?.timer0) { it?.toString()?.let { stringResource(Res.string.prop_dimen_min, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "turnOn",
            title = Title.ResId(Res.string.prop_turnOn_lb),
            value = Value.Data(this?.timer1) { it?.toString()?.let { stringResource(Res.string.prop_dimen_min, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "alarm0",
            title = Title.ResId(Res.string.prop_alarm0_lb),
            value = Value.Data(this?.alarm0) { it?.toString()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "alarm1",
            title = Title.ResId(Res.string.prop_alarm1_lb),
            value = Value.Data(this?.alarm1) { it?.toString()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "extOn0",
            title = Title.ResId(Res.string.prop_extOn0_lb),
            value = Value.Data(this?.extOn0) { it?.toString()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "extOn1",
            title = Title.ResId(Res.string.prop_extOn1_lb),
            value = Value.Data(this?.extOn1) { it?.toString()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "extOff0",
            title = Title.ResId(Res.string.prop_extOff0_lb),
            value = Value.Data(this?.extOff0) { it?.toString()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "extOff1",
            title = Title.ResId(Res.string.prop_extOff1_lb),
            value = Value.Data(this?.extOff1) { it?.toString()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "air0",
            title = Title.ResId(Res.string.prop_air0_lb),
            value = Value.Data(this?.air0) { it?.toString()?.let { stringResource(Res.string.prop_dimen_min, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "air1",
            title = Title.ResId(Res.string.prop_air1_lb),
            value = Value.Data(this?.air1) { it?.toString()?.let { stringResource(Res.string.prop_dimen_sec, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "spCO2",
            title = Title.ResId(Res.string.prop_CO2_lb),
            value = Value.Data(this?.spCO2) { it?.toString()?.let { stringResource(Res.string.prop_dimen_ppm, it) } ?: EMPTY_PLACEHOLDER },
        ),
       PropItem(
           id = "koffCurr",
           title = Title.ResId(Res.string.prop_koffCurr_lb),
           value = Value.Data(this?.koffCurr) { it?.toString() ?: EMPTY_PLACEHOLDER },
       ),
        PropItem(
            id = "hysteresis",
            title = Title.ResId(Res.string.prop_Hysteresis_lb),
            value = Value.Data(this?.hysteresis) { it?.toString()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "zonaFlap",
            title = Title.ResId(Res.string.prop_zoneFlap_lb),
            value = Value.Data(this?.zonaFlap) { it?.toString()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "turnTime",
            title = Title.ResId(Res.string.prop_turnTime_lb),
            value = Value.Data(this?.turnTime) { it?.toString()?.let { stringResource(Res.string.prop_dimen_sec, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "waitCooling",
            title = Title.ResId(Res.string.prop_waitCooling_lb),
            value = Value.Data(this?.waitCooling) { it?.toString()?.let { stringResource(Res.string.prop_dimen_sec, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "pkoff0",
            title = Title.ResId(Res.string.prop_pkoff0_lb),
            value = Value.Data(this?.pkoff0) { it?.toString() ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "pkoff1",
            title = Title.ResId(Res.string.prop_pkoff1_lb),
            value = Value.Data(this?.pkoff1) { it?.toString() ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "ikoff0",
            title = Title.ResId(Res.string.prop_ikoff0_lb),
            value = Value.Data(this?.ikoff0) { it?.toString() ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "ikoff1",
            title = Title.ResId(Res.string.prop_ikoff1_lb),
            value = Value.Data(this?.ikoff1) { it?.toString() ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "identif",
            title = Title.ResId(Res.string.prop_identif_lb),
            value = Value.Data(this?.node) { it?.toString() ?: EMPTY_PLACEHOLDER },
        ),
        // PropItem(
        //     id = "hihEnable",
        //     title = Title.ResId(Res.string.prop_RH_sensor_allowed_label),
        //     value = Value.Data(this?.hihEnable) { it?.toString() ?: EMPTY_PLACEHOLDER },
        // ),
        // PropItem(
        //     id = "kOffCurr",
        //     title = Title.ResId(Res.string.prop_scale_factor_label),
        //     value = Value.Data(this?.kOffCurr) { it?.toString() ?: EMPTY_PLACEHOLDER },
        // ),
        // PropItem(
        //     id = "coolOn",
        //     title = Title.ResId(Res.string.prop_not_used0_label),
        //     value = Value.Data(this?.coolOn) { it?.toString() ?: EMPTY_PLACEHOLDER },
        // ),
        // PropItem(
        //     id = "coolOff",
        //     title = Title.ResId(Res.string.prop_not_used1_label),
        //     value = Value.Data(this?.coolOff) { it?.toString() ?: EMPTY_PLACEHOLDER },
        // ),
        // PropItem(
        //     id = "zonality",
        //     title = Title.ResId(Res.string.prop_zone_threshold_label),
        //     value = Value.Data(this?.zonality) { it?.toString()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        // ),
        // PropItem(
        //     id = "timeOut",
        //     title = Title.ResId(Res.string.prop_waiting_time_label),
        //     value = Value.Data(this?.timeOut) { it?.toString()?.let { stringResource(Res.string.prop_dimen_min, it) } ?: EMPTY_PLACEHOLDER },
        // ),
        // PropItem(
        //     id = "energyMeter",
        //     title = Title.ResId(Res.string.prop_Wattmeter_label),
        //     value = Value.Data(this?.energyMeter) { it?.toString()?.let { stringResource(Res.string.prop_dimen_kwt, it) } ?: EMPTY_PLACEHOLDER },
        // ),
    )
}