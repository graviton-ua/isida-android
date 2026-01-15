package ua.graviton.isida.ui.scan

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.whoppah.common.compose.theme.WhoppahTheme
import com.whoppah.common.compose.toaster.AppToaster
import com.whoppah.common.compose.ui.WhScaffold
import com.whoppah.common.permissions.PermissionType
import com.whoppah.common.permissions.isGranted
import com.whoppah.common.permissions.rememberPermissionState
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

    // Auto-stop scanning when screen is disposed
    DisposableEffect(Unit) {
        onDispose { viewModel.stopScan() }
    }

    // Permission state for the camera.
    val scanPermission = rememberPermissionState(PermissionType.BLUETOOTH_SCAN) { isGranted ->
        if (isGranted) {
            viewModel.startScan()
        } else {
            // You can show a rationale here if needed.
            AppToaster.showError("BLUETOOTH_SCAN permission is required to start scanning.")
        }
    }

    val connectPermission = rememberPermissionState(PermissionType.BLUETOOTH_CONNECT) { isGranted ->
        if (isGranted) {
            //Do nothing
        } else {
            // You can show a rationale here if needed.
            AppToaster.showError("BLUETOOTH_CONNECT permission is required to connect device.")
        }
    }

    val startScan: () -> Unit = remember(scanPermission, viewModel) {
        { if (scanPermission.status.isGranted) viewModel.startScan() else scanPermission.launchPermissionRequest() }
    }

    val deviceClicked: (DiscoveredDevice) -> Unit = remember(connectPermission, onDeviceSelected, viewModel) {
        {
            if (connectPermission.status.isGranted) {
                viewModel.stopScan(); onDeviceSelected(it.address.value)
            } else connectPermission.launchPermissionRequest()
        }
    }

    ScanDevicesScreen(
        state = viewState,
        navigateUp = navigateUp,
        onStartScan = startScan,
        onStopScan = viewModel::stopScan,
        onDeviceClicked = deviceClicked,
    )
}

@Composable
private fun ScanDevicesScreen(
    state: ScanDevicesViewState,
    navigateUp: () -> Unit,
    onStartScan: () -> Unit,
    onStopScan: () -> Unit,
    onDeviceClicked: (DiscoveredDevice) -> Unit,
) {
    WhScaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Scan for devices") },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
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
                onClick = { if (state.isLoading) onStopScan() else onStartScan() },
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
                    DeviceItem(device = device, onClicked = { onDeviceClicked(device) })
                }
            }

            if (state.found.isNotEmpty()) {
                item { Header("New Devices") }
                items(state.found) { device ->
                    DeviceItem(device = device, onClicked = { onDeviceClicked(device) })
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
            navigateUp = {},
            onStartScan = {},
            onStopScan = {},
            onDeviceClicked = {}
        )
    }
}