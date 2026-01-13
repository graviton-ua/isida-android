package com.whoppah.common.compose.icons.navigation

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Navigation.ArrowUpward: ImageVector
    get() {
        if (_arrowUpward != null) return _arrowUpward!!
        _arrowUpward = materialIcon(name = "Navigation.ArrowUpward") {
            materialPath {
                moveTo(13.0F, 19.0F)
                verticalLineTo(7.83F)
                lineToRelative(4.88F, 4.88F)
                curveToRelative(0.39F, 0.39F, 1.03F, 0.39F, 1.42F, 0.0F)
                curveToRelative(0.39F, -0.39F, 0.39F, -1.02F, 0.0F, -1.41F)
                lineToRelative(-6.59F, -6.59F)
                curveToRelative(-0.39F, -0.39F, -1.02F, -0.39F, -1.41F, 0.0F)
                lineToRelative(-6.6F, 6.58F)
                curveToRelative(-0.39F, 0.39F, -0.39F, 1.02F, 0.0F, 1.41F)
                curveToRelative(0.39F, 0.39F, 1.02F, 0.39F, 1.41F, 0.0F)
                lineTo(11.0F, 7.83F)
                verticalLineTo(19.0F)
                curveToRelative(0.0F, 0.55F, 0.45F, 1.0F, 1.0F, 1.0F)
                reflectiveCurveToRelative(1.0F, -0.45F, 1.0F, -1.0F)
                close()
            }
        }
        return _arrowUpward!!
    }
private var _arrowUpward: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Navigation.ArrowUpward, contentDescription = null)