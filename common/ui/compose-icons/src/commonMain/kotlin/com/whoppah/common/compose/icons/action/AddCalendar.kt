package com.whoppah.common.compose.icons.action

import androidx.compose.foundation.Image
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Action.AddCalendar: ImageVector
    get() {
        if (_addCalendar != null) return _addCalendar!!
        _addCalendar = materialIcon(name = "Action.AddCalendar") {
            materialPath {
                verticalLineToRelative(0.0F)
                moveTo(19.0F, 20.4026F)
                verticalLineTo(9.40259F)
                horizontalLineTo(5.0F)
                verticalLineTo(20.4026F)
                horizontalLineTo(19.0F)

                moveTo(16.0F, 2.40259F)
                horizontalLineTo(18.0F)
                verticalLineTo(4.40259F)
                horizontalLineTo(19.0F)
                curveTo(20.11F, 4.40259F, 21.0F, 5.30259F, 21.0F, 6.40259F)
                verticalLineTo(20.4026F)
                curveTo(21.0F, 21.5126F, 20.11F, 22.4026F, 19.0F, 22.4026F)
                horizontalLineTo(5.0F)
                curveTo(3.89F, 22.4026F, 3.0F, 21.5026F, 3.0F, 20.4026F)
                verticalLineTo(6.40259F)
                curveTo(3.0F, 5.29259F, 3.89F, 4.40259F, 5.0F, 4.40259F)
                horizontalLineTo(6.0F)
                verticalLineTo(2.40259F)
                horizontalLineTo(8.0F)
                verticalLineTo(4.40259F)
                horizontalLineTo(16.0F)
                verticalLineTo(2.40259F)

                moveTo(11.0F, 10.9026F)
                horizontalLineTo(13.0F)
                verticalLineTo(13.9026F)
                horizontalLineTo(16.0F)
                verticalLineTo(15.9026F)
                horizontalLineTo(13.0F)
                verticalLineTo(18.9026F)
                horizontalLineTo(11.0F)
                verticalLineTo(15.9026F)
                horizontalLineTo(8.0F)
                verticalLineTo(13.9026F)
                horizontalLineTo(11.0F)
                verticalLineTo(10.9026F)

                close()
            }
        }
        return _addCalendar!!
    }

private var _addCalendar: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Image(imageVector = WhIcons.Action.AddCalendar, contentDescription = null)
}