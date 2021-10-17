package ua.graviton.isida.ui.stats

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import ua.graviton.isida.R
import ua.graviton.isida.data.bl.model.DataPackageDto
import ua.graviton.isida.domain.observers.ObserveDeviceData
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    observeDeviceData: ObserveDeviceData,
) : ViewModel() {

    val state: LiveData<StatsViewState> = observeDeviceData.flow
        .map {
            val deviceBgColor = when {
                it != null && it.fuses+it.errors+it.warning > 0 -> R.color.red_500
                it != null && it.state == 1 -> R.color.green_500
                it != null && it.state == 2 -> R.color.yellow_500
                else -> null
            }

            // Создаём модель состояния экрана
            StatsViewState(
                titleDeviceId = it?.cellId,
                titleDeviceBackgroundColor = deviceBgColor,
                items = it.toItems(),
            )
        }
        .onStart { emit(StatsViewState.Empty) }
        .asLiveData(viewModelScope.coroutineContext)
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
                this != null && pvT0 > spT0 -> R.color.red_900
                this != null && pvT0 < spT0 -> R.color.indigo_800
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
                this != null && pvT1 > spT1 -> R.color.red_900
                this != null && pvT1 < spT1 -> R.color.indigo_800
                else -> null
            }
        ),
        StatsItem(titleResId = R.string.pv_t2_label, value = this?.pvT2?.let { StatsItem.Value.FloatVal(value = if (it > 80) null else it) }),
        StatsItem(titleResId = R.string.pv_t3_label, value = this?.pvT3?.let { StatsItem.Value.FloatVal(value = if (it > 80) null else it) }),
        StatsItem(titleResId = R.string.cotwo, value = this?.pvCO2_1?.let { StatsItem.Value.IntVal(value = if (it < 400) null else it) }),
        StatsItem(titleResId = R.string.timer, value = this?.let { StatsItem.Value.IntVal(value = it.pvTimer, target = timer0) }),
        StatsItem(titleResId = R.string.counter, value = this?.pvTmrCount?.let { StatsItem.Value.IntVal(value = it) }),
        StatsItem(titleResId = R.string.power, value = this?.power?.let { StatsItem.Value.IntVal(value = it) }, valueColor = R.color.power),
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
                StatsItem.Value.TextResId(
                    value = when (it) {
                        0 -> R.string.state_Off
                        1 -> R.string.state_On
                        3 -> R.string.state_1
                        5 -> R.string.state_2
                        9 -> R.string.state_3
                        17 -> R.string.state_4
                        128 -> R.string.state_7
                        else -> null
                    }
                )
            },
            backgroundColor = this?.state?.let {
                when (it) {
                    0 -> R.color.blue_grey_100
                    1 -> R.color.green_500
                    2 -> R.color.yellow_500
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