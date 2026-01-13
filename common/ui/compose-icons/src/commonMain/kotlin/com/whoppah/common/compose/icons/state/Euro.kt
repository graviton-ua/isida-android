package com.whoppah.common.compose.icons.state

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.State.Euro: ImageVector
    get() {
        if (_euro != null) return _euro!!
        _euro = materialIcon(name = "State.Euro") {
            materialPath {
                moveTo(15.0F, 18.5F)
                curveToRelative(-2.51F, 0.0F, -4.68F, -1.42F, -5.76F, -3.5F)
                horizontalLineTo(15.0F)
                lineToRelative(1.0F, -2.0F)
                horizontalLineTo(8.58F)
                curveToRelative(-0.05F, -0.33F, -0.08F, -0.66F, -0.08F, -1.0F)
                reflectiveCurveToRelative(0.03F, -0.67F, 0.08F, -1.0F)
                horizontalLineTo(15.0F)
                lineToRelative(1.0F, -2.0F)
                horizontalLineTo(9.24F)
                curveTo(10.32F, 6.92F, 12.5F, 5.5F, 15.0F, 5.5F)
                curveToRelative(1.61F, 0.0F, 3.09F, 0.59F, 4.23F, 1.57F)
                lineTo(21.0F, 5.3F)
                curveTo(19.41F, 3.87F, 17.3F, 3.0F, 15.0F, 3.0F)
                curveToRelative(-3.92F, 0.0F, -7.24F, 2.51F, -8.48F, 6.0F)
                horizontalLineTo(3.0F)
                lineToRelative(-1.0F, 2.0F)
                horizontalLineToRelative(4.06F)
                curveTo(6.02F, 11.33F, 6.0F, 11.66F, 6.0F, 12.0F)
                reflectiveCurveToRelative(0.02F, 0.67F, 0.06F, 1.0F)
                horizontalLineTo(3.0F)
                lineToRelative(-1.0F, 2.0F)
                horizontalLineToRelative(4.52F)
                curveToRelative(1.24F, 3.49F, 4.56F, 6.0F, 8.48F, 6.0F)
                curveToRelative(2.31F, 0.0F, 4.41F, -0.87F, 6.0F, -2.3F)
                lineToRelative(-1.78F, -1.77F)
                curveTo(18.09F, 17.91F, 16.62F, 18.5F, 15.0F, 18.5F)
                close()
            }
        }
        return _euro!!
    }
private var _euro: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.State.Euro, contentDescription = null)