package ua.isida.ui.home

import androidx.compose.runtime.Immutable

@Immutable
internal data class HomeViewState(
    val deviceConnected: Boolean = false,
    val isLoading: Boolean = false
) {
    companion object {
        val Empty = HomeViewState()
    }
}