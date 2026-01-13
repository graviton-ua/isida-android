package com.whoppah.common.compose.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

object WhoppahShapes {
    /**
     * extraSmall - A shape style with 4 same-sized corners whose size are bigger than
     * [ZeroCornerSize] and smaller than [Shapes.small]. By default autocomplete menu, select menu,
     * snackbars, standard menu, and text fields use this shape.
     */
    val extraSmall: CornerBasedShape = RoundedCornerShape(2.dp)

    /**
     * small - A shape style with 4 same-sized corners whose size are bigger than
     * [Shapes.extraSmall] and smaller than [Shapes.medium]. By default chips use this shape.
     */
    val small: CornerBasedShape = RoundedCornerShape(4.dp)

    /**
     * medium - A shape style with 4 same-sized corners whose size are bigger than [Shapes.small]
     * and smaller than [Shapes.large]. By default cards and small FABs use this shape.
     */
    val medium: CornerBasedShape = RoundedCornerShape(8.dp)

    /**
     * large - A shape style with 4 same-sized corners whose size are bigger than [Shapes.medium]
     * and smaller than [Shapes.extraLarge]. By default extended FABs, FABs, and navigation drawers use
     * this shape.
     */
    val large: CornerBasedShape = RoundedCornerShape(12.dp)

    /**
     * extraLarge - A shape style with 4 same-sized corners whose size are bigger than
     * [Shapes.large] and smaller than [CircleShape]. By default large FABs use this shape.
     */
    val extraLarge: CornerBasedShape = RoundedCornerShape(16.dp)

    fun asMaterialShapes(): Shapes = Shapes(
        extraSmall = extraSmall, small = small,
        medium = medium,
        large = large, extraLarge = extraLarge,
    )
}