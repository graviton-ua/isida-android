package ua.graviton.isida.ui.home.stats

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 * Представляет собой отдельный элемент в списке на экране статистики.
 * Этот sealed интерфейс позволяет использовать различные типы элементов, такие как заголовки и информационные строки.
 *
 * @property id Уникальный идентификатор элемента, используемый для оптимизации списка (например, в LazyColumn).
 * @property title Заголовок элемента, который может быть ресурсом или обычной строкой.
 */
@Immutable
internal sealed interface StatsItem {
    val id: Int
    val title: Title

    /**
     * Представляет собой заголовок [StatsItem].
     * Может быть либо [StringResource], либо обычной строкой [String].
     */
    @Immutable
    sealed interface Title {
        /** Заголовок, определенный строковым ресурсом. */
        data class Resource(
            val res: StringResource,
            val args: List<Any> = emptyList() // 1. Добавляем поле для аргументов
        ) : Title

        /** Заголовок, определенный обычной строкой. */
        data class Raw(val text: String) : Title

        @Immutable
        class ComposableString(
            val key: Any,
            val text: @Composable () -> String,
        ) : Title {
            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (other !is ComposableString) return false
                return key == other.key
            }

            override fun hashCode(): Int = key.hashCode()

            companion object {
                fun composableString(key: Any, text: @Composable () -> String) = ComposableString(key, text)
                fun composableString(vararg keys: Any, text: @Composable () -> String) = ComposableString(keys.toList(), text)
            }
        }

        /**
         * Разрешает заголовок в [String] внутри Composable-контекста.
         */
        @Composable
        fun asString(): String {
            return when (this) {
                is Resource -> {
                    // 2. Используем версию stringResource, которая принимает аргументы
                    if (args.isEmpty()) {
                        stringResource(this.res)
                    } else {
                        // Используем spread operator (*), чтобы передать список как vararg
                        stringResource(this.res, *args.toTypedArray())
                    }
                }

                is Raw -> this.text

                is ComposableString -> this.text()
            }
        }
    }

    /**
     * Элемент закрепленного заголовка для группировки или маркировки разделов.
     *
     * @property title Заголовок заголовка.
     * @property backgroundColor Необязательный фоновый цвет для заголовка.
     *
     * **Пример использования:**
     * ```kotlin
     * StatsItem.Header(title = Res.string.section_title, backgroundColor = Color.LightGray)
     * ```
     */
    @Immutable
    data class Header(
        override val title: Title,
        val backgroundColor: Color? = null,
    ) : StatsItem {
        constructor(
            title: StringResource,
            vararg args: Any, // Добавляем vararg
            backgroundColor: Color? = null
        ) : this(Title.Resource(title, args.toList()), backgroundColor)

        constructor(title: String, backgroundColor: Color? = null) : this(Title.Raw(title), backgroundColor)

        override val id: Int get() = title.hashCode()
    }

    /**
     * Информационная строка, отображающая метку и значение (контент).
     *
     * @property title Метка строки.
     * @property content Данные для отображения (числовые, строковый ресурс или обычный текст).
     * @property backgroundColor Необязательный фоновый цвет для части строки со значением/контентом.
     *
     * **Пример использования:**
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
     * Определяет тип контента, отображаемого в строке [Info].
     */
    @Immutable
    sealed interface Content {
        /**
         * Числовой контент (например, показания датчиков).
         *
         * @property value Текущее значение.
         * @property target Необязательное целевое или заданное значение.
         * @property valueColor Необязательный цвет текста для значения (например, для индикации предупреждений).
         */
        @Immutable
        data class Numeric<T : Number>(
            val value: T?,
            val target: T? = null,
            val valueColor: Color? = null
        ) : Content

        /**
         * Контент на основе строковых ресурсов (например, коды состояния, перечисления).
         *
         * @property values Список текущих значений в виде строковых ресурсов.
         * @property targets Список целевых значений в виде строковых ресурсов.
         * @property valueColor Необязательный цвет текста для значения.
         */
        @Immutable
        data class TextResource(
            val values: List<StringResource>,
            val targets: List<StringResource> = emptyList(),
            val valueColor: Color? = null
        ) : Content

        /**
         * Обычный текстовый контент (например, динамические строки).
         *
         * @property value Текущее текстовое значение.
         * @property target Необязательное целевое текстовое значение.
         * @property valueColor Необязательный цвет текста для значения.
         */
        @Immutable
        data class TextRaw(
            val value: String?,
            val target: String? = null,
            val valueColor: Color? = null
        ) : Content
    }
}