package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.Bidding: ImageVector
    get() {
        if (_bidding != null) return _bidding!!
        _bidding = materialIcon(name = "Theme.Bidding") {
            materialPath {
                moveTo(21.0F, 18.56F)
                curveTo(21.0F, 18.2F, 20.85F, 17.86F, 20.62F, 17.61F)
                lineTo(16.84F, 13.84F)
                curveTo(16.59F, 13.6F, 16.25F, 13.45F, 15.89F, 13.45F)
                curveTo(15.5F, 13.45F, 15.18F, 13.62F, 14.9F, 13.9F)
                lineTo(12.23F, 11.24F)
                lineTo(13.54F, 9.93F)
                curveTo(13.64F, 9.83F, 13.69F, 9.71F, 13.69F, 9.57F)
                curveTo(13.69F, 9.44F, 13.64F, 9.31F, 13.54F, 9.22F)
                curveTo(13.86F, 9.53F, 14.09F, 9.82F, 14.56F, 9.82F)
                curveTo(14.83F, 9.82F, 15.07F, 9.72F, 15.27F, 9.53F)
                curveTo(15.64F, 9.18F, 16.14F, 8.8F, 16.14F, 8.24F)
                curveTo(16.14F, 7.98F, 16.04F, 7.72F, 15.85F, 7.53F)
                lineTo(11.61F, 3.29F)
                curveTo(11.42F, 3.1F, 11.16F, 3.0F, 10.9F, 3.0F)
                curveTo(10.34F, 3.0F, 9.97F, 3.5F, 9.61F, 3.87F)
                curveTo(9.43F, 4.07F, 9.32F, 4.31F, 9.32F, 4.58F)
                curveTo(9.32F, 5.06F, 9.61F, 5.29F, 9.93F, 5.6F)
                curveTo(9.83F, 5.51F, 9.71F, 5.45F, 9.57F, 5.45F)
                curveTo(9.44F, 5.45F, 9.31F, 5.51F, 9.22F, 5.6F)
                lineTo(5.6F, 9.22F)
                curveTo(5.51F, 9.31F, 5.45F, 9.44F, 5.45F, 9.57F)
                curveTo(5.45F, 9.71F, 5.51F, 9.83F, 5.6F, 9.93F)
                curveTo(5.29F, 9.61F, 5.06F, 9.32F, 4.58F, 9.32F)
                curveTo(4.31F, 9.32F, 4.07F, 9.43F, 3.87F, 9.61F)
                curveTo(3.5F, 9.97F, 3.0F, 10.34F, 3.0F, 10.9F)
                curveTo(3.0F, 11.16F, 3.1F, 11.42F, 3.29F, 11.61F)
                lineTo(7.53F, 15.85F)
                curveTo(7.72F, 16.04F, 7.98F, 16.14F, 8.24F, 16.14F)
                curveTo(8.8F, 16.14F, 9.18F, 15.64F, 9.53F, 15.27F)
                curveTo(9.72F, 15.07F, 9.82F, 14.83F, 9.82F, 14.56F)
                curveTo(9.82F, 14.09F, 9.53F, 13.86F, 9.22F, 13.54F)
                curveTo(9.31F, 13.64F, 9.44F, 13.69F, 9.57F, 13.69F)
                curveTo(9.71F, 13.69F, 9.83F, 13.64F, 9.93F, 13.54F)
                lineTo(11.24F, 12.23F)
                lineTo(13.9F, 14.9F)
                curveTo(13.62F, 15.18F, 13.45F, 15.5F, 13.45F, 15.89F)
                curveTo(13.45F, 16.25F, 13.6F, 16.59F, 13.85F, 16.83F)
                lineTo(17.62F, 20.62F)
                curveTo(17.86F, 20.85F, 18.2F, 21.0F, 18.56F, 21.0F)
                curveTo(18.91F, 21.0F, 19.25F, 20.85F, 19.5F, 20.62F)
                lineTo(20.62F, 19.49F)
                curveTo(20.85F, 19.25F, 21.0F, 18.91F, 21.0F, 18.56F)
                close()
            }
        }
        return _bidding!!
    }
private var _bidding: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.Bidding, contentDescription = null)