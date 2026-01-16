package ua.graviton.isida.ui.home.stats

import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.StringResource

/**
 * A builder class responsible for constructing a list of [StatsItem]s using a DSL.
 * It provides methods to easily add items with different content types and styling logic.
 */
class StatsListBuilder {
    private val list = mutableListOf<StatsItem>()

    /**
     * Adds a numeric item (Int, Float, etc.) to the list.
     * Use this for sensor readings like Temperature, Humidity, or Timer values.
     *
     * @param title The label resource ID for the item.
     * @param value The primary numeric value (e.g., current temp).
     * @param target The target/setpoint numeric value (optional, e.g., target temp).
     * @param backgroundColor Optional lambda to dynamically determine the row's background color based on [value].
     * @param valueColor Optional lambda to dynamically determine the text color based on [value] and [target].
     *
     * Example:
     * ```
     * item(
     *     title = Res.string.temp_label,
     *     value = currentTemp,
     *     target = setPoint,
     *     valueColor = { v, t -> if (v > t) Color.Red else Color.Green }
     * )
     * ```
     */
    fun <T : Number> item(
        title: StringResource,
        value: T?,
        target: T? = null,
        backgroundColor: ((value: T?) -> Color?)? = null,
        valueColor: ((value: T?, target: T?) -> Color?)? = null
    ) {
        list.add(
            StatsItem(
                titleResId = title,
                content = StatsItem.Content.Numeric(value, target, valueColor?.invoke(value, target)),
                backgroundColor = backgroundColor?.invoke(value)
            )
        )
    }

    /**
     * Adds an item that maps a value (usually an Enum or Int code) to a localized [StringResource].
     * Use this for status codes, errors, or modes that have translation resources.
     *
     * @param title The label resource ID.
     * @param value The source value to be mapped.
     * @param target An optional target value to be mapped.
     * @param backgroundColor Optional lambda for background color logic.
     * @param valueColor Optional lambda for text color logic.
     * @param mapper A function that converts the [value] (of type T) into a [StringResource]?.
     *
     * Example:
     * ```
     * mapStringResource(
     *     title = Res.string.status,
     *     value = statusCode,
     *     mapper = { code -> if (code == 1) Res.string.active else Res.string.inactive }
     * )
     * ```
     */
    fun <T> mapStringResource(
        title: StringResource,
        value: T?,
        target: T? = null,
        backgroundColor: ((value: T?) -> Color?)? = null,
        valueColor: ((value: T?) -> Color?)? = null,
        mapper: (T) -> StringResource?
    ) {
        list.add(
            StatsItem(
                titleResId = title,
                content = StatsItem.Content.TextResource(
                    value = if (value != null) mapper(value) else null,
                    target = if (target != null) mapper(target) else null,
                    valueColor = valueColor?.invoke(value),
                ),
                backgroundColor = backgroundColor?.invoke(value)
            )
        )
    }

    /**
     * Adds an item that maps a value to a raw String.
     * Use this when the text is dynamic or comes from a source without Resource IDs.
     *
     * @param title The label resource ID.
     * @param value The source value to be mapped.
     * @param target An optional target value to be mapped.
     * @param backgroundColor Optional lambda for background color logic.
     * @param valueColor Optional lambda for text color logic.
     * @param mapper A function that converts the [value] (of type T) into a [String]?.
     *
     * Example:
     * ```
     * mapString(
     *     title = Res.string.mode,
     *     value = modeInt,
     *     mapper = { it -> "Mode #$it" }
     * )
     * ```
     */
    fun <T> mapString(
        title: StringResource,
        value: T?,
        target: T? = null,
        backgroundColor: ((value: T?) -> Color?)? = null,
        valueColor: ((value: T?) -> Color?)? = null,
        mapper: (T) -> String?
    ) {
        list.add(
            StatsItem(
                titleResId = title,
                content = StatsItem.Content.TextRaw(
                    value = if (value != null) mapper(value) else null,
                    target = if (target != null) mapper(target) else null,
                    valueColor = valueColor?.invoke(value),
                ),
                backgroundColor = backgroundColor?.invoke(value)
            )
        )
    }

    /**
     * Finalizes the build process and returns the immutable list of [StatsItem]s.
     */
    fun build() = list.toList()
}

/**
 * DSL Entry Point for creating a list of [StatsItem]s.
 *
 * @param block The builder lambda where you define items using [StatsListBuilder.item], [StatsListBuilder.mapStringResource], etc.
 * @return A list of [StatsItem].
 */
fun buildStats(block: StatsListBuilder.() -> Unit): List<StatsItem> {
    val builder = StatsListBuilder()
    builder.block()
    return builder.build()
}