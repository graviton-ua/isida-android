package com.whoppah.common.compose.icons.action

import androidx.compose.foundation.Image
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Action.Logout: ImageVector
    get() {
        if (_logout != null) return _logout!!
        _logout = materialIcon(name = "Action.Logout") {
            materialPath {
                verticalLineToRelative(0.0F)
                moveTo(16.0F, 17.4026F)
                verticalLineTo(14.4026F)
                horizontalLineTo(9.0F)
                verticalLineTo(10.4026F)
                horizontalLineTo(16.0F)
                verticalLineTo(7.40259F)
                lineTo(21.0F, 12.4026F)
                lineTo(16.0F, 17.4026F)

                moveTo(14.0F, 2.40259F)
                curveTo(14.5304F, 2.40259F, 15.0391F, 2.6133F, 15.4142F, 2.98837F)
                curveTo(15.7893F, 3.36345F, 16.0F, 3.87215F, 16.0F, 4.40259F)
                verticalLineTo(6.40259F)
                horizontalLineTo(14.0F)
                verticalLineTo(4.40259F)
                horizontalLineTo(5.0F)
                verticalLineTo(20.4026F)
                horizontalLineTo(14.0F)
                verticalLineTo(18.4026F)
                horizontalLineTo(16.0F)
                verticalLineTo(20.4026F)
                curveTo(16.0F, 20.933F, 15.7893F, 21.4417F, 15.4142F, 21.8168F)
                curveTo(15.0391F, 22.1919F, 14.5304F, 22.4026F, 14.0F, 22.4026F)
                horizontalLineTo(5.0F)
                curveTo(4.46957F, 22.4026F, 3.96086F, 22.1919F, 3.58579F, 21.8168F)
                curveTo(3.21071F, 21.4417F, 3.0F, 20.933F, 3.0F, 20.4026F)
                verticalLineTo(4.40259F)
                curveTo(3.0F, 3.87215F, 3.21071F, 3.36345F, 3.58579F, 2.98837F)
                curveTo(3.96086F, 2.6133F, 4.46957F, 2.40259F, 5.0F, 2.40259F)
                horizontalLineTo(14.0F)

                close()
            }
        }
        return _logout!!
    }

private var _logout: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Image(imageVector = WhIcons.Action.Logout, contentDescription = null)
}