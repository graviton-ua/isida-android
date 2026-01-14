package ua.graviton.isida.ui.scan

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.whoppah.common.compose.theme.WhoppahTheme
import com.whoppah.common.compose.ui.WhScaffold
import com.whoppah.metrox.viewmodel.injectedViewModel
import kotlinx.serialization.Serializable
import ua.graviton.isida.data.bluetooth.DiscoveredDevice

@Serializable
data object ScanDevicesScreen : NavKey

@Composable
internal fun ScanDevicesScreen(
    viewModel: ScanDevicesViewModel = injectedViewModel(),
    navigateUp: () -> Unit,
    onDeviceSelected: (String) -> Unit,
) {
    val viewState by viewModel.state.collectAsStateWithLifecycle()

    ScanDevicesScreen(
        state = viewState,
        actioner = { action ->
            when (action) {
                is ScanDevicesAction.NavigateUp -> navigateUp()
                is ScanDevicesAction.OnDeviceClicked -> {
                    // Stop scanning before leaving
                    viewModel.submitAction(ScanDevicesAction.StopScanClicked)
                    // Pass the ID back to the navigation host
                    onDeviceSelected(action.device.address.value)
                }

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
    WhScaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Scan for devices") },
                navigationIcon = {
                    IconButton(onClick = { actioner(ScanDevicesAction.NavigateUp) }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = { if (state.isLoading) CircularProgressIndicator() },
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
                containerColor = if (state.isLoading) Color.Red else Color.Green,
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
            contentPadding = WindowInsets.navigationBars.asPaddingValues(),
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            if (state.paired.isNotEmpty()) {
                item { Header("Paired Devices") }
                items(state.paired) { device ->
                    DeviceItem(device) { actioner(ScanDevicesAction.OnDeviceClicked(device)) }
                }
            }

            if (state.found.isNotEmpty()) {
                item { Header("New Devices") }
                items(state.found) { device ->
                    DeviceItem(device) { actioner(ScanDevicesAction.OnDeviceClicked(device)) }
                }
            }
        }
    }
}

@Composable
private fun Header(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
private fun DeviceItem(
    device: DiscoveredDevice,
    onClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClicked() }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(text = device.name.ifEmpty { "Unknown Device" }, style = MaterialTheme.typography.bodyLarge)
        Text(text = device.address.value, style = MaterialTheme.typography.bodySmall)
    }
}

@Preview
@Composable
private fun Preview() {
    WhoppahTheme {
        ScanDevicesScreen(
            state = ScanDevicesViewState(),
            actioner = {}
        )
    }
}