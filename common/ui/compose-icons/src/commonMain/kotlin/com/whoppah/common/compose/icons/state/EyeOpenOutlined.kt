package com.whoppah.common.compose.icons.state

import androidx.compose.foundation.Image
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.State.EyeOpenOutlined: ImageVector
    get() {
        if (_eyeOpenOutlined != null) return _eyeOpenOutlined!!
        _eyeOpenOutlined = materialIcon(name = "State.EyeOpenOutlined") {
            materialPath {
                verticalLineToRelative(0.0F)
                moveTo(12.0F, 9.01782F)
                curveTo(12.7956F, 9.01782F, 13.5587F, 9.33389F, 14.1213F, 9.8965F)
                curveTo(14.6839F, 10.4591F, 15.0F, 11.2222F, 15.0F, 12.0178F)
                curveTo(15.0F, 12.8135F, 14.6839F, 13.5765F, 14.1213F, 14.1391F)
                curveTo(13.5587F, 14.7018F, 12.7956F, 15.0178F, 12.0F, 15.0178F)
                curveTo(11.2044F, 15.0178F, 10.4413F, 14.7018F, 9.87868F, 14.1391F)
                curveTo(9.31607F, 13.5765F, 9.0F, 12.8135F, 9.0F, 12.0178F)
                curveTo(9.0F, 11.2222F, 9.31607F, 10.4591F, 9.87868F, 9.8965F)
                curveTo(10.4413F, 9.33389F, 11.2044F, 9.01782F, 12.0F, 9.01782F)

                moveTo(12.0F, 4.51782F)
                curveTo(17.0F, 4.51782F, 21.27F, 7.62782F, 23.0F, 12.0178F)
                curveTo(21.27F, 16.4078F, 17.0F, 19.5178F, 12.0F, 19.5178F)
                curveTo(7.0F, 19.5178F, 2.73F, 16.4078F, 1.0F, 12.0178F)
                curveTo(2.73F, 7.62782F, 7.0F, 4.51782F, 12.0F, 4.51782F)

                moveTo(3.18F, 12.0178F)
                curveTo(4.83F, 15.3778F, 8.24F, 17.5178F, 12.0F, 17.5178F)
                curveTo(15.76F, 17.5178F, 19.17F, 15.3778F, 20.82F, 12.0178F)
                curveTo(19.17F, 8.65782F, 15.76F, 6.51782F, 12.0F, 6.51782F)
                curveTo(8.24F, 6.51782F, 4.83F, 8.65782F, 3.18F, 12.0178F)

                close()
            }
        }
        return _eyeOpenOutlined!!
    }

private var _eyeOpenOutlined: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Image(imageVector = WhIcons.State.EyeOpenOutlined, contentDescription = null)
}