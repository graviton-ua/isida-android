package ua.graviton.isida.ui.home.stats

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 * Represents a single row in the Statistics screen.
 * Can be a Header or an Info row (data item).
 */
@Immutable
sealed interface StatsItem {
    val id: Int
    val title: Title

    @Immutable
    sealed interface Title {
        data class Resource(val res: StringResource) : Title
        data class Raw(val text: String) : Title

        @Composable
        fun asString(): String {
            return when (this) {
                is Resource -> stringResource(this.res)
                is Raw -> this.text
            }
        }
    }

    @Immutable
    data class Header(
        override val title: Title,
        val backgroundColor: Color? = null,
    ) : StatsItem {
        constructor(title: StringResource, backgroundColor: Color? = null) : this(Title.Resource(title), backgroundColor)
        constructor(title: String, backgroundColor: Color? = null) : this(Title.Raw(title), backgroundColor)

        override val id: Int get() = title.hashCode()
    }

    /**
     * Represents a single data row.
     * Each item has a title, a content value (numeric or text), and optional styling.
     */
    @Immutable
    data class Info(
        override val title: Title,
        val content: Content,
        val backgroundColor: Color? = null,
    ) : StatsItem {
        constructor(title: StringResource, content: Content, backgroundColor: Color? = null) : this(Title.Resource(title), content, backgroundColor)
        constructor(title: String, content: Content, backgroundColor: Color? = null) : this(Title.Raw(title), content, backgroundColor)

        override val id: Int get() = title.hashCode()
    }

    /**
     * Polymorphic content holder for a [StatsItem].
     * Determines how the value is stored and rendered.
     */
    @Immutable
    sealed interface Content {
        /**
         * Represents a numeric value (e.g., Temperature, Humidity).
         * @property value The current reading.
         * @property target The target or setpoint value (optional).
         * @property valueColor Specific color for the value text (e.g., Red if out of range).
         */
        @Immutable
        data class Numeric<T : Number>(
            val value: T?,
            val target: T? = null,
            val valueColor: Color? = null
        ) : Content

        /**
         * Represents a text value resolved from a String Resource ID.
         * Used for status codes or enums that map to localized strings.
         */
        @Immutable
        data class TextResource(
            val values: List<StringResource>,
            val targets: List<StringResource> = emptyList(),
            val valueColor: Color? = null
        ) : Content

        /**
         * Represents a raw string value.
         * Used for text that doesn't have a resource ID or is dynamic.
         */
        @Immutable
        data class TextRaw(
            val value: String?,
            val target: String? = null,
            val valueColor: Color? = null
        ) : Content
    }
}
