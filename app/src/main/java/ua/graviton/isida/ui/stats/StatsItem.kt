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
        data class Int(val value: kotlin.Int?, val target: kotlin.Int? = null) : Value()
        data class Float(val value: kotlin.Float?, val target: kotlin.Float? = null) : Value()
        data class Text(val value: String?, val target: String? = null) : Value()
    }
}