package ua.isida.ui.home.program

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material.icons.outlined.Summarize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import ua.isida.common.ui.compose.theme.AppTheme
import ua.isida.common.ui.compose.ui.DeviceNotConnectedPlaceholder
import ua.isida.common.ui.navigation.HomeTabScreen
import ua.isida.common.ui.navigation.result.ResultEffect
import ua.isida.common.ui.navigation.result.ResultEventBus
import ua.isida.common.ui.resources.*
import ua.isida.data.protocol.packets.TableDay
import ua.isida.data.protocol.packets.v1.TableDayV1
import ua.isida.metrox.viewmodel.injectedViewModel
import ua.isida.ui.setday.SetDayScreenResult

@Serializable
data object ProgramScreen : HomeTabScreen {
    override val icon: ImageVector = Icons.Outlined.Summarize
    override val title: StringResource = Res.string.home_tab_programtable
}

@Composable
internal fun ProgramScreen(
    viewModel: ProgramViewModel = injectedViewModel(),
    resultBus: ResultEventBus,
    navigateSetDay: (Int, TableDay) -> Unit,
) {
    ResultEffect<SetDayScreenResult>(resultEventBus = resultBus) { viewModel.onDayUpdated(it.index, it.day) }

    val state by viewModel.state.collectAsStateWithLifecycle()
    ProgramScreen(
        state = state,
        onFetch = viewModel::fetchTable,
        onTableSelected = viewModel::selectTableHeader,
        onOpenReset = viewModel::openResetDialog,
        onCloseReset = viewModel::closeResetDialog,
        onApplyPreset = viewModel::applyPreset,
        onEditDay = navigateSetDay,
    )
}

@Composable
private fun ProgramScreen(
    state: ProgramViewState,
    onFetch: () -> Unit,
    onTableSelected: (Int) -> Unit,
    onOpenReset: () -> Unit,
    onCloseReset: () -> Unit,
    onApplyPreset: () -> Unit,
    onEditDay: (Int, TableDay) -> Unit,
) {
    if (!state.deviceConnected) {
        DeviceNotConnectedPlaceholder()
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            ControlPanel(
                selectedTable = state.selectedTable,
                hasData = state.table != null,
                isLoading = state.isLoading,
                onFetch = onFetch,
                onTableSelected = onTableSelected,
                onOpenReset = onOpenReset,
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )

            val table = state.table
            if (table == null && !state.isLoading) {
                Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
                    Text(
                        text = stringResource(Res.string.program_table_no_data),
                        textAlign = TextAlign.Center
                    )
                }
            } else if (table != null) {
                ProgramTable(
                    table = table,
                    onEditDay = onEditDay,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

    if (state.showResetDialog) {
        ResetDialog(
            onDismiss = onCloseReset,
            onConfirm = onApplyPreset
        )
    }
}

@Composable
private fun ControlPanel(
    selectedTable: Int,
    hasData: Boolean,
    isLoading: Boolean,
    onFetch: () -> Unit,
    onTableSelected: (Int) -> Unit,
    onOpenReset: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box {
            OutlinedButton(
                onClick = { expanded = true },
                enabled = !isLoading,
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(text = stringResource(Res.string.program_table_number, selectedTable))
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                (1..4).forEach { number ->
                    DropdownMenuItem(
                        text = { Text(text = stringResource(Res.string.program_table_number, number)) },
                        onClick = {
                            onTableSelected(number)
                            expanded = false
                        }
                    )
                }
            }
        }

        TextButton(
            onClick = onFetch,
            enabled = !isLoading,
        ) {
            Text(text = stringResource(Res.string.btn_refresh))
        }

        IconButton(
            onClick = onOpenReset,
            enabled = !isLoading,
        ) {
            Icon(imageVector = Icons.Default.Restore, contentDescription = stringResource(Res.string.content_desc_presets))
        }

        Spacer(Modifier.weight(1f))

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
        }
    }
}

@Composable
private fun ResetDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(Res.string.dialog_reset_title)) },
        text = {
            Text(text = stringResource(Res.string.dialog_reset_message))
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
            ) {
                Text(stringResource(Res.string.btn_reset))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(Res.string.btn_cancel))
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ProgramTable(
    table: Table,
    onEditDay: (Int, TableDay) -> Unit,
    modifier: Modifier = Modifier,
) {
    val horizontalScrollState = rememberScrollState()

    LazyColumn(modifier = modifier) {
        table.rows.forEachIndexed { index, row ->
            when (row) {
                is Table.Row.StickyHeader -> stickyHeader(key = "header_$index") {
                    TableRow(row, horizontalScrollState)
                }

                is Table.Row.Header -> item(key = "header_$index", contentType = "header") {
                    TableRow(row, horizontalScrollState)
                }

                is Table.Row.Day -> item(key = "row_$index", contentType = "day") {
                    TableRow(
                        row = row,
                        scrollState = horizontalScrollState,
                        modifier = Modifier.clickable { onEditDay(row.index, row.day) }
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        thickness = 0.5.dp,
                        color = Color.LightGray
                    )
                }
            }
        }
    }
}

@Composable
private fun TableRow(
    row: Table.Row,
    scrollState: ScrollState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (row is Table.Row.Header || row is Table.Row.StickyHeader)
                    Modifier.background(MaterialTheme.colorScheme.surfaceVariant)
                else Modifier
            )
            .horizontalScroll(scrollState)
            .padding(vertical = if (row is Table.Row.Header) 8.dp else 12.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        row.cells.forEach { cell ->
            Text(
                text = cell.value(),
                modifier = if (cell.width != Dp.Unspecified) Modifier.width(cell.width) else Modifier,
                fontWeight = if (row is Table.Row.Header || row is Table.Row.StickyHeader) FontWeight.Bold else FontWeight.Normal,
                textAlign = TextAlign.Center,
                style = if (row is Table.Row.Header || row is Table.Row.StickyHeader) MaterialTheme.typography.labelMedium else MaterialTheme.typography.bodyMedium,
                color = cell.style.color ?: Color.Unspecified
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        ProgramScreen(
            state = ProgramViewState(
                deviceConnected = true,
                table = buildTable {
                    header {
                        cell(width = 60.dp) { stringResource(Res.string.program_table_day) }
                        cell(width = 80.dp) { stringResource(Res.string.program_table_t0) }
                        cell(width = 80.dp) { stringResource(Res.string.program_table_t1) }
                        cell(width = 60.dp) { stringResource(Res.string.program_table_rh) }
                        cell(width = 60.dp) { stringResource(Res.string.program_table_flp) }
                        cell(width = 60.dp) { stringResource(Res.string.program_table_tr) }
                        cell(width = 60.dp) { stringResource(Res.string.program_table_cl) }
                    }
                    repeat(30) { index ->
                        val day = TableDayV1(37.5f, 30.0f, 55, 10, 1, 0)
                        day(index, day) {
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
            ),
            onFetch = {},
            onTableSelected = {},
            onOpenReset = {},
            onCloseReset = {},
            onApplyPreset = {},
            onEditDay = { _, _ -> },
        )
    }
}