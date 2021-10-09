package ua.graviton.isida.ui.stats

data class StatsViewState(
    val deviceId: Int? = null,
    val items: List<StatsItem> = emptyList(),
) {
    companion object {
        val Empty = StatsViewState()
    }
}