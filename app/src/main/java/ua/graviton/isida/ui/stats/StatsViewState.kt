package ua.graviton.isida.ui.stats

import androidx.compose.ui.graphics.Color

data class StatsViewState(
    val titleDeviceId: Int?,
    val titleDeviceBackgroundColor: Color?,

    val items: List<StatsItem>,
) {
    companion object {
        val Empty = StatsViewState(
            titleDeviceId = null,
            titleDeviceBackgroundColor = null,
            items = emptyList()
        )

        val Preview = StatsViewState(
            titleDeviceId = 1,
            titleDeviceBackgroundColor = null,
            items = listOf(

            )
        )
    }
}