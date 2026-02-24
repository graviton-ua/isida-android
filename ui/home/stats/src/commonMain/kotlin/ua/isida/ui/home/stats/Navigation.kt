package ua.isida.ui.home.stats

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import ua.isida.common.ui.navigation.Navigator

fun EntryProviderScope<NavKey>.addStatsScreen(
    navigator: Navigator,
) {
    entry<StatsScreen> {
        StatsScreen()
    }
}