package com.whoppah.common.compose.icons.navigation

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Navigation.KeyboardArrowLeft: ImageVector
    get() {
        if (_keyboardArrowLeft != null) return _keyboardArrowLeft!!

        _keyboardArrowLeft = materialIcon(name = "WhIcons.Navigation.KeyboardArrowLeft", autoMirror = true) {
            materialPath {
                moveTo(15.41f, 16.59f)
                lineTo(10.83f, 12.0f)
                lineToRelative(4.58f, -4.59f)
                lineTo(14.0f, 6.0f)
                lineToRelative(-6.0f, 6.0f)
                lineToRelative(6.0f, 6.0f)
                lineToRelative(1.41f, -1.41f)
                close()
            }
        }
        return _keyboardArrowLeft!!
    }

private var _keyboardArrowLeft: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Navigation.KeyboardArrowLeft, contentDescription = null)