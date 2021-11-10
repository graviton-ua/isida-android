package ua.graviton.isida.ui.home.prop

data class PropViewState(
    val cellNumber: Int = 0
) {
    companion object {
        val Empty = PropViewState()
    }
}
