package com.whoppah.common.compose.icons.state

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.State.HourglassOutlined: ImageVector
    get() {
        if (_hourglassOutlined != null) return _hourglassOutlined!!

        _hourglassOutlined = materialIcon(name = "WhIcons.State.HourglassOutlined") {
            materialPath {
                moveTo(x = 6.0f, y = 2.01782f)
                horizontalLineTo(x = 18.0f)
                verticalLineTo(y = 8.01782f)
                lineTo(x = 14.0f, y = 12.0178f)
                lineTo(x = 18.0f, y = 16.0178f)
                verticalLineTo(y = 22.0178f)
                horizontalLineTo(x = 6.0f)
                verticalLineTo(y = 16.0178f)
                lineTo(x = 10.0f, y = 12.0178f)
                lineTo(x = 6.0f, y = 8.01782f)
                verticalLineTo(y = 2.01782f)
                close()
                moveTo(x = 16.0f, y = 16.5178f)
                lineTo(x = 12.0f, y = 12.5178f)
                lineTo(x = 8.0f, y = 16.5178f)
                verticalLineTo(y = 20.0178f)
                horizontalLineTo(x = 16.0f)
                verticalLineTo(y = 16.5178f)
                close()
                moveTo(x = 12.0f, y = 11.5178f)
                lineTo(x = 16.0f, y = 7.51782f)
                verticalLineTo(y = 4.01782f)
                horizontalLineTo(x = 8.0f)
                verticalLineTo(y = 7.51782f)
                lineTo(x = 12.0f, y = 11.5178f)
                close()
                moveTo(x = 10.0f, y = 6.01782f)
                horizontalLineTo(x = 14.0f)
                verticalLineTo(y = 6.76782f)
                lineTo(x = 12.0f, y = 8.76782f)
                lineTo(x = 10.0f, y = 6.76782f)
                verticalLineTo(y = 6.01782f)
                close()
            }
        }
        return _hourglassOutlined!!
    }

@Suppress("ObjectPropertyName")
private var _hourglassOutlined: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.State.HourglassOutlined, contentDescription = null)

