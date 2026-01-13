package com.whoppah.common.compose.icons.action

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Action.Cancel: ImageVector
    //get() = Icons.Filled.PhotoCancel
    get() {
        if (_cancel != null) return _cancel!!
        _cancel = materialIcon(name = "Action.Cancel") {
            materialPath {
                moveTo(12.0F, 2.0F)
                curveTo(6.47F, 2.0F, 2.0F, 6.47F, 2.0F, 12.0F)
                reflectiveCurveToRelative(4.47F, 10.0F, 10.0F, 10.0F)
                reflectiveCurveToRelative(10.0F, -4.47F, 10.0F, -10.0F)
                reflectiveCurveTo(17.53F, 2.0F, 12.0F, 2.0F)
                close()

                moveTo(17.0F, 15.59F)
                lineTo(15.59F, 17.0F)
                lineTo(12.0F, 13.41F)
                lineTo(8.41F, 17.0F)
                lineTo(7.0F, 15.59F)
                lineTo(10.59F, 12.0F)
                lineTo(7.0F, 8.41F)
                lineTo(8.41F, 7.0F)
                lineTo(12.0F, 10.59F)
                lineTo(15.59F, 7.0F)
                lineTo(17.0F, 8.41F)
                lineTo(13.41F, 12.0F)
                lineTo(17.0F, 15.59F)
                close()
            }
        }
        return _cancel!!
    }
private var _cancel: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Action.Cancel, contentDescription = null)