package ua.graviton.isida.ui.stats

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color

data class StatsItem(
    @StringRes val titleResId: Int,
    val value: Value? = null,
    val valueColor: Color? = null,
    val backgroundColor: Color? = null,
) {

    sealed class Value {
        data class IntVal(val value: Int?, val target: Int? = null) : Value()
        data class FloatVal(val value: Float?, val target: Float? = null) : Value()
        data class TextRaw(val value: String?, val target: String? = null) : Value()
        data class TextResId(@StringRes val value: Int?, val target: Int? = null) : Value()
    }
}