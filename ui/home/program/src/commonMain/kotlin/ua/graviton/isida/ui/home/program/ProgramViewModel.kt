package ua.graviton.isida.ui.home.program

import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.whoppah.metrox.viewmodel.ViewModelKey
import com.whoppah.metrox.viewmodel.ViewModelScope
import com.whoppah.util.AppCoroutineDispatchers
import com.whoppah.util.ObservableLoadingCounter
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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
    dispatchers: AppCoroutineDispatchers,
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

    private val tableState = table.map { packet -> packet?.toState() }.flowOn(dispatchers.computation)

    val state: StateFlow<ProgramViewState> = combine(
        connectionState,
        selectedTable,
        tableState,
        loadingState.observable,
        showResetDialog
    ) { state, selected, table, loading, resetDialog ->
        val connected = state == ConnectionState.CONNECTED
        ProgramViewState(
            deviceConnected = connected,
            selectedTable = selected,
            isLoading = loading,
            table = if (connected) table else null,
            showResetDialog = resetDialog,
            availablePresets = ProgramPreset.ALL
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ProgramViewState(),
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
        val tableNumber = selectedTable.value
        val currentTable = table.value ?: return
        viewModelScope.launch {
            loadingState.addLoader()
            updateProgramTable.executeSync(UpdateProgramTable.Params(tableNumber, currentTable))
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
        logger.d { "onDayUpdated: $index, $day" }
        table.update { currentTable ->
            val result: TablePacket? = when (currentTable) {
                is TablePacketV1 -> {
                    if (day is TableDayV1) {
                        val newDays = currentTable.days.toMutableList()
                        if (index in newDays.indices) {
                            newDays[index] = day
                            currentTable.copy(days = newDays)
                        } else currentTable
                    } else currentTable
                }

                else -> currentTable
            }
            result
        }
    }


    private fun TablePacket.toState(): Table? = when (this) {
        is TablePacketV1 -> toState()
        else -> {
            logger.w { "Unknown table type" }
            null
        }
    }

    private fun TablePacketV1.toState(): Table = buildTable {
        stickyHeader {
            cell(width = 60.dp) { "Day" }
            cell(width = 80.dp) { "T0" }
            cell(width = 80.dp) { "T1" }
            cell(width = 60.dp) { "Rh" }
            cell(width = 60.dp) { "Flp" }
            cell(width = 60.dp) { "Tr" }
            cell(width = 60.dp) { "Cl" }
        }
        var alreadyAddedHeader = false
        days.forEachIndexed { index, day ->
            if (day.spT1 > 30.0 && !alreadyAddedHeader) {
                header {
                    cell(width = 460.dp) { "Посмотри внимательно на экран" }
                }
                alreadyAddedHeader = true
            }
            day(index = index, day = day) {
                cell(width = 60.dp) { (index + 1).toString() }
                cell(width = 80.dp) { day.spT0.toString() }
                cell(width = 80.dp) { day.spT1.toString() }
                cell(width = 60.dp) { day.spRh.toString() }
                cell(width = 60.dp) { day.spFlp.toString() }
                cell(width = 60.dp) { day.spTr.toString() }
                cell(width = 60.dp) { day.spCl.toString() }
            }
        }
    }
    // private fun TablePacketV1.toState(): Table = buildTable {
    //     header {
    //         cell(width = 60.dp) { "Day" }
    //         cell(width = 80.dp) { "T0" }
    //         cell(width = 80.dp) { "T1" }
    //         cell(width = 60.dp) { "Rh" }
    //         cell(width = 60.dp) { "Flp" }
    //         cell(width = 60.dp) { "Tr" }
    //         cell(width = 60.dp) { "Cl" }
    //     }
    //     days.take(15).forEachIndexed { index, day ->
    //         row(day) {
    //             cell(width = 60.dp) { (index + 1).toString() }
    //             cell(width = 80.dp) { day.spT0.toString() }
    //             cell(width = 80.dp) { day.spT1.toString() }
    //             cell(width = 60.dp) { day.spRh.toString() }
    //             cell(width = 60.dp) { day.spFlp.toString() }
    //             cell(width = 60.dp) { day.spTr.toString() }
    //             cell(width = 60.dp) { day.spCl.toString() }
    //         }
    //     }
    //     header {
    //         cell(width = 60.dp) { "Day" }
    //         cell(width = 80.dp) { "T0" }
    //         cell(width = 80.dp) { "T1" }
    //         cell(width = 60.dp) { "Rh" }
    //         cell(width = 60.dp) { "Flp" }
    //         cell(width = 60.dp) { "Tr" }
    //         cell(width = 60.dp) { "Cl" }
    //     }
    //     days.takeLast(15).forEachIndexed { index, day ->
    //         row(day) {
    //             cell(width = 60.dp) { (index + 1).toString() }
    //             cell(width = 80.dp) { day.spT0.toString() }
    //             cell(width = 80.dp) { day.spT1.toString() }
    //             cell(width = 60.dp) { day.spRh.toString() }
    //             cell(width = 60.dp) { day.spFlp.toString() }
    //             cell(width = 60.dp) { day.spTr.toString() }
    //             cell(width = 60.dp) { day.spCl.toString() }
    //         }
    //     }
    // }
}
