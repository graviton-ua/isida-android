package ua.graviton.isida.ui.home.stats

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 * Represents a single item in the Statistics screen list.
 * This sealed interface allows for different types of items, such as headers and information rows.
 *
 * @property id A unique identifier for the item, used for list optimization (e.g., in LazyColumn).
 * @property title The title of the item, which can be a resource or a raw string.
 */
@Immutable
sealed interface StatsItem {
    val id: Int
    val title: Title

    /**
     * Represents the title of a [StatsItem].
     * Can be either a [StringResource] or a raw [String].
     */
    @Immutable
    sealed interface Title {
        /** A title defined by a string resource. */
        data class Resource(val res: StringResource) : Title
        /** A title defined by a raw string. */
        data class Raw(val text: String) : Title

        /**
         * Resolves the title to a [String] within a Composable context.
         */
        @Composable
        fun asString(): String {
            return when (this) {
                is Resource -> stringResource(this.res)
                is Raw -> this.text
            }
        }
    }

    /**
     * A sticky header item for grouping or labeling sections.
     *
     * @property title The header title.
     * @property backgroundColor Optional background color for the header.
     *
     * **Example of usage:**
     * ```kotlin
     * StatsItem.Header(title = Res.string.section_title, backgroundColor = Color.LightGray)
     * ```
     */
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
     * An information row displaying a label and a value (content).
     *
     * @property title The label of the row.
     * @property content The data content to display (numeric, text resource, or raw text).
     * @property backgroundColor Optional background color for the value/content part of the row.
     *
     * **Example of usage:**
     * ```kotlin
     * StatsItem.Info(
     *     title = Res.string.temperature,
     *     content = StatsItem.Content.Numeric(value = 25.5f, target = 24.0f)
     * )
     * ```
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
     * Defines the type of content displayed in an [Info] row.
     */
    @Immutable
    sealed interface Content {
        /**
         * Numeric content (e.g., sensor readings).
         *
         * @property value The current value.
         * @property target An optional target or setpoint value.
         * @property valueColor Optional text color for the value (e.g., to indicate warnings).
         */
        @Immutable
        data class Numeric<T : Number>(
            val value: T?,
            val target: T? = null,
            val valueColor: Color? = null
        ) : Content

        /**
         * Content based on string resources (e.g., status codes, enums).
         *
         * @property values A list of current values as string resources.
         * @property targets A list of target values as string resources.
         * @property valueColor Optional text color for the value.
         */
        @Immutable
        data class TextResource(
            val values: List<StringResource>,
            val targets: List<StringResource> = emptyList(),
            val valueColor: Color? = null
        ) : Content

        /**
         * Raw text content (e.g., dynamic strings).
         *
         * @property value The current text value.
         * @property target An optional target text value.
         * @property valueColor Optional text color for the value.
         */
        @Immutable
        data class TextRaw(
            val value: String?,
            val target: String? = null,
            val valueColor: Color? = null
        ) : Content
    }
}