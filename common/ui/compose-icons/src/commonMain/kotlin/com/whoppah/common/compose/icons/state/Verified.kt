package com.whoppah.common.compose.icons.state

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.State.Verified: ImageVector
    //get() = Icons.Default.Verified
    get() {
        if (_verified != null) return _verified!!
        _verified = materialIcon(name = "State.Verified") {
            materialPath {
                moveTo(23.0F, 12.0F)
                lineToRelative(-2.44F, -2.79F)
                lineToRelative(0.34F, -3.69F)
                lineToRelative(-3.61F, -0.82F)
                lineTo(15.4F, 1.5F)
                lineTo(12.0F, 2.96F)
                lineTo(8.6F, 1.5F)
                lineTo(6.71F, 4.69F)
                lineTo(3.1F, 5.5F)
                lineTo(3.44F, 9.2F)
                lineTo(1.0F, 12.0F)
                lineToRelative(2.44F, 2.79F)
                lineToRelative(-0.34F, 3.7F)
                lineToRelative(3.61F, 0.82F)
                lineTo(8.6F, 22.5F)
                lineToRelative(3.4F, -1.47F)
                lineToRelative(3.4F, 1.46F)
                lineToRelative(1.89F, -3.19F)
                lineToRelative(3.61F, -0.82F)
                lineToRelative(-0.34F, -3.69F)
                lineTo(23.0F, 12.0F)
                close()

                moveTo(10.09F, 16.72F)
                lineToRelative(-3.8F, -3.81F)
                lineToRelative(1.48F, -1.48F)
                lineToRelative(2.32F, 2.33F)
                lineToRelative(5.85F, -5.87F)
                lineToRelative(1.48F, 1.48F)
                lineTo(10.09F, 16.72F)
                close()
            }
        }
        return _verified!!
    }
private var _verified: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.State.Verified, contentDescription = null)