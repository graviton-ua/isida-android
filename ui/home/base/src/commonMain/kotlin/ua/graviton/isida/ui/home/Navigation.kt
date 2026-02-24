package ua.graviton.isida.ui.home

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import ua.graviton.isida.data.protocol.packets.TableDay
import ua.isida.common.ui.navigation.NavigatorWithResultBus

fun EntryProviderScope<NavKey>.addHomeScreen(
    navigator: NavigatorWithResultBus,
    navigateScanDevices: () -> Unit,
    openPowerDialog: () -> Unit,
    openSetPropDialog: (String) -> Unit,
    navigateSetDay: (Int, TableDay) -> Unit,
) {
    entry<HomeScreen> {
        HomeScreen(
            resultBus = navigator.resultBus,
            connectDevice = navigateScanDevices,
            openPowerDialog = openPowerDialog,
            openSetPropDialog = openSetPropDialog,
            navigateSetDay = navigateSetDay,
        )
    }
}