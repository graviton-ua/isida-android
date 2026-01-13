package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.TagOutline: ImageVector
    get() {
        if (_tagOutline != null) return _tagOutline!!
        _tagOutline = materialIcon(name = "Theme.TagOutline") {
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
                reflectiveCurveToRelative(1.05F, -0.22F, 1.41F, -0.59F)
                lineToRelative(7.0F, -7.0F)
                curveToRelative(0.37F, -0.36F, 0.59F, -0.86F, 0.59F, -1.41F)
                reflectiveCurveToRelative(-0.23F, -1.06F, -0.59F, -1.42F)
                close()

                moveTo(13.0F, 20.01F)
                lineTo(4.0F, 11.0F)
                verticalLineTo(4.0F)
                horizontalLineToRelative(7.0F)
                verticalLineToRelative(-0.01F)
                lineToRelative(9.0F, 9.0F)
                lineToRelative(-7.0F, 7.02F)
                close()
            }
            materialPath {
                moveTo(6.5f, 6.5f)
                moveToRelative(-1.5f, 0.0f)
                arcToRelative(1.5f, 1.5f, 0.0f, isMoreThanHalf = true, isPositiveArc = true, dx1 = 3.0f, dy1 = 0.0f)
                arcToRelative(1.5f, 1.5f, 0.0f, isMoreThanHalf = true, isPositiveArc = true, dx1 = -3.0f, dy1 = 0.0f)
                close()
            }
        }
        return _tagOutline!!
    }
private var _tagOutline: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.TagOutline, contentDescription = null)