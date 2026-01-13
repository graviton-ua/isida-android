package com.whoppah.common.compose.icons.navigation

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Navigation.MenuV: ImageVector
    get() {
        if (_menuV != null) return _menuV!!

        _menuV = materialIcon(name = "WhIcons.Navigation.MenuV") {
            materialPath(
                pathFillType = PathFillType.EvenOdd,
            ) {
                moveTo(x = 12.0f, y = 8.01782f)
                curveTo(x1 = 13.1f, y1 = 8.01782f, x2 = 14.0f, y2 = 7.11782f, x3 = 14.0f, y3 = 6.01782f)
                curveTo(x1 = 14.0f, y1 = 4.91782f, x2 = 13.1f, y2 = 4.01782f, x3 = 12.0f, y3 = 4.01782f)
                curveTo(x1 = 10.9f, y1 = 4.01782f, x2 = 10.0f, y2 = 4.91782f, x3 = 10.0f, y3 = 6.01782f)
                curveTo(x1 = 10.0f, y1 = 7.11782f, x2 = 10.9f, y2 = 8.01782f, x3 = 12.0f, y3 = 8.01782f)
                close()
                moveTo(x = 12.0f, y = 10.0178f)
                curveTo(x1 = 10.9f, y1 = 10.0178f, x2 = 10.0f, y2 = 10.9178f, x3 = 10.0f, y3 = 12.0178f)
                curveTo(x1 = 10.0f, y1 = 13.1178f, x2 = 10.9f, y2 = 14.0178f, x3 = 12.0f, y3 = 14.0178f)
                curveTo(x1 = 13.1f, y1 = 14.0178f, x2 = 14.0f, y2 = 13.1178f, x3 = 14.0f, y3 = 12.0178f)
                curveTo(x1 = 14.0f, y1 = 10.9178f, x2 = 13.1f, y2 = 10.0178f, x3 = 12.0f, y3 = 10.0178f)
                close()
                moveTo(x = 10.0f, y = 18.0178f)
                curveTo(x1 = 10.0f, y1 = 16.9178f, x2 = 10.9f, y2 = 16.0178f, x3 = 12.0f, y3 = 16.0178f)
                curveTo(x1 = 13.1f, y1 = 16.0178f, x2 = 14.0f, y2 = 16.9178f, x3 = 14.0f, y3 = 18.0178f)
                curveTo(x1 = 14.0f, y1 = 19.1178f, x2 = 13.1f, y2 = 20.0178f, x3 = 12.0f, y3 = 20.0178f)
                curveTo(x1 = 10.9f, y1 = 20.0178f, x2 = 10.0f, y2 = 19.1178f, x3 = 10.0f, y3 = 18.0178f)
                close()
            }
        }
        return _menuV!!
    }

@Suppress("ObjectPropertyName")
private var _menuV: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Navigation.MenuV, contentDescription = null)