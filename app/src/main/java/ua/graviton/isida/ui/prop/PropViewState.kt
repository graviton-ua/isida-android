package ua.graviton.isida.ui.prop

data class PropViewState(
    val cellNumber: Int = 0
) {
    companion object {
        val Empty = PropViewState()
    }
}
