package com.whoppah.common.compose.icons.navigation

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Navigation.Menu: ImageVector
    get() {
        if (_menu != null) return _menu!!

        _menu = materialIcon(name = "WhIcons.Navigation.Menu") {
            materialPath(
                pathFillType = PathFillType.EvenOdd,
            ) {
                moveTo(x = 4.0f, y = 8.01782f)
                curveTo(x1 = 3.45f, y1 = 8.01782f, x2 = 3.0f, y2 = 7.56782f, x3 = 3.0f, y3 = 7.01782f)
                curveTo(x1 = 3.0f, y1 = 6.46782f, x2 = 3.45f, y2 = 6.01782f, x3 = 4.0f, y3 = 6.01782f)
                horizontalLineTo(x = 20.0f)
                curveTo(x1 = 20.55f, y1 = 6.01782f, x2 = 21.0f, y2 = 6.46782f, x3 = 21.0f, y3 = 7.01782f)
                curveTo(x1 = 21.0f, y1 = 7.56782f, x2 = 20.55f, y2 = 8.01782f, x3 = 20.0f, y3 = 8.01782f)
                horizontalLineTo(x = 4.0f)
                close()
                moveTo(x = 4.0f, y = 13.0178f)
                horizontalLineTo(x = 20.0f)
                curveTo(x1 = 20.55f, y1 = 13.0178f, x2 = 21.0f, y2 = 12.5678f, x3 = 21.0f, y3 = 12.0178f)
                curveTo(x1 = 21.0f, y1 = 11.4678f, x2 = 20.55f, y2 = 11.0178f, x3 = 20.0f, y3 = 11.0178f)
                horizontalLineTo(x = 4.0f)
                curveTo(x1 = 3.45f, y1 = 11.0178f, x2 = 3.0f, y2 = 11.4678f, x3 = 3.0f, y3 = 12.0178f)
                curveTo(x1 = 3.0f, y1 = 12.5678f, x2 = 3.45f, y2 = 13.0178f, x3 = 4.0f, y3 = 13.0178f)
                close()
                moveTo(x = 4.0f, y = 18.0178f)
                horizontalLineTo(x = 20.0f)
                curveTo(x1 = 20.55f, y1 = 18.0178f, x2 = 21.0f, y2 = 17.5678f, x3 = 21.0f, y3 = 17.0178f)
                curveTo(x1 = 21.0f, y1 = 16.4678f, x2 = 20.55f, y2 = 16.0178f, x3 = 20.0f, y3 = 16.0178f)
                horizontalLineTo(x = 4.0f)
                curveTo(x1 = 3.45f, y1 = 16.0178f, x2 = 3.0f, y2 = 16.4678f, x3 = 3.0f, y3 = 17.0178f)
                curveTo(x1 = 3.0f, y1 = 17.5678f, x2 = 3.45f, y2 = 18.0178f, x3 = 4.0f, y3 = 18.0178f)
                close()
            }
        }
        return _menu!!
    }

@Suppress("ObjectPropertyName")
private var _menu: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Navigation.Menu, contentDescription = null)