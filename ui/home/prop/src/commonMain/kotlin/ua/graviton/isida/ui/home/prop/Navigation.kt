package ua.graviton.isida.ui.home.prop

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import ua.graviton.isida.ui.navigation.Navigator

fun EntryProviderScope<NavKey>.addPropScreen(
    navigator: Navigator,
    openSetPropDialog: (String) -> Unit,
) {
    entry<PropScreen> {
        PropScreen(
            openSetPropDialog = openSetPropDialog,
        )
    }
}