package ua.graviton.isida.ui.stats

import androidx.annotation.StringRes

data class StatsItem(
    @StringRes val titleResId: Int,
    val value: Number? = null,
)