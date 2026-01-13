package com.whoppah.common.compose.icons.state

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.State.NotificationAdd: ImageVector
    get() {
        if (_notificationAdd != null) return _notificationAdd!!
        _notificationAdd = materialIcon(name = "State.NotificationAdd") {
            materialPath {
                moveTo(16.0F, 14.0F)
                verticalLineToRelative(3.0F)
                horizontalLineTo(8.0F)
                verticalLineToRelative(-7.0F)
                curveToRelative(0.0F, -2.21F, 1.79F, -4.0F, 4.0F, -4.0F)
                curveToRelative(0.85F, 0.0F, 1.64F, 0.26F, 2.28F, 0.72F)
                lineToRelative(1.43F, -1.43F)
                curveToRelative(-0.64F, -0.51F, -1.39F, -0.88F, -2.21F, -1.09F)
                verticalLineTo(3.5F)
                curveTo(13.5F, 2.67F, 12.83F, 2.0F, 12.0F, 2.0F)
                reflectiveCurveToRelative(-1.5F, 0.67F, -1.5F, 1.5F)
                verticalLineToRelative(0.7F)
                curveTo(7.91F, 4.86F, 6.0F, 7.21F, 6.0F, 10.0F)
                verticalLineToRelative(7.0F)
                horizontalLineTo(4.0F)
                verticalLineToRelative(2.0F)
                horizontalLineToRelative(16.0F)
                verticalLineToRelative(-2.0F)
                horizontalLineToRelative(-2.0F)
                verticalLineToRelative(-3.0F)
                horizontalLineTo(16.0F)
                close()

                moveTo(12.0F, 22.0F)
                curveToRelative(1.1F, 0.0F, 2.0F, -0.9F, 2.0F, -2.0F)
                horizontalLineToRelative(-4.0F)
                curveTo(10.0F, 21.1F, 10.9F, 22.0F, 12.0F, 22.0F)
                close()

                moveTo(24.0F, 8.0F)
                horizontalLineToRelative(-3.0F)
                verticalLineTo(5.0F)
                horizontalLineToRelative(-2.0F)
                verticalLineToRelative(3.0F)
                horizontalLineToRelative(-3.0F)
                verticalLineToRelative(2.0F)
                horizontalLineToRelative(3.0F)
                verticalLineToRelative(3.0F)
                horizontalLineToRelative(2.0F)
                verticalLineToRelative(-3.0F)
                horizontalLineToRelative(3.0F)
                verticalLineTo(8.0F)
                close()
            }
        }
        return _notificationAdd!!
    }
private var _notificationAdd: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.State.NotificationAdd, contentDescription = null)