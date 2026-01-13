package com.whoppah.common.compose.icons.state

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.State.FlashOn: ImageVector
    get() {
        if (_flashOn != null) return _flashOn!!

        _flashOn = materialIcon(name = "WhIcons.State.FlashOn") {
            materialPath {
                moveTo(x = 7.0f, y = 2.0f)
                verticalLineToRelative(dy = 11.0f)
                horizontalLineToRelative(dx = 3.0f)
                verticalLineToRelative(dy = 9.0f)
                lineToRelative(dx = 7.0f, dy = -12.0f)
                horizontalLineToRelative(dx = -4.0f)
                lineToRelative(dx = 3.0f, dy = -8.0f)
                close()
            }
        }
        return _flashOn!!
    }

@Suppress("ObjectPropertyName")
private var _flashOn: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.State.FlashOn, contentDescription = null)