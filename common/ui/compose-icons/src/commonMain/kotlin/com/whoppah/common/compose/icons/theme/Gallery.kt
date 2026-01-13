package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.Gallery: ImageVector
    //get() = Icons.Default.Image
    get() {
        if (_gallery != null) return _gallery!!
        _gallery = materialIcon(name = "Theme.Gallery") {
            materialPath {
                moveTo(21.0F, 19.0F)
                verticalLineTo(5.0F)
                curveToRelative(0.0F, -1.1F, -0.9F, -2.0F, -2.0F, -2.0F)
                horizontalLineTo(5.0F)
                curveToRelative(-1.1F, 0.0F, -2.0F, 0.9F, -2.0F, 2.0F)
                verticalLineToRelative(14.0F)
                curveToRelative(0.0F, 1.1F, 0.9F, 2.0F, 2.0F, 2.0F)
                horizontalLineToRelative(14.0F)
                curveToRelative(1.1F, 0.0F, 2.0F, -0.9F, 2.0F, -2.0F)
                close()

                moveTo(8.5F, 13.5F)
                lineToRelative(2.5F, 3.01F)
                lineTo(14.5F, 12.0F)
                lineToRelative(4.5F, 6.0F)
                horizontalLineTo(5.0F)
                lineToRelative(3.5F, -4.5F)
                close()
            }
        }
        return _gallery!!
    }
private var _gallery: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.Gallery, contentDescription = null)