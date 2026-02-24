package ua.isida.ui.setprop

import androidx.compose.ui.window.DialogProperties
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.scene.DialogSceneStrategy
import ua.isida.metrox.viewmodel.injectedViewModel
import ua.isida.common.ui.navigation.Navigator

fun EntryProviderScope<NavKey>.addSetPropDialog(
    navigator: Navigator,
) {
    entry<SetPropDialog>(
        metadata = DialogSceneStrategy.dialog(DialogProperties(usePlatformDefaultWidth = true))
    ) { key ->
        SetPropDialog(
            viewModel = injectedViewModel<SetPropViewModel, SetPropViewModel.Factory> { it.create(id = key.id) },
            navigateUp = navigator::navigateUp,
        )
    }
}