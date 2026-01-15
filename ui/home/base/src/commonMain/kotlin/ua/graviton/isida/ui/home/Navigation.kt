package ua.graviton.isida.ui.home

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import ua.graviton.isida.ui.navigation.Navigator

fun EntryProviderScope<NavKey>.addHomeScreen(
    navigator: Navigator,
    navigateScanDevices: () -> Unit,
    openPowerDialog: () -> Unit,
    openSetPropDialog: (String) -> Unit,
) {
    entry<HomeScreen> {
        HomeScreen(
            connectDevice = navigateScanDevices,
            disconnectDevice = {},
            openPowerDialog = openPowerDialog,
            openSetPropDialog = openSetPropDialog,
        )
    }
}