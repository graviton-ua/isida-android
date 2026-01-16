package ua.graviton.isida.ui.home.stats

import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.StringResource

/**
 * Represents a single row in the Statistics screen.
 * Each item has a title, a content value (numeric or text), and optional styling.
 *
 * @property titleResId The resource ID for the label/title of the row.
 * @property content The data payload of the item, defined by [Content].
 * @property backgroundColor Optional background color for the entire row.
 */
data class StatsItem(
    val titleResId: StringResource,
    val content: Content,
    val backgroundColor: Color? = null,
) {
    /**
     * Polymorphic content holder for a [StatsItem].
     * Determines how the value is stored and rendered.
     */
    sealed interface Content {
        /**
         * Represents a numeric value (e.g., Temperature, Humidity).
         * @property value The current reading.
         * @property target The target or setpoint value (optional).
         * @property valueColor Specific color for the value text (e.g., Red if out of range).
         */
        data class Numeric<T : Number>(
            val value: T?,
            val target: T? = null,
            val valueColor: Color? = null
        ) : Content

        /**
         * Represents a text value resolved from a String Resource ID.
         * Used for status codes or enums that map to localized strings.
         */
        data class TextResource(
            val value: StringResource?,
            val target: StringResource? = null,
            val valueColor: Color? = null
        ) : Content

        /**
         * Represents a raw string value.
         * Used for text that doesn't have a resource ID or is dynamic.
         */
        data class TextRaw(
            val value: String?,
            val target: String? = null,
            val valueColor: Color? = null
        ) : Content
    }
}
