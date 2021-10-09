package ua.graviton.isida.ui.stats

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import ua.graviton.isida.R
import ua.graviton.isida.data.bl.model.DataPackageDto
import ua.graviton.isida.domain.DataHolder
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val state = DataHolder.latestData
        .map { data ->
            StatsViewState(
                deviceId = data?.cellId,
                items = data.toItems(),
            )
        }
        .onStart { emit(StatsViewState.Empty) }
        .asLiveData(viewModelScope.coroutineContext)
}

private fun DataPackageDto?.toItems(): List<StatsItem> {
    return listOf(
        StatsItem(titleResId = R.string.sensor0, value = this?.pvT0),
        StatsItem(titleResId = R.string.sensor1, value = this?.pvT1),
        StatsItem(titleResId = R.string.sensor2, value = this?.pvT2),
        StatsItem(titleResId = R.string.sensor3, value = this?.pvT3),
        StatsItem(titleResId = R.string.cotwo, value = this?.pvCO2_1),
        StatsItem(titleResId = R.string.timer, value = this?.pvTimer),
        StatsItem(titleResId = R.string.counter, value = this?.pvTmrCount),
        StatsItem(titleResId = R.string.flap, value = this?.pvFlap),
    )
}