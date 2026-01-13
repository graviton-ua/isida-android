package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.GlobalWorld: ImageVector
    //get() = Icons.Default.Public
    get() {
        if (_globalWorld != null) return _globalWorld!!
        _globalWorld = materialIcon(name = "Theme.GlobalWorld") {
            materialPath {
                moveTo(12.0F, 2.0F)
                curveTo(6.48F, 2.0F, 2.0F, 6.48F, 2.0F, 12.0F)
                reflectiveCurveToRelative(4.48F, 10.0F, 10.0F, 10.0F)
                reflectiveCurveToRelative(10.0F, -4.48F, 10.0F, -10.0F)
                reflectiveCurveTo(17.52F, 2.0F, 12.0F, 2.0F)
                close()

                moveTo(11.0F, 19.93F)
                curveToRelative(-3.95F, -0.49F, -7.0F, -3.85F, -7.0F, -7.93F)
                curveToRelative(0.0F, -0.62F, 0.08F, -1.21F, 0.21F, -1.79F)
                lineTo(9.0F, 15.0F)
                verticalLineToRelative(1.0F)
                curveToRelative(0.0F, 1.1F, 0.9F, 2.0F, 2.0F, 2.0F)
                verticalLineToRelative(1.93F)
                close()

                moveTo(17.9F, 17.39F)
                curveToRelative(-0.26F, -0.81F, -1.0F, -1.39F, -1.9F, -1.39F)
                horizontalLineToRelative(-1.0F)
                verticalLineToRelative(-3.0F)
                curveToRelative(0.0F, -0.55F, -0.45F, -1.0F, -1.0F, -1.0F)
                lineTo(8.0F, 12.0F)
                verticalLineToRelative(-2.0F)
                horizontalLineToRelative(2.0F)
                curveToRelative(0.55F, 0.0F, 1.0F, -0.45F, 1.0F, -1.0F)
                lineTo(11.0F, 7.0F)
                horizontalLineToRelative(2.0F)
                curveToRelative(1.1F, 0.0F, 2.0F, -0.9F, 2.0F, -2.0F)
                verticalLineToRelative(-0.41F)
                curveToRelative(2.93F, 1.19F, 5.0F, 4.06F, 5.0F, 7.41F)
                curveToRelative(0.0F, 2.08F, -0.8F, 3.97F, -2.1F, 5.39F)
                close()
            }
        }
        return _globalWorld!!
    }
private var _globalWorld: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.GlobalWorld, contentDescription = null)