package com.whoppah.common.compose.icons.state

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.State.FlashAuto: ImageVector
    get() {
        if (_flashAuto != null) return _flashAuto!!

        _flashAuto = materialIcon(name = "WhIcons.State.FlashAuto") {
            materialPath {
                moveTo(x = 3.0f, y = 2.0f)
                verticalLineToRelative(dy = 12.0f)
                horizontalLineToRelative(dx = 3.0f)
                verticalLineToRelative(dy = 9.0f)
                lineToRelative(dx = 7.0f, dy = -12.0f)
                horizontalLineTo(x = 9.0f)
                lineToRelative(dx = 4.0f, dy = -9.0f)
                horizontalLineTo(x = 3.0f)
                close()
                moveToRelative(dx = 16.0f, dy = 0.0f)
                horizontalLineToRelative(dx = -2.0f)
                lineToRelative(dx = -3.2f, dy = 9.0f)
                horizontalLineToRelative(dx = 1.9f)
                lineToRelative(dx = 0.7f, dy = -2.0f)
                horizontalLineToRelative(dx = 3.2f)
                lineToRelative(dx = 0.7f, dy = 2.0f)
                horizontalLineToRelative(dx = 1.9f)
                lineTo(x = 19.0f, y = 2.0f)
                close()
                moveToRelative(dx = -2.15f, dy = 5.65f)
                lineTo(x = 18.0f, y = 4.0f)
                lineToRelative(dx = 1.15f, dy = 3.65f)
                horizontalLineToRelative(dx = -2.3f)
                close()
            }
        }
        return _flashAuto!!
    }

@Suppress("ObjectPropertyName")
private var _flashAuto: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.State.FlashAuto, contentDescription = null)