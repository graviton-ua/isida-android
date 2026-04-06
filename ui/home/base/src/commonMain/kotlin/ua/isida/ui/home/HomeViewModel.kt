package ua.isida.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ua.isida.data.bluetooth.ConnectionState
import ua.isida.domain.bluetooth.DeviceConnectionManager
import ua.isida.util.ObservableLoadingCounter

@Inject
@ViewModelKey(HomeViewModel::class)
@ContributesIntoMap(AppScope::class)
internal class HomeViewModel(
    private val deviceConnectionManager: DeviceConnectionManager,
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
}