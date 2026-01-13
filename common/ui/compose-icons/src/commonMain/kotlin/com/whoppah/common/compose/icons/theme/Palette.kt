package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.Palette: ImageVector
    get() {
        if (_palette != null) return _palette!!
        _palette = materialIcon(name = "Theme.Palette") {
            materialPath {
                moveTo(12.0F, 2.0F)
                curveTo(6.49F, 2.0F, 2.0F, 6.49F, 2.0F, 12.0F)
                reflectiveCurveToRelative(4.49F, 10.0F, 10.0F, 10.0F)
                curveToRelative(1.38F, 0.0F, 2.5F, -1.12F, 2.5F, -2.5F)
                curveToRelative(0.0F, -0.61F, -0.23F, -1.2F, -0.64F, -1.67F)
                curveToRelative(-0.08F, -0.1F, -0.13F, -0.21F, -0.13F, -0.33F)
                curveToRelative(0.0F, -0.28F, 0.22F, -0.5F, 0.5F, -0.5F)
                horizontalLineTo(16.0F)
                curveToRelative(3.31F, 0.0F, 6.0F, -2.69F, 6.0F, -6.0F)
                curveTo(22.0F, 6.04F, 17.51F, 2.0F, 12.0F, 2.0F)
                close()

                moveTo(17.5F, 13.0F)
                curveToRelative(-0.83F, 0.0F, -1.5F, -0.67F, -1.5F, -1.5F)
                curveToRelative(0.0F, -0.83F, 0.67F, -1.5F, 1.5F, -1.5F)
                reflectiveCurveToRelative(1.5F, 0.67F, 1.5F, 1.5F)
                curveTo(19.0F, 12.33F, 18.33F, 13.0F, 17.5F, 13.0F)
                close()

                moveTo(14.5F, 9.0F)
                curveTo(13.67F, 9.0F, 13.0F, 8.33F, 13.0F, 7.5F)
                curveTo(13.0F, 6.67F, 13.67F, 6.0F, 14.5F, 6.0F)
                reflectiveCurveTo(16.0F, 6.67F, 16.0F, 7.5F)
                curveTo(16.0F, 8.33F, 15.33F, 9.0F, 14.5F, 9.0F)
                close()

                moveTo(5.0F, 11.5F)
                curveTo(5.0F, 10.67F, 5.67F, 10.0F, 6.5F, 10.0F)
                reflectiveCurveTo(8.0F, 10.67F, 8.0F, 11.5F)
                curveTo(8.0F, 12.33F, 7.33F, 13.0F, 6.5F, 13.0F)
                reflectiveCurveTo(5.0F, 12.33F, 5.0F, 11.5F)
                close()

                moveTo(11.0F, 7.5F)
                curveTo(11.0F, 8.33F, 10.33F, 9.0F, 9.5F, 9.0F)
                reflectiveCurveTo(8.0F, 8.33F, 8.0F, 7.5F)
                curveTo(8.0F, 6.67F, 8.67F, 6.0F, 9.5F, 6.0F)
                reflectiveCurveTo(11.0F, 6.67F, 11.0F, 7.5F)
                close()
            }
        }
        return _palette!!
    }
private var _palette: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.Palette, contentDescription = null)