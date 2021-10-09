package ua.graviton.isida.ui.stats

data class StatsViewState(
    val cellNumber: Int = 0,
    val temp0: Float = 0f,
    val temp1: Float = 0f,
    val temp2: Float = 0f,
    val temp3: Float = 0f,
    val rh: Float = 0f,
    val coTwo: Int = 0,
    val timer: Int = 0,
    val count: Int = 0,
    val flap: Int = 0,
) {
    companion object {
        val Empty = StatsViewState()
    }
}