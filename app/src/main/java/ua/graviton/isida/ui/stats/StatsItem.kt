package ua.graviton.isida.ui.stats

import android.graphics.Color
import androidx.annotation.StringRes

data class StatsItem(
    @StringRes val titleResId: Int,
    val value: Number? = null,
    val titleColor: Int = Color.BLACK,
)