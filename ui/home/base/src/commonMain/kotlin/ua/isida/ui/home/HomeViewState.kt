package ua.isida.ui.home

import androidx.compose.runtime.Immutable

@Immutable
data class HomeViewState(
    val deviceConnected: Boolean = false,
    val isLoading: Boolean = false
) {
    companion object {
        val Empty = HomeViewState()
    }
}