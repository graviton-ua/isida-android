package ua.graviton.isida.ui.home.stats

import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.StringResource

data class StatsItem(
    val titleResId: StringResource,
    val value: Value? = null,
    val valueColor: Color? = null,
    val backgroundColor: Color? = null,
) {

    sealed class Value {
        data class IntVal(val value: Int?, val target: Int? = null) : Value()
        data class FloatVal(val value: Float?, val target: Float? = null) : Value()
        data class TextRaw(val value: String?, val target: String? = null) : Value()
        data class TextResId(val value: StringResource?, val target: StringResource? = null) : Value()
    }
}