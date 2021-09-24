package ua.graviton.isida.ui.info

data class InfoViewState(
    val cellNumber: Int = 0
) {
    companion object {
        val Empty = InfoViewState()
    }
}