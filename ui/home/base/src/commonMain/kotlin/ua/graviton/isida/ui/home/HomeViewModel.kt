package ua.graviton.isida.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.whoppah.metrox.viewmodel.ViewModelKey
import com.whoppah.metrox.viewmodel.ViewModelScope
import com.whoppah.util.ObservableLoadingCounter
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ua.graviton.isida.data.bluetooth.ConnectionState
import ua.graviton.isida.data.protocol.commands.v1.DeviceModeCommandV1
import ua.graviton.isida.domain.bluetooth.DeviceConnectionManager
import ua.graviton.isida.domain.interactors.SendCommand

@Inject
@ViewModelKey(HomeViewModel::class)
@ContributesIntoMap(ViewModelScope::class)
class HomeViewModel(
    private val deviceConnectionManager: DeviceConnectionManager,
    private val sendCommand: SendCommand,
) : ViewModel() {
    private val logger by lazy { Logger.withTag("HomeViewModel") }
    private val loadingState = ObservableLoadingCounter()
    private val pendingActions = MutableSharedFlow<HomeAction>()

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

    fun disconnect() {
        viewModelScope.launch { deviceConnectionManager.disconnect() }
    }

    fun sendTest() {
        viewModelScope.launch {
            val command = DeviceModeCommandV1(mode = 1)
            sendCommand(command)
                .onSuccess { logger.d { "Command sent" } }
                .onFailure { logger.w(it) { "Failed to send command" } }
        }
    }

    fun submitAction(action: HomeAction) {
        viewModelScope.launch { pendingActions.emit(action) }
    }
}