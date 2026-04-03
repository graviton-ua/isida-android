package ua.isida.ui.logreport

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import ua.isida.common.ui.navigation.Navigator

fun EntryProviderScope<NavKey>.addLogReportScreen(
    navigator: Navigator,
) {
    entry<LogReportScreen> {
        LogReportScreen(
            onBack = navigator::navigateUp,
        )
    }
}
