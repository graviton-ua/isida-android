package ua.isida.ui.logreport

import androidx.compose.ui.window.DialogProperties
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.scene.DialogSceneStrategy
import ua.isida.common.ui.navigation.Navigator

fun EntryProviderScope<NavKey>.addLogReportScreen(
    navigator: Navigator,
) {
    entry<LogReportScreen>(
        metadata = DialogSceneStrategy.dialog(DialogProperties(usePlatformDefaultWidth = true))
    ) {
        LogReportScreen(
            onBack = navigator::navigateUp,
        )
    }
}
