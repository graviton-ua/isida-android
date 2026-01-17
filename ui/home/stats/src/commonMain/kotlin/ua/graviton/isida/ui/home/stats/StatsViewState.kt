package ua.graviton.isida.ui.home.stats

import androidx.compose.ui.graphics.Color

/**
 * Represents the immutable UI state for the Stats Screen.
 * This state is produced by [StatsViewModel] and consumed by [StatsScreen].
 *
 * @property titleDeviceId The unique identifier of the device (e.g., node ID) to display in the header.
 * @property titleDeviceBackgroundColor The background color for the device title header, typically used to indicate the device's connection or operational status (e.g., Green for OK, Red for Error).
 * @property items The list of [StatsItem] rows (headers and info items) to display in the list.
 */
data class StatsViewState(
    val titleDeviceId: Int?,
    val titleDeviceBackgroundColor: Color?,

    val items: List<StatsItem>,
) {
    companion object {
        /**
         * The initial empty state, used before any data is loaded.
         * Contains placeholder items to define the list structure.
         */
        val Empty = StatsViewState(
            titleDeviceId = null,
            titleDeviceBackgroundColor = null,
            items = PlaceholderStats,
        )

        /**
         * A preview state populated with dummy data, suitable for UI tool previews.
         */
        val Preview = StatsViewState(
            titleDeviceId = 1,
            titleDeviceBackgroundColor = null,
            items = PlaceholderStats,
        )
    }
}
