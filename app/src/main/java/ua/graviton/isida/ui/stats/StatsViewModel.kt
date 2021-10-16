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
                it != null && it.pvT0 > it.pvT1 -> R.color.black
                it != null && it.pvT3 < it.pvT1 - 10 -> R.color.black
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
                StatsItem.Value.Float(
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
                StatsItem.Value.Float(
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
        StatsItem(titleResId = R.string.sensor2, value = this?.pvT2?.let { StatsItem.Value.Float(value = if (it > 80) null else it) }),
        StatsItem(titleResId = R.string.sensor3, value = this?.pvT3?.let { StatsItem.Value.Float(value = if (it > 80) null else it) }),
        StatsItem(titleResId = R.string.cotwo, value = this?.pvCO2_1?.let { StatsItem.Value.Int(value = if (it < 400) null else it) }),
        StatsItem(titleResId = R.string.timer, value = this?.let { StatsItem.Value.Int(value = it.pvTimer, target = timer0) }),
        StatsItem(titleResId = R.string.counter, value = this?.pvTmrCount?.let { StatsItem.Value.Int(value = it) }),
        StatsItem(titleResId = R.string.power, value = this?.power?.let { StatsItem.Value.Int(value = it) }, valueColor = R.color.power),
        StatsItem(titleResId = R.string.flap, value = this?.pvFlap?.let { StatsItem.Value.Int(value = it) }),
        StatsItem(titleResId = R.string.fuses, value = this?.fuses?.let {
            StatsItem.Value.Text(
                value = when (it) {
                    //255 -> "НЕТ"
                    211 -> "Andrew"
                    221 -> "Andrew"
                    231 -> "Andrew"
                    241 -> "Andrew"
                    else -> "NET"
                }
            )
        }),
        StatsItem(titleResId = R.string.errors, value = this?.errors?.let {
            StatsItem.Value.Text(
                value = when (it) {
                    //255 -> "НЕТ"
                    211 -> "Andrew"
                    221 -> "Andrew"
                    231 -> "Andrew"
                    241 -> "Andrew"
                    else -> "NET"
                }
            )
        }),
        StatsItem(titleResId = R.string.warnings, value = this?.warning?.let {
            StatsItem.Value.Text(
                value = when (it) {
                    //255 -> "НЕТ"
                    211 -> "Andrew"
                    221 -> "Andrew"
                    231 -> "Andrew"
                    241 -> "Andrew"
                    else -> "NET"
                }
            )
        }),
        StatsItem(
            titleResId = R.string.state,
            value = this?.state?.let {
                StatsItem.Value.Text(
                    value = when (it) {
                        0 -> "ОТКЛЮЧЕНА"
                        1 -> "ВКЛЮЧЕНА"
                        2 -> "ПОВОРОТ"
                        else -> null
                    }
                )
            },
            backgroundColor = this?.let {
                when {
                    it.pvT0 > it.pvT1 -> R.color.black
                    it.pvT3 < it.pvT1 - 10 -> R.color.black
                    else -> null
                }
            }
        ),
        //  расширенный режим работы  0-СИРЕНА; 1-ВЕНТ. 2-Форс НАГР. 3-Форс ОХЛЖД. 4-Форс ОСУШ. 5-Дубляж увлажнения
        StatsItem(titleResId = R.string.extendMode, value = this?.extendMode?.let {
            StatsItem.Value.Text(
                value = when (it) {
                    0 -> "СИРЕНА"
                    1 -> "ВЕНТИЛЯЦИЯ"
                    2 -> "Форс.НАГРЕВ"
                    3 -> "Форс.ОХЛАЖД."
                    4 -> "Форс.ОСУШЕН."
                    4 -> "УВЛАЖНЕНИЕ"
                    else -> null
                }
            )
        }),
        StatsItem(titleResId = R.string.programm, value = this?.programm?.let {
            StatsItem.Value.Text(
                value = when (it) {
                    0 -> "ОТКЛЮЧЕНА"
                    1 -> "ВКЛЮЧЕНА"
                    2 -> "ПОВОРОТ"
                    else -> null
                }
            )
        }),
        StatsItem(titleResId = R.string.incubation, value = this?.hours?.let { StatsItem.Value.Int(value = it) }),
        StatsItem(titleResId = R.string.energyMeter, value = this?.energyMeter?.let { StatsItem.Value.Int(value = it) }),
    )
}