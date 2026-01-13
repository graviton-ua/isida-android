package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.Tag: ImageVector
    get() {
        if (_tag != null) return _tag!!
        _tag = materialIcon(name = "Theme.Tag") {
            materialPath {
                moveTo(21.41F, 11.58F)
                lineToRelative(-9.0F, -9.0F)
                curveTo(12.05F, 2.22F, 11.55F, 2.0F, 11.0F, 2.0F)
                horizontalLineTo(4.0F)
                curveToRelative(-1.1F, 0.0F, -2.0F, 0.9F, -2.0F, 2.0F)
                verticalLineToRelative(7.0F)
                curveToRelative(0.0F, 0.55F, 0.22F, 1.05F, 0.59F, 1.42F)
                lineToRelative(9.0F, 9.0F)
                curveToRelative(0.36F, 0.36F, 0.86F, 0.58F, 1.41F, 0.58F)
                curveToRelative(0.55F, 0.0F, 1.05F, -0.22F, 1.41F, -0.59F)
                lineToRelative(7.0F, -7.0F)
                curveToRelative(0.37F, -0.36F, 0.59F, -0.86F, 0.59F, -1.41F)
                curveToRelative(0.0F, -0.55F, -0.23F, -1.06F, -0.59F, -1.42F)
                close()

                moveTo(5.5F, 7.0F)
                curveTo(4.67F, 7.0F, 4.0F, 6.33F, 4.0F, 5.5F)
                reflectiveCurveTo(4.67F, 4.0F, 5.5F, 4.0F)
                reflectiveCurveTo(7.0F, 4.67F, 7.0F, 5.5F)
                reflectiveCurveTo(6.33F, 7.0F, 5.5F, 7.0F)
                close()
            }
        }
        return _tag!!
    }
private var _tag: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.Tag, contentDescription = null)