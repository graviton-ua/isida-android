package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.TipsAndUpdates: ImageVector
    get() {
        if (_tipsAndUpdates != null) return _tipsAndUpdates!!
        _tipsAndUpdates = materialIcon(name = "Theme.TipsAndUpdates") {
            materialPath {
                moveTo(7.0F, 20.0F)
                horizontalLineToRelative(4.0F)
                curveToRelative(0.0F, 1.1F, -0.9F, 2.0F, -2.0F, 2.0F)
                reflectiveCurveTo(7.0F, 21.1F, 7.0F, 20.0F)
                close()

                moveTo(5.0F, 19.0F)
                horizontalLineToRelative(8.0F)
                verticalLineToRelative(-2.0F)
                horizontalLineTo(5.0F)
                verticalLineTo(19.0F)
                close()

                moveTo(16.5F, 9.5F)
                curveToRelative(0.0F, 3.82F, -2.66F, 5.86F, -3.77F, 6.5F)
                horizontalLineTo(5.27F)
                curveTo(4.16F, 15.36F, 1.5F, 13.32F, 1.5F, 9.5F)
                curveTo(1.5F, 5.36F, 4.86F, 2.0F, 9.0F, 2.0F)
                reflectiveCurveTo(16.5F, 5.36F, 16.5F, 9.5F)
                close()

                moveTo(21.37F, 7.37F)
                lineTo(20.0F, 8.0F)
                lineToRelative(1.37F, 0.63F)
                lineTo(22.0F, 10.0F)
                lineToRelative(0.63F, -1.37F)
                lineTo(24.0F, 8.0F)
                lineToRelative(-1.37F, -0.63F)
                lineTo(22.0F, 6.0F)
                lineTo(21.37F, 7.37F)
                close()

                moveTo(19.0F, 6.0F)
                lineToRelative(0.94F, -2.06F)
                lineTo(22.0F, 3.0F)
                lineToRelative(-2.06F, -0.94F)
                lineTo(19.0F, 0.0F)
                lineToRelative(-0.94F, 2.06F)
                lineTo(16.0F, 3.0F)
                lineToRelative(2.06F, 0.94F)
                lineTo(19.0F, 6.0F)
                close()
            }
        }
        return _tipsAndUpdates!!
    }
private var _tipsAndUpdates: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.TipsAndUpdates, contentDescription = null)