package ua.graviton.isida.ui.stats

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import ua.graviton.isida.R

data class StatsItem(
    @StringRes val titleResId: Int,
    val value: Any? = null,
    val targetValue: Any? = null,
    @ColorRes val valueColor: Int? = null,
    @ColorRes val backgroundColor: Int? = null,
)