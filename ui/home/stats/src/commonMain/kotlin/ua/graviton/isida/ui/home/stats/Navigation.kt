package ua.graviton.isida.ui.home.stats

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import ua.graviton.isida.ui.navigation.Navigator

fun EntryProviderScope<NavKey>.addStatsScreen(
    navigator: Navigator,
) {
    entry<StatsScreen> {
        StatsScreen()
    }
}