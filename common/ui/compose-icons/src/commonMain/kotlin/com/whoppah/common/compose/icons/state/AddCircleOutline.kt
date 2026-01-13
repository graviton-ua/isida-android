package com.whoppah.common.compose.icons.state

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.State.AddCircleOutline: ImageVector
    get() {
        if (_addCircleOutline != null) return _addCircleOutline!!
        _addCircleOutline = materialIcon(name = "State.AddCircleOutline") {
            materialPath {
                moveTo(13.0F, 7.0F)
                horizontalLineToRelative(-2.0F)
                verticalLineToRelative(4.0F)
                lineTo(7.0F, 11.0F)
                verticalLineToRelative(2.0F)
                horizontalLineToRelative(4.0F)
                verticalLineToRelative(4.0F)
                horizontalLineToRelative(2.0F)
                verticalLineToRelative(-4.0F)
                horizontalLineToRelative(4.0F)
                verticalLineToRelative(-2.0F)
                horizontalLineToRelative(-4.0F)
                lineTo(13.0F, 7.0F)
                close()

                moveTo(12.0F, 2.0F)
                curveTo(6.48F, 2.0F, 2.0F, 6.48F, 2.0F, 12.0F)
                reflectiveCurveToRelative(4.48F, 10.0F, 10.0F, 10.0F)
                reflectiveCurveToRelative(10.0F, -4.48F, 10.0F, -10.0F)
                reflectiveCurveTo(17.52F, 2.0F, 12.0F, 2.0F)
                close()

                moveTo(12.0F, 20.0F)
                curveToRelative(-4.41F, 0.0F, -8.0F, -3.59F, -8.0F, -8.0F)
                reflectiveCurveToRelative(3.59F, -8.0F, 8.0F, -8.0F)
                reflectiveCurveToRelative(8.0F, 3.59F, 8.0F, 8.0F)
                reflectiveCurveToRelative(-3.59F, 8.0F, -8.0F, 8.0F)
                close()
            }
        }
        return _addCircleOutline!!
    }
private var _addCircleOutline: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.State.AddCircleOutline, contentDescription = null)