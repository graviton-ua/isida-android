package ua.graviton.isida.ui.scan

import android.bluetooth.BluetoothDevice
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ua.graviton.isida.ui.compose.TopAppBar
import ua.graviton.isida.ui.theme.IsidaTheme
import ua.graviton.isida.ui.utils.collectAsStateWithLifecycle

@Composable
fun ScanDevicesScreen(
    navigateUp: () -> Unit,
    onStartScanClicked: () -> Unit,
    onStopScanClicked: () -> Unit,
    onDeviceClicked: (BluetoothDevice) -> Unit,
) {
    ScanDevicesScreen(
        viewModel = hiltViewModel(),
        navigateUp = navigateUp,
        onStartScanClicked = onStartScanClicked,
        onStopScanClicked = onStopScanClicked,
        onDeviceClicked = onDeviceClicked,
    )
}

@Composable
fun ScanDevicesScreen(
    viewModel: ScanDevicesViewModel,
    navigateUp: () -> Unit,
    onStartScanClicked: () -> Unit,
    onStopScanClicked: () -> Unit,
    onDeviceClicked: (BluetoothDevice) -> Unit
) {
    val viewState by viewModel.state.collectAsStateWithLifecycle()

    ScanDevicesScreen(
        state = viewState,
        actioner = { action ->
            when (action) {
                is ScanDevicesAction.NavigateUp -> navigateUp()
                is ScanDevicesAction.StartScanClicked -> onStartScanClicked()
                is ScanDevicesAction.StopScanClicked -> onStopScanClicked()
                is ScanDevicesAction.OnDeviceClicked -> onDeviceClicked(action.device)
                else -> viewModel.submitAction(action)
            }
        }
    )
}

@Composable
private fun ScanDevicesScreen(
    state: ScanDevicesViewState,
    actioner: (ScanDevicesAction) -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    val lazyListState = rememberLazyListState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "Scan for devices") },
                navigationIcon = {
                    IconButton(onClick = { actioner(ScanDevicesAction.NavigateUp) }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = { if (state.isLoading) CircularProgressIndicator() },
                backgroundColor = MaterialTheme.colors.surface,
                contentColor = contentColorFor(MaterialTheme.colors.surface),
                contentPadding = WindowInsets.statusBars.asPaddingValues(),
                modifier = Modifier.fillMaxWidth()
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (state.isLoading) actioner(ScanDevicesAction.StopScanClicked)
                    else actioner(ScanDevicesAction.StartScanClicked)
                },
                backgroundColor = if (state.isLoading) Color.Red else Color.Green,
                modifier = Modifier.navigationBarsPadding()
            ) {
                Text(
                    text = if (state.isLoading) "Stop scan" else "Start scan",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        },
    ) {
        LazyColumn(
            state = lazyListState,
            contentPadding = WindowInsets.navigationBars.asPaddingValues(),
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {

            items(state.paired) { device ->
                Device(
                    device = device,
                    onClicked = { actioner(ScanDevicesAction.OnDeviceClicked(device)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            items(state.found) { device ->
                Device(
                    device = device,
                    onClicked = { actioner(ScanDevicesAction.OnDeviceClicked(device)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun Device(
    device: BluetoothDevice,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clickable { onClicked() }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(text = device.name ?: "")
        Text(text = device.address ?: "")
    }
}

@Preview
@Composable
private fun Preview() {
    IsidaTheme {
        ScanDevicesScreen(
            state = ScanDevicesViewState.Preview,
            actioner = {}
        )
    }
}