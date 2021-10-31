package ua.graviton.isida.ui.home.prop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ua.graviton.isida.domain.observers.ObserveDeviceData
import ua.graviton.isida.ui.utils.ObservableLoadingCounter
import javax.inject.Inject

@HiltViewModel
class PropViewModel @Inject constructor(
    observeDeviceData: ObserveDeviceData,
) : ViewModel() {
    private val loadingState = ObservableLoadingCounter()
    private val pendingActions = MutableSharedFlow<PropAction>()

    val state: StateFlow<PropViewState> = combine(
        observeDeviceData.flow, loadingState.observable
    ) { data, loading ->
        PropViewState(
            cellNumber = data?.deviceNumber ?: 0,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PropViewState.Empty,
    )

    init {
        viewModelScope.launch {
            pendingActions.collect { action ->
                when (action) {
                    //is PropAction.RefreshCart -> observeShopCart(Unit)
                    else -> Unit
                }
            }
        }
    }

    fun submitAction(action: PropAction) {
        viewModelScope.launch { pendingActions.emit(action) }
    }
}