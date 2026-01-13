package com.whoppah.common.compose.icons.state

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.State.Notification: ImageVector
    get() {
        if (_notification != null) return _notification!!
        _notification = materialIcon(name = "State.Notification") {
            materialPath {
                moveTo(19.0F, 17.0F)
                verticalLineTo(11.8F)
                curveTo(18.5F, 11.9F, 18.0F, 12.0F, 17.5F, 12.0F)
                horizontalLineTo(17.0F)
                verticalLineTo(18.0F)
                horizontalLineTo(7.0F)
                verticalLineTo(11.0F)
                curveTo(7.0F, 8.2F, 9.2F, 6.0F, 12.0F, 6.0F)
                curveTo(12.1F, 4.7F, 12.7F, 3.6F, 13.5F, 2.7F)
                curveTo(13.2F, 2.3F, 12.6F, 2.0F, 12.0F, 2.0F)
                curveTo(10.9F, 2.0F, 10.0F, 2.9F, 10.0F, 4.0F)
                verticalLineTo(4.3F)
                curveTo(7.0F, 5.2F, 5.0F, 7.9F, 5.0F, 11.0F)
                verticalLineTo(17.0F)
                lineTo(3.0F, 19.0F)
                verticalLineTo(20.0F)
                horizontalLineTo(21.0F)
                verticalLineTo(19.0F)
                lineTo(19.0F, 17.0F)

                moveTo(10.0F, 21.0F)
                curveTo(10.0F, 22.1F, 10.9F, 23.0F, 12.0F, 23.0F)
                curveTo(13.1F, 23.0F, 14.0F, 22.1F, 14.0F, 21.0F)
                horizontalLineTo(10.0F)

                moveTo(21.0F, 6.5F)
                curveTo(21.0F, 8.4F, 19.4F, 10.0F, 17.5F, 10.0F)
                curveTo(15.6F, 10.0F, 14.0F, 8.4F, 14.0F, 6.5F)
                curveTo(14.0F, 4.6F, 15.6F, 3.0F, 17.5F, 3.0F)
                curveTo(19.4F, 3.0F, 21.0F, 4.6F, 21.0F, 6.5F)
                close()
            }
        }
        return _notification!!
    }
private var _notification: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.State.Notification, contentDescription = null)