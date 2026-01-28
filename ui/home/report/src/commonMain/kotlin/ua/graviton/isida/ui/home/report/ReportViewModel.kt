package ua.graviton.isida.ui.home.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whoppah.metrox.viewmodel.ViewModelKey
import com.whoppah.metrox.viewmodel.ViewModelScope
import com.whoppah.util.ObservableLoadingCounter
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ua.graviton.isida.data.protocol.packets.v1.StatusPacketV1
import ua.graviton.isida.domain.observers.ObserveStatus
import java.util.UUID

@Inject
@ViewModelKey(ReportViewModel::class)
@ContributesIntoMap(ViewModelScope::class)
class ReportViewModel(
    observeStatus: ObserveStatus,
) : ViewModel() {
    private val loadingState = ObservableLoadingCounter()
    private val pendingActions = MutableSharedFlow<ReportAction>()
    private val headerState = MutableStateFlow("")
    private val itemsState = MutableStateFlow<List<String>>(emptyList())

    val state: StateFlow<ReportViewState> = combine(
        observeStatus.flow.mapNotNull { packet ->
            when (packet) {
                is StatusPacketV1 -> packet.node
                else -> null
            }
        }.onStart { emit(0) },
        headerState, itemsState, loadingState.observable
    ) { node, header, items, loading ->
        ReportViewState(
            cellNumber = node,
            header = header,
            items = items,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ReportViewState.Empty,
    )

    init {
        viewModelScope.launch {
            pendingActions.collect { action ->
                when (action) {
                    is ReportAction.Start -> start().also { it.join() }
                    else -> Unit
                }
            }
        }
    }

    fun submitAction(action: ReportAction) {
        viewModelScope.launch { pendingActions.emit(action) }
    }


    private fun CoroutineScope.start() = launch {
        headerState.value = UUID.randomUUID().toString()
        itemsState.value = listOf(
            "andrew", "thomas", "roman", "lora"
        )
    }
}