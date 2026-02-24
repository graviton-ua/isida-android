package ua.isida.ui.home.stats

import androidx.compose.ui.graphics.Color
import ua.isida.common.ui.resources.ComposableString

/**
 * Класс-строитель, отвечающий за создание списка элементов [StatsItem] с использованием DSL.
 * Предоставляет методы для удобного добавления элементов с различными типами контента и логикой стилизации.
 *
 * **Пример использования:**
 * ```kotlin
 * val items = buildStats {
 *     header(Res.string.section_sensors)
 *     item(Res.string.temperature, currentTemp, targetTemp)
 *     mapStringResource(Res.string.status, statusCode) { code -> ... }
 * }
 * ```
 */
/**
 * DSL builder for [StatsItem.Style].
 */
internal class StyleBuilder {
    var backgroundColor: Color? = null
    var titleColor: Color? = null
    var valueColor: Color? = null

    fun build() = StatsItem.Style(
        backgroundColor = backgroundColor,
        titleColor = titleColor,
        valueColor = valueColor
    )
}

internal class StatsListBuilder {
    private val list = mutableListOf<StatsItem>()

    fun header(
        title: ComposableString,
        style: StyleBuilder.() -> Unit = {},
    ) {
        val builder = StyleBuilder()
        builder.style()
        list.add(StatsItem.Header(title = title, style = builder.build()))
    }

    fun item(
        title: ComposableString,
        content: ComposableString,
        style: (StyleBuilder.() -> Unit)? = null,
    ) {
        val builder = StyleBuilder()
        style?.invoke(builder)
        list.add(
            StatsItem.Info(
                title = title,
                content = content,
                style = builder.build()
            )
        )
    }

    /**
     * Завершает процесс сборки и возвращает неизменяемый список [StatsItem].
     */
    fun build() = list.toList()
}

/**
 * Точка входа DSL для создания списка элементов [StatsItem].
 *
 * @param block Лямбда строителя, в которой вы определяете элементы с помощью [StatsListBuilder.item], [StatsListBuilder.mapStringResource] и т. д.
 * @return Список элементов [StatsItem].
 *
 * **Пример использования:**
 * ```kotlin
 * val statsList = buildStats {
 *    header(Res.string.group_1)
 *    item(Res.string.sensor_1, 10)
 * }
 * ```
 */
internal fun buildStats(block: StatsListBuilder.() -> Unit): List<StatsItem> {
    val builder = StatsListBuilder()
    builder.block()
    return builder.build()
}
