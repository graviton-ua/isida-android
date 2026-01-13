package com.whoppah.common.compose.icons.state

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.State.CrossCircleOutlined: ImageVector
    get() {
        if (_crossCircleOutlined != null) return _crossCircleOutlined!!

        _crossCircleOutlined = materialIcon(name = "WhIcons.State.CrossCircleOutlined") {
            materialPath {
                moveTo(x = 12.0f, y = 20.0178f)
                curveTo(x1 = 7.59f, y1 = 20.0178f, x2 = 4.0f, y2 = 16.4278f, x3 = 4.0f, y3 = 12.0178f)
                curveTo(x1 = 4.0f, y1 = 7.60782f, x2 = 7.59f, y2 = 4.01782f, x3 = 12.0f, y3 = 4.01782f)
                curveTo(x1 = 16.41f, y1 = 4.01782f, x2 = 20.0f, y2 = 7.60782f, x3 = 20.0f, y3 = 12.0178f)
                curveTo(x1 = 20.0f, y1 = 16.4278f, x2 = 16.41f, y2 = 20.0178f, x3 = 12.0f, y3 = 20.0178f)
                close()
                moveTo(x = 12.0f, y = 2.01782f)
                curveTo(x1 = 6.47f, y1 = 2.01782f, x2 = 2.0f, y2 = 6.48782f, x3 = 2.0f, y3 = 12.0178f)
                curveTo(x1 = 2.0f, y1 = 17.5478f, x2 = 6.47f, y2 = 22.0178f, x3 = 12.0f, y3 = 22.0178f)
                curveTo(x1 = 17.53f, y1 = 22.0178f, x2 = 22.0f, y2 = 17.5478f, x3 = 22.0f, y3 = 12.0178f)
                curveTo(x1 = 22.0f, y1 = 6.48782f, x2 = 17.53f, y2 = 2.01782f, x3 = 12.0f, y3 = 2.01782f)
                close()
                moveTo(x = 14.59f, y = 8.01782f)
                lineTo(x = 12.0f, y = 10.6078f)
                lineTo(x = 9.41f, y = 8.01782f)
                lineTo(x = 8.0f, y = 9.42782f)
                lineTo(x = 10.59f, y = 12.0178f)
                lineTo(x = 8.0f, y = 14.6078f)
                lineTo(x = 9.41f, y = 16.0178f)
                lineTo(x = 12.0f, y = 13.4278f)
                lineTo(x = 14.59f, y = 16.0178f)
                lineTo(x = 16.0f, y = 14.6078f)
                lineTo(x = 13.41f, y = 12.0178f)
                lineTo(x = 16.0f, y = 9.42782f)
                lineTo(x = 14.59f, y = 8.01782f)
                close()
            }
        }
        return _crossCircleOutlined!!
    }

@Suppress("ObjectPropertyName")
private var _crossCircleOutlined: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.State.CrossCircleOutlined, contentDescription = null)