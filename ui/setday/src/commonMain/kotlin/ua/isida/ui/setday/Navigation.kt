package ua.isida.ui.setday

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.zacsweers.metrox.viewmodel.assistedMetroViewModel
import ua.isida.common.ui.navigation.NavigatorWithResultBus

fun EntryProviderScope<NavKey>.addSetDayScreen(
    navigator: NavigatorWithResultBus,
) {
    entry<SetDayScreen> { key ->
        SetDayScreen(
            viewModel = assistedMetroViewModel<SetDayViewModel, SetDayViewModel.Factory> { create(index = key.index, day = key.day) },
            navigateUp = navigator::navigateUp,
            onSubmit = { result ->
                navigator.resultBus.sendResult(result = result)
                navigator.navigateUp()
            },
        )
    }
}