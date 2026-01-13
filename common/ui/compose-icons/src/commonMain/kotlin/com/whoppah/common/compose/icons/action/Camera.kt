package com.whoppah.common.compose.icons.action

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Action.Camera: ImageVector
    //get() = Icons.Filled.PhotoCamera
    get() {
        if (_camera != null) return _camera!!
        _camera = materialIcon(name = "Action.Camera") {
            materialPath {
                moveTo(12.0F, 12.0F)
                moveToRelative(-3.2F, 0.0F)
                arcToRelative(3.2F, 3.2F, 0.0F, isMoreThanHalf = true, isPositiveArc = true, dx1 = 6.4F, dy1 = 0.0F)
                arcToRelative(3.2F, 3.2F, 0.0F, isMoreThanHalf = true, isPositiveArc = true, dx1 = -6.4F, dy1 = 0.0F)
                close()
            }
            materialPath {
                moveTo(9.0f, 2.0f)
                lineTo(7.17f, 4.0f)
                lineTo(4.0f, 4.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                verticalLineToRelative(12.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(16.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                lineTo(22.0f, 6.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                horizontalLineToRelative(-3.17f)
                lineTo(15.0f, 2.0f)
                lineTo(9.0f, 2.0f)
                close()

                moveTo(12.0f, 17.0f)
                curveToRelative(-2.76f, 0.0f, -5.0f, -2.24f, -5.0f, -5.0f)
                reflectiveCurveToRelative(2.24f, -5.0f, 5.0f, -5.0f)
                reflectiveCurveToRelative(5.0f, 2.24f, 5.0f, 5.0f)
                reflectiveCurveToRelative(-2.24f, 5.0f, -5.0f, 5.0f)
                close()
            }
        }
        return _camera!!
    }
private var _camera: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Action.Camera, contentDescription = null)