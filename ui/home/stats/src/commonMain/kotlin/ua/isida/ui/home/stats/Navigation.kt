package ua.isida.ui.home.stats

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey

fun EntryProviderScope<NavKey>.addStatsScreen() {
    entry<StatsScreen> {
        StatsScreen()
    }
}