package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.HandWave: ImageVector
    get() {
        if (_wavingHand != null) return _wavingHand!!
        _wavingHand = materialIcon(name = "Theme.WavingHand") {
            materialPath {
                moveTo(7.03F, 4.95F)
                lineTo(3.49F, 8.49F)
                curveToRelative(-3.32F, 3.32F, -3.32F, 8.7F, 0.0F, 12.02F)
                reflectiveCurveToRelative(8.7F, 3.32F, 12.02F, 0.0F)
                lineToRelative(6.01F, -6.01F)
                curveToRelative(0.97F, -0.97F, 0.97F, -2.56F, 0.0F, -3.54F)
                curveToRelative(-0.12F, -0.12F, -0.25F, -0.23F, -0.39F, -0.32F)
                lineToRelative(0.39F, -0.39F)
                curveToRelative(0.97F, -0.97F, 0.97F, -2.56F, 0.0F, -3.54F)
                curveToRelative(-0.16F, -0.16F, -0.35F, -0.3F, -0.54F, -0.41F)
                curveToRelative(0.4F, -0.92F, 0.23F, -2.02F, -0.52F, -2.77F)
                curveToRelative(-0.87F, -0.87F, -2.22F, -0.96F, -3.2F, -0.28F)
                curveToRelative(-0.1F, -0.15F, -0.21F, -0.29F, -0.34F, -0.42F)
                curveToRelative(-0.97F, -0.97F, -2.56F, -0.97F, -3.54F, 0.0F)
                lineToRelative(-2.51F, 2.51F)
                curveToRelative(-0.09F, -0.14F, -0.2F, -0.27F, -0.32F, -0.39F)
                curveTo(9.58F, 3.98F, 8.0F, 3.98F, 7.03F, 4.95F)
                close()

                moveTo(8.44F, 6.37F)
                curveToRelative(0.2F, -0.2F, 0.51F, -0.2F, 0.71F, 0.0F)
                curveToRelative(0.2F, 0.2F, 0.2F, 0.51F, 0.0F, 0.71F)
                lineToRelative(-3.18F, 3.18F)
                curveToRelative(1.17F, 1.17F, 1.17F, 3.07F, 0.0F, 4.24F)
                lineToRelative(1.41F, 1.41F)
                curveToRelative(1.45F, -1.45F, 1.82F, -3.57F, 1.12F, -5.36F)
                lineToRelative(6.3F, -6.3F)
                curveToRelative(0.2F, -0.2F, 0.51F, -0.2F, 0.71F, 0.0F)
                reflectiveCurveToRelative(0.2F, 0.51F, 0.0F, 0.71F)
                lineToRelative(-4.6F, 4.6F)
                lineToRelative(1.41F, 1.41F)
                lineToRelative(6.01F, -6.01F)
                curveToRelative(0.2F, -0.2F, 0.51F, -0.2F, 0.71F, 0.0F)
                curveToRelative(0.2F, 0.2F, 0.2F, 0.51F, 0.0F, 0.71F)
                lineToRelative(-6.01F, 6.01F)
                lineToRelative(1.41F, 1.41F)
                lineToRelative(4.95F, -4.95F)
                curveToRelative(0.2F, -0.2F, 0.51F, -0.2F, 0.71F, 0.0F)
                curveToRelative(0.2F, 0.2F, 0.2F, 0.51F, 0.0F, 0.71F)
                lineToRelative(-5.66F, 5.66F)
                lineToRelative(1.41F, 1.41F)
                lineToRelative(3.54F, -3.54F)
                curveToRelative(0.2F, -0.2F, 0.51F, -0.2F, 0.71F, 0.0F)
                curveToRelative(0.2F, 0.2F, 0.2F, 0.51F, 0.0F, 0.71F)
                lineTo(14.1F, 19.1F)
                curveToRelative(-2.54F, 2.54F, -6.65F, 2.54F, -9.19F, 0.0F)
                reflectiveCurveToRelative(-2.54F, -6.65F, 0.0F, -9.19F)
                lineTo(8.44F, 6.37F)
                close()

                moveTo(23.0F, 17.0F)
                curveToRelative(0.0F, 3.31F, -2.69F, 6.0F, -6.0F, 6.0F)
                verticalLineToRelative(-1.5F)
                curveToRelative(2.48F, 0.0F, 4.5F, -2.02F, 4.5F, -4.5F)
                horizontalLineTo(23.0F)
                close()

                moveTo(1.0F, 7.0F)
                curveToRelative(0.0F, -3.31F, 2.69F, -6.0F, 6.0F, -6.0F)
                verticalLineToRelative(1.5F)
                curveTo(4.52F, 2.5F, 2.5F, 4.52F, 2.5F, 7.0F)
                horizontalLineTo(1.0F)
                close()
            }
        }
        return _wavingHand!!
    }
private var _wavingHand: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.HandWave, contentDescription = null)