package com.whoppah.common.compose.icons.action

import androidx.compose.foundation.Image
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Action.Import: ImageVector
    get() {
        if (_import != null) return _import!!
        _import = materialIcon(name = "Action.Import") {
            materialPath {
                verticalLineToRelative(0.0F)
                moveTo(19.0F, 3.40259F)
                horizontalLineTo(5.0F)
                curveTo(3.89F, 3.40259F, 3.0F, 4.29259F, 3.0F, 5.40259F)
                verticalLineTo(9.40259F)
                horizontalLineTo(5.0F)
                verticalLineTo(5.40259F)
                horizontalLineTo(19.0F)
                verticalLineTo(19.4026F)
                horizontalLineTo(5.0F)
                verticalLineTo(15.4026F)
                horizontalLineTo(3.0F)
                verticalLineTo(19.4026F)
                curveTo(3.0F, 19.933F, 3.21071F, 20.4417F, 3.58579F, 20.8168F)
                curveTo(3.96086F, 21.1919F, 4.46957F, 21.4026F, 5.0F, 21.4026F)
                horizontalLineTo(19.0F)
                curveTo(19.5304F, 21.4026F, 20.0391F, 21.1919F, 20.4142F, 20.8168F)
                curveTo(20.7893F, 20.4417F, 21.0F, 19.933F, 21.0F, 19.4026F)
                verticalLineTo(5.40259F)
                curveTo(21.0F, 4.29259F, 20.1F, 3.40259F, 19.0F, 3.40259F)

                moveTo(10.08F, 15.9826F)
                lineTo(11.5F, 17.4026F)
                lineTo(16.5F, 12.4026F)
                lineTo(11.5F, 7.40259F)
                lineTo(10.08F, 8.81259F)
                lineTo(12.67F, 11.4026F)
                horizontalLineTo(3.0F)
                verticalLineTo(13.4026F)
                horizontalLineTo(12.67F)
                lineTo(10.08F, 15.9826F)

                close()
            }
        }
        return _import!!
    }

private var _import: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Image(imageVector = WhIcons.Action.Import, contentDescription = null)
}