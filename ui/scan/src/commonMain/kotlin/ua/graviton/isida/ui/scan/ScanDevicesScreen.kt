package ua.graviton.isida.ui.scan

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import ua.isida.common.ui.compose.theme.AppTheme
import ua.isida.common.ui.compose.toaster.AppToaster
import ua.isida.common.ui.permissions.PermissionType
import ua.isida.common.ui.permissions.isGranted
import ua.isida.common.ui.permissions.rememberPermissionState
import ua.isida.common.ui.services.ServiceType
import ua.isida.common.ui.services.rememberServiceEnabler
import ua.isida.metrox.viewmodel.injectedViewModel
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

    val bluetoothEnabler = rememberServiceEnabler(
        type = ServiceType.BLUETOOTH,
        onEnabled = { viewModel.startScan() }, // Success callback
        onDenied = { AppToaster.showError("Bluetooth is required to scan") }
    )

    // Note: We chain them. Location is requested AFTER Bluetooth is confirmed.
    val locationEnabler = rememberServiceEnabler(
        type = ServiceType.LOCATION,
        onEnabled = { bluetoothEnabler.requestEnable() }, // Once GPS is on, check Bluetooth
        onDenied = { AppToaster.showError("Location is required to scan") }
    )

    val bePermission = rememberPermissionState(
        PermissionType.BLUETOOTH_SCAN, PermissionType.BLUETOOTH_CONNECT, PermissionType.LOCATION,
    ) { isGranted ->
        if (isGranted) {
            // Permissions OK -> Now check System Services
            locationEnabler.requestEnable()
        } else {
            // You can show a rationale here if needed.
            AppToaster.showError("BLUETOOTH_SCAN and BLUETOOTH_CONNECT permission is required to start scanning.")
        }
    }

    val startScan: () -> Unit = remember(bePermission, locationEnabler) {
        { if (bePermission.status.isGranted) locationEnabler.requestEnable() else bePermission.launchPermissionRequest() }
    }

    val deviceClicked: (DiscoveredDevice) -> Unit = remember(onDeviceSelected, viewModel) {
        { viewModel.selectDevice(it.address); onDeviceSelected(it.address.value) }
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
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Scan for devices") },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = { if (state.isScanning) CircularProgressIndicator() },
                contentPadding = WindowInsets.statusBars.asPaddingValues(),
                modifier = Modifier.fillMaxWidth()
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { if (state.isScanning) onStopScan() else onStartScan() },
                modifier = Modifier.navigationBarsPadding()
            ) {
                Text(
                    text = if (state.isScanning) "Stop scan" else "Start scan",
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
    AppTheme {
        ScanDevicesScreen(
            state = ScanDevicesViewState(),
            navigateUp = {},
            onStartScan = {},
            onStopScan = {},
            onDeviceClicked = {}
        )
    }
}