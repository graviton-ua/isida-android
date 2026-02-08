package ua.graviton.isida.ui.setday

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.whoppah.metrox.viewmodel.ViewModelAssistedFactory
import com.whoppah.metrox.viewmodel.ViewModelKey
import com.whoppah.metrox.viewmodel.ViewModelScope
import com.whoppah.util.AppCoroutineDispatchers
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesIntoMap
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import ua.graviton.isida.data.protocol.packets.TableDay

@AssistedInject
class SetDayViewModel(
    @Assisted private val index: Int,
    @Assisted private val day: TableDay,
    dispatchers: AppCoroutineDispatchers,
) : ViewModel() {

    @AssistedFactory
    @ViewModelKey(SetDayViewModel::class)
    @ContributesIntoMap(ViewModelScope::class)
    interface Factory : ViewModelAssistedFactory {
        fun create(index: Int, day: TableDay): SetDayViewModel
    }

    private val logger by lazy { Logger.withTag("SetDayViewModel") }

    private val _events = Channel<SetDayViewEvent>(Channel.BUFFERED)
    val events: Flow<SetDayViewEvent> = _events.receiveAsFlow()

    val state: StateFlow<SetDayViewState> = flowOf(1).map { _ ->
        SetDayViewState(
            waitingForData = true,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SetDayViewState(),
    )


    fun send() {

    }
}