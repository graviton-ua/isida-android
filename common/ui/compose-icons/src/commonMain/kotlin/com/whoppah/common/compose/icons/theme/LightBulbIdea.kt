package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.LightBulbIdea: ImageVector
    get() {
        if (_lightBulbIdea != null) return _lightBulbIdea!!
        _lightBulbIdea = materialIcon(name = "Theme.LightBulbIdea") {
            materialPath {
                moveTo(20.0F, 11.0F)
                horizontalLineTo(23.0F)
                verticalLineTo(13.0F)
                horizontalLineTo(20.0F)
                verticalLineTo(11.0F)

                moveTo(1.0F, 11.0F)
                horizontalLineTo(4.0F)
                verticalLineTo(13.0F)
                horizontalLineTo(1.0F)
                verticalLineTo(11.0F)

                moveTo(13.0F, 1.0F)
                verticalLineTo(4.0F)
                horizontalLineTo(11.0F)
                verticalLineTo(1.0F)
                horizontalLineTo(13.0F)

                moveTo(4.92F, 3.5F)
                lineTo(7.05F, 5.64F)
                lineTo(5.63F, 7.05F)
                lineTo(3.5F, 4.93F)
                lineTo(4.92F, 3.5F)

                moveTo(16.95F, 5.63F)
                lineTo(19.07F, 3.5F)
                lineTo(20.5F, 4.93F)
                lineTo(18.37F, 7.05F)
                lineTo(16.95F, 5.63F)

                moveTo(12.0F, 6.0F)
                curveTo(13.59F, 6.0F, 15.12F, 6.63F, 16.24F, 7.76F)
                curveTo(17.37F, 8.88F, 18.0F, 10.41F, 18.0F, 12.0F)
                curveTo(18.0F, 14.22F, 16.79F, 16.16F, 15.0F, 17.2F)
                verticalLineTo(19.0F)
                curveTo(15.0F, 19.27F, 14.89F, 19.52F, 14.71F, 19.71F)
                curveTo(14.52F, 19.89F, 14.27F, 20.0F, 14.0F, 20.0F)
                horizontalLineTo(10.0F)
                curveTo(9.73F, 20.0F, 9.48F, 19.89F, 9.29F, 19.71F)
                curveTo(9.11F, 19.52F, 9.0F, 19.27F, 9.0F, 19.0F)
                verticalLineTo(17.2F)
                curveTo(7.21F, 16.16F, 6.0F, 14.22F, 6.0F, 12.0F)
                curveTo(6.0F, 10.41F, 6.63F, 8.88F, 7.76F, 7.76F)
                curveTo(8.88F, 6.63F, 10.41F, 6.0F, 12.0F, 6.0F)

                moveTo(14.0F, 21.0F)
                verticalLineTo(22.0F)
                curveTo(14.0F, 22.27F, 13.89F, 22.52F, 13.71F, 22.71F)
                curveTo(13.52F, 22.89F, 13.27F, 23.0F, 13.0F, 23.0F)
                horizontalLineTo(11.0F)
                curveTo(10.73F, 23.0F, 10.48F, 22.89F, 10.29F, 22.71F)
                curveTo(10.11F, 22.52F, 10.0F, 22.27F, 10.0F, 22.0F)
                verticalLineTo(21.0F)
                horizontalLineTo(14.0F)

                moveTo(11.0F, 18.0F)
                horizontalLineTo(13.0F)
                verticalLineTo(15.87F)
                curveTo(14.73F, 15.43F, 16.0F, 13.86F, 16.0F, 12.0F)
                curveTo(16.0F, 10.94F, 15.58F, 9.92F, 14.83F, 9.17F)
                curveTo(14.08F, 8.42F, 13.06F, 8.0F, 12.0F, 8.0F)
                curveTo(10.94F, 8.0F, 9.92F, 8.42F, 9.17F, 9.17F)
                curveTo(8.42F, 9.92F, 8.0F, 10.94F, 8.0F, 12.0F)
                curveTo(8.0F, 13.86F, 9.27F, 15.43F, 11.0F, 15.87F)
                verticalLineTo(18.0F)
                close()
            }
        }
        return _lightBulbIdea!!
    }
private var _lightBulbIdea: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.LightBulbIdea, contentDescription = null)