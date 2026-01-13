package com.whoppah.common.compose.icons.state

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.State.Zap: ImageVector
    get() {
        if (_zap != null) return _zap!!

        _zap = materialIcon(name = "WhIcons.State.Zap") {
            materialPath {
                moveTo(x = 20.7661f, y = 9.52609f)
                horizontalLineTo(x = 12.5009f)
                lineTo(x = 14.5046f, y = 3.6705f)
                curveTo(x1 = 14.8803f, y1 = 2.42464f, x2 = 13.3775f, y2 = 1.42794f, x3 = 12.3757f, y3 = 2.42464f)
                lineTo(x = 2.35728f, y = 12.3916f)
                curveTo(x1 = 1.6059f, y1 = 13.1391f, x2 = 2.10682f, y2 = 14.5096f, x3 = 3.23389f, y3 = 14.5096f)
                horizontalLineTo(x = 11.4991f)
                lineTo(x = 9.4954f, y = 20.3651f)
                curveTo(x1 = 9.11971f, y1 = 21.611f, x2 = 10.6225f, y2 = 22.6077f, x3 = 11.6243f, y3 = 21.611f)
                lineTo(x = 21.6427f, y = 11.6441f)
                curveTo(x1 = 22.3941f, y1 = 10.8965f, x2 = 21.8932f, y2 = 9.52609f, x3 = 20.7661f, y3 = 9.52609f)
                close()
            }
        }
        return _zap!!
    }

@Suppress("ObjectPropertyName")
private var _zap: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.State.Zap, contentDescription = null)