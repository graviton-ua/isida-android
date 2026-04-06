package ua.isida.ui.home.program

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import ua.isida.common.ui.navigation.result.ResultEventBus
import ua.isida.data.protocol.packets.TableDay

fun EntryProviderScope<NavKey>.addProgramScreen(
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