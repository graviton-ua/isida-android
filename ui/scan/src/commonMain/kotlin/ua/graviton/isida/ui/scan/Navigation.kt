package ua.graviton.isida.ui.scan

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import ua.isida.common.ui.navigation.Navigator

fun EntryProviderScope<NavKey>.addScanDevicesScreen(
    navigator: Navigator,
) {
    entry<ScanDevicesScreen> {
        ScanDevicesScreen(
            navigateUp = navigator::navigateUp,
            onDeviceSelected = { navigator.navigateUp() },
        )
    }
}