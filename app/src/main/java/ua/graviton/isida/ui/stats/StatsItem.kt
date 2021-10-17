package ua.graviton.isida.ui.stats

import androidx.annotation.ColorRes
import androidx.annotation.StringRes

data class StatsItem(
    @StringRes val titleResId: Int,
    val value: Value? = null,
    @ColorRes val valueColor: Int? = null,
    @ColorRes val backgroundColor: Int? = null,
) {

    sealed class Value {
        data class IntVal(val value: Int?, val target: Int? = null) : Value()
        data class FloatVal(val value: Float?, val target: Float? = null) : Value()
        data class TextRaw(val value: String?, val target: String? = null) : Value()
        data class TextResId(@StringRes val value: Int?, val target: Int? = null) : Value()
    }
}