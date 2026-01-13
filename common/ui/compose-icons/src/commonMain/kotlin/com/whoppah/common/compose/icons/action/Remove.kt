package com.whoppah.common.compose.icons.action

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Action.Remove: ImageVector
    get() {
        if (_remove != null) return _remove!!
        _remove = materialIcon(name = "Action.Remove") {
            materialPath {
                moveTo(18.0F, 13.0F)
                horizontalLineTo(6.0F)
                curveToRelative(-0.55F, 0.0F, -1.0F, -0.45F, -1.0F, -1.0F)
                reflectiveCurveToRelative(0.45F, -1.0F, 1.0F, -1.0F)
                horizontalLineToRelative(12.0F)
                curveToRelative(0.55F, 0.0F, 1.0F, 0.45F, 1.0F, 1.0F)
                reflectiveCurveToRelative(-0.45F, 1.0F, -1.0F, 1.0F)
                close()
            }
        }
        return _remove!!
    }
private var _remove: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Action.Remove, contentDescription = null)