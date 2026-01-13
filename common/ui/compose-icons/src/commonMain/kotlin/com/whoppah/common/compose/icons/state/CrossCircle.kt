package com.whoppah.common.compose.icons.state

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.State.CrossCircle: ImageVector
    get() {
        if (_crossCircle != null) return _crossCircle!!
        _crossCircle = materialIcon(name = "State.CrossCircle") {
            materialPath {
                moveTo(2.0F, 12.0178F)
                curveTo(2.0F, 6.48782F, 6.47F, 2.01782F, 12.0F, 2.01782F)
                curveTo(17.53F, 2.01782F, 22.0F, 6.48782F, 22.0F, 12.0178F)
                curveTo(22.0F, 17.5478F, 17.53F, 22.0178F, 12.0F, 22.0178F)
                curveTo(6.47F, 22.0178F, 2.0F, 17.5478F, 2.0F, 12.0178F)

                moveTo(14.8906F, 16.3177F)
                curveTo(15.2806F, 16.7077F, 15.9106F, 16.7077F, 16.3006F, 16.3177F)
                curveTo(16.6806F, 15.9277F, 16.6806F, 15.2877F, 16.3006F, 14.9077F)
                lineTo(13.4106F, 12.0177F)
                lineTo(16.3006F, 9.12765F)
                curveTo(16.6906F, 8.73765F, 16.6906F, 8.10765F, 16.3006F, 7.71765F)
                curveTo(15.9106F, 7.32765F, 15.2806F, 7.32765F, 14.8906F, 7.71765F)
                lineTo(12.0006F, 10.6077F)
                lineTo(9.11059F, 7.71765F)
                curveTo(8.72059F, 7.32765F, 8.09059F, 7.32765F, 7.70059F, 7.71765F)
                curveTo(7.51333F, 7.90448F, 7.4081F, 8.15813F, 7.4081F, 8.42265F)
                curveTo(7.4081F, 8.68717F, 7.51333F, 8.94082F, 7.70059F, 9.12765F)
                lineTo(10.5906F, 12.0177F)
                lineTo(7.70059F, 14.9077F)
                curveTo(7.51333F, 15.0945F, 7.4081F, 15.3481F, 7.4081F, 15.6127F)
                curveTo(7.4081F, 15.8772F, 7.51333F, 16.1308F, 7.70059F, 16.3177F)
                curveTo(8.09059F, 16.7077F, 8.72059F, 16.7077F, 9.11059F, 16.3177F)
                lineTo(12.0006F, 13.4277F)
                lineTo(14.8906F, 16.3177F)
                close()
            }
        }
        return _crossCircle!!
    }
private var _crossCircle: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.State.CrossCircle, contentDescription = null)