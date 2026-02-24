package ua.graviton.isida.ui.home.program

import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import ua.isida.common.ui.resources.*
import ua.isida.metrox.viewmodel.ViewModelKey
import ua.isida.metrox.viewmodel.ViewModelScope
import ua.isida.util.AppCoroutineDispatchers
import ua.isida.util.ObservableLoadingCounter
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
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
        }.also { it.invokeOnCompletion { loadingState.removeLoader() } }
    }

    private var sendTableJob: Job? = null

    private fun sendTable() {
        val tableNumber = selectedTable.value
        val currentTable = table.value ?: return
        sendTableJob?.cancel()
        sendTableJob = viewModelScope.launch {
            loadingState.addLoader()
            updateProgramTable.executeSync(UpdateProgramTable.Params(tableNumber, currentTable))
                .onSuccess { logger.d { "Table updated successfully" } }
                .onFailure { logger.w(it) { "Failed to update table" } }
        }.also { it.invokeOnCompletion { loadingState.removeLoader() } }
    }

    fun openResetDialog() {
        showResetDialog.value = true
    }

    fun closeResetDialog() {
        showResetDialog.value = false
    }

    fun applyPreset() {
        val index = selectedTable.value - 1
        val presets = ProgramPreset.ALL
        if (index in presets.indices) {
            table.value = presets[index].table
            sendTable()
        }
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
        // After updating each day we always update table
        sendTable()
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
            cell(width = 60.dp) { stringResource(Res.string.program_table_day) }
            cell(width = 80.dp) { stringResource(Res.string.program_table_t0) }
            cell(width = 80.dp) { stringResource(Res.string.program_table_t1) }
            cell(width = 60.dp) { stringResource(Res.string.program_table_rh) }
            cell(width = 60.dp) { stringResource(Res.string.program_table_flp) }
            cell(width = 60.dp) { stringResource(Res.string.program_table_tr) }
            cell(width = 60.dp) { stringResource(Res.string.program_table_cl) }
        }
        var alreadyAddedHeader = false
        days.forEachIndexed { index, day ->
            if (day.spT0 < 30.0 && !alreadyAddedHeader) {
                header {
                    cell(width = 460.dp) { stringResource(Res.string.program_table_complete) }
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
                cell(width = 60.dp) { day.spCO2.toString() }
            }
        }
    }
}
