package ua.isida.ui.devicemode

import androidx.compose.ui.window.DialogProperties
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.scene.DialogSceneStrategy
import ua.isida.common.ui.navigation.Navigator

fun EntryProviderScope<NavKey>.addDeviceModeDialog(
    navigator: Navigator,
) {
    entry<DeviceModeDialog>(
        metadata = DialogSceneStrategy.dialog(DialogProperties(usePlatformDefaultWidth = true))
    ) {
        DeviceModeDialog(
            navigateUp = navigator::navigateUp,
        )
    }
}