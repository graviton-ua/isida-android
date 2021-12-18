package ua.graviton.isida.ui.home.prop

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
            value = Value.Data(this?.spT0) { it?.format() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "spT1",
            title = Title.ResId(R.string.prop_wet_sensor_label),
            value = Value.Data(this?.spT1) { it?.format() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "spRh0",
            title = Title.Text("spRh0"),
            value = Value.Data(this?.spRh0) { it?.format() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "spRh1",
            title = Title.Text("spRh1"),
            value = Value.Data(this?.spRh1) { it?.format() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "K0",
            title = Title.Text("K0"),
            value = Value.Data(this?.K0) { it?.toString() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "K1",
            title = Title.Text("K1"),
            value = Value.Data(this?.K1) { it?.toString() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "Ti0",
            title = Title.Text("Ti0"),
            value = Value.Data(this?.Ti0) { it?.toString() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "Ti1",
            title = Title.Text("Ti1"),
            value = Value.Data(this?.Ti1) { it?.toString() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "minRun",
            title = Title.Text("minRun"),
            value = Value.Data(this?.minRun) { it?.toString() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "maxRun",
            title = Title.Text("maxRun"),
            value = Value.Data(this?.maxRun) { it?.toString() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "period",
            title = Title.Text("period"),
            value = Value.Data(this?.period) { it?.toString() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "timeOut",
            title = Title.Text("timeOut"),
            value = Value.Data(this?.timeOut) { it?.toString() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "energyMeter",
            title = Title.Text("energyMeter"),
            value = Value.Data(this?.energyMeter) { it?.toString() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "timer0",
            title = Title.Text("timer0"),
            value = Value.Data(this?.timer0) { it?.toString() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "timer1",
            title = Title.Text("timer1"),
            value = Value.Data(this?.timer1) { it?.toString() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "alarm0",
            title = Title.Text("alarm0"),
            value = Value.Data(this?.alarm0) { it?.toString() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "alarm1",
            title = Title.Text("alarm1"),
            value = Value.Data(this?.alarm1) { it?.toString() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "extOn0",
            title = Title.Text("extOn0"),
            value = Value.Data(this?.extOn0) { it?.toString() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "extOn1",
            title = Title.Text("extOn1"),
            value = Value.Data(this?.extOn1) { it?.toString() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "extOff0",
            title = Title.Text("extOff0"),
            value = Value.Data(this?.extOff0) { it?.toString() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "extOff1",
            title = Title.Text("extOff1"),
            value = Value.Data(this?.extOff1) { it?.toString() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "air0",
            title = Title.Text("air0"),
            value = Value.Data(this?.air0) { it?.toString() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "air1",
            title = Title.Text("air1"),
            value = Value.Data(this?.air1) { it?.toString() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "spCO2",
            title = Title.Text("spCO2"),
            value = Value.Data(this?.spCO2) { it?.toString() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "deviceNumber",
            title = Title.Text("deviceNumber"),
            value = Value.Data(this?.deviceNumber) { it?.toString() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "state",
            title = Title.Text("state"),
            value = Value.Data(this?.state) { it?.toString() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "extendMode",
            title = Title.Text("extendMode"),
            value = Value.Data(this?.extendMode) { it?.toString() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "relayMode",
            title = Title.Text("relayMode"),
            value = Value.Data(this?.relayMode) { it?.toString() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "programm",
            title = Title.Text("programm"),
            value = Value.Data(this?.programm) { it?.toString() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "hysteresis",
            title = Title.Text("hysteresis"),
            value = Value.Data(this?.hysteresis) { it?.toString() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "forceHeat",
            title = Title.Text("forceHeat"),
            value = Value.Data(this?.forceHeat) { it?.toString() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "turnTime",
            title = Title.Text("turnTime"),
            value = Value.Data(this?.turnTime) { it?.toString() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "hihEnable",
            title = Title.Text("hihEnable"),
            value = Value.Data(this?.hihEnable) { it?.toString() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "kOffCurr",
            title = Title.Text("kOffCurr"),
            value = Value.Data(this?.kOffCurr) { it?.toString() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "coolOn",
            title = Title.Text("coolOn"),
            value = Value.Data(this?.coolOn) { it?.toString() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "coolOff",
            title = Title.Text("coolOff"),
            value = Value.Data(this?.coolOff) { it?.toString() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
        PropItem(
            id = "zonality",
            title = Title.Text("zonality"),
            value = Value.Data(this?.zonality) { it?.toString() ?: EMPTY_PLACEHOLDER },
            action = null,
        ),
    )
}