package com.whoppah.common.compose.ui

import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

/**
 * Composable Modifier extension function to draw a circular badge on a composable,
 * controlled by alignment and optional offsets.
 *
 * IMPORTANT: This is now a @Composable function to access LocalLayoutDirection.
 * It correctly chains onto the receiver Modifier.
 *
 * @param color The color of the badge.
 * @param radius The radius of the badge circle. Defaults to 6.dp.
 * @param enabled Whether the badge should be drawn. Defaults to true.
 * @param alignment The alignment of the badge within the composable's bounds.
 * Defaults to Alignment.TopEnd.
 * @param xOffset Horizontal offset relative to the aligned position.
 * Positive values move the badge to the right, negative to the left. Defaults to 0.dp.
 * @param yOffset Vertical offset relative to the aligned position.
 * Positive values move the badge down, negative moves it up. Defaults to 0.dp.
 */
@Stable
fun Modifier.badge(
    color: Color,
    radius: Dp = 4.dp,
    enabled: Boolean = true,
    alignment: Alignment = Alignment.Center,
    xOffset: Dp = 14.dp,
    yOffset: Dp = (-12).dp
): Modifier = this.composed { // Use Modifier.composed
    // Get the layout direction within the Composable scope provided by composed
    val layoutDirection: LayoutDirection = LocalLayoutDirection.current

    // Return the actual modifier instance from within composed
    if (enabled) {
        Modifier.drawWithContent {
            // Draw the original content first
            drawContent()

            // Calculate the badge diameter in pixels
            val badgeDiameterPx = (radius * 2).toPx()
            // Create the size of the badge's bounding box as IntSize
            val badgeSize = IntSize(badgeDiameterPx.roundToInt(), badgeDiameterPx.roundToInt())

            // Get the size of the composable area
            val composableSize = IntSize(size.width.roundToInt(), size.height.roundToInt())

            // Calculate the position of the badge's top-left corner based on alignment
            val badgeTopLeftIntOffset = alignment.align(
                size = badgeSize,
                space = composableSize,
                layoutDirection = layoutDirection // Use layoutDirection read inside composed
            )

            // Calculate the center of the badge, applying the offsets relative to the aligned position
            val badgeCenter = Offset(
                x = badgeTopLeftIntOffset.x + radius.toPx() + xOffset.toPx(),
                y = badgeTopLeftIntOffset.y + radius.toPx() + yOffset.toPx()
            )

            // Draw the circle badge
            drawCircle(
                color = color,
                radius = radius.toPx(),
                center = badgeCenter
            )
        }
    } else {
        // If not enabled, return an empty modifier (identity)
        Modifier
    }
}

//@Preview
//@Composable
//private fun Preview() {
//    Text(
//        text = "Content",
//        modifier = Modifier
//            .padding(16.dp)
//            .badge(
//                color = Color.Red,
//                alignment = Alignment.TopEnd,
//                xOffset = 6.dp, yOffset = (-4).dp,
//            )
//    )
//}