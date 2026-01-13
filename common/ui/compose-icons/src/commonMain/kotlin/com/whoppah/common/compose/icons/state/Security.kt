package com.whoppah.common.compose.icons.state

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.State.Security: ImageVector
    get() {
        if (_security != null) return _security!!
        _security = materialIcon(name = "State.Security") {
            materialPath {
                moveTo(5.05778F, 4.85926F)
                lineTo(11.28F, 2.15026F)
                curveTo(11.7333F, 1.94991F, 12.2667F, 1.94991F, 12.72F, 2.15026F)
                lineTo(18.9422F, 4.85926F)
                curveTo(19.5822F, 5.13799F, 20.0F, 5.76516F, 20.0F, 6.4533F)
                verticalLineTo(10.5473F)
                curveTo(20.0F, 15.3817F, 16.5867F, 19.9025F, 12.0F, 21.0F)
                curveTo(7.41333F, 19.9025F, 4.0F, 15.3817F, 4.0F, 10.5473F)
                verticalLineTo(6.4533F)
                curveTo(4.0F, 5.76516F, 4.41778F, 5.13799F, 5.05778F, 4.85926F)

                moveTo(7.28928F, 12.8991F)
                lineTo(9.59151F, 15.1551F)
                curveTo(9.93817F, 15.4949F, 10.5071F, 15.4949F, 10.8448F, 15.1551F)
                lineTo(16.7026F, 9.41486F)
                curveTo(17.0493F, 9.07515F, 17.0493F, 8.52638F, 16.7026F, 8.18667F)
                curveTo(16.356F, 7.84696F, 15.796F, 7.84696F, 15.4493F, 8.18667F)
                lineTo(10.2226F, 13.3085F)
                lineTo(8.54262F, 11.6709F)
                curveTo(8.19595F, 11.3312F, 7.63595F, 11.3312F, 7.28928F, 11.6709F)
                curveTo(7.12284F, 11.8337F, 7.0293F, 12.0546F, 7.0293F, 12.285F)
                curveTo(7.0293F, 12.5154F, 7.12284F, 12.7364F, 7.28928F, 12.8991F)
                close()
            }
        }
        return _security!!
    }
private var _security: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.State.Security, contentDescription = null)