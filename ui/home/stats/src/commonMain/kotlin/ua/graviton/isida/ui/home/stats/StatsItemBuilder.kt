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
internal class StatsListBuilder {
    private val list = mutableListOf<StatsItem>()

    fun header(
        title: StatsItem.Title,
        backgroundColor: Color? = null,
    ) = list.add(StatsItem.Header(title = title, backgroundColor = backgroundColor))

    /**
     * Добавляет элемент заголовка с заголовком в виде обычной строки.
     *
     * @param title Текст заголовка.
     * @param backgroundColor Необязательный фоновый цвет.
     */
    fun header(
        title: String,
        backgroundColor: Color? = null,
    ) = list.add(StatsItem.Header(title = title, backgroundColor = backgroundColor))

    /**
     * Добавляет элемент заголовка с заголовком в виде ID ресурса.
     *
     * @param title ID ресурса заголовка.
     * @param backgroundColor Необязательный фоновый цвет.
     */
    fun header(
        title: StringResource,
        vararg args: Any, // Принимаем аргументы
        backgroundColor: Color? = null,
    ) = list.add(
        StatsItem.Header(
            // Создаем Title.Resource напрямую, преобразуя массив в список
            title = StatsItem.Title.Resource(title, args.toList()),
            backgroundColor = backgroundColor
        )
    )


    /**
     * Добавляет числовой элемент (Int, Float и т. д.) в список.
     * Используйте это для показаний датчиков, таких как температура, влажность или значения таймера.
     *
     * @param title ID ресурса метки для элемента.
     * @param value Основное числовое значение (например, текущая температура).
     * @param target Целевое/заданное числовое значение (необязательно, например, целевая температура).
     * @param backgroundColor Необязательная лямбда для динамического определения фонового цвета значения на основе [value].
     * @param valueColor Необязательная лямбда для динамического определения цвета текста на основе [value] и [target].
     *
     * **Пример использования:**
     * ```kotlin
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
            StatsItem.Info(
                title = title,
                content = StatsItem.Content.Numeric(value, target, valueColor?.invoke(value, target)),
                backgroundColor = backgroundColor?.invoke(value)
            )
        )
    }

    /**
     * Добавляет элемент, который сопоставляет одно значение (обычно Enum или Int код) с локализованным [StringResource].
     * Используйте это для простых кодов состояния или состояний.
     *
     * @param title ID ресурса метки.
     * @param value Исходное значение для сопоставления.
     * @param target Необязательное целевое значение для сопоставления.
     * @param backgroundColor Необязательная лямбда для логики фонового цвета.
     * @param valueColor Необязательная лямбда для логики цвета текста.
     * @param mapper Функция, которая преобразует [value] (типа T) в один [StringResource]?.
     *
     * **Пример использования:**
     * ```kotlin
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
        backgroundColor: ((value: T?, target: T?) -> Color?)? = null,
        valueColor: ((value: T?, target: T?) -> Color?)? = null,
        mapper: ((T) -> StringResource?)? = null,
    ) {
        list.add(
            StatsItem.Info(
                title = title,
                content = StatsItem.Content.TextResource(
                    values = mapper?.let { if (value != null) listOfNotNull(it.invoke(value)) else null } ?: emptyList(),
                    targets = mapper?.let { if (target != null) listOfNotNull(it.invoke(target)) else null } ?: emptyList(),
                    valueColor = valueColor?.invoke(value, target),
                ),
                backgroundColor = backgroundColor?.invoke(value, target)
            )
        )
    }

    /**
     * Добавляет элемент, который сопоставляет значение со **списком** [StringResource].
     * Используйте это, когда одно значение представляет несколько состояний или флагов (например, битовую маску).
     *
     * @param title ID ресурса метки.
     * @param value Исходное значение для сопоставления.
     * @param target Необязательное целевое значение для сопоставления.
     * @param backgroundColor Необязательная лямбда для логики фонового цвета.
     * @param valueColor Необязательная лямбда для логики цвета текста.
     * @param mapper Функция, которая преобразует [value] (типа T) в [List] из [StringResource].
     *
     * **Пример использования:**
     * ```kotlin
     * mapStringResources(
     *     title = Res.string.features,
     *     value = featureFlags,
     *     mapper = { flags ->
     *         val list = mutableListOf<StringResource>()
     *         if (flags has 1) list.add(Res.string.feature_1)
     *         if (flags has 2) list.add(Res.string.feature_2)
     *         list
     *     }
     * )
     * ```
     */
    fun <T> mapStringResources(
        title: StringResource,
        value: T?,
        target: T? = null,
        backgroundColor: ((value: T?, target: T?) -> Color?)? = null,
        valueColor: ((value: T?, target: T?) -> Color?)? = null,
        mapper: (T) -> List<StringResource>,
    ) {
        list.add(
            StatsItem.Info(
                title = title,
                content = StatsItem.Content.TextResource(
                    values = if (value != null) mapper(value) else emptyList(),
                    targets = if (target != null) mapper(target) else emptyList(),
                    valueColor = valueColor?.invoke(value, target),
                ),
                backgroundColor = backgroundColor?.invoke(value, target)
            )
        )
    }

    /**
     * Добавляет элемент, который сопоставляет значение с обычной строкой (Raw String).
     * Используйте это, когда текст является динамическим или поступает из источника без ID ресурсов (например, отформатированные даты, имена).
     *
     * @param title ID ресурса метки.
     * @param value Исходное значение для сопоставления.
     * @param target Необязательное целевое значение для сопоставления.
     * @param backgroundColor Необязательная лямбда для логики фонового цвета.
     * @param valueColor Необязательная лямбда для логики цвета текста.
     * @param mapper Функция, которая преобразует [value] (типа T) в [String]?.
     *
     * **Пример использования:**
     * ```kotlin
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
            StatsItem.Info(
                title = title,
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
