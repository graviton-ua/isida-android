package ua.graviton.isida.ui.setprop

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import ua.graviton.isida.domain.models.getProperty
import ua.graviton.isida.domain.observers.ObserveDeviceData
import javax.inject.Inject

@HiltViewModel
class SetPropViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    observeDeviceData: ObserveDeviceData,
) : ViewModel() {
    val events = MutableSharedFlow<SetPropEvent>()
    private val pendingActions = MutableSharedFlow<SetPropAction>()

    private val _id = MutableStateFlow<String?>(savedStateHandle["id"])
    val state: StateFlow<SetPropViewState> = combine(
        _id.onEach {  Timber.d("Property id: $it") },
        observeDeviceData.flow.onEach {  Timber.d("Data: $it") }
    ) { id, data ->
        if (id == null) return@combine SetPropViewState.Empty
        if (data != null) {
            val property = data.getProperty(id)
            if (property != null)
                SetPropViewState.Success(
                    property = property,
                )
            else SetPropViewState.NotFound
        } else SetPropViewState.NoData
    }.onEach { Timber.d("State: $it") }.stateIn(
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