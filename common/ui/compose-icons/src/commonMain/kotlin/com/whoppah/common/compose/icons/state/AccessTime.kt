package com.whoppah.common.compose.icons.state

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.State.AccessTime: ImageVector
    get() {
        if (_accessTime != null) return _accessTime!!
        _accessTime = materialIcon(name = "State.AccessTime") {
            materialPath {
                moveTo(11.99F, 2.0F)
                curveTo(6.47F, 2.0F, 2.0F, 6.48F, 2.0F, 12.0F)
                reflectiveCurveToRelative(4.47F, 10.0F, 9.99F, 10.0F)
                curveTo(17.52F, 22.0F, 22.0F, 17.52F, 22.0F, 12.0F)
                reflectiveCurveTo(17.52F, 2.0F, 11.99F, 2.0F)
                close()

                moveTo(12.0F, 20.0F)
                curveToRelative(-4.42F, 0.0F, -8.0F, -3.58F, -8.0F, -8.0F)
                reflectiveCurveToRelative(3.58F, -8.0F, 8.0F, -8.0F)
                reflectiveCurveToRelative(8.0F, 3.58F, 8.0F, 8.0F)
                reflectiveCurveToRelative(-3.58F, 8.0F, -8.0F, 8.0F)
                close()
            }
        }
        return _accessTime!!
    }
private var _accessTime: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.State.AccessTime, contentDescription = null)