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
    val style: Style

    @Immutable
    data class Style(
        val backgroundColor: Color? = null,
        val titleColor: Color? = null,
        val valueColor: Color? = null,
    )

    /**
     * Представляет собой заголовок [StatsItem].
     * Может быть либо [StringResource], либо обычной строкой [String].
     */
    @Immutable
    sealed interface Title {
        /** Заголовок, определенный строковым ресурсом. */
        @Deprecated(
            "Use Title.ComposableString instead",
            ReplaceWith(
                "Title.ComposableString.composableString(key = res to args) { stringResource(res, *args.toTypedArray()) }",
                "org.jetbrains.compose.resources.stringResource"
            )
        )
        data class Resource(
            val res: StringResource,
            val args: List<Any> = emptyList() // 1. Добавляем поле для аргументов
        ) : Title

        /** Заголовок, определенный обычной строкой. */
        @Deprecated(
            "Use Title.ComposableString instead",
            ReplaceWith("Title.ComposableString.composableString(key = text) { text }")
        )
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
     * @property style Стилизация элемента.
     *
     * **Пример использования:**
     * ```kotlin
     * StatsItem.Header(title = Res.string.section_title, style = StatsItem.Style(backgroundColor = Color.LightGray))
     * ```
     */
    @Immutable
    data class Header(
        override val title: Title,
        override val style: Style = Style(),
    ) : StatsItem {
        @Deprecated(
            "Use primary constructor with Title.ComposableString",
            ReplaceWith(
                "Header(title = Title.ComposableString.composableString(key = title to args) { stringResource(title, *args.toTypedArray()) }, style = style)",
                "org.jetbrains.compose.resources.stringResource"
            )
        )
        constructor(
            title: StringResource,
            vararg args: Any, // Добавляем vararg
            style: Style = Style()
        ) : this(Title.Resource(title, args.toList()), style)

        @Deprecated(
            "Use primary constructor with Title.ComposableString",
            ReplaceWith("Header(title = Title.ComposableString.composableString(key = title) { title }, style = style)")
        )
        constructor(title: String, style: Style = Style()) : this(Title.Raw(title), style)

        override val id: Int get() = title.hashCode()
    }

    /**
     * Информационная строка, отображающая метку и значение (контент).
     *
     * @property title Метка строки.
     * @property content Данные для отображения (числовые, строковый ресурс или обычный текст).
     * @property style Стилизация элемента.
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
        override val style: Style = Style(),
    ) : StatsItem {
        @Deprecated(
            "Use primary constructor with Title.ComposableString",
            ReplaceWith(
                "Info(title = Title.ComposableString.composableString(key = title) { stringResource(title) }, content = content, style = style)",
                "org.jetbrains.compose.resources.stringResource"
            )
        )
        constructor(title: StringResource, content: Content, style: Style = Style()) : this(Title.Resource(title), content, style)

        @Deprecated(
            "Use primary constructor with Title.ComposableString",
            ReplaceWith("Info(title = Title.ComposableString.composableString(key = title) { title }, content = content, style = style)")
        )
        constructor(title: String, content: Content, style: Style = Style()) : this(Title.Raw(title), content, style)

        override val id: Int get() = title.hashCode()
    }

    @Immutable
    data class InfoString(
        override val title: Title,
        val content: Title.ComposableString,
        override val style: Style = Style(),
    ) : StatsItem {
        override val id: Int = title.hashCode()
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
         */
        @Immutable
        data class Numeric<T : Number>(
            val value: T?,
            val target: T? = null,
        ) : Content

        /**
         * Контент на основе строковых ресурсов (например, коды состояния, перечисления).
         *
         * @property values Список текущих значений в виде строковых ресурсов.
         * @property targets Список целевых значений в виде строковых ресурсов.
         */
        @Immutable
        data class TextResource(
            val values: List<StringResource>,
            val targets: List<StringResource> = emptyList(),
        ) : Content

        /**
         * Обычный текстовый контент (например, динамические строки).
         *
         * @property value Текущее текстовое значение.
         * @property target Необязательное целевое текстовое значение.
         */
        @Immutable
        data class TextRaw(
            val value: String?,
            val target: String? = null,
        ) : Content
    }
}