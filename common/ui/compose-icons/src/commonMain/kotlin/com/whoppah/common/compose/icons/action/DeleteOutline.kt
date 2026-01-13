package com.whoppah.common.compose.icons.action

import androidx.compose.foundation.Image
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Action.DeleteOutline: ImageVector
    get() {
        if (_deleteOutline != null) return _deleteOutline!!
        _deleteOutline = materialIcon(name = "Action.DeleteOutline") {
            materialPath {
                verticalLineToRelative(0.0F)
                moveTo(9.0F, 3.40259F)
                verticalLineTo(4.40259F)
                horizontalLineTo(4.0F)
                verticalLineTo(6.40259F)
                horizontalLineTo(5.0F)
                verticalLineTo(19.4026F)
                curveTo(5.0F, 19.933F, 5.21071F, 20.4417F, 5.58579F, 20.8168F)
                curveTo(5.96086F, 21.1919F, 6.46957F, 21.4026F, 7.0F, 21.4026F)
                horizontalLineTo(17.0F)
                curveTo(17.5304F, 21.4026F, 18.0391F, 21.1919F, 18.4142F, 20.8168F)
                curveTo(18.7893F, 20.4417F, 19.0F, 19.933F, 19.0F, 19.4026F)
                verticalLineTo(6.40259F)
                horizontalLineTo(20.0F)
                verticalLineTo(4.40259F)
                horizontalLineTo(15.0F)
                verticalLineTo(3.40259F)
                horizontalLineTo(9.0F)

                moveTo(7.0F, 6.40259F)
                horizontalLineTo(17.0F)
                verticalLineTo(19.4026F)
                horizontalLineTo(7.0F)
                verticalLineTo(6.40259F)

                moveTo(9.0F, 8.40259F)
                verticalLineTo(17.4026F)
                horizontalLineTo(11.0F)
                verticalLineTo(8.40259F)
                horizontalLineTo(9.0F)

                moveTo(13.0F, 8.40259F)
                verticalLineTo(17.4026F)
                horizontalLineTo(15.0F)
                verticalLineTo(8.40259F)
                horizontalLineTo(13.0F)

                close()
            }
        }
        return _deleteOutline!!
    }

private var _deleteOutline: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Image(imageVector = WhIcons.Action.DeleteOutline, contentDescription = null)
}