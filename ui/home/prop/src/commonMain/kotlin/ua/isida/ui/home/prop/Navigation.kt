package ua.isida.ui.home.prop

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import ua.isida.common.ui.navigation.Navigator

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