package com.whoppah.common.compose.icons.action

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Action.BookmarkOutlined: ImageVector
    get() {
        if (_bookmarkOutlined != null) return _bookmarkOutlined!!

        _bookmarkOutlined = materialIcon(name = "WhIcons.Action.BookmarkOutlined") {
            materialPath {
                moveTo(x = 17.0f, y = 18.0f)
                lineTo(x = 12.0f, y = 15.82f)
                lineTo(x = 7.0f, y = 18.0f)
                verticalLineTo(y = 5.0f)
                horizontalLineTo(x = 17.0f)
                moveTo(x = 17.0f, y = 3.0f)
                horizontalLineTo(x = 7.0f)
                curveTo(x1 = 6.46957f, y1 = 3.0f, x2 = 5.96086f, y2 = 3.21071f, x3 = 5.58579f, y3 = 3.58579f)
                curveTo(x1 = 5.21071f, y1 = 3.96086f, x2 = 5.0f, y2 = 4.46957f, x3 = 5.0f, y3 = 5.0f)
                verticalLineTo(y = 21.0f)
                lineTo(x = 12.0f, y = 18.0f)
                lineTo(x = 19.0f, y = 21.0f)
                verticalLineTo(y = 5.0f)
                curveTo(x1 = 19.0f, y1 = 3.89f, x2 = 18.1f, y2 = 3.0f, x3 = 17.0f, y3 = 3.0f)
                close()
            }
        }
        return _bookmarkOutlined!!
    }

@Suppress("ObjectPropertyName")
private var _bookmarkOutlined: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Action.BookmarkOutlined, contentDescription = null)