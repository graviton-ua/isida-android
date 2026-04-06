package ua.isida.ui.home.prop

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey

fun EntryProviderScope<NavKey>.addPropScreen(
    openSetPropDialog: (String) -> Unit,
) {
    entry<PropScreen> {
        PropScreen(
            openSetPropDialog = openSetPropDialog,
        )
    }
}