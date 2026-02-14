package ua.graviton.isida.ui.home.program

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Upload
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
import com.whoppah.common.compose.theme.WhoppahTheme
import com.whoppah.common.compose.ui.DeviceNotConnectedPlaceholder
import com.whoppah.common.resources.Res
import com.whoppah.common.resources.home_tab_programtable
import com.whoppah.metrox.viewmodel.injectedViewModel
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource
import ua.graviton.isida.data.protocol.packets.TableDay
import ua.graviton.isida.data.protocol.packets.v1.TableDayV1
import ua.graviton.isida.ui.navigation.HomeTabScreen
import ua.graviton.isida.ui.navigation.result.ResultEffect
import ua.graviton.isida.ui.navigation.result.ResultEventBus
import ua.graviton.isida.ui.setday.SetDayScreenResult

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
        onSend = viewModel::sendTable,
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
    onSend: () -> Unit,
    onTableSelected: (Int) -> Unit,
    onOpenReset: () -> Unit,
    onCloseReset: () -> Unit,
    onApplyPreset: (ProgramPreset) -> Unit,
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
                onSend = onSend,
                onTableSelected = onTableSelected,
                onOpenReset = onOpenReset,
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )

            val table = state.table
            if (table == null && !state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "No data. Press Refresh to get program table.")
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
            presets = state.availablePresets,
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
    onSend: () -> Unit,
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
                Text(text = "Table $selectedTable")
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                (1..4).forEach { number ->
                    DropdownMenuItem(
                        text = { Text("Table $number") },
                        onClick = {
                            onTableSelected(number)
                            expanded = false
                        }
                    )
                }
            }
        }

        IconButton(
            onClick = onFetch,
            enabled = !isLoading,
        ) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
        }

        IconButton(
            onClick = onOpenReset,
            enabled = !isLoading,
        ) {
            Icon(imageVector = Icons.Default.Tune, contentDescription = "Presets")
        }

        Spacer(Modifier.weight(1f))

        FilledIconButton(
            onClick = onSend,
            enabled = !isLoading && hasData,
        ) {
            Icon(imageVector = Icons.Default.Upload, contentDescription = "Upload Table")
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
        }
    }
}

@Composable
private fun ResetDialog(
    presets: List<ProgramPreset>,
    onDismiss: () -> Unit,
    onConfirm: (ProgramPreset) -> Unit,
) {
    var selectedPreset by remember { mutableStateOf(presets.firstOrNull()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Reset to Default") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "Choose a preset to reset the table values:")
                presets.forEach { preset ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedPreset == preset,
                            onClick = { selectedPreset = preset }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = preset.name, style = MaterialTheme.typography.bodyLarge)
                            Text(
                                text = preset.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { selectedPreset?.let { onConfirm(it) } },
                enabled = selectedPreset != null
            ) {
                Text("Reset")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
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
                is Table.Row.Header -> stickyHeader(key = "header_$index") {
                    TableRow(row, horizontalScrollState)
                }
                is Table.Row.Default -> item(key = "row_$index") {
                    TableRow(
                        row = row,
                        scrollState = horizontalScrollState,
                        modifier = Modifier.clickable(enabled = row.day != null) {
                            row.day?.let { onEditDay(index, it) }
                        }
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
            .then(if (row is Table.Row.Header) Modifier.background(MaterialTheme.colorScheme.surfaceVariant) else Modifier)
            .horizontalScroll(scrollState)
            .padding(vertical = if (row is Table.Row.Header) 8.dp else 12.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        row.cells.forEach { cell ->
            Text(
                text = cell.value(),
                modifier = if (cell.width != Dp.Unspecified) Modifier.width(cell.width) else Modifier,
                fontWeight = if (row is Table.Row.Header) FontWeight.Bold else FontWeight.Normal,
                textAlign = TextAlign.Center,
                style = if (row is Table.Row.Header) MaterialTheme.typography.labelMedium else MaterialTheme.typography.bodyMedium,
                color = cell.style.color ?: Color.Unspecified
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    WhoppahTheme {
        ProgramScreen(
            state = ProgramViewState(
                table = null // Or provide a mock table if needed
            ),
            onFetch = {},
            onSend = {},
            onTableSelected = {},
            onOpenReset = {},
            onCloseReset = {},
            onApplyPreset = {},
            onEditDay = { _, _ -> },
        )
    }
}