package com.whoppah.common.compose.icons.state

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.State.Visibility: ImageVector
    get() {
        if (_visibility != null) return _visibility!!
        _visibility = materialIcon(name = "State.Visibility") {
            materialPath {
                moveTo(12.0F, 4.5F)
                curveTo(7.0F, 4.5F, 2.73F, 7.61F, 1.0F, 12.0F)
                curveToRelative(1.73F, 4.39F, 6.0F, 7.5F, 11.0F, 7.5F)
                reflectiveCurveToRelative(9.27F, -3.11F, 11.0F, -7.5F)
                curveToRelative(-1.73F, -4.39F, -6.0F, -7.5F, -11.0F, -7.5F)
                close()

                moveTo(12.0F, 17.0F)
                curveToRelative(-2.76F, 0.0F, -5.0F, -2.24F, -5.0F, -5.0F)
                reflectiveCurveToRelative(2.24F, -5.0F, 5.0F, -5.0F)
                reflectiveCurveToRelative(5.0F, 2.24F, 5.0F, 5.0F)
                reflectiveCurveToRelative(-2.24F, 5.0F, -5.0F, 5.0F)
                close()

                moveTo(12.0F, 9.0F)
                curveToRelative(-1.66F, 0.0F, -3.0F, 1.34F, -3.0F, 3.0F)
                reflectiveCurveToRelative(1.34F, 3.0F, 3.0F, 3.0F)
                reflectiveCurveToRelative(3.0F, -1.34F, 3.0F, -3.0F)
                reflectiveCurveToRelative(-1.34F, -3.0F, -3.0F, -3.0F)
                close()
            }
        }
        return _visibility!!
    }
private var _visibility: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.State.Visibility, contentDescription = null)