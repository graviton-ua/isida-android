package ua.isida.common.ui.compose

import androidx.compose.foundation.background
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape

@Stable
fun Modifier.backgroundNotNull(color: Color?, shape: Shape = RectangleShape): Modifier =
    if (color != null && color != Color.Unspecified && color != Color.Transparent) this.background(color, shape) else this