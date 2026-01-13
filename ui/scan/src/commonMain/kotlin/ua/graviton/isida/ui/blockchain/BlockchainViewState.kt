package ua.crypto.ui.blockchain

import androidx.compose.runtime.Immutable

@Immutable
data class BlockchainViewState(
    val feed: List<String> = emptyList(),
) {

    companion object {
        val Init = BlockchainViewState()
    }
}