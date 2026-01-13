package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.Rocket: ImageVector
    get() {
        if (_rocket != null) return _rocket!!
        _rocket = materialIcon(name = "Theme.Rocket") {
            materialPath {
                moveTo(13.13F, 22.19F)
                lineTo(11.5F, 18.36F)
                curveTo(13.07F, 17.78F, 14.54F, 17.0F, 15.9F, 16.09F)
                lineTo(13.13F, 22.19F)

                moveTo(5.64F, 12.5F)
                lineTo(1.81F, 10.87F)
                lineTo(7.91F, 8.1F)
                curveTo(7.0F, 9.46F, 6.22F, 10.93F, 5.64F, 12.5F)

                moveTo(21.61F, 2.39F)
                curveTo(21.61F, 2.39F, 16.66F, 0.27F, 11.0F, 5.93F)
                curveTo(8.81F, 8.12F, 7.5F, 10.53F, 6.65F, 12.64F)
                curveTo(6.37F, 13.39F, 6.56F, 14.21F, 7.11F, 14.77F)
                lineTo(9.24F, 16.89F)
                curveTo(9.79F, 17.45F, 10.61F, 17.63F, 11.36F, 17.35F)
                curveTo(13.5F, 16.53F, 15.88F, 15.19F, 18.07F, 13.0F)
                curveTo(23.73F, 7.34F, 21.61F, 2.39F, 21.61F, 2.39F)

                moveTo(14.54F, 9.46F)
                curveTo(13.76F, 8.68F, 13.76F, 7.41F, 14.54F, 6.63F)
                curveTo(15.32F, 5.85F, 16.59F, 5.85F, 17.37F, 6.63F)
                curveTo(18.14F, 7.41F, 18.15F, 8.68F, 17.37F, 9.46F)
                curveTo(16.59F, 10.24F, 15.32F, 10.24F, 14.54F, 9.46F)

                moveTo(8.88F, 16.53F)
                lineTo(7.47F, 15.12F)
                lineTo(8.88F, 16.53F)

                moveTo(6.24F, 22.0F)
                lineTo(9.88F, 18.36F)
                curveTo(9.54F, 18.27F, 9.21F, 18.12F, 8.91F, 17.91F)
                lineTo(4.83F, 22.0F)
                horizontalLineTo(6.24F)

                moveTo(2.0F, 22.0F)
                horizontalLineTo(3.41F)
                lineTo(8.18F, 17.24F)
                lineTo(6.76F, 15.83F)
                lineTo(2.0F, 20.59F)
                verticalLineTo(22.0F)

                moveTo(2.0F, 19.17F)
                lineTo(6.09F, 15.09F)
                curveTo(5.88F, 14.79F, 5.73F, 14.47F, 5.64F, 14.12F)
                lineTo(2.0F, 17.76F)
                verticalLineTo(19.17F)
                close()
            }
        }
        return _rocket!!
    }
private var _rocket: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.Rocket, contentDescription = null)