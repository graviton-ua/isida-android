package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.Texture: ImageVector
    get() {
        if (_texture != null) return _texture!!
        _texture = materialIcon(name = "Theme.Texture") {
            materialPath {
                moveTo(19.51F, 3.08F)
                lineTo(3.08F, 19.51F)
                curveToRelative(0.09F, 0.34F, 0.27F, 0.65F, 0.51F, 0.9F)
                curveToRelative(0.25F, 0.24F, 0.56F, 0.42F, 0.9F, 0.51F)
                lineTo(20.93F, 4.49F)
                curveToRelative(-0.19F, -0.69F, -0.73F, -1.23F, -1.42F, -1.41F)
                close()

                moveTo(11.88F, 3.0F)
                lineTo(3.0F, 11.88F)
                verticalLineToRelative(2.83F)
                lineTo(14.71F, 3.0F)
                horizontalLineToRelative(-2.83F)
                close()

                moveTo(5.0F, 3.0F)
                curveToRelative(-1.1F, 0.0F, -2.0F, 0.9F, -2.0F, 2.0F)
                verticalLineToRelative(2.0F)
                lineToRelative(4.0F, -4.0F)
                lineTo(5.0F, 3.0F)
                close()

                moveTo(19.0F, 21.0F)
                curveToRelative(0.55F, 0.0F, 1.05F, -0.22F, 1.41F, -0.59F)
                curveToRelative(0.37F, -0.36F, 0.59F, -0.86F, 0.59F, -1.41F)
                verticalLineToRelative(-2.0F)
                lineToRelative(-4.0F, 4.0F)
                horizontalLineToRelative(2.0F)
                close()

                moveTo(9.29F, 21.0F)
                horizontalLineToRelative(2.83F)
                lineTo(21.0F, 12.12F)
                lineTo(21.0F, 9.29F)
                lineTo(9.29F, 21.0F)
                close()
            }
        }
        return _texture!!
    }
private var _texture: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.Texture, contentDescription = null)