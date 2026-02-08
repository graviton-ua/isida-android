package ua.graviton.isida.ui.home.program

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
                hasData = state.items.isNotEmpty(),
                isLoading = state.isLoading,
                onFetch = onFetch,
                onSend = onSend,
                onTableSelected = onTableSelected,
                onOpenReset = onOpenReset,
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )

            if (state.items.isEmpty() && !state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "No data. Press Refresh to get program table.")
                }
            } else {
                ProgramTable(
                    items = state.items,
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

@Composable
private fun ProgramTable(
    items: List<ProgramViewState.ProgramItem>,
    onEditDay: (Int, TableDay) -> Unit,
    modifier: Modifier = Modifier,
) {
    val horizontalScrollState = rememberScrollState()

    LazyColumn(modifier = modifier) {
        stickyHeader {
            HeaderRow(horizontalScrollState)
        }
        items(items) { item ->
            DataRow(
                item = item, scrollState = horizontalScrollState,
                onClick = {
                    onEditDay(
                        item.day,
                        TableDayV1(spT0 = item.t0, spT1 = item.t1, spRh = item.rh, spFlp = item.flp, spTr = item.tr, spCl = item.cl)
                    )
                },
                modifier = Modifier,
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 8.dp),
                thickness = 0.5.dp,
                color = Color.LightGray
            )
        }
    }
}

@Composable
private fun HeaderRow(scrollState: ScrollState) {
    Row(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(vertical = 8.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HeaderCell("Day", Modifier.width(60.dp))
        HeaderCell("T0", Modifier.width(80.dp))
        HeaderCell("T1", Modifier.width(80.dp))
        HeaderCell("Rh", Modifier.width(60.dp))
        HeaderCell("Flp", Modifier.width(60.dp))
        HeaderCell("Tr", Modifier.width(60.dp))
        HeaderCell("Cl", Modifier.width(60.dp))
    }
}

@Composable
private fun RowScope.HeaderCell(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.labelMedium
    )
}

@Composable
private fun DataRow(
    item: ProgramViewState.ProgramItem,
    scrollState: ScrollState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .horizontalScroll(scrollState)
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DataCell(item.day.toString(), Modifier.width(60.dp))
        DataCell(item.t0.toString(), Modifier.width(80.dp))
        DataCell(item.t1.toString(), Modifier.width(80.dp))
        DataCell(item.rh.toString(), Modifier.width(60.dp))
        DataCell(item.flp.toString(), Modifier.width(60.dp))
        DataCell(item.tr.toString(), Modifier.width(60.dp))
        DataCell(item.cl.toString(), Modifier.width(60.dp))
    }
}

@Composable
private fun RowScope.DataCell(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyMedium
    )
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    WhoppahTheme {
        ProgramScreen(
            state = ProgramViewState(
                items = listOf(
                    ProgramViewState.ProgramItem(1, 37.8f, 28.5f, 55, 0, 1, 0),
                    ProgramViewState.ProgramItem(2, 37.7f, 28.4f, 55, 0, 1, 0),
                )
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