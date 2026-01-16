package ua.graviton.isida.ui.home.stats

import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.StringResource

class StatsListBuilder {
    private val list = mutableListOf<StatsItem>()

    /**
     * Adds a numeric item (Int, Float, etc.).
     * @param title The label resource ID.
     * @param value The primary value.
     * @param target The target/setpoint value (optional).
     * @param backgroundColor Optional lambda to determine the background color.
     * @param valueColor Optional lambda to determine the value's color based on logic.
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
     * Adds an item that maps a value to a String Resource.
     * @param title The label resource ID.
     * @param value The source value (e.g., an Int status code).
     * @param valueColor Optional color logic.
     * @param backgroundColor Optional background color logic.
     * @param mapper Function that converts the source value [T] to a [StringResource].
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
     * @param title The label resource ID.
     * @param value The source value.
     * @param mapper Function that converts the source value [T] to a [String].
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

    fun build() = list.toList()
}

/**
 * DSL entry point.
 */
fun buildStats(block: StatsListBuilder.() -> Unit): List<StatsItem> {
    val builder = StatsListBuilder()
    builder.block()
    return builder.build()
}