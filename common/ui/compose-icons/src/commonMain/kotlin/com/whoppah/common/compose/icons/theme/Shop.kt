package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.Shop: ImageVector
    get() {
        if (_shop != null) return _shop!!
        _shop = materialIcon(name = "Theme.Shop") {
            materialPath {
                moveTo(5.91F, 9.63F)
                curveTo(5.72F, 10.8F, 5.13F, 11.42F, 4.14F, 11.59F)
                curveTo(2.67F, 11.83F, 1.72F, 10.34F, 2.08F, 9.11F)
                curveTo(2.15F, 8.88F, 2.24F, 8.66F, 2.35F, 8.45F)
                curveTo(2.96F, 7.11F, 3.59F, 5.77F, 4.19F, 4.42F)
                curveTo(4.23F, 4.28F, 4.32F, 4.17F, 4.43F, 4.09F)
                curveTo(4.55F, 4.02F, 4.69F, 3.99F, 4.83F, 4.0F)
                curveTo(9.61F, 4.01F, 14.39F, 4.01F, 19.17F, 4.0F)
                curveTo(19.31F, 3.99F, 19.45F, 4.02F, 19.57F, 4.09F)
                curveTo(19.68F, 4.17F, 19.77F, 4.28F, 19.82F, 4.42F)
                curveTo(20.46F, 5.84F, 21.09F, 7.26F, 21.77F, 8.67F)
                curveTo(22.32F, 9.81F, 21.79F, 10.94F, 21.09F, 11.35F)
                curveTo(19.89F, 12.06F, 18.37F, 11.28F, 18.14F, 9.83F)
                curveTo(18.13F, 9.78F, 18.12F, 9.74F, 18.1F, 9.69F)
                curveTo(18.08F, 10.19F, 17.87F, 10.66F, 17.52F, 11.01F)
                curveTo(17.18F, 11.36F, 16.72F, 11.57F, 16.24F, 11.61F)
                curveTo(15.28F, 11.72F, 14.57F, 11.17F, 14.02F, 9.87F)
                curveTo(13.94F, 10.38F, 13.67F, 10.85F, 13.27F, 11.17F)
                curveTo(13.07F, 11.34F, 12.84F, 11.46F, 12.6F, 11.53F)
                curveTo(12.36F, 11.61F, 12.11F, 11.63F, 11.85F, 11.61F)
                curveTo(10.84F, 11.5F, 10.2F, 10.89F, 9.99F, 9.81F)
                curveTo(9.82F, 10.43F, 9.58F, 10.94F, 9.08F, 11.26F)
                curveTo(8.85F, 11.42F, 8.58F, 11.53F, 8.31F, 11.58F)
                curveTo(8.03F, 11.64F, 7.74F, 11.63F, 7.47F, 11.56F)
                curveTo(6.6F, 11.36F, 6.01F, 10.65F, 5.91F, 9.63F)

                moveTo(14.04F, 11.41F)
                curveTo(15.42F, 12.71F, 17.01F, 12.55F, 18.11F, 11.37F)
                curveTo(18.31F, 11.54F, 18.51F, 11.7F, 18.72F, 11.85F)
                curveTo(18.94F, 11.97F, 19.16F, 12.08F, 19.39F, 12.17F)
                verticalLineTo(19.95F)
                horizontalLineTo(11.32F)
                verticalLineTo(13.95F)
                horizontalLineTo(6.48F)
                verticalLineTo(20.0F)
                horizontalLineTo(4.61F)
                verticalLineTo(19.67F)
                curveTo(4.61F, 17.29F, 4.61F, 14.91F, 4.61F, 12.52F)
                curveTo(4.6F, 12.43F, 4.62F, 12.33F, 4.67F, 12.25F)
                curveTo(4.72F, 12.17F, 4.8F, 12.11F, 4.89F, 12.08F)
                curveTo(5.26F, 11.89F, 5.61F, 11.67F, 5.95F, 11.44F)
                curveTo(6.21F, 11.72F, 6.51F, 11.94F, 6.85F, 12.09F)
                curveTo(7.19F, 12.24F, 7.56F, 12.32F, 7.93F, 12.32F)
                curveTo(8.31F, 12.32F, 8.69F, 12.24F, 9.04F, 12.08F)
                curveTo(9.39F, 11.92F, 9.7F, 11.69F, 9.95F, 11.4F)
                curveTo(11.29F, 12.61F, 12.66F, 12.64F, 14.04F, 11.41F)

                moveTo(17.72F, 15.81F)
                verticalLineTo(13.95F)
                horizontalLineTo(14.14F)
                verticalLineTo(15.81F)
                horizontalLineTo(17.72F)
                close()
            }
        }
        return _shop!!
    }
private var _shop: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.Shop, contentDescription = null)