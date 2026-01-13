package com.whoppah.common.compose

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode

val brushMultiColor: Brush = Brush.linearGradient(
    0.0f to Color.Yellow,
    0.25f to Color.Green,
    0.5f to Color.Blue,
    0.75f to Color.Red,
    start = Offset.Zero,
    end = Offset(20f, 20f),
    tileMode = TileMode.Repeated,
)
val brushTransparent: Brush = Brush.linearGradient(
    0.0f to Color.White,
    0.25f to Color.Gray,
    start = Offset.Zero,
    end = Offset(10f, 10f),
    tileMode = TileMode.Repeated,
)