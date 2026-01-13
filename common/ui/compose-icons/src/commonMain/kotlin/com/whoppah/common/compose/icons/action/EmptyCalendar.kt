package com.whoppah.common.compose.icons.action

import androidx.compose.foundation.Image
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Action.EmptyCalendar: ImageVector
    get() {
        if (_emptyCalendar != null) return _emptyCalendar!!
        _emptyCalendar = materialIcon(name = "Action.EmptyCalendar") {
            materialPath {
                verticalLineToRelative(0.0F)
                moveTo(19.0F, 3.40259F)
                horizontalLineTo(18.0F)
                verticalLineTo(1.40259F)
                horizontalLineTo(16.0F)
                verticalLineTo(3.40259F)
                horizontalLineTo(8.0F)
                verticalLineTo(1.40259F)
                horizontalLineTo(6.0F)
                verticalLineTo(3.40259F)
                horizontalLineTo(5.0F)
                curveTo(3.89F, 3.40259F, 3.0F, 4.30259F, 3.0F, 5.40259F)
                verticalLineTo(19.4026F)
                curveTo(3.0F, 20.5126F, 3.9F, 21.4026F, 5.0F, 21.4026F)
                horizontalLineTo(19.0F)
                curveTo(20.11F, 21.4026F, 21.0F, 20.5126F, 21.0F, 19.4026F)
                verticalLineTo(5.40259F)
                curveTo(21.0F, 4.30259F, 20.11F, 3.40259F, 19.0F, 3.40259F)

                moveTo(19.0F, 19.4026F)
                horizontalLineTo(5.0F)
                verticalLineTo(9.40259F)
                horizontalLineTo(19.0F)
                verticalLineTo(19.4026F)

                moveTo(19.0F, 7.40259F)
                horizontalLineTo(5.0F)
                verticalLineTo(5.40259F)
                horizontalLineTo(19.0F)
                verticalLineTo(7.40259F)

                close()
            }
        }
        return _emptyCalendar!!
    }

private var _emptyCalendar: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Image(imageVector = WhIcons.Action.EmptyCalendar, contentDescription = null)
}