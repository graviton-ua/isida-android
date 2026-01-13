package com.whoppah.common.compose.icons.state

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.State.Message: ImageVector
    get() {
        if (_message != null) return _message!!
        _message = materialIcon(name = "State.Message") {
            materialPath {
                moveTo(20.0F, 2.0F)
                lineTo(4.0F, 2.0F)
                curveToRelative(-1.1F, 0.0F, -1.99F, 0.9F, -1.99F, 2.0F)
                lineTo(2.0F, 22.0F)
                lineToRelative(4.0F, -4.0F)
                horizontalLineToRelative(14.0F)
                curveToRelative(1.1F, 0.0F, 2.0F, -0.9F, 2.0F, -2.0F)
                lineTo(22.0F, 4.0F)
                curveToRelative(0.0F, -1.1F, -0.9F, -2.0F, -2.0F, -2.0F)
                close()

                moveTo(18.0F, 14.0F)
                lineTo(6.0F, 14.0F)
                verticalLineToRelative(-2.0F)
                horizontalLineToRelative(12.0F)
                verticalLineToRelative(2.0F)
                close()

                moveTo(18.0F, 11.0F)
                lineTo(6.0F, 11.0F)
                lineTo(6.0F, 9.0F)
                horizontalLineToRelative(12.0F)
                verticalLineToRelative(2.0F)
                close()

                moveTo(18.0F, 8.0F)
                lineTo(6.0F, 8.0F)
                lineTo(6.0F, 6.0F)
                horizontalLineToRelative(12.0F)
                verticalLineToRelative(2.0F)
                close()
            }
        }
        return _message!!
    }
private var _message: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.State.Message, contentDescription = null)