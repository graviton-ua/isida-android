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
                    cellNumber = it.cellNumber,
                    temp0 = it.temp0,
                    temp1 = it.temp1,
                    temp2 = it.temp2,
                    temp3 = it.temp3,
                    rh = it.rh,
                    coTwo = it.coTwo,
                    timer = it.timer,
                    count = it.count,
                    flap = it.flap,
                )
            } ?: StatsViewState.Empty
        }
        .onStart { emit(StatsViewState.Empty) }
        .asLiveData(viewModelScope.coroutineContext)
}