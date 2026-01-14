package ua.graviton.isida.ui.home

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import ua.graviton.isida.ui.navigation.Navigator

fun EntryProviderScope<NavKey>.addHomeScreen(
    navigator: Navigator,
    openPowerDialog: () -> Unit,
    openSetPropDialog: (String) -> Unit,
) {
    entry<HomeScreen> {
        HomeScreen(
            connectDevice = {},
            disconnectDevice = {},
            openPowerDialog = openPowerDialog,
            openSetPropDialog = openSetPropDialog,
        )
    }
}