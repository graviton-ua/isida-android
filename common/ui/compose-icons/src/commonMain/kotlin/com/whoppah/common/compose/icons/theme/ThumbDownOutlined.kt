package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.ThumbDownOutlined: ImageVector
    get() {
        if (_thumbDownOutlined != null) return _thumbDownOutlined!!

        _thumbDownOutlined = materialIcon(name = "WhIcons.Theme.ThumbDownOutlined") {
            materialPath {
                moveTo(x = 19.0f, y = 15.0178f)
                verticalLineTo(y = 3.01782f)
                horizontalLineTo(x = 23.0f)
                verticalLineTo(y = 15.0178f)
                horizontalLineTo(x = 19.0f)
                close()
                moveTo(x = 15.0f, y = 3.01782f)
                curveTo(x1 = 15.5304f, y1 = 3.01782f, x2 = 16.0391f, y2 = 3.22854f, x3 = 16.4142f, y3 = 3.60361f)
                curveTo(x1 = 16.7893f, y1 = 3.97868f, x2 = 17.0f, y2 = 4.48739f, x3 = 17.0f, y3 = 5.01782f)
                verticalLineTo(y = 15.0178f)
                curveTo(x1 = 17.0f, y1 = 15.5678f, x2 = 16.78f, y2 = 16.0678f, x3 = 16.41f, y3 = 16.4278f)
                lineTo(x = 9.83f, y = 23.0178f)
                lineTo(x = 8.77f, y = 21.9578f)
                curveTo(x1 = 8.5f, y1 = 21.6878f, x2 = 8.33f, y2 = 21.3178f, x3 = 8.33f, y3 = 20.8978f)
                lineTo(x = 8.36f, y = 20.5878f)
                lineTo(x = 9.31f, y = 16.0178f)
                horizontalLineTo(x = 3.0f)
                curveTo(x1 = 1.89f, y1 = 16.0178f, x2 = 1.0f, y2 = 15.1178f, x3 = 1.0f, y3 = 14.0178f)
                verticalLineTo(y = 12.0178f)
                curveTo(x1 = 1.0f, y1 = 11.7578f, x2 = 1.05f, y2 = 11.5178f, x3 = 1.14f, y3 = 11.2878f)
                lineTo(x = 4.16f, y = 4.23782f)
                curveTo(x1 = 4.46f, y1 = 3.51782f, x2 = 5.17f, y2 = 3.01782f, x3 = 6.0f, y3 = 3.01782f)
                horizontalLineTo(x = 15.0f)
                close()
                moveTo(x = 15.0f, y = 5.01782f)
                horizontalLineTo(x = 5.97f)
                lineTo(x = 3.0f, y = 12.0178f)
                verticalLineTo(y = 14.0178f)
                horizontalLineTo(x = 11.78f)
                lineTo(x = 10.65f, y = 19.3378f)
                lineTo(x = 15.0f, y = 14.9878f)
                verticalLineTo(y = 5.01782f)
                close()
            }
        }
        return _thumbDownOutlined!!
    }

@Suppress("ObjectPropertyName")
private var _thumbDownOutlined: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.ThumbDownOutlined, contentDescription = null)