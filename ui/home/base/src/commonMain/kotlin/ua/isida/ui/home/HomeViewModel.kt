package ua.isida.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ua.isida.metrox.viewmodel.ViewModelKey
import ua.isida.metrox.viewmodel.ViewModelScope
import ua.isida.util.ObservableLoadingCounter
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ua.isida.data.bluetooth.ConnectionState
import ua.isida.domain.bluetooth.DeviceConnectionManager

@Inject
@ViewModelKey(HomeViewModel::class)
@ContributesIntoMap(ViewModelScope::class)
class HomeViewModel(
    private val deviceConnectionManager: DeviceConnectionManager,
    private val audit: ua.isida.util.AuditLogger,
) : ViewModel() {
    private val loadingState = ObservableLoadingCounter()

    val state: StateFlow<HomeViewState> = combine(
        deviceConnectionManager.connectionState.map { it == ConnectionState.CONNECTED }, loadingState.observable
    ) { deviceConnected, loading ->
        HomeViewState(
            deviceConnected = deviceConnected,
            isLoading = loading,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeViewState.Empty,
    )

    fun disconnect() {
        viewModelScope.launch { deviceConnectionManager.disconnect() }
    }

    fun exportLogs() {
        audit.exportLogs()
    }
}