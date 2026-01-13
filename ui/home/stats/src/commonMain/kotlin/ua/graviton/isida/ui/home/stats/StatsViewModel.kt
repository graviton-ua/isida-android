package ua.graviton.isida.ui.home.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whoppah.common.compose.theme.IsidaColor
import com.whoppah.common.resources.Res
import com.whoppah.common.resources.*
import com.whoppah.metrox.viewmodel.ViewModelKey
import com.whoppah.metrox.viewmodel.ViewModelScope
import com.whoppah.util.ObservableLoadingCounter
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.*
import ua.graviton.isida.data.models.DataPackageDto
import ua.graviton.isida.domain.IsidaCommands
import ua.graviton.isida.domain.observers.ObserveDeviceData

@Inject
@ViewModelKey(StatsViewModel::class)
@ContributesIntoMap(ViewModelScope::class)
class StatsViewModel(
    observeDeviceData: ObserveDeviceData,
) : ViewModel() {
    private val loadingState = ObservableLoadingCounter()

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
            titleDeviceId = data?.cellId,
            titleDeviceBackgroundColor = deviceBgColor,
            items = data.toItems(),
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = StatsViewState.Empty,
    )


}

private fun DataPackageDto?.toItems(): List<StatsItem> {
    return listOf(
        StatsItem(
            titleResId = Res.string.pv_t0_label,
            value = this?.let {
                StatsItem.Value.FloatVal(
                    value = if (pvT0 > 80) null else pvT0,
                    target = spT0
                )
            },
            valueColor = when {
                this != null && pvT0 > spT0 -> IsidaColor.Red900
                this != null && pvT0 < spT0 -> IsidaColor.Indigo800
                else -> null
            }
        ),
        StatsItem(
            titleResId = when {
                this?.pvRh != 0f -> Res.string.pv_rh_label
                else -> Res.string.pv_t1_label
            },
            value = this?.let {
                StatsItem.Value.FloatVal(
                    value = if (pvT1 > 80) null else pvT1,
                    target = spT1
                )
            },
            valueColor = when {
                this != null && pvT1 > spT1 -> IsidaColor.Red900
                this != null && pvT1 < spT1 -> IsidaColor.Indigo800
                else -> null
            }
        ),
        StatsItem(titleResId = Res.string.pv_t2_label, value = this?.pvT2?.let { StatsItem.Value.FloatVal(value = if (it > 80) null else it) }),
        StatsItem(titleResId = Res.string.pv_t3_label, value = this?.pvT3?.let { StatsItem.Value.FloatVal(value = if (it > 80) null else it) }),
        StatsItem(titleResId = Res.string.cotwo, value = this?.pvCO2_1?.let { StatsItem.Value.IntVal(value = if (it < 400) null else it) }),
        StatsItem(titleResId = Res.string.timer, value = this?.let { StatsItem.Value.IntVal(value = it.pvTimer, target = timer0) }),
        StatsItem(titleResId = Res.string.counter, value = this?.pvTmrCount?.let { StatsItem.Value.IntVal(value = it) }),
        StatsItem(titleResId = Res.string.power, value = this?.power?.let { StatsItem.Value.IntVal(value = it) }, valueColor = IsidaColor.Power),
        StatsItem(titleResId = Res.string.flap, value = this?.pvFlap?.let { StatsItem.Value.IntVal(value = it) }),
        StatsItem(
            titleResId = Res.string.fuses,
            value = this?.fuses?.let {
                StatsItem.Value.TextResId(
                    value = when (it) {
                        1 -> Res.string.fuses_0
                        2 -> Res.string.fuses_1
                        4 -> Res.string.fuses_2
                        8 -> Res.string.fuses_3
                        else -> Res.string.no
                    }
                )
            }),
        StatsItem(
            titleResId = Res.string.errors,
            value = this?.errors?.let {
                StatsItem.Value.TextResId(
                    value = when (it) {
                        1 -> Res.string.error_01
                        2 -> Res.string.error_02
                        4 -> Res.string.error_04
                        8 -> Res.string.error_08
                        else -> Res.string.no
                    }
                )
            }),
        StatsItem(
            titleResId = Res.string.warnings,
            value = this?.warning?.let {
                StatsItem.Value.TextResId(
                    value = when (it) {
                        1 -> Res.string.warning_01
                        2 -> Res.string.warning_02
                        4 -> Res.string.warning_04
                        8 -> Res.string.warning_08
                        else -> Res.string.no
                    }
                )
            }),
        StatsItem(
            titleResId = Res.string.state,
            value = this?.state?.let {
                val mode = when (it) {
                    it or IsidaCommands.DeviceMode.ENABLE.code -> IsidaCommands.DeviceMode.ENABLE
                    it or IsidaCommands.DeviceMode.ONLY_ROTATION.code -> IsidaCommands.DeviceMode.ONLY_ROTATION
                    else -> IsidaCommands.DeviceMode.DISABLE
                }
                val extras = if (mode == IsidaCommands.DeviceMode.ENABLE) {
                    val result = mutableListOf<IsidaCommands.DeviceModeExtra>()
                    if (it == it or IsidaCommands.DeviceModeExtra.EXTRA_1.code) result.add(IsidaCommands.DeviceModeExtra.EXTRA_1)
                    if (it == it or IsidaCommands.DeviceModeExtra.EXTRA_2.code) result.add(IsidaCommands.DeviceModeExtra.EXTRA_2)
                    if (it == it or IsidaCommands.DeviceModeExtra.EXTRA_3.code) result.add(IsidaCommands.DeviceModeExtra.EXTRA_3)
                    if (it == it or IsidaCommands.DeviceModeExtra.EXTRA_4.code) result.add(IsidaCommands.DeviceModeExtra.EXTRA_4)
                    result
                } else {
                    emptyList()
                }
                StatsItem.Value.TextResId(
                    value = when (mode) {
                        IsidaCommands.DeviceMode.DISABLE -> Res.string.device_mode_disabled
                        IsidaCommands.DeviceMode.ONLY_ROTATION -> Res.string.device_mode_turn
                        IsidaCommands.DeviceMode.ENABLE -> when {
                            extras.contains(IsidaCommands.DeviceModeExtra.EXTRA_3) -> Res.string.device_mode_extra_3
                            extras.contains(IsidaCommands.DeviceModeExtra.EXTRA_4) -> Res.string.device_mode_extra_4
                            else -> Res.string.device_mode_enabled
                        }
                    }
                )
            },
            backgroundColor = this?.state?.let {
                when (it) {
                    0 -> IsidaColor.BlueGrey100
                    1 -> IsidaColor.Green500
                    2 -> IsidaColor.Yellow500
                    else -> null
                }
            }
        ),
        //  расширенный режим работы  0-СИРЕНА; 1-ВЕНТ. 2-Форс НАГР. 3-Форс ОХЛЖД. 4-Форс ОСУШ. 5-Дубляж увлажнения
        StatsItem(titleResId = Res.string.extendMode, value = this?.extendMode?.let {
            StatsItem.Value.TextRaw(
                value = when (it) {
                    0 -> "СИРЕНА"
                    1 -> "ВЕНТИЛЯЦИЯ"
                    2 -> "Форс.НАГРЕВ"
                    3 -> "Форс.ОХЛАЖД."
                    4 -> "Форс.ОСУШЕН."
                    5 -> "УВЛАЖНЕНИЕ"
                    else -> null
                }
            )
        }),
        StatsItem(titleResId = Res.string.programm, value = this?.programm?.let {
            StatsItem.Value.TextResId(
                value = when (it) {
                    0 -> Res.string.no
                    1 -> Res.string.chickens
                    2 -> Res.string.ducklings
                    3 -> Res.string.ducklings
                    4 -> Res.string.quail
                    else -> null
                }
            )
        }),
        StatsItem(titleResId = Res.string.incubation, value = this?.hours?.let { StatsItem.Value.IntVal(value = it) }),
        StatsItem(titleResId = Res.string.energyMeter, value = this?.energyMeter?.let { StatsItem.Value.IntVal(value = it) }),
    )
}