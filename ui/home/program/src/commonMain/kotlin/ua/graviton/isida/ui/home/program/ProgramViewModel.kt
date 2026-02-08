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
import ua.graviton.isida.data.protocol.packets.TableDay
import ua.graviton.isida.data.protocol.packets.TablePacket
import ua.graviton.isida.data.protocol.packets.v1.TableDayV1
import ua.graviton.isida.data.protocol.packets.v1.TablePacketV1
import ua.graviton.isida.domain.bluetooth.DeviceConnectionManager
import ua.graviton.isida.domain.interactors.GetProgramTable
import ua.graviton.isida.domain.interactors.UpdateProgramTable

@Inject
@ViewModelKey(ProgramViewModel::class)
@ContributesIntoMap(ViewModelScope::class)
class ProgramViewModel(
    manager: DeviceConnectionManager,
    private val getProgramTable: GetProgramTable,
    private val updateProgramTable: UpdateProgramTable,
) : ViewModel() {
    private val logger by lazy { Logger.withTag("ProgramViewModel") }

    private val connectionState = manager.connectionState
    private val selectedTable = MutableStateFlow(1)
    private val table = MutableStateFlow<TablePacket?>(null)
    private val loadingState = ObservableLoadingCounter()
    private val showResetDialog = MutableStateFlow(false)

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
        selectedTable,
        itemsState,
        loadingState.observable,
        showResetDialog
    ) { state, selected, items, loading, resetDialog ->
        val connected = state == ConnectionState.CONNECTED
        ProgramViewState(
            deviceConnected = connected,
            selectedTable = selected,
            isLoading = loading,
            items = if (connected) items else emptyList(),
            showResetDialog = resetDialog,
            availablePresets = ProgramPreset.ALL
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ProgramViewState.Empty,
    )

    private var fetchTableJob: Job? = null

    fun selectTableHeader(number: Int) {
        selectedTable.value = number
        fetchTable()
    }

    fun fetchTable() {
        fetchTableJob?.cancel()
        fetchTableJob = viewModelScope.launch {
            loadingState.addLoader()
            getProgramTable.byNumber(selectedTable.value)
                .onSuccess { table.value = it }
                .onFailure { logger.w(it) { "Failed to fetch table" } }
            loadingState.removeLoader()
        }
    }

    fun sendTable() {
        val currentTable = table.value ?: return
        viewModelScope.launch {
            loadingState.addLoader()
            updateProgramTable.executeSync(UpdateProgramTable.Params(currentTable))
                .onSuccess { logger.d { "Table updated successfully" } }
                .onFailure { logger.w(it) { "Failed to update table" } }
            loadingState.removeLoader()
        }
    }

    fun openResetDialog() {
        showResetDialog.value = true
    }

    fun closeResetDialog() {
        showResetDialog.value = false
    }

    fun applyPreset(preset: ProgramPreset) {
        table.value = preset.table
        closeResetDialog()
    }

    fun onDayUpdated(index: Int, day: TableDay) {
        //TODO: Currently index is coming as Day number (starting from 1..30). We should Use Index instead (starting from 0..29)
        logger.d { "onDayUpdated: $index, $day" }
        table.update { currentTable ->
            val result: TablePacket? = when (currentTable) {
                is TablePacketV1 -> {
                    if (day is TableDayV1) {
                        val dayIndex = index - 1
                        val newDays = currentTable.days.toMutableList()
                        if (dayIndex in newDays.indices) {
                            newDays[dayIndex] = day
                            currentTable.copy(days = newDays)
                        } else currentTable
                    } else currentTable
                }

                else -> currentTable
            }
            result
        }
    }
}
