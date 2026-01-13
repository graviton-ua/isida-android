package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.Swatches: ImageVector
    get() {
        if (_swatches != null) return _swatches!!
        _swatches = materialIcon(name = "Theme.Swatches") {
            materialPath {
                moveTo(20.0F, 14.0F)
                horizontalLineTo(6.0F)
                curveTo(3.8F, 14.0F, 2.0F, 15.8F, 2.0F, 18.0F)
                curveTo(2.0F, 20.2F, 3.8F, 22.0F, 6.0F, 22.0F)
                horizontalLineTo(20.0F)
                curveTo(21.1F, 22.0F, 22.0F, 21.1F, 22.0F, 20.0F)
                verticalLineTo(16.0F)
                curveTo(22.0F, 14.9F, 21.1F, 14.0F, 20.0F, 14.0F)

                moveTo(6.0F, 20.0F)
                curveTo(4.9F, 20.0F, 4.0F, 19.1F, 4.0F, 18.0F)
                curveTo(4.0F, 16.9F, 4.9F, 16.0F, 6.0F, 16.0F)
                curveTo(7.1F, 16.0F, 8.0F, 16.9F, 8.0F, 18.0F)
                curveTo(8.0F, 19.1F, 7.1F, 20.0F, 6.0F, 20.0F)

                moveTo(6.3F, 12.0F)
                lineTo(13.0F, 5.3F)
                curveTo(13.8F, 4.5F, 15.0F, 4.5F, 15.8F, 5.3F)
                lineTo(18.6F, 8.1F)
                curveTo(19.4F, 8.9F, 19.4F, 10.1F, 18.6F, 10.9F)
                lineTo(17.7F, 12.0F)
                horizontalLineTo(6.3F)

                moveTo(2.0F, 13.5F)
                verticalLineTo(4.0F)
                curveTo(2.0F, 2.9F, 2.9F, 2.0F, 4.0F, 2.0F)
                horizontalLineTo(8.0F)
                curveTo(9.1F, 2.0F, 10.0F, 2.9F, 10.0F, 4.0F)
                verticalLineTo(5.5F)
                lineTo(2.0F, 13.5F)
                close()
            }
        }
        return _swatches!!
    }
private var _swatches: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.Swatches, contentDescription = null)