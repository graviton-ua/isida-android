package ua.graviton.isida.ui.home.prop

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import ua.graviton.isida.ui.navigation.Navigator
import ua.graviton.isida.ui.setprop.SetPropDialog

fun EntryProviderScope<NavKey>.addPropScreen(
    navigator: Navigator,
) {
    entry<PropScreen> {
        PropScreen(
            openSetPropDialog = { id ->
                navigator.navigateTo(SetPropDialog(id))
            }
        )
    }
}