package com.whoppah.common.compose.icons.navigation

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Navigation.MenuH: ImageVector
    get() {
        if (_menuH != null) return _menuH!!

        _menuH = materialIcon(name = "WhIcons.Navigation.MenuH") {
            materialPath(
                pathFillType = PathFillType.EvenOdd,
            ) {
                moveTo(x = 6.0f, y = 10.0178f)
                curveTo(x1 = 4.9f, y1 = 10.0178f, x2 = 4.0f, y2 = 10.9178f, x3 = 4.0f, y3 = 12.0178f)
                curveTo(x1 = 4.0f, y1 = 13.1178f, x2 = 4.9f, y2 = 14.0178f, x3 = 6.0f, y3 = 14.0178f)
                curveTo(x1 = 7.1f, y1 = 14.0178f, x2 = 8.0f, y2 = 13.1178f, x3 = 8.0f, y3 = 12.0178f)
                curveTo(x1 = 8.0f, y1 = 10.9178f, x2 = 7.1f, y2 = 10.0178f, x3 = 6.0f, y3 = 10.0178f)
                close()
                moveTo(x = 18.0f, y = 10.0178f)
                curveTo(x1 = 16.9f, y1 = 10.0178f, x2 = 16.0f, y2 = 10.9178f, x3 = 16.0f, y3 = 12.0178f)
                curveTo(x1 = 16.0f, y1 = 13.1178f, x2 = 16.9f, y2 = 14.0178f, x3 = 18.0f, y3 = 14.0178f)
                curveTo(x1 = 19.1f, y1 = 14.0178f, x2 = 20.0f, y2 = 13.1178f, x3 = 20.0f, y3 = 12.0178f)
                curveTo(x1 = 20.0f, y1 = 10.9178f, x2 = 19.1f, y2 = 10.0178f, x3 = 18.0f, y3 = 10.0178f)
                close()
                moveTo(x = 10.0f, y = 12.0178f)
                curveTo(x1 = 10.0f, y1 = 10.9178f, x2 = 10.9f, y2 = 10.0178f, x3 = 12.0f, y3 = 10.0178f)
                curveTo(x1 = 13.1f, y1 = 10.0178f, x2 = 14.0f, y2 = 10.9178f, x3 = 14.0f, y3 = 12.0178f)
                curveTo(x1 = 14.0f, y1 = 13.1178f, x2 = 13.1f, y2 = 14.0178f, x3 = 12.0f, y3 = 14.0178f)
                curveTo(x1 = 10.9f, y1 = 14.0178f, x2 = 10.0f, y2 = 13.1178f, x3 = 10.0f, y3 = 12.0178f)
                close()
            }
        }
        return _menuH!!
    }

@Suppress("ObjectPropertyName")
private var _menuH: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Navigation.MenuH, contentDescription = null)