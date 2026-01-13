package com.whoppah.common.compose.icons.navigation

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Navigation.ChevronLeft: ImageVector
    get() {
        if (_chevronLeft != null) return _chevronLeft!!
        _chevronLeft = materialIcon(name = "Navigation.ChevronLeft") {
            materialPath {
                moveTo(14.71F, 6.71F)
                curveToRelative(-0.39F, -0.39F, -1.02F, -0.39F, -1.41F, 0.0F)
                lineTo(8.71F, 11.3F)
                curveToRelative(-0.39F, 0.39F, -0.39F, 1.02F, 0.0F, 1.41F)
                lineToRelative(4.59F, 4.59F)
                curveToRelative(0.39F, 0.39F, 1.02F, 0.39F, 1.41F, 0.0F)
                curveToRelative(0.39F, -0.39F, 0.39F, -1.02F, 0.0F, -1.41F)
                lineTo(10.83F, 12.0F)
                lineToRelative(3.88F, -3.88F)
                curveToRelative(0.39F, -0.39F, 0.38F, -1.03F, 0.0F, -1.41F)
                close()
            }
        }
        return _chevronLeft!!
    }
private var _chevronLeft: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Navigation.ChevronLeft, contentDescription = null)