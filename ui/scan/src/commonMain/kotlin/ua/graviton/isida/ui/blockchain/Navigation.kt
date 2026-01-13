package ua.crypto.ui.blockchain

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import ua.graviton.isida.ui.navigation.Navigator

fun EntryProviderScope<NavKey>.addBlockchainScreen(
    navigator: Navigator,
) {
    entry<BlockchainScreen> {
        BlockchainScreen()
    }
}