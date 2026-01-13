package com.whoppah.common.compose.icons.state

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.State.FlashOff: ImageVector
    get() {
        if (_flashOff != null) return _flashOff!!

        _flashOff = materialIcon(name = "WhIcons.State.FlashOff") {
            materialPath {
                moveTo(x = 17.0f, y = 10.0f)
                horizontalLineToRelative(dx = -3.61f)
                lineToRelative(dx = 2.28f, dy = 2.28f)
                close()
                moveToRelative(dx = 0.0f, dy = -8.0f)
                horizontalLineTo(x = 7.0f)
                verticalLineToRelative(dy = 1.61f)
                lineToRelative(dx = 6.13f, dy = 6.13f)
                close()
                moveToRelative(dx = -13.59f, dy = 0.86f)
                lineTo(x = 2.0f, y = 4.27f)
                lineToRelative(dx = 5.0f, dy = 5.0f)
                verticalLineTo(y = 13.0f)
                horizontalLineToRelative(dx = 3.0f)
                verticalLineToRelative(dy = 9.0f)
                lineToRelative(dx = 3.58f, dy = -6.15f)
                lineTo(x = 17.73f, y = 20.0f)
                lineToRelative(dx = 1.41f, dy = -1.41f)
                close()
            }
        }
        return _flashOff!!
    }

@Suppress("ObjectPropertyName")
private var _flashOff: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.State.FlashOff, contentDescription = null)