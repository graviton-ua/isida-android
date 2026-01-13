package com.whoppah.common.compose.icons.state

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.State.Star: ImageVector
    get() {
        if (_star != null) return _star!!
        _star = materialIcon(name = "State.Star") {
            materialPath {
                moveTo(12.0f, 17.27f)
                lineTo(18.18f, 21.0f)
                lineToRelative(-1.64f, -7.03f)
                lineTo(22.0f, 9.24f)
                lineToRelative(-7.19f, -0.61f)
                lineTo(12.0f, 2.0f)
                lineTo(9.19f, 8.63f)
                lineTo(2.0f, 9.24f)
                lineToRelative(5.46f, 4.73f)
                lineTo(5.82f, 21.0f)
                lineTo(12.0f, 17.27f)
                close()
            }
        }
        return _star!!
    }
private var _star: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.State.Star, contentDescription = null)