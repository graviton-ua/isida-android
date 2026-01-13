package com.whoppah.common.compose.icons.state

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.State.StarOutlined: ImageVector
    get() {
        if (_starOutlined != null) return _starOutlined!!
        _starOutlined = materialIcon(name = "State.StarOutlined") {
            materialPath {
                moveTo(22.0F, 9.24F)
                lineToRelative(-7.19F, -0.62F)
                lineTo(12.0F, 2.0F)
                lineTo(9.19F, 8.63F)
                lineTo(2.0F, 9.24F)
                lineToRelative(5.46F, 4.73F)
                lineTo(5.82F, 21.0F)
                lineTo(12.0F, 17.27F)
                lineTo(18.18F, 21.0F)
                lineToRelative(-1.63F, -7.03F)
                lineTo(22.0F, 9.24F)
                close()

                moveTo(12.0F, 15.4F)
                lineToRelative(-3.76F, 2.27F)
                lineToRelative(1.0F, -4.28F)
                lineToRelative(-3.32F, -2.88F)
                lineToRelative(4.38F, -0.38F)
                lineTo(12.0F, 6.1F)
                lineToRelative(1.71F, 4.04F)
                lineToRelative(4.38F, 0.38F)
                lineToRelative(-3.32F, 2.88F)
                lineToRelative(1.0F, 4.28F)
                lineTo(12.0F, 15.4F)
                close()
            }
        }
        return _starOutlined!!
    }
private var _starOutlined: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.State.StarOutlined, contentDescription = null)