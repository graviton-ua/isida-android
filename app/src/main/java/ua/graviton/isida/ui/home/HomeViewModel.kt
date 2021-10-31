package ua.graviton.isida.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ua.graviton.isida.domain.observers.ObserveDeviceData
import ua.graviton.isida.ui.utils.ObservableLoadingCounter
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    observeDeviceData: ObserveDeviceData,
) : ViewModel() {
    private val loadingState = ObservableLoadingCounter()
    private val pendingActions = MutableSharedFlow<HomeAction>()

    val state: StateFlow<HomeViewState> = combine(
        observeDeviceData.flow, loadingState.observable
    ) { data, loading ->
        HomeViewState(
            isLoading = loading,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeViewState.Empty,
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

    fun submitAction(action: HomeAction) {
        viewModelScope.launch { pendingActions.emit(action) }
    }
}