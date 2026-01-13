package com.whoppah.common.compose.icons.navigation

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Navigation.ChevronRight: ImageVector
    get() {
        if (_chevronRight != null) return _chevronRight!!
        _chevronRight = materialIcon(name = "Navigation.ChevronRight") {
            materialPath {
                moveTo(9.29F, 6.71F)
                curveToRelative(-0.39F, 0.39F, -0.39F, 1.02F, 0.0F, 1.41F)
                lineTo(13.17F, 12.0F)
                lineToRelative(-3.88F, 3.88F)
                curveToRelative(-0.39F, 0.39F, -0.39F, 1.02F, 0.0F, 1.41F)
                curveToRelative(0.39F, 0.39F, 1.02F, 0.39F, 1.41F, 0.0F)
                lineToRelative(4.59F, -4.59F)
                curveToRelative(0.39F, -0.39F, 0.39F, -1.02F, 0.0F, -1.41F)
                lineTo(10.7F, 6.7F)
                curveToRelative(-0.38F, -0.38F, -1.02F, -0.38F, -1.41F, 0.01F)
                close()
            }
        }
        return _chevronRight!!
    }
private var _chevronRight: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Navigation.ChevronRight, contentDescription = null)