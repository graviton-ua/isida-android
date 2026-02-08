package ua.graviton.isida.ui.home.prop

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whoppah.common.compose.theme.IsidaColor
import com.whoppah.common.resources.*
import com.whoppah.common.resources.ComposableString.Companion.composableString
import com.whoppah.metrox.viewmodel.ViewModelKey
import com.whoppah.metrox.viewmodel.ViewModelScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.*
import org.jetbrains.compose.resources.stringResource
import ua.graviton.isida.data.bluetooth.ConnectionState
import ua.graviton.isida.data.protocol.packets.v1.StatusPacketV1
import ua.graviton.isida.domain.bluetooth.DeviceConnectionManager
import ua.graviton.isida.domain.observers.ObserveStatus
import java.util.Locale

@Inject
@ViewModelKey(PropViewModel::class)
@ContributesIntoMap(ViewModelScope::class)
class PropViewModel(
    manager: DeviceConnectionManager,
    observeStatus: ObserveStatus,
) : ViewModel() {
    private val connectionState = manager.connectionState
    private val packets = observeStatus.flow.stateIn(
        scope = viewModelScope, started = SharingStarted.WhileSubscribed(), initialValue = null,
    )

    private val uiItems = packets.mapNotNull { packet ->
        when (packet) {
            is StatusPacketV1 -> packet.toItems()
            else -> null
        }
    }.onStart { emit(emptyList()) }

    val state: StateFlow<PropViewState> = combine(connectionState, uiItems) { state, items ->
        val connected = state == ConnectionState.CONNECTED
        PropViewState(
            deviceConnected = connected,
            items = if (connected) items else emptyList(),
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
    val currentPermission = this@toItems?.permission
    val programm = this@toItems?.programm
    item(
        id = "spT0",
        title = composableString { stringResource(Res.string.prop_spT0_lb) },
        value = composableString(this@toItems?.spT0) {
            this@toItems?.spT0?.format()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER
        },
        style = {
            val currVal = this@toItems?.spT0
            backgroundColor = when {
                currVal == null -> null
                currVal > 40.0 -> IsidaColor.Red100
                currVal > 38.5 -> IsidaColor.Yellow100
                currVal < 35.0 -> IsidaColor.Blue500
                currVal < 36.5 -> IsidaColor.Blue100
                else -> null
            }
        }
    )
    item(
        id = "spT1",
        title = composableString { stringResource(Res.string.prop_spT1_lb) },
        value = composableString(this@toItems?.spT1) {
            this@toItems?.spT1?.format()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER
        },
        style = {
            val currVal = this@toItems?.spT1
            backgroundColor = when {
                currVal == null -> null
                currVal > 38.0 -> IsidaColor.Red100
                currVal > 35.5 -> IsidaColor.Yellow100
                currVal < 27.5 -> IsidaColor.Blue500
                currVal < 28.0 -> IsidaColor.Blue100
                else -> null
            }
        }
    )
    item(
        id = "permission",
        title = composableString { stringResource(Res.string.prop_Rh_lb) },
        value = composableString(this@toItems?.permission) {
            when (currentPermission) {
                0 -> stringResource(Res.string.no)
                1 -> stringResource(Res.string.prop_Rh1_lb)
                2 -> stringResource(Res.string.prop_Rh2_lb)
                else -> currentPermission?.toString() ?: EMPTY_PLACEHOLDER
            }
        },
        style = {
            if (currentPermission != null) {
                if (currentPermission == 0) {
                    backgroundColor = IsidaColor.Yellow100
                    // valueColor = Color.Yellow
                    // valueBackgroundColor = Color.Green
                }
            }
        }
    )
    if (currentPermission != null) {
        if (currentPermission > 0) {
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
        }
    }
    item(
        id = "extendMode",
        title = composableString { stringResource(Res.string.prop_extMode_lb) },
        value = composableString(this@toItems?.extendMode) {
            when (val currentValue = this@toItems?.extendMode) {
                0 -> stringResource(Res.string.prop_extMode0)
                1 -> stringResource(Res.string.prop_extMode1)
                2 -> stringResource(Res.string.prop_extMode2)
                3 -> stringResource(Res.string.prop_extMode3)
                4 -> stringResource(Res.string.prop_extMode4)
                5 -> stringResource(Res.string.prop_extMode5)
                null -> EMPTY_PLACEHOLDER
                else -> currentValue.toString()
            }
        },
        style = {
            when (val currentValue = this@toItems?.extendMode) {
                0 -> Color.LightGray
                1 -> IsidaColor.Red900
                2 -> IsidaColor.Red100
                3 -> IsidaColor.Blue100
                4 -> IsidaColor.Yellow100
                5 -> IsidaColor.Blue500
                null -> EMPTY_PLACEHOLDER
                else -> currentValue.toString()
            }
        }
    )
    item(
        id = "relayMode",
        title = composableString { stringResource(Res.string.prop_relMode_lb) },
        value = composableString(this@toItems?.relayMode) {
            when (val currentValue = this@toItems?.relayMode) {
                0 -> stringResource(Res.string.prop_relMode0)
                1 -> stringResource(Res.string.prop_relMode1)
                2 -> stringResource(Res.string.prop_relMode2)
                3 -> stringResource(Res.string.prop_relMode3)
                4 -> stringResource(Res.string.prop_relMode4)
                null -> EMPTY_PLACEHOLDER
                else -> currentValue.toString()
            }
        }
    )
    item(
        id = "program",
        title = composableString { stringResource(Res.string.prop_program_lb) },
        value = composableString(this@toItems?.programm) {
            when (programm) {
                0 -> stringResource(Res.string.no)
                else -> {
                    val label = stringResource(Res.string.prop_program_lb)
                    "$label $programm"
                }
            }
        },
        style = {
            if (programm != null) {
                if (programm > 0) {
                    backgroundColor = IsidaColor.Yellow500
                    // valueColor = Color.Yellow
                    // valueBackgroundColor = Color.Green
                }
            }
        }
    )
    item(
        id = "minRun",
        title = composableString { stringResource(Res.string.prop_minImpulse_lb) },
        value = composableString(this@toItems?.minRun) {
            this@toItems?.minRun?.toString()?.let { stringResource(Res.string.prop_dimen_sec, it) } ?: EMPTY_PLACEHOLDER
        },
        style = {
            val currVal = this@toItems?.minRun
            backgroundColor = when {
                currVal == null -> null
                currVal > 5.0 -> IsidaColor.Red100
                currVal > 3.0 -> IsidaColor.Yellow100
                currVal < 0.3 -> IsidaColor.Yellow500
                currVal < 0.5 -> IsidaColor.Yellow100
                else -> null
            }
        }
    )
    item(
        id = "maxRun",
        title = composableString { stringResource(Res.string.prop_maxImpulse_lb) },
        value = composableString(this@toItems?.maxRun) {
            this@toItems?.maxRun?.toString()?.let { stringResource(Res.string.prop_dimen_sec, it) } ?: EMPTY_PLACEHOLDER
        },
        style = {
            val currVal = this@toItems?.maxRun
            backgroundColor = when {
                currVal == null -> null
                currVal > 50 -> IsidaColor.Red100
                currVal > 30 -> IsidaColor.Yellow100
                currVal < 3 -> IsidaColor.Yellow500
                currVal < 5 -> IsidaColor.Yellow100
                else -> null
            }
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
            when (val tmr1 = this@toItems?.timer1) {
                0 -> stringResource(Res.string.txt_limit_switch)
                null -> EMPTY_PLACEHOLDER
                else -> tmr1.toString().let { stringResource(Res.string.prop_dimen_sec, it) }
            }
        },
        style = {
            val tmr1 = this@toItems?.timer1
            if (tmr1 != null) {
                if (tmr1 > 0) {
                    backgroundColor = IsidaColor.Yellow500
                    // valueColor = Color.Yellow
                    // valueBackgroundColor = Color.Green
                }
            }
        }
    )
    item(
        id = "alarm0",
        title = composableString { stringResource(Res.string.prop_alarm0_lb) },
        value = composableString(this@toItems?.alarm0) {
            this@toItems?.alarm0?.toString()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER
        },
        style = {
            val currVal = this@toItems?.alarm0
            backgroundColor = when {
                currVal == null -> null
                currVal > 5.0 -> IsidaColor.Red100
                currVal > 2.0 -> IsidaColor.Yellow100
                currVal < 0.3 -> IsidaColor.Red100
                currVal < 0.5 -> IsidaColor.Yellow100
                else -> null
            }
        }
    )
    item(
        id = "alarm1",
        title = composableString { stringResource(Res.string.prop_alarm1_lb) },
        value = composableString(this@toItems?.alarm1) {
            this@toItems?.alarm1?.toString()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER
        },
        style = {
            val currVal = this@toItems?.alarm1
            backgroundColor = when {
                currVal == null -> null
                currVal > 10.0 -> IsidaColor.Red100
                currVal > 5.0 -> IsidaColor.Yellow100
                currVal < 0.6 -> IsidaColor.Red100
                currVal < 1.0 -> IsidaColor.Yellow100
                else -> null
            }
        }
    )
    item(
        id = "extOn0",
        title = composableString { stringResource(Res.string.prop_extOn0_lb) },
        value = composableString(this@toItems?.extOn0) {
            this@toItems?.extOn0?.toString()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER
        },
        style = {
            val currVal = this@toItems?.extOn0
            backgroundColor = when {
                currVal == null -> null
                currVal > 15.0 -> IsidaColor.Red100
                currVal > 5.0 -> IsidaColor.Yellow100
                currVal < 0.3 -> IsidaColor.Red100
                currVal < 0.5 -> IsidaColor.Yellow100
                else -> null
            }
        }
    )
    item(
        id = "extOn1",
        title = composableString { stringResource(Res.string.prop_extOn1_lb) },
        value = composableString(this@toItems?.extOn1) {
            this@toItems?.extOn1?.toString()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER
        },
        style = {
            val currVal = this@toItems?.extOn1
            backgroundColor = when {
                currVal == null -> null
                currVal > 15.0 -> IsidaColor.Red100
                currVal > 5.0 -> IsidaColor.Yellow100
                currVal < 0.3 -> IsidaColor.Red100
                currVal < 0.5 -> IsidaColor.Yellow100
                else -> null
            }
        }
    )
    item(
        id = "extOff0",
        title = composableString { stringResource(Res.string.prop_extOff0_lb) },
        value = composableString(this@toItems?.extOff0) {
            this@toItems?.extOff0?.toString()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER
        },
        style = {
            val currVal = this@toItems?.extOff0
            backgroundColor = when {
                currVal == null -> null
                currVal > 10.0 -> IsidaColor.Red100
                currVal > 3.0 -> IsidaColor.Yellow100
                currVal < 0.2 -> IsidaColor.Yellow100
                else -> null
            }
        }
    )
    item(
        id = "extOff1",
        title = composableString { stringResource(Res.string.prop_extOff1_lb) },
        value = composableString(this@toItems?.extOff1) {
            this@toItems?.extOff1?.toString()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER
        },
        style = {
            val currVal = this@toItems?.extOff1
            backgroundColor = when {
                currVal == null -> null
                currVal > 10.0 -> IsidaColor.Red100
                currVal > 3.0 -> IsidaColor.Yellow100
                currVal < 0.2 -> IsidaColor.Yellow100
                else -> null
            }
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
        },
        style = {
            val currVal = this@toItems?.spCO2
            backgroundColor = when {
                currVal == null -> null
                currVal > 3500 -> IsidaColor.Red100
                currVal > 2500 -> IsidaColor.Yellow100
                currVal < 1500 -> IsidaColor.Yellow100
                else -> null
            }
        }
    )
    item(
        id = "koffCurr",
        title = composableString { stringResource(Res.string.prop_koffCurr_lb) },
        value = composableString(this@toItems?.koffCurr) {
            this@toItems?.koffCurr?.toString() ?: EMPTY_PLACEHOLDER
        },
        style = {
            val currVal = this@toItems?.koffCurr
            backgroundColor = when {
                currVal == null -> null
                currVal > 200 -> IsidaColor.Red100
                currVal > 120 -> IsidaColor.Yellow100
                currVal < 50 -> IsidaColor.Red500
                currVal < 80 -> IsidaColor.Yellow100
                else -> null
            }
        }
    )
    item(
        id = "hysteresis",
        title = composableString { stringResource(Res.string.prop_Hysteresis_lb) },
        value = composableString(this@toItems?.hysteresis) {
            this@toItems?.hysteresis?.toString()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER
        }
    )
    item(
        id = "zonality",
        title = composableString { stringResource(Res.string.prop_zonelity_lb) },
        value = composableString(this@toItems?.zonality) {
            this@toItems?.zonality?.toString()?.let { stringResource(Res.string.prop_dimen_celsius, it) } ?: EMPTY_PLACEHOLDER
        },
        style = {
            val currentValue = this@toItems?.zonality
            if (currentValue != null) {
                if (currentValue > 2) backgroundColor = IsidaColor.Yellow100
            }
        }
    )
    item(
        id = "flapRestrictions",
        title = composableString { stringResource(Res.string.prop_flapRestr_lb) },
        value = composableString(this@toItems?.flapRestrictions) {
            this@toItems?.flapRestrictions?.toString()?.let { stringResource(Res.string.prop_dimen_percent, it) } ?: EMPTY_PLACEHOLDER
        }
    )
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
        },
        style = {
            val currVal = this@toItems?.pkoff0
            backgroundColor = when {
                currVal == null -> null
                currVal > 120 -> IsidaColor.Red100
                currVal > 80 -> IsidaColor.Yellow100
                currVal < 10 -> IsidaColor.Red500
                currVal < 30 -> IsidaColor.Yellow500
                else -> null
            }
        }
    )
    item(
        id = "pkoff1",
        title = composableString { stringResource(Res.string.prop_pkoff1_lb) },
        value = composableString(this@toItems?.pkoff1) {
            this@toItems?.pkoff1?.toString() ?: EMPTY_PLACEHOLDER
        },
        style = {
            val currVal = this@toItems?.pkoff1
            backgroundColor = when {
                currVal == null -> null
                currVal > 120 -> IsidaColor.Red100
                currVal > 80 -> IsidaColor.Yellow100
                currVal < 10 -> IsidaColor.Red500
                currVal < 30 -> IsidaColor.Yellow500
                else -> null
            }
        }
    )
    item(
        id = "ikoff0",
        title = composableString { stringResource(Res.string.prop_ikoff0_lb) },
        value = composableString(this@toItems?.ikoff0) {
            this@toItems?.ikoff0?.toString() ?: EMPTY_PLACEHOLDER
        },
        style = {
            val currVal = this@toItems?.ikoff0
            backgroundColor = when {
                currVal == null -> null
                currVal > 100 -> IsidaColor.Red500
                currVal > 50 -> IsidaColor.Yellow100
                currVal == 0 -> IsidaColor.Red900
                currVal < 10 -> IsidaColor.Yellow100
                else -> null
            }
        }
    )
    item(
        id = "ikoff1",
        title = composableString { stringResource(Res.string.prop_ikoff1_lb) },
        value = composableString(this@toItems?.ikoff1) {
            this@toItems?.ikoff1?.toString() ?: EMPTY_PLACEHOLDER
        },
        style = {
            val currVal = this@toItems?.ikoff1
            backgroundColor = when {
                currVal == null -> null
                currVal > 100 -> IsidaColor.Red500
                currVal > 50 -> IsidaColor.Yellow100
                currVal == 0 -> IsidaColor.Red900
                currVal < 10 -> IsidaColor.Yellow100
                else -> null
            }
        }
    )
    item(
        id = "identif",
        title = composableString { stringResource(Res.string.prop_identif_lb) },
        value = composableString(this@toItems?.node) {
            this@toItems?.node?.toString() ?: EMPTY_PLACEHOLDER
        },
        style = {
            val currVal = this@toItems?.node
            backgroundColor = when {
                currVal == null -> null
                currVal > 50 -> IsidaColor.Red100
                currVal > 30 -> IsidaColor.Yellow100
                currVal == 0 -> IsidaColor.Red500
                else -> null
            }
        }
    )
}