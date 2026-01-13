package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.ThumbUpOutlined: ImageVector
    get() {
        if (_thumbUpOutlined != null) return _thumbUpOutlined!!

        _thumbUpOutlined = materialIcon(name = "WhIcons.Theme.ThumbUpOutlined") {
            materialPath {
                moveTo(x = 5.0f, y = 9.01782f)
                verticalLineTo(y = 21.0178f)
                horizontalLineTo(x = 1.0f)
                verticalLineTo(y = 9.01782f)
                horizontalLineTo(x = 5.0f)
                close()
                moveTo(x = 9.0f, y = 21.0178f)
                curveTo(x1 = 8.46957f, y1 = 21.0178f, x2 = 7.96086f, y2 = 20.8071f, x3 = 7.58579f, y3 = 20.432f)
                curveTo(x1 = 7.21071f, y1 = 20.057f, x2 = 7.0f, y2 = 19.5483f, x3 = 7.0f, y3 = 19.0178f)
                verticalLineTo(y = 9.01782f)
                curveTo(x1 = 7.0f, y1 = 8.46782f, x2 = 7.22f, y2 = 7.96782f, x3 = 7.59f, y3 = 7.60782f)
                lineTo(x = 14.17f, y = 1.01782f)
                lineTo(x = 15.23f, y = 2.07782f)
                curveTo(x1 = 15.5f, y1 = 2.34782f, x2 = 15.67f, y2 = 2.71782f, x3 = 15.67f, y3 = 3.12782f)
                lineTo(x = 15.64f, y = 3.44782f)
                lineTo(x = 14.69f, y = 8.01782f)
                horizontalLineTo(x = 21.0f)
                curveTo(x1 = 22.11f, y1 = 8.01782f, x2 = 23.0f, y2 = 8.91782f, x3 = 23.0f, y3 = 10.0178f)
                verticalLineTo(y = 12.0178f)
                curveTo(x1 = 23.0f, y1 = 12.2778f, x2 = 22.95f, y2 = 12.5178f, x3 = 22.86f, y3 = 12.7478f)
                lineTo(x = 19.84f, y = 19.7978f)
                curveTo(x1 = 19.54f, y1 = 20.5178f, x2 = 18.83f, y2 = 21.0178f, x3 = 18.0f, y3 = 21.0178f)
                horizontalLineTo(x = 9.0f)
                close()
                moveTo(x = 9.0f, y = 19.0178f)
                horizontalLineTo(x = 18.03f)
                lineTo(x = 21.0f, y = 12.0178f)
                verticalLineTo(y = 10.0178f)
                horizontalLineTo(x = 12.21f)
                lineTo(x = 13.34f, y = 4.69782f)
                lineTo(x = 9.0f, y = 9.04782f)
                verticalLineTo(y = 19.0178f)
                close()
            }
        }
        return _thumbUpOutlined!!
    }

@Suppress("ObjectPropertyName")
private var _thumbUpOutlined: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.ThumbUpOutlined, contentDescription = null)