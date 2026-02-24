package ua.graviton.isida.ui.home.program

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import ua.isida.data.protocol.packets.TableDay
import ua.isida.common.ui.navigation.Navigator
import ua.isida.common.ui.navigation.result.ResultEventBus

fun EntryProviderScope<NavKey>.addProgramScreen(
    navigator: Navigator,
    resultBus: ResultEventBus,
    navigateSetDay: (Int, TableDay) -> Unit,
) {
    entry<ProgramScreen> {
        ProgramScreen(
            resultBus = resultBus,
            navigateSetDay = navigateSetDay,
        )
    }
}