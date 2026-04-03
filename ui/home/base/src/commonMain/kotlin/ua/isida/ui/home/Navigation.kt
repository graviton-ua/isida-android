package ua.isida.ui.home

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import ua.isida.common.ui.navigation.NavigatorWithResultBus
import ua.isida.data.protocol.packets.TableDay

fun EntryProviderScope<NavKey>.addHomeScreen(
    navigator: NavigatorWithResultBus,
    navigateScanDevices: () -> Unit,
    onShowLogs: () -> Unit,
    openPowerDialog: () -> Unit,
    openSetPropDialog: (String) -> Unit,
    navigateSetDay: (Int, TableDay) -> Unit,
) {
    entry<HomeScreen> {
        HomeScreen(
            resultBus = navigator.resultBus,
            connectDevice = navigateScanDevices,
            onShowLogs = onShowLogs,
            openPowerDialog = openPowerDialog,
            openSetPropDialog = openSetPropDialog,
            navigateSetDay = navigateSetDay,
        )
    }
}