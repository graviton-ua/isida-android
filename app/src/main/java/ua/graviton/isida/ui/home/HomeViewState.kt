package ua.graviton.isida.ui.home

data class HomeViewState(
    val deviceConnected: Boolean = false,
    val isLoading: Boolean = false
) {
    companion object {
        val Empty = HomeViewState()
    }
}