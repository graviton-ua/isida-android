package ua.graviton.isida.ui.home.stats

import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.StringResource

data class StatsItem(
    val titleResId: StringResource,
    val content: Content,
    val backgroundColor: Color? = null,
) {
    sealed interface Content {
        data class Numeric<T : Number>(
            val value: T?,
            val target: T? = null,
            val valueColor: Color? = null
        ) : Content

        data class TextResource(
            val value: StringResource?,
            val target: StringResource? = null,
            val valueColor: Color? = null
        ) : Content

        data class TextRaw(
            val value: String?,
            val target: String? = null,
            val valueColor: Color? = null
        ) : Content
    }
}
