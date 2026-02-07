package ua.graviton.isida.ui.home.program

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.whoppah.metrox.viewmodel.ViewModelKey
import com.whoppah.metrox.viewmodel.ViewModelScope
import com.whoppah.util.ObservableLoadingCounter
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ua.graviton.isida.data.bluetooth.ConnectionState
import ua.graviton.isida.data.protocol.packets.TablePacket
import ua.graviton.isida.data.protocol.packets.v1.TablePacketV1
import ua.graviton.isida.domain.bluetooth.DeviceConnectionManager
import ua.graviton.isida.domain.interactors.GetProgramTable

@Inject
@ViewModelKey(ProgramViewModel::class)
@ContributesIntoMap(ViewModelScope::class)
class ProgramViewModel(
    manager: DeviceConnectionManager,
    private val getProgramTable: GetProgramTable,
) : ViewModel() {
    private val logger by lazy { Logger.withTag("ProgramViewModel") }

    private val connectionState = manager.connectionState
    private val table = MutableStateFlow<TablePacket?>(null)
    private val loadingState = ObservableLoadingCounter()

    private val itemsState = table.map { packet ->
        when (packet) {
            is TablePacketV1 -> packet.days.mapIndexed { index, day ->
                ProgramViewState.ProgramItem(
                    day = index + 1,
                    t0 = day.spT0,
                    t1 = day.spT1,
                    rh = day.spRh,
                    flp = day.spFlp,
                    tr = day.spTr,
                    cl = day.spCl,
                )
            }

            else -> emptyList()
        }
    }

    val state: StateFlow<ProgramViewState> = combine(
        connectionState,
        itemsState,
        loadingState.observable
    ) { state, items, loading ->
        ProgramViewState(
            isLoading = loading,
            items = if (state == ConnectionState.CONNECTED) items else emptyList(),
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ProgramViewState.Empty,
    )

    private var fetchTableJob: Job? = null

    fun fetchTable() {
        fetchTableJob?.cancel()
        fetchTableJob = viewModelScope.launch {
            loadingState.addLoader()
            getProgramTable.byNumber(1)
                .onSuccess { table.value = it }
                .onFailure { logger.w(it) { "Failed to fetch table" } }
            loadingState.removeLoader()
        }
    }
}
