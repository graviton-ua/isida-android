package ua.graviton.isida.ui.home.report

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import ua.graviton.isida.ui.navigation.Navigator

fun EntryProviderScope<NavKey>.addReportScreen(
    navigator: Navigator,
) {
    entry<ReportScreen> {
        ReportScreen()
    }
}