package ua.graviton.isida.ui.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ua.graviton.isida.domain.observers.ObserveDeviceData
import ua.graviton.isida.ui.utils.ObservableLoadingCounter
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    observeDeviceData: ObserveDeviceData,
) : ViewModel() {
    private val loadingState = ObservableLoadingCounter()
    private val pendingActions = MutableSharedFlow<ReportAction>()

    val state: StateFlow<ReportViewState> = combine(
        observeDeviceData.flow, loadingState.observable
    ) { data, loading ->
        ReportViewState(
            cellNumber = data?.deviceNumber ?: 0,
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
                    //is ReportAction.RefreshCart -> observeShopCart(Unit)
                    else -> Unit
                }
            }
        }
    }

    fun submitAction(action: ReportAction) {
        viewModelScope.launch { pendingActions.emit(action) }
    }
}