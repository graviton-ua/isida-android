package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.VacuumOutlined: ImageVector
    get() {
        if (_vacuumOutlined != null) return _vacuumOutlined!!
        _vacuumOutlined = materialIcon(name = "Theme.VacuumOutlined") {
            materialPath {
                moveTo(20.66F, 20.0F)
                lineTo(13.87F, 3.81F)
                curveTo(13.5F, 2.97F, 12.93F, 2.29F, 12.16F, 1.77F)
                curveTo(11.4F, 1.26F, 10.55F, 1.0F, 9.61F, 1.0F)
                curveTo(8.77F, 1.0F, 8.0F, 1.21F, 7.3F, 1.63F)
                curveTo(6.6F, 2.05F, 6.04F, 2.62F, 5.63F, 3.32F)
                curveTo(5.22F, 4.02F, 5.0F, 4.8F, 5.0F, 5.64F)
                lineTo(5.03F, 9.0F)
                horizontalLineTo(2.03F)
                verticalLineTo(14.45F)
                curveTo(2.65F, 14.17F, 3.31F, 14.03F, 4.0F, 14.03F)
                verticalLineTo(11.03F)
                horizontalLineTo(9.0F)
                curveTo(9.57F, 11.03F, 10.04F, 11.23F, 10.43F, 11.62F)
                curveTo(10.82F, 12.0F, 11.0F, 12.47F, 11.0F, 13.0F)
                verticalLineTo(20.03F)
                horizontalLineTo(8.91F)
                curveTo(8.76F, 20.75F, 8.44F, 21.41F, 7.97F, 22.0F)
                horizontalLineTo(13.0F)
                verticalLineTo(13.0F)
                curveTo(13.0F, 12.28F, 12.8F, 11.62F, 12.45F, 11.0F)
                curveTo(12.1F, 10.38F, 11.61F, 9.91F, 11.0F, 9.56F)
                curveTo(10.42F, 9.2F, 9.75F, 9.0F, 9.0F, 9.0F)
                horizontalLineTo(7.0F)
                verticalLineTo(5.64F)
                curveTo(7.0F, 4.92F, 7.25F, 4.31F, 7.76F, 3.79F)
                curveTo(8.27F, 3.27F, 8.89F, 3.0F, 9.61F, 3.0F)
                curveTo(10.14F, 3.0F, 10.63F, 3.16F, 11.06F, 3.46F)
                curveTo(11.49F, 3.76F, 11.81F, 4.14F, 12.0F, 4.61F)
                lineTo(18.46F, 20.0F)
                lineTo(16.0F, 20.03F)
                verticalLineTo(22.0F)
                horizontalLineTo(23.0F)
                verticalLineTo(20.03F)
                lineTo(20.66F, 20.0F)

                moveTo(4.0F, 18.0F)
                curveTo(4.55F, 18.0F, 5.0F, 18.45F, 5.0F, 19.0F)
                curveTo(5.0F, 19.55F, 4.55F, 20.0F, 4.0F, 20.0F)
                curveTo(3.45F, 20.0F, 3.0F, 19.55F, 3.0F, 19.0F)
                curveTo(3.0F, 18.45F, 3.45F, 18.0F, 4.0F, 18.0F)

                moveTo(4.0F, 16.0F)
                curveTo(2.34F, 16.0F, 1.0F, 17.34F, 1.0F, 19.0F)
                curveTo(1.0F, 20.66F, 2.34F, 22.0F, 4.0F, 22.0F)
                curveTo(5.66F, 22.0F, 7.0F, 20.66F, 7.0F, 19.0F)
                curveTo(7.0F, 17.34F, 5.66F, 16.0F, 4.0F, 16.0F)
                close()
            }
        }
        return _vacuumOutlined!!
    }
private var _vacuumOutlined: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.VacuumOutlined, contentDescription = null)