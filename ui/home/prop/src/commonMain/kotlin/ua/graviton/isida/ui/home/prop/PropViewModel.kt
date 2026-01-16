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
import ua.graviton.isida.data.models.DataPackageDto
import ua.graviton.isida.domain.observers.ObserveDeviceData
import ua.graviton.isida.ui.home.prop.PropItem.Title
import ua.graviton.isida.ui.home.prop.PropItem.Value

@Inject
@ViewModelKey(PropViewModel::class)
@ContributesIntoMap(ViewModelScope::class)
class PropViewModel(
    observeDeviceData: ObserveDeviceData,
) : ViewModel() {
    private val pendingActions = MutableSharedFlow<PropAction>()

    val state: StateFlow<PropViewState> = observeDeviceData.flow.map { data ->
        PropViewState(
            items = data.toItems(),
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

private fun DataPackageDto?.toItems(): List<PropItem> {
    return listOf(
        PropItem(
            id = "spT0",
            title = Title.ResId(Res.string.prop_dry_sensor_label),
            value = Value.Data(this?.spT0) { it?.format()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "spT1",
            title = Title.ResId(Res.string.prop_wet_sensor_label),
            value = Value.Data(this?.spT1) { it?.format()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "spRh0",
            title = Title.ResId(Res.string.prop_rh_ofset_label),
            value = Value.Data(this?.spRh0) { it?.format()?.let { stringResource(Res.string.prop_dimen_percent, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "spRh1",
            title = Title.ResId(Res.string.prop_rh_sensor_label),
            value = Value.Data(this?.spRh1) { it?.format()?.let { stringResource(Res.string.prop_dimen_percent, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "K0",
            title = Title.ResId(Res.string.prop_p_coef_dry_label),
            value = Value.Data(this?.pkoff0) { it?.toString() ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "K1",
            title = Title.ResId(Res.string.prop_p_coef_wet_label),
            value = Value.Data(this?.pkoff1) { it?.toString() ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "Ti0",
            title = Title.ResId(Res.string.prop_i_coef_dry_label),
            value = Value.Data(this?.ikoff0) { it?.toString() ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "Ti1",
            title = Title.ResId(Res.string.prop_i_coef_wet_label),
            value = Value.Data(this?.ikoff1) { it?.toString() ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "minRun",
            title = Title.ResId(Res.string.prop_min_impulse_label),
            value = Value.Data(this?.minRun) { it?.toString()?.let { stringResource(Res.string.prop_dimen_sec, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "maxRun",
            title = Title.ResId(Res.string.prop_max_impulse_label),
            value = Value.Data(this?.maxRun) { it?.toString()?.let { stringResource(Res.string.prop_dimen_sec, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "period",
            title = Title.ResId(Res.string.prop_repeat_time_label),
            value = Value.Data(this?.period) { it?.toString()?.let { stringResource(Res.string.prop_dimen_sec, it) } ?: EMPTY_PLACEHOLDER },
        ),
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
        PropItem(
            id = "timer0",
            title = Title.ResId(Res.string.prop_turned_off_label),
            value = Value.Data(this?.timer0) { it?.toString()?.let { stringResource(Res.string.prop_dimen_min, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "timer1",
            title = Title.ResId(Res.string.prop_turned_on_label),
            value = Value.Data(this?.timer1) { it?.toString()?.let { stringResource(Res.string.prop_dimen_min, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "alarm0",
            title = Title.ResId(Res.string.prop_bias_alarm_dry_label),
            value = Value.Data(this?.alarm0) { it?.toString()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "alarm1",
            title = Title.ResId(Res.string.prop_bias_alarm_wet_label),
            value = Value.Data(this?.alarm1) { it?.toString()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "extOn0",
            title = Title.ResId(Res.string.prop_bias_extOn_dry_label),
            value = Value.Data(this?.extOn0) { it?.toString()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "extOn1",
            title = Title.ResId(Res.string.prop_bias_extOn_wet_label),
            value = Value.Data(this?.extOn1) { it?.toString()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "extOff0",
            title = Title.ResId(Res.string.prop_bias_extOff_dry_label),
            value = Value.Data(this?.extOff0) { it?.toString()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "extOff1",
            title = Title.ResId(Res.string.prop_bias_extOff_wet_label),
            value = Value.Data(this?.extOff1) { it?.toString()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "air0",
            title = Title.ResId(Res.string.prop_pause_airing_label),
            value = Value.Data(this?.air0) { it?.toString()?.let { stringResource(Res.string.prop_dimen_min, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "air1",
            title = Title.ResId(Res.string.prop_airing_work_label),
            value = Value.Data(this?.air1) { it?.toString()?.let { stringResource(Res.string.prop_dimen_sec, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "spCO2",
            title = Title.ResId(Res.string.prop_CO2_concentration_label),
            value = Value.Data(this?.spCO2) { it?.toString()?.let { stringResource(Res.string.prop_dimen_ppm, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "deviceNumber",
            title = Title.ResId(Res.string.prop_id_label),
            value = Value.Data(this?.node) { it?.toString() ?: EMPTY_PLACEHOLDER },
        ),
//        PropItem(
//            id = "state",
//            title = Title.ResId(Res.string.prop_camera_status_label),
//            value = Value.Data(this?.state) { it?.toString() ?: EMPTY_PLACEHOLDER },
//        ),
        PropItem(
            id = "extendMode",
            title = Title.ResId(Res.string.prop_extended_mode_label),
            value = Value.Data(this?.extendMode) { it?.toString() ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "relayMode",
            title = Title.ResId(Res.string.prop_working_mode_label),
            value = Value.Data(this?.relayMode) { it?.toString() ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "programm",
            title = Title.ResId(Res.string.prop_program_number_label),
            value = Value.Data(this?.programm) { it?.toString() ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "hysteresis",
            title = Title.ResId(Res.string.prop_Hysteresis_label),
            value = Value.Data(this?.hysteresis) { it?.toString()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        ),
        // PropItem(
        //     id = "forceHeat",
        //     title = Title.ResId(Res.string.prop_forced_heating_label),
        //     value = Value.Data(this?.forceHeat) { it?.toString()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        // ),
        PropItem(
            id = "turnTime",
            title = Title.ResId(Res.string.prop_tray_passage_time_label),
            value = Value.Data(this?.turnTime) { it?.toString()?.let { stringResource(Res.string.prop_dimen_sec, it) } ?: EMPTY_PLACEHOLDER },
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
    )
}