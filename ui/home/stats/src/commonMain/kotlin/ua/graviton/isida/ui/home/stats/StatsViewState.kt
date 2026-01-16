package ua.graviton.isida.ui.home.stats

import androidx.compose.ui.graphics.Color

/**
 * Represents the UI state for the Stats Screen.
 *
 * @property titleDeviceId The ID of the device to display in the header (e.g., "Cell #1").
 * @property titleDeviceBackgroundColor The background color for the header, typically used to indicate status (Green/Red/Yellow).
 * @property items The list of data rows to display.
 */
data class StatsViewState(
    val titleDeviceId: Int?,
    val titleDeviceBackgroundColor: Color?,

    val items: List<StatsItem>,
) {
    companion object {
        /**
         * Initial empty state.
         */
        val Empty = StatsViewState(
            titleDeviceId = null,
            titleDeviceBackgroundColor = null,
            items = PlaceholderStats,
        )

        /**
         * Preview state for UI tooling.
         */
        val Preview = StatsViewState(
            titleDeviceId = 1,
            titleDeviceBackgroundColor = null,
            items = PlaceholderStats,
        )
    }
}