package com.whoppah.common.compose.icons.action

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Action.Send: ImageVector
    get() {
        if (_send != null) return _send!!
        _send = materialIcon(name = "Action.Send") {
            materialPath {
                moveTo(12.5F, 20.525F)
                lineTo(18.8334F, 4.69168F)
                lineTo(3.0F, 11.025F)
                lineTo(6.69445F, 14.7195F)
                lineTo(15.6667F, 7.85835F)
                lineTo(8.80557F, 16.8306F)
                lineTo(12.5F, 20.525F)
                close()
            }
        }
        return _send!!
    }
private var _send: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Action.Send, contentDescription = null)