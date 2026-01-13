package com.whoppah.common.compose.icons.state

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.State.AllInclusive: ImageVector
    get() {
        if (_allInclusive != null) return _allInclusive!!
        _allInclusive = materialIcon(name = "State.AllInclusive") {
            materialPath {
                moveTo(18.6F, 6.62F)
                curveToRelative(-1.44F, 0.0F, -2.8F, 0.56F, -3.77F, 1.53F)
                lineTo(12.0F, 10.66F)
                lineTo(10.48F, 12.0F)
                horizontalLineToRelative(0.01F)
                lineTo(7.8F, 14.39F)
                curveToRelative(-0.64F, 0.64F, -1.49F, 0.99F, -2.4F, 0.99F)
                curveToRelative(-1.87F, 0.0F, -3.39F, -1.51F, -3.39F, -3.38F)
                reflectiveCurveTo(3.53F, 8.62F, 5.4F, 8.62F)
                curveToRelative(0.91F, 0.0F, 1.76F, 0.35F, 2.44F, 1.03F)
                lineToRelative(1.13F, 1.0F)
                lineToRelative(1.51F, -1.34F)
                lineTo(9.22F, 8.2F)
                curveTo(8.2F, 7.18F, 6.84F, 6.62F, 5.4F, 6.62F)
                curveTo(2.42F, 6.62F, 0.0F, 9.04F, 0.0F, 12.0F)
                reflectiveCurveToRelative(2.42F, 5.38F, 5.4F, 5.38F)
                curveToRelative(1.44F, 0.0F, 2.8F, -0.56F, 3.77F, -1.53F)
                lineToRelative(2.83F, -2.5F)
                lineToRelative(0.01F, 0.01F)
                lineTo(13.52F, 12.0F)
                horizontalLineToRelative(-0.01F)
                lineToRelative(2.69F, -2.39F)
                curveToRelative(0.64F, -0.64F, 1.49F, -0.99F, 2.4F, -0.99F)
                curveToRelative(1.87F, 0.0F, 3.39F, 1.51F, 3.39F, 3.38F)
                reflectiveCurveToRelative(-1.52F, 3.38F, -3.39F, 3.38F)
                curveToRelative(-0.9F, 0.0F, -1.76F, -0.35F, -2.44F, -1.03F)
                lineToRelative(-1.14F, -1.01F)
                lineToRelative(-1.51F, 1.34F)
                lineToRelative(1.27F, 1.12F)
                curveToRelative(1.02F, 1.01F, 2.37F, 1.57F, 3.82F, 1.57F)
                curveToRelative(2.98F, 0.0F, 5.4F, -2.41F, 5.4F, -5.38F)
                reflectiveCurveToRelative(-2.42F, -5.37F, -5.4F, -5.37F)
                close()
            }
        }
        return _allInclusive!!
    }
private var _allInclusive: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.State.AllInclusive, contentDescription = null)