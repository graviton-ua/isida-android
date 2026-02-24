package ua.graviton.isida.ui.setday

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import ua.isida.metrox.viewmodel.injectedViewModel
import ua.isida.common.ui.navigation.NavigatorWithResultBus

fun EntryProviderScope<NavKey>.addSetDayScreen(
    navigator: NavigatorWithResultBus,
) {
    entry<SetDayScreen> { key ->
        SetDayScreen(
            viewModel = injectedViewModel<SetDayViewModel, SetDayViewModel.Factory> { it.create(index = key.index, day = key.day) },
            navigateUp = navigator::navigateUp,
            onSubmit = { result ->
                navigator.resultBus.sendResult(result = result)
                navigator.navigateUp()
            },
        )
    }
}