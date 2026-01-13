package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons
import com.whoppah.common.compose.icons.whoppahPath

val WhIcons.Theme.Art: ImageVector
    get() {
        if (_art != null) return _art!!
        _art = materialIcon(name = "Theme.Art") {
            materialPath {
                moveTo(10.51F, 9.04F)
                curveTo(10.31F, 6.84F, 11.8F, 5.42F, 13.21F, 5.19F)
                curveTo(14.89F, 4.9F, 16.56F, 6.15F, 16.87F, 7.98F)
                curveTo(17.19F, 9.85F, 16.04F, 11.69F, 14.33F, 12.01F)
                curveTo(13.99F, 12.07F, 13.62F, 12.01F, 13.26F, 12.0F)
                lineTo(13.27F, 12.02F)
                verticalLineTo(9.06F)
                curveTo(13.09F, 9.06F, 12.94F, 9.06F, 12.79F, 9.06F)
                curveTo(12.03F, 9.05F, 11.26F, 9.04F, 10.49F, 9.03F)
                lineTo(10.51F, 9.04F)
                lineTo(10.51F, 9.04F)

                moveTo(9.32F, 15.17F)
                curveTo(9.47F, 15.15F, 9.62F, 15.12F, 9.77F, 15.12F)
                curveTo(10.82F, 15.11F, 11.87F, 15.12F, 12.92F, 15.11F)
                curveTo(13.06F, 15.11F, 13.19F, 15.09F, 13.37F, 15.08F)
                curveTo(14.14F, 16.31F, 14.93F, 17.56F, 15.75F, 18.86F)
                horizontalLineTo(7.08F)
                curveTo(7.16F, 18.71F, 7.22F, 18.6F, 7.29F, 18.48F)
                curveTo(7.92F, 17.49F, 8.55F, 16.49F, 9.18F, 15.49F)
                curveTo(9.24F, 15.39F, 9.28F, 15.28F, 9.32F, 15.17F)
                lineTo(9.32F, 15.17F)

                moveTo(9.32F, 15.17F)
                curveTo(8.94F, 15.15F, 8.56F, 15.13F, 8.17F, 15.12F)
                curveTo(8.02F, 15.11F, 7.87F, 15.12F, 7.7F, 15.12F)
                verticalLineTo(9.04F)
                curveTo(8.63F, 9.04F, 9.57F, 9.04F, 10.51F, 9.04F)
                lineTo(10.5F, 9.03F)
                curveTo(10.52F, 9.17F, 10.55F, 9.3F, 10.58F, 9.44F)
                curveTo(10.88F, 10.82F, 12.09F, 11.98F, 13.27F, 12.02F)
                lineTo(13.26F, 12.0F)
                curveTo(13.26F, 12.93F, 13.26F, 13.85F, 13.26F, 14.91F)
                curveTo(12.61F, 13.88F, 12.02F, 12.95F, 11.41F, 11.97F)
                curveTo(10.79F, 12.95F, 10.21F, 13.88F, 9.62F, 14.8F)
                curveTo(9.53F, 14.94F, 9.42F, 15.05F, 9.32F, 15.17F)
                lineTo(9.33F, 15.17F)
                lineTo(9.32F, 15.17F)
                close()
            }
            whoppahPath(fill = null, stroke = SolidColor(Color.Black)) {
                moveTo(19.0F, 3.0F)
                horizontalLineTo(5.0F)
                verticalLineTo(21.0F)
                horizontalLineTo(19.0F)
                verticalLineTo(3.0F)
                close()
            }
        }
        return _art!!
    }
private var _art: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.Art, contentDescription = null)