package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.Spark: ImageVector
    get() {
        if (_spark != null) return _spark!!
        _spark = materialIcon(name = "Theme.Spark") {
            materialPath {
                moveTo(9.1F, 16.73F)
                curveTo(9.26F, 16.55F, 9.36F, 16.32F, 9.37F, 16.08F)
                curveTo(9.38F, 15.84F, 9.29F, 15.6F, 9.14F, 15.42F)
                lineTo(4.34F, 9.68F)
                lineTo(4.33F, 9.69F)
                curveTo(4.07F, 9.43F, 3.72F, 9.29F, 3.36F, 9.3F)
                curveTo(3.0F, 9.31F, 2.66F, 9.46F, 2.41F, 9.72F)
                curveTo(2.15F, 9.99F, 2.01F, 10.35F, 2.0F, 10.72F)
                curveTo(1.99F, 11.1F, 2.13F, 11.46F, 2.37F, 11.74F)
                lineTo(2.39F, 11.76F)
                curveTo(2.4F, 11.76F, 2.41F, 11.77F, 2.42F, 11.78F)
                curveTo(2.47F, 11.83F, 2.52F, 11.88F, 2.58F, 11.92F)
                lineTo(7.85F, 16.76F)
                curveTo(8.03F, 16.92F, 8.25F, 17.01F, 8.48F, 17.0F)
                curveTo(8.71F, 16.99F, 8.94F, 16.9F, 9.1F, 16.73F)
                close()
            }
            materialPath {
                moveTo(12.0F, 14.8F)
                curveTo(12.26F, 14.8F, 12.52F, 14.72F, 12.71F, 14.57F)
                curveTo(12.9F, 14.42F, 13.02F, 14.22F, 13.04F, 14.0F)
                lineTo(13.56F, 7.49F)
                curveTo(13.57F, 7.42F, 13.58F, 7.36F, 13.58F, 7.29F)
                curveTo(13.58F, 7.28F, 13.58F, 7.27F, 13.58F, 7.26F)
                verticalLineTo(7.23F)
                curveTo(13.56F, 6.9F, 13.39F, 6.59F, 13.1F, 6.36F)
                curveTo(12.8F, 6.13F, 12.41F, 6.0F, 12.01F, 6.0F)
                curveTo(11.6F, 6.0F, 11.22F, 6.13F, 10.92F, 6.36F)
                curveTo(10.63F, 6.59F, 10.46F, 6.9F, 10.44F, 7.23F)
                horizontalLineTo(10.42F)
                lineTo(10.96F, 14.0F)
                curveTo(10.97F, 14.22F, 11.09F, 14.42F, 11.28F, 14.57F)
                curveTo(11.48F, 14.72F, 11.73F, 14.8F, 12.0F, 14.8F)
                close()
            }
            materialPath {
                moveTo(16.15F, 16.77F)
                lineTo(21.42F, 11.92F)
                curveTo(21.48F, 11.88F, 21.53F, 11.83F, 21.59F, 11.78F)
                lineTo(21.61F, 11.76F)
                lineTo(21.63F, 11.74F)
                curveTo(21.87F, 11.47F, 22.01F, 11.1F, 22.0F, 10.73F)
                curveTo(21.99F, 10.35F, 21.85F, 9.99F, 21.6F, 9.72F)
                curveTo(21.34F, 9.46F, 21.0F, 9.31F, 20.64F, 9.3F)
                curveTo(20.28F, 9.29F, 19.93F, 9.43F, 19.67F, 9.69F)
                lineTo(19.66F, 9.68F)
                lineTo(14.86F, 15.41F)
                curveTo(14.7F, 15.59F, 14.62F, 15.83F, 14.63F, 16.07F)
                curveTo(14.64F, 16.32F, 14.74F, 16.55F, 14.9F, 16.72F)
                curveTo(15.06F, 16.89F, 15.28F, 16.99F, 15.52F, 17.0F)
                curveTo(15.75F, 17.01F, 15.97F, 16.92F, 16.15F, 16.77F)
                close()
            }
        }
        return _spark!!
    }
private var _spark: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.Spark, contentDescription = null)