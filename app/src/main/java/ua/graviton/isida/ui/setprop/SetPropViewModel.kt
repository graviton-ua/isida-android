package ua.graviton.isida.ui.setprop

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ua.graviton.isida.domain.observers.ObserveDeviceData
import javax.inject.Inject

@HiltViewModel
class SetPropViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    observeDeviceData: ObserveDeviceData,
) : ViewModel() {
    val events = MutableSharedFlow<SetPropEvent>()
    private val pendingActions = MutableSharedFlow<SetPropAction>()

    val state: StateFlow<SetPropViewState> = flow {
        emit(SetPropViewState.Empty)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SetPropViewState.Empty,
    )

    init {
        // Listen actions
        viewModelScope.launch {
            pendingActions.collect { action ->
                when (action) {
                    else -> Unit
                }
            }
        }
    }

    fun submitAction(action: SetPropAction) {
        viewModelScope.launch { pendingActions.emit(action) }
    }
}