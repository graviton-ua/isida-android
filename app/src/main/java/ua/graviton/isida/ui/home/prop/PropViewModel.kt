package ua.graviton.isida.ui.home.prop

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ua.graviton.isida.R
import ua.graviton.isida.data.bl.model.DataPackageDto
import ua.graviton.isida.domain.observers.ObserveDeviceData
import ua.graviton.isida.ui.home.prop.PropItem.Title
import ua.graviton.isida.ui.home.prop.PropItem.Value
import javax.inject.Inject

@HiltViewModel
class PropViewModel @Inject constructor(
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

    fun submitAction(action: PropAction) {
        viewModelScope.launch { pendingActions.emit(action) }
    }
}

private fun Float.format(): String = String.format("%.1f", this)
private const val EMPTY_PLACEHOLDER = "--"

private fun DataPackageDto?.toItems(): List<PropItem> {
    return listOf(
        PropItem(
            id = "spT0",
            title = Title.ResId(R.string.prop_dry_sensor_label),
            value = Value.Data(this?.spT0) { it?.format()?.let { stringResource(R.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "spT1",
            title = Title.ResId(R.string.prop_wet_sensor_label),
            value = Value.Data(this?.spT1) { it?.format()?.let { stringResource(R.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "spRh0",
            title = Title.ResId(R.string.prop_rh_ofset_label),
            value = Value.Data(this?.spRh0) { it?.format()?.let { stringResource(R.string.prop_dimen_percent, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "spRh1",
            title = Title.ResId(R.string.prop_rh_sensor_label),
            value = Value.Data(this?.spRh1) { it?.format()?.let { stringResource(R.string.prop_dimen_percent, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "K0",
            title = Title.ResId(R.string.prop_p_coef_dry_label),
            value = Value.Data(this?.K0) { it?.toString() ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "K1",
            title = Title.ResId(R.string.prop_p_coef_wet_label),
            value = Value.Data(this?.K1) { it?.toString() ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "Ti0",
            title = Title.ResId(R.string.prop_i_coef_dry_label),
            value = Value.Data(this?.Ti0) { it?.toString() ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "Ti1",
            title = Title.ResId(R.string.prop_i_coef_wet_label),
            value = Value.Data(this?.Ti1) { it?.toString() ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "minRun",
            title = Title.ResId(R.string.prop_min_impulse_label),
            value = Value.Data(this?.minRun) { it?.toString()?.let { stringResource(R.string.prop_dimen_sec, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "maxRun",
            title = Title.ResId(R.string.prop_max_impulse_label),
            value = Value.Data(this?.maxRun) { it?.toString()?.let { stringResource(R.string.prop_dimen_sec, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "period",
            title = Title.ResId(R.string.prop_repeat_time_label),
            value = Value.Data(this?.period) { it?.toString()?.let { stringResource(R.string.prop_dimen_sec, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "timeOut",
            title = Title.ResId(R.string.prop_waiting_time_label),
            value = Value.Data(this?.timeOut) { it?.toString()?.let { stringResource(R.string.prop_dimen_min, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "energyMeter",
            title = Title.ResId(R.string.prop_Wattmeter_label),
            value = Value.Data(this?.energyMeter) { it?.toString()?.let { stringResource(R.string.prop_dimen_kwt, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "timer0",
            title = Title.ResId(R.string.prop_turned_off_label),
            value = Value.Data(this?.timer0) { it?.toString()?.let { stringResource(R.string.prop_dimen_min, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "timer1",
            title = Title.ResId(R.string.prop_turned_on_label),
            value = Value.Data(this?.timer1) { it?.toString()?.let { stringResource(R.string.prop_dimen_min, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "alarm0",
            title = Title.ResId(R.string.prop_bias_alarm_dry_label),
            value = Value.Data(this?.alarm0) { it?.toString()?.let { stringResource(R.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "alarm1",
            title = Title.ResId(R.string.prop_bias_alarm_wet_label),
            value = Value.Data(this?.alarm1) { it?.toString()?.let { stringResource(R.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "extOn0",
            title = Title.ResId(R.string.prop_bias_extOn_dry_label),
            value = Value.Data(this?.extOn0) { it?.toString()?.let { stringResource(R.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "extOn1",
            title = Title.ResId(R.string.prop_bias_extOn_wet_label),
            value = Value.Data(this?.extOn1) { it?.toString()?.let { stringResource(R.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "extOff0",
            title = Title.ResId(R.string.prop_bias_extOff_dry_label),
            value = Value.Data(this?.extOff0) { it?.toString()?.let { stringResource(R.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "extOff1",
            title = Title.ResId(R.string.prop_bias_extOff_wet_label),
            value = Value.Data(this?.extOff1) { it?.toString()?.let { stringResource(R.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "air0",
            title = Title.ResId(R.string.prop_pause_airing_label),
            value = Value.Data(this?.air0) { it?.toString()?.let { stringResource(R.string.prop_dimen_min, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "air1",
            title = Title.ResId(R.string.prop_airing_work_label),
            value = Value.Data(this?.air1) { it?.toString()?.let { stringResource(R.string.prop_dimen_sec, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "spCO2",
            title = Title.ResId(R.string.prop_CO2_concentration_label),
            value = Value.Data(this?.spCO2) { it?.toString()?.let { stringResource(R.string.prop_dimen_ppm, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "deviceNumber",
            title = Title.ResId(R.string.prop_id_label),
            value = Value.Data(this?.deviceNumber) { it?.toString() ?: EMPTY_PLACEHOLDER },
        ),
//        PropItem(
//            id = "state",
//            title = Title.ResId(R.string.prop_camera_status_label),
//            value = Value.Data(this?.state) { it?.toString() ?: EMPTY_PLACEHOLDER },
//        ),
        PropItem(
            id = "extendMode",
            title = Title.ResId(R.string.prop_extended_mode_label),
            value = Value.Data(this?.extendMode) { it?.toString() ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "relayMode",
            title = Title.ResId(R.string.prop_working_mode_label),
            value = Value.Data(this?.relayMode) { it?.toString() ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "programm",
            title = Title.ResId(R.string.prop_program_number_label),
            value = Value.Data(this?.programm) { it?.toString() ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "hysteresis",
            title = Title.ResId(R.string.prop_Hysteresis_label),
            value = Value.Data(this?.hysteresis) { it?.toString()?.let { stringResource(R.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "forceHeat",
            title = Title.ResId(R.string.prop_forced_heating_label),
            value = Value.Data(this?.forceHeat) { it?.toString()?.let { stringResource(R.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "turnTime",
            title = Title.ResId(R.string.prop_tray_passage_time_label),
            value = Value.Data(this?.turnTime) { it?.toString()?.let { stringResource(R.string.prop_dimen_sec, it) } ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "hihEnable",
            title = Title.ResId(R.string.prop_RH_sensor_allowed_label),
            value = Value.Data(this?.hihEnable) { it?.toString() ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "kOffCurr",
            title = Title.ResId(R.string.prop_scale_factor_label),
            value = Value.Data(this?.kOffCurr) { it?.toString() ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "coolOn",
            title = Title.ResId(R.string.prop_not_used0_label),
            value = Value.Data(this?.coolOn) { it?.toString() ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "coolOff",
            title = Title.ResId(R.string.prop_not_used1_label),
            value = Value.Data(this?.coolOff) { it?.toString() ?: EMPTY_PLACEHOLDER },
        ),
        PropItem(
            id = "zonality",
            title = Title.ResId(R.string.prop_zone_threshold_label),
            value = Value.Data(this?.zonality) { it?.toString()?.let { stringResource(R.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER },
        ),
    )
}