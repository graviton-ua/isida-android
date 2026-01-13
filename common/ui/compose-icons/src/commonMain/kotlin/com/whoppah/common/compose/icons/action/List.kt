package com.whoppah.common.compose.icons.action

import androidx.compose.foundation.Image
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Action.List: ImageVector
    get() {
        if (_list != null) return _list!!
        _list = materialIcon(name = "Action.List") {
            materialPath {
                verticalLineToRelative(0.0F)
                moveTo(4.0F, 13.4026F)
                curveTo(4.55F, 13.4026F, 5.0F, 12.9526F, 5.0F, 12.4026F)
                curveTo(5.0F, 11.8526F, 4.55F, 11.4026F, 4.0F, 11.4026F)
                curveTo(3.45F, 11.4026F, 3.0F, 11.8526F, 3.0F, 12.4026F)
                curveTo(3.0F, 12.9526F, 3.45F, 13.4026F, 4.0F, 13.4026F)

                moveTo(4.0F, 17.4026F)
                curveTo(4.55F, 17.4026F, 5.0F, 16.9526F, 5.0F, 16.4026F)
                curveTo(5.0F, 15.8526F, 4.55F, 15.4026F, 4.0F, 15.4026F)
                curveTo(3.45F, 15.4026F, 3.0F, 15.8526F, 3.0F, 16.4026F)
                curveTo(3.0F, 16.9526F, 3.45F, 17.4026F, 4.0F, 17.4026F)

                moveTo(4.0F, 9.40259F)
                curveTo(4.55F, 9.40259F, 5.0F, 8.95259F, 5.0F, 8.40259F)
                curveTo(5.0F, 7.85259F, 4.55F, 7.40259F, 4.0F, 7.40259F)
                curveTo(3.45F, 7.40259F, 3.0F, 7.85259F, 3.0F, 8.40259F)
                curveTo(3.0F, 8.95259F, 3.45F, 9.40259F, 4.0F, 9.40259F)

                moveTo(8.0F, 13.4026F)
                horizontalLineTo(20.0F)
                curveTo(20.55F, 13.4026F, 21.0F, 12.9526F, 21.0F, 12.4026F)
                curveTo(21.0F, 11.8526F, 20.55F, 11.4026F, 20.0F, 11.4026F)
                horizontalLineTo(8.0F)
                curveTo(7.45F, 11.4026F, 7.0F, 11.8526F, 7.0F, 12.4026F)
                curveTo(7.0F, 12.9526F, 7.45F, 13.4026F, 8.0F, 13.4026F)

                moveTo(8.0F, 17.4026F)
                horizontalLineTo(20.0F)
                curveTo(20.55F, 17.4026F, 21.0F, 16.9526F, 21.0F, 16.4026F)
                curveTo(21.0F, 15.8526F, 20.55F, 15.4026F, 20.0F, 15.4026F)
                horizontalLineTo(8.0F)
                curveTo(7.45F, 15.4026F, 7.0F, 15.8526F, 7.0F, 16.4026F)
                curveTo(7.0F, 16.9526F, 7.45F, 17.4026F, 8.0F, 17.4026F)

                moveTo(7.0F, 8.40259F)
                curveTo(7.0F, 8.95259F, 7.45F, 9.40259F, 8.0F, 9.40259F)
                horizontalLineTo(20.0F)
                curveTo(20.55F, 9.40259F, 21.0F, 8.95259F, 21.0F, 8.40259F)
                curveTo(21.0F, 7.85259F, 20.55F, 7.40259F, 20.0F, 7.40259F)
                horizontalLineTo(8.0F)
                curveTo(7.45F, 7.40259F, 7.0F, 7.85259F, 7.0F, 8.40259F)

                close()
            }
        }
        return _list!!
    }

private var _list: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Image(imageVector = WhIcons.Action.List, contentDescription = null)
}