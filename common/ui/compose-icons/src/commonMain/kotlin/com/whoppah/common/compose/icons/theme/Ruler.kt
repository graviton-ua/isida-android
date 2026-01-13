package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.Ruler: ImageVector
    get() {
        if (_ruler != null) return _ruler!!
        _ruler = materialIcon(name = "Theme.Ruler") {
            materialPath {
                moveTo(1.39F, 18.36F)
                lineTo(3.16F, 16.6F)
                lineTo(4.58F, 18.0F)
                lineTo(5.64F, 16.95F)
                lineTo(4.22F, 15.54F)
                lineTo(5.64F, 14.12F)
                lineTo(8.11F, 16.6F)
                lineTo(9.17F, 15.54F)
                lineTo(6.7F, 13.06F)
                lineTo(8.11F, 11.65F)
                lineTo(9.53F, 13.06F)
                lineTo(10.59F, 12.0F)
                lineTo(9.17F, 10.59F)
                lineTo(10.59F, 9.17F)
                lineTo(13.06F, 11.65F)
                lineTo(14.12F, 10.59F)
                lineTo(11.65F, 8.11F)
                lineTo(13.06F, 6.7F)
                lineTo(14.47F, 8.11F)
                lineTo(15.54F, 7.05F)
                lineTo(14.12F, 5.64F)
                lineTo(15.54F, 4.22F)
                lineTo(18.0F, 6.7F)
                lineTo(19.07F, 5.64F)
                lineTo(16.6F, 3.16F)
                lineTo(18.36F, 1.39F)
                lineTo(22.61F, 5.64F)
                lineTo(5.64F, 22.61F)
                lineTo(1.39F, 18.36F)
                close()
            }
        }
        return _ruler!!
    }
private var _ruler: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.Ruler, contentDescription = null)