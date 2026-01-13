package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.CustomerSupport: ImageVector
    get() {
        if (_customerSupport != null) return _customerSupport!!
        _customerSupport = materialIcon(name = "Theme.CustomerSupport") {
            materialPath {
                moveTo(18.72F, 15.4231F)
                curveTo(19.07F, 14.5731F, 19.26F, 13.6631F, 19.26F, 12.6631F)
                curveTo(19.26F, 11.9431F, 19.15F, 11.2531F, 18.96F, 10.6131F)
                curveTo(18.31F, 10.7631F, 17.63F, 10.8431F, 16.92F, 10.8431F)
                curveTo(13.86F, 10.8431F, 11.15F, 9.33307F, 9.5F, 7.00307F)
                curveTo(8.61F, 9.16307F, 6.91F, 10.9231F, 4.77F, 11.8831F)
                curveTo(4.73F, 12.1331F, 4.73F, 12.4031F, 4.73F, 12.6631F)
                curveTo(4.73F, 14.5912F, 5.49594F, 16.4403F, 6.85933F, 17.8037F)
                curveTo(8.22272F, 19.1671F, 10.0719F, 19.9331F, 12.0F, 19.9331F)
                curveTo(13.05F, 19.9331F, 14.06F, 19.7031F, 14.97F, 19.2931F)
                curveTo(15.54F, 20.3831F, 15.8F, 20.9231F, 15.78F, 20.9231F)
                curveTo(14.14F, 21.4731F, 12.87F, 21.7431F, 12.0F, 21.7431F)
                curveTo(9.58F, 21.7431F, 7.27F, 20.7931F, 5.57F, 19.0831F)
                curveTo(4.53F, 18.0431F, 3.76F, 16.7731F, 3.33F, 15.3931F)
                horizontalLineTo(2.0F)
                verticalLineTo(10.8431F)
                horizontalLineTo(3.09F)
                curveTo(3.93F, 6.70307F, 7.6F, 3.58307F, 12.0F, 3.58307F)
                curveTo(14.4F, 3.58307F, 16.71F, 4.53307F, 18.42F, 6.24307F)
                curveTo(19.69F, 7.50307F, 20.54F, 9.11307F, 20.89F, 10.8431F)
                horizontalLineTo(22.0F)
                verticalLineTo(15.3331F)
                verticalLineTo(15.3531F)
                verticalLineTo(15.3931F)
                horizontalLineTo(21.94F)
                lineTo(18.38F, 18.6631F)
                lineTo(13.08F, 18.0631F)
                verticalLineTo(16.3931F)
                horizontalLineTo(17.91F)
                lineTo(18.72F, 15.4231F)

                moveTo(9.27F, 12.4331F)
                curveTo(9.57F, 12.4331F, 9.86F, 12.5531F, 10.07F, 12.7731F)
                curveTo(10.28F, 12.9831F, 10.4F, 13.2731F, 10.4F, 13.5731F)
                curveTo(10.4F, 13.8731F, 10.28F, 14.1631F, 10.07F, 14.3731F)
                curveTo(9.86F, 14.5831F, 9.57F, 14.7031F, 9.27F, 14.7031F)
                curveTo(8.64F, 14.7031F, 8.13F, 14.2031F, 8.13F, 13.5731F)
                curveTo(8.13F, 12.9431F, 8.64F, 12.4331F, 9.27F, 12.4331F)

                moveTo(14.72F, 12.4331F)
                curveTo(15.35F, 12.4331F, 15.85F, 12.9431F, 15.85F, 13.5731F)
                curveTo(15.85F, 14.2031F, 15.35F, 14.7031F, 14.72F, 14.7031F)
                curveTo(14.09F, 14.7031F, 13.58F, 14.2031F, 13.58F, 13.5731F)
                curveTo(13.58F, 13.2707F, 13.7001F, 12.9808F, 13.9139F, 12.767F)
                curveTo(14.1277F, 12.5532F, 14.4177F, 12.4331F, 14.72F, 12.4331F)
                close()
            }
        }
        return _customerSupport!!
    }
private var _customerSupport: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.CustomerSupport, contentDescription = null)