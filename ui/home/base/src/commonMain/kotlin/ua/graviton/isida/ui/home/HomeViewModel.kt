package ua.graviton.isida.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ua.isida.metrox.viewmodel.ViewModelKey
import ua.isida.metrox.viewmodel.ViewModelScope
import com.whoppah.util.ObservableLoadingCounter
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ua.graviton.isida.data.bluetooth.ConnectionState
import ua.graviton.isida.domain.bluetooth.DeviceConnectionManager

@Inject
@ViewModelKey(HomeViewModel::class)
@ContributesIntoMap(ViewModelScope::class)
class HomeViewModel(
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