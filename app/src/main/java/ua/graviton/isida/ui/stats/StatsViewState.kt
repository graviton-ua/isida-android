package ua.graviton.isida.ui.stats

data class StatsViewState(
    val titleDeviceId: Int? = null,
    val titleDeviceBackgroundColor: Int? = null,

    val items: List<StatsItem> = emptyList(),
) {
    companion object {
        val Empty = StatsViewState()
    }
}