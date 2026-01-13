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
import ua.graviton.isida.domain.observers.ObserveDeviceData
import java.util.UUID

@Inject
@ViewModelKey(ReportViewModel::class)
@ContributesIntoMap(ViewModelScope::class)
class ReportViewModel(
    observeDeviceData: ObserveDeviceData,
) : ViewModel() {
    private val loadingState = ObservableLoadingCounter()
    private val pendingActions = MutableSharedFlow<ReportAction>()
    private val headerState = MutableStateFlow("")
    private val itemsState = MutableStateFlow<List<String>>(emptyList())

    val state: StateFlow<ReportViewState> = combine(
        observeDeviceData.flow, headerState, itemsState, loadingState.observable
    ) { data, header, items, loading ->
        ReportViewState(
            cellNumber = data?.deviceNumber ?: 0,
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