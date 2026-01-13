package com.whoppah.common.compose.icons

import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.DefaultFillType
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathBuilder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object WhIcons {
    object Profile
    object State
    object Navigation
    object Theme
    object Action
}

/**
 * Utility delegate to construct a Whoppah icon with default size information.
 * This is used by generated icons, and should not be used manually.
 *
 * @param name the full name of the generated icon
 * @param block builder lambda to add paths to this vector asset
 */
inline fun whoppahIcon(
    name: String,
    defaultWidth: Dp = WhoppahIconDimension.dp,
    defaultHeight: Dp = WhoppahIconDimension.dp,
    viewportWidth: Float = WhoppahIconDimension,
    viewportHeight: Float = WhoppahIconDimension,
    autoMirror: Boolean = false,
    block: ImageVector.Builder.() -> ImageVector.Builder
): ImageVector = ImageVector.Builder(
    name = name,
    defaultWidth = defaultWidth,
    defaultHeight = defaultHeight,
    viewportWidth = viewportWidth,
    viewportHeight = viewportHeight,
    autoMirror = autoMirror,
).block().build()

/**
 * Adds a vector path to this icon with Material defaults.
 *
 * @param fillAlpha fill alpha for this path
 * @param strokeAlpha stroke alpha for this path
 * @param pathFillType [PathFillType] for this path
 * @param pathBuilder builder lambda to add commands to this path
 */
inline fun ImageVector.Builder.whoppahPath(
    fill: Brush? = SolidColor(Color.Black),
    fillAlpha: Float = 1f,
    stroke: Brush? = null,
    strokeAlpha: Float = 1f,
    pathFillType: PathFillType = DefaultFillType,
    pathBuilder: PathBuilder.() -> Unit
) = path(
    fill = fill,
    fillAlpha = fillAlpha,
    stroke = stroke,
    strokeAlpha = strokeAlpha,
    strokeLineWidth = 1f,
    strokeLineCap = StrokeCap.Butt,
    strokeLineJoin = StrokeJoin.Bevel,
    strokeLineMiter = 1f,
    pathFillType = pathFillType,
    pathBuilder = pathBuilder
)

@PublishedApi
internal const val WhoppahIconDimension = 24f