package com.whoppah.common.compose.icons.action

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Action.EditCalendar: ImageVector
    get() {
        if (_editCalendar != null) return _editCalendar!!
        _editCalendar = materialIcon(name = "Action.EditCalendar") {
            materialPath {
                moveTo(12.0F, 22.0F)
                horizontalLineTo(5.0F)
                curveToRelative(-1.11F, 0.0F, -2.0F, -0.9F, -2.0F, -2.0F)
                lineTo(3.01F, 6.0F)
                curveToRelative(0.0F, -1.1F, 0.88F, -2.0F, 1.99F, -2.0F)
                horizontalLineToRelative(1.0F)
                verticalLineTo(2.0F)
                horizontalLineToRelative(2.0F)
                verticalLineToRelative(2.0F)
                horizontalLineToRelative(8.0F)
                verticalLineTo(2.0F)
                horizontalLineToRelative(2.0F)
                verticalLineToRelative(2.0F)
                horizontalLineToRelative(1.0F)
                curveToRelative(1.1F, 0.0F, 2.0F, 0.9F, 2.0F, 2.0F)
                verticalLineToRelative(6.0F)
                horizontalLineToRelative(-2.0F)
                verticalLineToRelative(-2.0F)
                horizontalLineTo(5.0F)
                verticalLineToRelative(10.0F)
                horizontalLineToRelative(7.0F)
                verticalLineTo(22.0F)
                close()

                moveTo(22.13F, 16.99F)
                lineToRelative(0.71F, -0.71F)
                curveToRelative(0.39F, -0.39F, 0.39F, -1.02F, 0.0F, -1.41F)
                lineToRelative(-0.71F, -0.71F)
                curveToRelative(-0.39F, -0.39F, -1.02F, -0.39F, -1.41F, 0.0F)
                lineToRelative(-0.71F, 0.71F)
                lineTo(22.13F, 16.99F)
                close()

                moveTo(21.42F, 17.7F)
                lineToRelative(-5.3F, 5.3F)
                horizontalLineTo(14.0F)
                verticalLineToRelative(-2.12F)
                lineToRelative(5.3F, -5.3F)
                lineTo(21.42F, 17.7F)
                close()
            }
        }
        return _editCalendar!!
    }
private var _editCalendar: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Action.EditCalendar, contentDescription = null)