package ua.graviton.isida.ui.home.stats

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Представляет собой отдельный элемент в списке на экране статистики.
 * Этот sealed интерфейс позволяет использовать различные типы элементов, такие как заголовки и информационные строки.
 *
 * @property id Уникальный идентификатор элемента, используемый для оптимизации списка (например, в LazyColumn).
 * @property title Заголовок элемента, который может быть ресурсом или обычной строкой.
 */
@Immutable
sealed interface StatsItem {
    val id: Int
    val title: ComposableString
    val style: Style

    @Immutable
    data class Style(
        val backgroundColor: Color? = null,
        val titleColor: Color? = null,
        val valueColor: Color? = null,
    )


    @Immutable
    class ComposableString(
        val key: Any,
        val text: @Composable () -> String,
    ) {
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
        override val title: ComposableString,
        override val style: Style = Style(),
    ) : StatsItem {
        override val id: Int get() = title.hashCode()
    }

    @Immutable
    data class Info(
        override val title: ComposableString,
        val content: ComposableString,
        override val style: Style = Style(),
    ) : StatsItem {
        override val id: Int = title.hashCode()
    }
}