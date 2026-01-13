package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.HandHeartOutlined: ImageVector
    get() {
        if (_handHeartOutlined != null) return _handHeartOutlined!!
        _handHeartOutlined = materialIcon(name = "Theme.HandHeartOutlined") {
            materialPath {
                moveTo(16.0F, 3.23F)
                curveTo(16.71F, 2.41F, 17.61F, 2.0F, 18.7F, 2.0F)
                curveTo(19.61F, 2.0F, 20.37F, 2.33F, 21.0F, 3.0F)
                curveTo(21.63F, 3.67F, 21.96F, 4.43F, 22.0F, 5.3F)
                curveTo(22.0F, 6.0F, 21.67F, 6.81F, 21.0F, 7.76F)
                curveTo(20.33F, 8.71F, 19.68F, 9.5F, 19.03F, 10.15F)
                curveTo(18.38F, 10.79F, 17.37F, 11.74F, 16.0F, 13.0F)
                curveTo(14.61F, 11.74F, 13.59F, 10.79F, 12.94F, 10.15F)
                curveTo(12.29F, 9.51F, 11.63F, 8.71F, 10.97F, 7.76F)
                curveTo(10.31F, 6.81F, 10.0F, 6.0F, 10.0F, 5.3F)
                curveTo(10.0F, 4.39F, 10.32F, 3.63F, 10.97F, 3.0F)
                curveTo(11.62F, 2.37F, 12.4F, 2.04F, 13.31F, 2.0F)
                curveTo(14.38F, 2.0F, 15.27F, 2.41F, 16.0F, 3.23F)

                moveTo(22.0F, 19.0F)
                verticalLineTo(20.0F)
                lineTo(14.0F, 22.5F)
                lineTo(7.0F, 20.56F)
                verticalLineTo(22.0F)
                horizontalLineTo(1.0F)
                verticalLineTo(11.0F)
                horizontalLineTo(8.97F)
                lineTo(15.13F, 13.3F)
                curveTo(16.25F, 13.72F, 17.0F, 14.8F, 17.0F, 16.0F)
                horizontalLineTo(19.0F)
                curveTo(20.66F, 16.0F, 22.0F, 17.34F, 22.0F, 19.0F)

                moveTo(5.0F, 20.0F)
                verticalLineTo(13.0F)
                horizontalLineTo(3.0F)
                verticalLineTo(20.0F)
                horizontalLineTo(5.0F)

                moveTo(19.9F, 18.57F)
                curveTo(19.74F, 18.24F, 19.39F, 18.0F, 19.0F, 18.0F)
                horizontalLineTo(13.65F)
                curveTo(13.11F, 18.0F, 12.58F, 17.92F, 12.07F, 17.75F)
                lineTo(9.69F, 16.96F)
                lineTo(10.32F, 15.06F)
                lineTo(12.7F, 15.85F)
                curveTo(13.0F, 15.95F, 15.0F, 16.0F, 15.0F, 16.0F)
                curveTo(15.0F, 15.63F, 14.77F, 15.3F, 14.43F, 15.17F)
                lineTo(8.61F, 13.0F)
                horizontalLineTo(7.0F)
                verticalLineTo(18.5F)
                lineTo(13.97F, 20.41F)
                lineTo(19.9F, 18.57F)
                close()
            }
        }
        return _handHeartOutlined!!
    }
private var _handHeartOutlined: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.HandHeartOutlined, contentDescription = null)