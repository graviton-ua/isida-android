package com.whoppah.common.compose.icons.action

import androidx.compose.foundation.Image
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Action.Login: ImageVector
    get() {
        if (_login != null) return _login!!
        _login = materialIcon(name = "Action.Login") {
            materialPath {
                verticalLineToRelative(0.0F)
                moveTo(10.0F, 17.4026F)
                verticalLineTo(14.4026F)
                horizontalLineTo(3.0F)
                verticalLineTo(10.4026F)
                horizontalLineTo(10.0F)
                verticalLineTo(7.40259F)
                lineTo(15.0F, 12.4026F)
                lineTo(10.0F, 17.4026F)

                moveTo(10.0F, 2.40259F)
                horizontalLineTo(19.0F)
                curveTo(19.5304F, 2.40259F, 20.0391F, 2.6133F, 20.4142F, 2.98837F)
                curveTo(20.7893F, 3.36345F, 21.0F, 3.87215F, 21.0F, 4.40259F)
                verticalLineTo(20.4026F)
                curveTo(21.0F, 20.933F, 20.7893F, 21.4417F, 20.4142F, 21.8168F)
                curveTo(20.0391F, 22.1919F, 19.5304F, 22.4026F, 19.0F, 22.4026F)
                horizontalLineTo(10.0F)
                curveTo(9.46957F, 22.4026F, 8.96086F, 22.1919F, 8.58579F, 21.8168F)
                curveTo(8.21071F, 21.4417F, 8.0F, 20.933F, 8.0F, 20.4026F)
                verticalLineTo(18.4026F)
                horizontalLineTo(10.0F)
                verticalLineTo(20.4026F)
                horizontalLineTo(19.0F)
                verticalLineTo(4.40259F)
                horizontalLineTo(10.0F)
                verticalLineTo(6.40259F)
                horizontalLineTo(8.0F)
                verticalLineTo(4.40259F)
                curveTo(8.0F, 3.87215F, 8.21071F, 3.36345F, 8.58579F, 2.98837F)
                curveTo(8.96086F, 2.6133F, 9.46957F, 2.40259F, 10.0F, 2.40259F)

                close()
            }
        }
        return _login!!
    }

private var _login: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Image(imageVector = WhIcons.Action.Login, contentDescription = null)
}