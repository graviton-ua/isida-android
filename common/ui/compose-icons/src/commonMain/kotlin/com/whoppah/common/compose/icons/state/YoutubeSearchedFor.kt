package com.whoppah.common.compose.icons.state

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.State.YoutubeSearchedFor: ImageVector
    get() {
        if (_youtubeSearchedFor != null) return _youtubeSearchedFor!!
        _youtubeSearchedFor = materialIcon(name = "State.YoutubeSearchedFor") {
            materialPath {
                moveTo(17.01F, 14.0F)
                horizontalLineToRelative(-0.8F)
                lineToRelative(-0.27F, -0.27F)
                curveToRelative(0.98F, -1.14F, 1.57F, -2.61F, 1.57F, -4.23F)
                curveToRelative(0.0F, -3.59F, -2.91F, -6.5F, -6.5F, -6.5F)
                reflectiveCurveToRelative(-6.5F, 3.0F, -6.5F, 6.5F)
                horizontalLineTo(2.0F)
                lineToRelative(3.84F, 4.0F)
                lineToRelative(4.16F, -4.0F)
                horizontalLineTo(6.51F)
                curveTo(6.51F, 7.0F, 8.53F, 5.0F, 11.01F, 5.0F)
                reflectiveCurveToRelative(4.5F, 2.01F, 4.5F, 4.5F)
                curveToRelative(0.0F, 2.48F, -2.02F, 4.5F, -4.5F, 4.5F)
                curveToRelative(-0.65F, 0.0F, -1.26F, -0.14F, -1.82F, -0.38F)
                lineTo(7.71F, 15.1F)
                curveToRelative(0.97F, 0.57F, 2.09F, 0.9F, 3.3F, 0.9F)
                curveToRelative(1.61F, 0.0F, 3.08F, -0.59F, 4.22F, -1.57F)
                lineToRelative(0.27F, 0.27F)
                verticalLineToRelative(0.79F)
                lineToRelative(5.01F, 4.99F)
                lineTo(22.0F, 19.0F)
                lineToRelative(-4.99F, -5.0F)
                close()
            }
        }
        return _youtubeSearchedFor!!
    }
private var _youtubeSearchedFor: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.State.YoutubeSearchedFor, contentDescription = null)