package ua.graviton.isida.ui.home.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ua.graviton.isida.R
import ua.graviton.isida.data.bl.IsidaCommands
import ua.graviton.isida.data.bl.model.DataPackageDto
import ua.graviton.isida.domain.observers.ObserveDeviceData
import ua.graviton.isida.ui.theme.IsidaColor
import ua.graviton.isida.ui.utils.ObservableLoadingCounter
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    observeDeviceData: ObserveDeviceData,
) : ViewModel() {
    private val loadingState = ObservableLoadingCounter()
    private val pendingActions = MutableSharedFlow<StatsAction>()

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

    init {
        viewModelScope.launch {
            pendingActions.collect { action ->
                when (action) {
                    //is ReportAction.RefreshCart -> observeShopCart(Unit)
                    else -> Unit
                }
            }
        }
    }

    fun submitAction(action: StatsAction) {
        viewModelScope.launch { pendingActions.emit(action) }
    }
}

private fun DataPackageDto?.toItems(): List<StatsItem> {
    return listOf(
        StatsItem(
            titleResId = R.string.pv_t0_label,
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
                this?.pvRh != 0f -> R.string.pv_rh_label
                else -> R.string.pv_t1_label
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
        StatsItem(titleResId = R.string.pv_t2_label, value = this?.pvT2?.let { StatsItem.Value.FloatVal(value = if (it > 80) null else it) }),
        StatsItem(titleResId = R.string.pv_t3_label, value = this?.pvT3?.let { StatsItem.Value.FloatVal(value = if (it > 80) null else it) }),
        StatsItem(titleResId = R.string.cotwo, value = this?.pvCO2_1?.let { StatsItem.Value.IntVal(value = if (it < 400) null else it) }),
        StatsItem(titleResId = R.string.timer, value = this?.let { StatsItem.Value.IntVal(value = it.pvTimer, target = timer0) }),
        StatsItem(titleResId = R.string.counter, value = this?.pvTmrCount?.let { StatsItem.Value.IntVal(value = it) }),
        StatsItem(titleResId = R.string.power, value = this?.power?.let { StatsItem.Value.IntVal(value = it) }, valueColor = IsidaColor.Power),
        StatsItem(titleResId = R.string.flap, value = this?.pvFlap?.let { StatsItem.Value.IntVal(value = it) }),
        StatsItem(
            titleResId = R.string.fuses,
            value = this?.fuses?.let {
                StatsItem.Value.TextResId(
                    value = when (it) {
                        1 -> R.string.fuses_0
                        2 -> R.string.fuses_1
                        4 -> R.string.fuses_2
                        8 -> R.string.fuses_3
                        else -> R.string.no
                    }
                )
            }),
        StatsItem(
            titleResId = R.string.errors,
            value = this?.errors?.let {
                StatsItem.Value.TextResId(
                    value = when (it) {
                        1 -> R.string.error_01
                        2 -> R.string.error_02
                        4 -> R.string.error_04
                        8 -> R.string.error_08
                        else -> R.string.no
                    }
                )
            }),
        StatsItem(
            titleResId = R.string.warnings,
            value = this?.warning?.let {
                StatsItem.Value.TextResId(
                    value = when (it) {
                        1 -> R.string.warning_01
                        2 -> R.string.warning_02
                        4 -> R.string.warning_04
                        8 -> R.string.warning_08
                        else -> R.string.no
                    }
                )
            }),
        StatsItem(
            titleResId = R.string.state,
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
                        IsidaCommands.DeviceMode.DISABLE -> R.string.device_mode_disabled
                        IsidaCommands.DeviceMode.ONLY_ROTATION -> R.string.device_mode_turn
                        IsidaCommands.DeviceMode.ENABLE -> when {
                            extras.contains(IsidaCommands.DeviceModeExtra.EXTRA_3) -> R.string.device_mode_extra_3
                            extras.contains(IsidaCommands.DeviceModeExtra.EXTRA_4) -> R.string.device_mode_extra_4
                            else -> R.string.device_mode_enabled
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
        StatsItem(titleResId = R.string.extendMode, value = this?.extendMode?.let {
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
        StatsItem(titleResId = R.string.programm, value = this?.programm?.let {
            StatsItem.Value.TextResId(
                value = when (it) {
                    0 -> R.string.no
                    1 -> R.string.chickens
                    2 -> R.string.ducklings
                    3 -> R.string.ducklings
                    4 -> R.string.quail
                    else -> null
                }
            )
        }),
        StatsItem(titleResId = R.string.incubation, value = this?.hours?.let { StatsItem.Value.IntVal(value = it) }),
        StatsItem(titleResId = R.string.energyMeter, value = this?.energyMeter?.let { StatsItem.Value.IntVal(value = it) }),
    )
}