package ua.graviton.isida.ui.home.program

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.whoppah.util.ObservableLoadingCounter
import com.whoppah.metrox.viewmodel.ViewModelKey
import com.whoppah.metrox.viewmodel.ViewModelScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ua.graviton.isida.data.protocol.packets.TablePacket
import ua.graviton.isida.data.protocol.packets.v1.StatusPacketV1
import ua.graviton.isida.data.protocol.packets.v1.TableDayV1
import ua.graviton.isida.data.protocol.packets.v1.TablePacketV1
import ua.graviton.isida.domain.interactors.GetProgramTable
import ua.graviton.isida.domain.observers.ObserveStatus

@Inject
@ViewModelKey(ProgramViewModel::class)
@ContributesIntoMap(ViewModelScope::class)
class ProgramViewModel(
    private val getProgramTable: GetProgramTable,
    private val observeStatus: ObserveStatus,
) : ViewModel() {
    private val logger by lazy { Logger.withTag("ProgramViewModel") }

    private val table = MutableStateFlow<TablePacket?>(
        TablePacketV1(
            days = List(30) { index ->
                TableDayV1(
                    spT0 = 37.5f + index * 0.1f,
                    spT1 = 28.3f + index * 0.05f,
                    spRh = 50 + index,
                    spFlp = 10 + index,
                    spTr = 1,
                    spCl = 0
                )
            }
        )
    )
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
        observeStatus.flow.mapNotNull { packet ->
            when (packet) {
                is StatusPacketV1 -> packet.node
                else -> null
            }
        }.onStart { emit(0) },
        itemsState,
        loadingState.observable
    ) { node, items, loading ->
        ProgramViewState(
            isLoading = loading,
            cellNumber = node,
            items = items,
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
            getProgramTable()
                .onSuccess { table.value = it }
                .onFailure { logger.w(it) { "Failed to fetch table" } }
            loadingState.removeLoader()
        }
    }
}
