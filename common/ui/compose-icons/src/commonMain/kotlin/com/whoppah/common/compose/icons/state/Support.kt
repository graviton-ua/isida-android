package com.whoppah.common.compose.icons.state

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.State.Support: ImageVector
    get() {
        if (_support != null) return _support!!
        _support = materialIcon(name = "State.Support") {
            materialPath {
                moveTo(12.0F, 2.0F)
                curveTo(6.48F, 2.0F, 2.0F, 6.48F, 2.0F, 12.0F)
                curveToRelative(0.0F, 5.52F, 4.48F, 10.0F, 10.0F, 10.0F)
                reflectiveCurveToRelative(10.0F, -4.48F, 10.0F, -10.0F)
                curveTo(22.0F, 6.48F, 17.52F, 2.0F, 12.0F, 2.0F)
                close()

                moveTo(19.46F, 9.12F)
                lineToRelative(-2.78F, 1.15F)
                curveToRelative(-0.51F, -1.36F, -1.58F, -2.44F, -2.95F, -2.94F)
                lineToRelative(1.15F, -2.78F)
                curveTo(16.98F, 5.35F, 18.65F, 7.02F, 19.46F, 9.12F)
                close()

                moveTo(12.0F, 15.0F)
                curveToRelative(-1.66F, 0.0F, -3.0F, -1.34F, -3.0F, -3.0F)
                reflectiveCurveToRelative(1.34F, -3.0F, 3.0F, -3.0F)
                reflectiveCurveToRelative(3.0F, 1.34F, 3.0F, 3.0F)
                reflectiveCurveTo(13.66F, 15.0F, 12.0F, 15.0F)
                close()

                moveTo(9.13F, 4.54F)
                lineToRelative(1.17F, 2.78F)
                curveToRelative(-1.38F, 0.5F, -2.47F, 1.59F, -2.98F, 2.97F)
                lineTo(4.54F, 9.13F)
                curveTo(5.35F, 7.02F, 7.02F, 5.35F, 9.13F, 4.54F)
                close()

                moveTo(4.54F, 14.87F)
                lineToRelative(2.78F, -1.15F)
                curveToRelative(0.51F, 1.38F, 1.59F, 2.46F, 2.97F, 2.96F)
                lineToRelative(-1.17F, 2.78F)
                curveTo(7.02F, 18.65F, 5.35F, 16.98F, 4.54F, 14.87F)
                close()

                moveTo(14.88F, 19.46F)
                lineToRelative(-1.15F, -2.78F)
                curveToRelative(1.37F, -0.51F, 2.45F, -1.59F, 2.95F, -2.97F)
                lineToRelative(2.78F, 1.17F)
                curveTo(18.65F, 16.98F, 16.98F, 18.65F, 14.88F, 19.46F)
                close()
            }
        }
        return _support!!
    }
private var _support: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.State.Support, contentDescription = null)