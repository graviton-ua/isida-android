package ua.graviton.isida.ui.stats

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import ua.graviton.isida.domain.DataHolder
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val state = DataHolder.latestData
        .map { data ->
            data?.let {
                StatsViewState(
                    cellNumber = it.cellId,
                    temp0 = it.pvT0,
                    temp1 = it.pvT1,
                    temp2 = it.pvT2,
                    temp3 = it.pvT3,
                    rh = it.pvRh,
                    coTwo = it.pvCO2_1,
                    timer = it.pvTimer,
                    count = it.pvTmrCount,
                    flap = it.pvFlap,
                )
            } ?: StatsViewState.Empty
        }
        .onStart { emit(StatsViewState.Empty) }
        .asLiveData(viewModelScope.coroutineContext)
}