package ua.graviton.isida.ui.home.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whoppah.common.compose.theme.IsidaColor
import com.whoppah.common.resources.*
import com.whoppah.metrox.viewmodel.ViewModelKey
import com.whoppah.metrox.viewmodel.ViewModelScope
import com.whoppah.util.ObservableLoadingCounter
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import ua.graviton.isida.data.models.DataPackageDto
import ua.graviton.isida.domain.IsidaCommands
import ua.graviton.isida.domain.observers.ObserveDeviceData

@Inject
@ViewModelKey(StatsViewModel::class)
@ContributesIntoMap(ViewModelScope::class)
/**
 * ViewModel for the Stats Screen.
 * Responsible for observing device data and transforming it into a list of [StatsItem]s
 * using the [buildStats] DSL.
 */
class StatsViewModel(
    observeDeviceData: ObserveDeviceData,
) : ViewModel() {
    private val loadingState = ObservableLoadingCounter()

    /**
     * Stream of UI state.
     * Combines the latest device data with loading state.
     * If data is null (not yet received), it falls back to [PlaceholderStats] to show the structure.
     */
    val state: StateFlow<StatsViewState> = combine(
        observeDeviceData.flow, loadingState.observable
    ) { data, loading ->
        val deviceBgColor = when {
            data != null && data.fuses + data.errors + data.warning > 0 -> IsidaColor.Red500
            data != null && data.state == 1 -> IsidaColor.Green500
            data != null && data.state == 2 -> IsidaColor.Yellow500
            else -> null
        }

        // Создаём модель состояния экрана
        StatsViewState(
            titleDeviceId = data?.node,
            titleDeviceBackgroundColor = deviceBgColor,
            items = data?.toItems() ?: PlaceholderStats,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = StatsViewState.Empty,
    )
}

/**
 * A static list of items used when no real data is available.
 * Mirrors the structure of [toItems] but with all values set to null.
 * This ensures the UI displays the correct labels and layout even before the first packet arrives.
 */
internal val PlaceholderStats = buildStats {
    item<Float>(Res.string.pv_t0_label, null, null)
    item<Float>(Res.string.pv_t1_label, null, null)
    item<Float>(Res.string.pv_t2_label, null)
    item<Int>(Res.string.cotwo, null)
    item<Int>(Res.string.timer, null, null)
    item<Int>(Res.string.power, null)
    item<Int>(Res.string.flap, null)
    mapStringResource<Int>(Res.string.fuses, null) { null }
    mapStringResource<Int>(Res.string.errors, null) { null }
    mapStringResource<Int>(Res.string.warnings, null) { null }
    mapStringResource<Int>(Res.string.state, null) { null }
    mapString<Int>(Res.string.extendMode, null) { null }
    mapStringResource<Int>(Res.string.programm, null) { null }
}

/**
 * Transforms a [DataPackageDto] into a list of [StatsItem]s.
 * Uses the [buildStats] DSL to define the layout and logic for each row.
 * This is where the mapping from raw bytes to UI representation happens.
 */
private fun DataPackageDto.toItems(): List<StatsItem> = buildStats {
    // T0
    item(
        title = Res.string.pv_t0_label,
        value = if (pvT0 > 80) null else pvT0,
        target = spT0,
        valueColor = { value, target ->
            when {
                value == null || target == null -> null
                value > target -> IsidaColor.Red900
                value < target -> IsidaColor.Indigo800
                else -> null
            }
        },
    )

    // T1 / RH
    item(
        title = if (pvRh != 0) Res.string.pv_rh_label else Res.string.pv_t1_label,
        value = if (pvT1 > 80) null else pvT1,
        target = spT1,
        valueColor = { value, target ->
            when {
                value == null || target == null -> null
                value > target -> IsidaColor.Red900
                value < target -> IsidaColor.Indigo800
                else -> null
            }
        },
    )

    // T2
    item(
        title = Res.string.pv_t2_label,
        value = if (pvT2 > 80) null else pvT2,
    )

    // CO2
    item(
        title = Res.string.cotwo,
        value = if (pvCO2 < 400) null else pvCO2,
    )

    // Timer
    item(
        title = Res.string.timer,
        value = pvTimer,
        target = timer0,
    )

    // Power
    item(
        title = Res.string.power,
        value = power,
        valueColor = { _, _ -> IsidaColor.Power },
    )

    // Flap
    item(
        title = Res.string.flap,
        value = pvFlap,
    )

    // Fuses
    mapStringResource(
        title = Res.string.fuses,
        value = fuses,
        mapper = { value ->
            when (value) {
                1 -> Res.string.fuses_0
                2 -> Res.string.fuses_1
                4 -> Res.string.fuses_2
                8 -> Res.string.fuses_3
                else -> Res.string.no
            }
        },
    )

    // Errors
    mapStringResource(
        title = Res.string.errors,
        value = errors,
        mapper = { value ->
            when (value) {
                1 -> Res.string.error_01
                2 -> Res.string.error_02
                4 -> Res.string.error_04
                8 -> Res.string.error_08
                else -> Res.string.no
            }
        },
    )

    // Warnings
    mapStringResource(
        title = Res.string.warnings,
        value = warning,
        mapper = { value ->
            when (value) {
                1 -> Res.string.warning_01
                2 -> Res.string.warning_02
                4 -> Res.string.warning_04
                8 -> Res.string.warning_08
                else -> Res.string.no
            }
        },
    )

    // State
    mapStringResource(
        title = Res.string.state,
        value = state,
        backgroundColor = { value ->
            when (value) {
                0 -> IsidaColor.BlueGrey100
                1 -> IsidaColor.Green500
                2 -> IsidaColor.Yellow500
                else -> null
            }
        },
        mapper = { value ->
            val mode = when (value) {
                value or IsidaCommands.DeviceMode.ENABLE.code -> IsidaCommands.DeviceMode.ENABLE
                value or IsidaCommands.DeviceMode.ONLY_ROTATION.code -> IsidaCommands.DeviceMode.ONLY_ROTATION
                else -> IsidaCommands.DeviceMode.DISABLE
            }
            val extras = if (mode == IsidaCommands.DeviceMode.ENABLE) {
                val result = mutableListOf<IsidaCommands.DeviceModeExtra>()
                if (value == value or IsidaCommands.DeviceModeExtra.EXTRA_1.code) result.add(IsidaCommands.DeviceModeExtra.EXTRA_1)
                if (value == value or IsidaCommands.DeviceModeExtra.EXTRA_2.code) result.add(IsidaCommands.DeviceModeExtra.EXTRA_2)
                if (value == value or IsidaCommands.DeviceModeExtra.EXTRA_3.code) result.add(IsidaCommands.DeviceModeExtra.EXTRA_3)
                if (value == value or IsidaCommands.DeviceModeExtra.EXTRA_4.code) result.add(IsidaCommands.DeviceModeExtra.EXTRA_4)
                result
            } else {
                emptyList()
            }

            when (mode) {
                IsidaCommands.DeviceMode.DISABLE -> Res.string.device_mode_disabled
                IsidaCommands.DeviceMode.ONLY_ROTATION -> Res.string.device_mode_turn
                IsidaCommands.DeviceMode.ENABLE -> when {
                    extras.contains(IsidaCommands.DeviceModeExtra.EXTRA_3) -> Res.string.device_mode_extra_3
                    extras.contains(IsidaCommands.DeviceModeExtra.EXTRA_4) -> Res.string.device_mode_extra_4
                    else -> Res.string.device_mode_enabled
                }
            }
        },
    )

    // Extend Mode (Raw String)
    mapString(
        title = Res.string.extendMode,
        value = extendMode,
        mapper = { value ->
            when (value) {
                0 -> "СИРЕНА"
                1 -> "ВЕНТИЛЯЦИЯ"
                2 -> "Форс.НАГРЕВ"
                3 -> "Форс.ОХЛАЖД."
                4 -> "Форс.ОСУШЕН."
                5 -> "УВЛАЖНЕНИЕ"
                else -> null
            }
        },
    )

    // Program
    mapStringResource(
        title = Res.string.programm,
        value = programm,
        mapper = { value ->
            when (value) {
                0 -> Res.string.no
                1 -> Res.string.chickens
                2 -> Res.string.ducklings
                3 -> Res.string.ducklings
                4 -> Res.string.quail
                else -> null
            }
        },
    )
}