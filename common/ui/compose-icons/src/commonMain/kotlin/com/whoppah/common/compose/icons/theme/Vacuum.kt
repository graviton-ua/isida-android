package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.Vacuum: ImageVector
    get() {
        if (_vacuum != null) return _vacuum!!
        _vacuum = materialIcon(name = "Theme.Vacuum") {
            materialPath {
                moveTo(23.0F, 20.0F)
                verticalLineTo(22.0F)
                horizontalLineTo(16.0F)
                verticalLineTo(20.0F)
                horizontalLineTo(18.46F)
                lineTo(12.0F, 4.61F)
                curveTo(11.81F, 4.14F, 11.5F, 3.76F, 11.06F, 3.46F)
                curveTo(10.62F, 3.16F, 10.14F, 3.0F, 9.61F, 3.0F)
                curveTo(8.9F, 3.0F, 8.28F, 3.27F, 7.76F, 3.79F)
                curveTo(7.24F, 4.31F, 7.0F, 4.92F, 7.0F, 5.64F)
                verticalLineTo(9.0F)
                horizontalLineTo(8.0F)
                curveTo(10.21F, 9.0F, 12.0F, 10.79F, 12.0F, 13.0F)
                verticalLineTo(22.0F)
                horizontalLineTo(8.0F)
                curveTo(8.61F, 21.16F, 9.0F, 20.13F, 9.0F, 19.0F)
                curveTo(9.0F, 16.24F, 6.76F, 14.0F, 4.0F, 14.0F)
                curveTo(3.29F, 14.0F, 2.61F, 14.15F, 2.0F, 14.42F)
                verticalLineTo(9.0F)
                horizontalLineTo(5.0F)
                verticalLineTo(5.64F)
                curveTo(5.0F, 4.8F, 5.23F, 4.0F, 5.63F, 3.32F)
                curveTo(6.04F, 2.62F, 6.59F, 2.06F, 7.3F, 1.63F)
                curveTo(8.0F, 1.21F, 8.77F, 1.0F, 9.61F, 1.0F)
                curveTo(10.55F, 1.0F, 11.4F, 1.26F, 12.16F, 1.77F)
                curveTo(12.92F, 2.28F, 13.5F, 2.97F, 13.87F, 3.81F)
                lineTo(20.66F, 20.0F)
                horizontalLineTo(23.0F)

                moveTo(7.0F, 19.0F)
                curveTo(7.0F, 20.66F, 5.66F, 22.0F, 4.0F, 22.0F)
                curveTo(2.34F, 22.0F, 1.0F, 20.66F, 1.0F, 19.0F)
                curveTo(1.0F, 17.34F, 2.34F, 16.0F, 4.0F, 16.0F)
                curveTo(5.66F, 16.0F, 7.0F, 17.34F, 7.0F, 19.0F)

                moveTo(5.0F, 19.0F)
                curveTo(5.0F, 18.45F, 4.55F, 18.0F, 4.0F, 18.0F)
                curveTo(3.45F, 18.0F, 3.0F, 18.45F, 3.0F, 19.0F)
                curveTo(3.0F, 19.55F, 3.45F, 20.0F, 4.0F, 20.0F)
                curveTo(4.55F, 20.0F, 5.0F, 19.55F, 5.0F, 19.0F)
                close()
            }
        }
        return _vacuum!!
    }
private var _vacuum: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.Vacuum, contentDescription = null)