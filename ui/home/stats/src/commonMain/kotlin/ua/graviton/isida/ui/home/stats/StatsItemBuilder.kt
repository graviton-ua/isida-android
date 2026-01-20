package ua.graviton.isida.ui.home.stats

import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.StringResource

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
        title: StatsItem.Title,
        style: StyleBuilder.() -> Unit = {},
    ) {
        val builder = StyleBuilder()
        builder.style()
        list.add(StatsItem.Header(title = title, style = builder.build()))
    }

    /**
     * Добавляет элемент заголовка с заголовком в виде обычной строки.
     *
     * @param title Текст заголовка.
     * @param style Лямбда для настройки стиля.
     */
    @Deprecated(
        "Use header(StatsItem.Title) instead",
        ReplaceWith("header(title = StatsItem.Title.ComposableString.composableString(key = title) { title }, style = style)")
    )
    fun header(
        title: String,
        style: StyleBuilder.() -> Unit = {},
    ) {
        val builder = StyleBuilder()
        builder.style()
        list.add(StatsItem.Header(title = title, style = builder.build()))
    }

    /**
     * Добавляет элемент заголовка с заголовком в виде ID ресурса.
     *
     * @param title ID ресурса заголовка.
     * @param style Лямбда для настройки стиля.
     */
    @Deprecated(
        "Use header(StatsItem.Title) instead",
        ReplaceWith(
            "header(title = StatsItem.Title.ComposableString.composableString(key = title to args) { stringResource(title, *args) }, style = style)",
            "org.jetbrains.compose.resources.stringResource"
        )
    )
    fun header(
        title: StringResource,
        vararg args: Any, // Принимаем аргументы
        style: StyleBuilder.() -> Unit = {},
    ) {
        val builder = StyleBuilder()
        builder.style()
        list.add(
            StatsItem.Header(
                // Создаем Title.Resource напрямую, преобразуя массив в список
                title = StatsItem.Title.Resource(title, args.toList()),
                style = builder.build()
            )
        )
    }


    /**
     * Добавляет числовой элемент (Int, Float и т. д.) в список.
     * Используйте это для показаний датчиков, таких как температура, влажность или значения таймера.
     *
     * @param title ID ресурса метки для элемента.
     * @param value Основное числовое значение (например, текущая температура).
     * @param target Целевое/заданное числовое значение (необязательно, например, целевая температура).
     * @param style Лямбда для настройки стиля на основе значений.
     */
    @Deprecated("Use item(title = StatsItem.Title, content = StatsItem.Title.ComposableString) instead")
    fun <T : Number> item(
        title: StatsItem.Title,
        value: T?,
        target: T? = null,
        style: (StyleBuilder.(value: T?, target: T?) -> Unit)? = null,
    ) {
        val builder = StyleBuilder()
        style?.invoke(builder, value, target)
        list.add(
            StatsItem.Info(
                title = title,
                content = StatsItem.Content.Numeric(value, target),
                style = builder.build()
            )
        )
    }

    fun item(
        title: StatsItem.Title,
        content: StatsItem.Title.ComposableString,
        style: (StyleBuilder.() -> Unit)? = null,
    ) {
        val builder = StyleBuilder()
        style?.invoke(builder)
        list.add(
            StatsItem.InfoString(
                title = title,
                content = content,
                style = builder.build()
            )
        )
    }

    @Deprecated("Use item(title = StatsItem.Title, content = StatsItem.Title.ComposableString) instead")
    fun <T : Number> item(
        title: StringResource,
        value: T?,
        target: T? = null,
        style: (StyleBuilder.(value: T?, target: T?) -> Unit)? = null,
    ) = item(StatsItem.Title.Resource(title), value, target, style)

    /**
     * Добавляет элемент, который сопоставляет одно значение (обычно Enum или Int код) с локализованным [StringResource].
     * Используйте это для простых кодов состояния или состояний.
     *
     * @param title ID ресурса метки.
     * @param value Исходное значение для сопоставления.
     * @param target Необязательное целевое значение для сопоставления.
     * @param style Лямбда для настройки стиля.
     * @param mapper Функция, которая преобразует [value] (типа T) в один [StringResource]?.
     */
    @Deprecated("Use item(title = StatsItem.Title, content = StatsItem.Title.ComposableString) instead")
    fun <T> mapStringResource(
        title: StatsItem.Title,
        value: T?,
        target: T? = null,
        style: (StyleBuilder.(value: T?, target: T?) -> Unit)? = null,
        mapper: ((T) -> StringResource?)? = null,
    ) {
        val builder = StyleBuilder()
        style?.invoke(builder, value, target)
        list.add(
            StatsItem.Info(
                title = title,
                content = StatsItem.Content.TextResource(
                    values = mapper?.let { if (value != null) listOfNotNull(it.invoke(value)) else null } ?: emptyList(),
                    targets = mapper?.let { if (target != null) listOfNotNull(it.invoke(target)) else null } ?: emptyList(),
                ),
                style = builder.build()
            )
        )
    }

    @Deprecated("Use item(title = StatsItem.Title, content = StatsItem.Title.ComposableString) instead")
    fun <T> mapStringResource(
        title: StringResource,
        value: T?,
        target: T? = null,
        style: (StyleBuilder.(value: T?, target: T?) -> Unit)? = null,
        mapper: ((T) -> StringResource?)? = null,
    ) = mapStringResource(StatsItem.Title.Resource(title), value, target, style, mapper)

    /**
     * Добавляет элемент, который сопоставляет значение со **списком** [StringResource].
     * Используйте это, когда одно значение представляет несколько состояний или флагов (например, битовую маску).
     *
     * @param title ID ресурса метки.
     * @param value Исходное значение для сопоставления.
     * @param target Необязательное целевое значение для сопоставления.
     * @param style Лямбда для настройки стиля.
     * @param mapper Функция, которая преобразует [value] (типа T) в [List] из [StringResource].
     */
    @Deprecated("Use item(title = StatsItem.Title, content = StatsItem.Title.ComposableString) instead")
    fun <T> mapStringResources(
        title: StatsItem.Title,
        value: T?,
        target: T? = null,
        style: (StyleBuilder.(value: T?, target: T?) -> Unit)? = null,
        mapper: (T) -> List<StringResource>,
    ) {
        val builder = StyleBuilder()
        style?.invoke(builder, value, target)
        list.add(
            StatsItem.Info(
                title = title,
                content = StatsItem.Content.TextResource(
                    values = if (value != null) mapper(value) else emptyList(),
                    targets = if (target != null) mapper(target) else emptyList(),
                ),
                style = builder.build()
            )
        )
    }

    @Deprecated("Use item(title = StatsItem.Title, content = StatsItem.Title.ComposableString) instead")
    fun <T> mapStringResources(
        title: StringResource,
        value: T?,
        target: T? = null,
        style: (StyleBuilder.(value: T?, target: T?) -> Unit)? = null,
        mapper: (T) -> List<StringResource>,
    ) = mapStringResources(StatsItem.Title.Resource(title), value, target, style, mapper)

    /**
     * Добавляет элемент, который сопоставляет значение с обычной строкой (Raw String).
     * Используйте это, когда текст является динамическим или поступает из источника без ID ресурсов (например, отформатированные даты, имена).
     *
     * @param title ID ресурса метки.
     * @param value Исходное значение для сопоставления.
     * @param target Необязательное целевое значение для сопоставления.
     * @param style Лямбда для настройки стиля.
     * @param mapper Функция, которая преобразует [value] (типа T) в [String]?.
     */
    @Deprecated("Use item(title = StatsItem.Title, content = StatsItem.Title.ComposableString) instead")
    fun <T> mapString(
        title: StatsItem.Title,
        value: T?,
        target: T? = null,
        style: (StyleBuilder.(value: T?) -> Unit)? = null,
        mapper: (T) -> String?
    ) {
        val builder = StyleBuilder()
        style?.invoke(builder, value)
        list.add(
            StatsItem.Info(
                title = title,
                content = StatsItem.Content.TextRaw(
                    value = if (value != null) mapper(value) else null,
                    target = if (target != null) mapper(target) else null,
                ),
                style = builder.build()
            )
        )
    }

    @Deprecated("Use item(title = StatsItem.Title, content = StatsItem.Title.ComposableString) instead")
    fun <T> mapString(
        title: StringResource,
        value: T?,
        target: T? = null,
        style: (StyleBuilder.(value: T?) -> Unit)? = null,
        mapper: (T) -> String?
    ) = mapString(StatsItem.Title.Resource(title), value, target, style, mapper)

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
