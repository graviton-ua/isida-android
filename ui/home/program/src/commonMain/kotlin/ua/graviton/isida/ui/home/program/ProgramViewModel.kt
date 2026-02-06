package ua.graviton.isida.ui.home.program

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whoppah.metrox.viewmodel.ViewModelKey
import com.whoppah.metrox.viewmodel.ViewModelScope
import com.whoppah.util.ObservableLoadingCounter
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.*
import ua.graviton.isida.data.protocol.packets.v1.StatusPacketV1
import ua.graviton.isida.domain.observers.ObserveStatus

@Inject
@ViewModelKey(ProgramViewModel::class)
@ContributesIntoMap(ViewModelScope::class)
class ProgramViewModel(
    observeStatus: ObserveStatus,
) : ViewModel() {
    private val loadingState = ObservableLoadingCounter()
    private val headerState = MutableStateFlow("")
    private val itemsState = MutableStateFlow<List<String>>(emptyList())

    val state: StateFlow<ProgramViewState> = combine(
        observeStatus.flow.mapNotNull { packet ->
            when (packet) {
                is StatusPacketV1 -> packet.node
                else -> null
            }
        }.onStart { emit(0) },
        headerState, itemsState, loadingState.observable
    ) { node, header, items, loading ->
        ProgramViewState(
            cellNumber = node,
            header = header,
            items = items,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ProgramViewState.Empty,
    )
}