package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.PartyPopper: ImageVector
    get() {
        if (_partyPopper != null) return _partyPopper!!
        _partyPopper = materialIcon(name = "Theme.PartyPopper") {
            materialPath {
                moveTo(2.0F, 22.0F)
                lineToRelative(14.0F, -5.0F)
                lineToRelative(-9.0F, -9.0F)
                close()
            }
            materialPath {
                moveTo(14.53f, 12.53f)
                lineToRelative(5.59f, -5.59f)
                curveToRelative(0.49f, -0.49f, 1.28f, -0.49f, 1.77f, 0.0f)
                lineToRelative(0.59f, 0.59f)
                lineToRelative(1.06f, -1.06f)
                lineToRelative(-0.59f, -0.59f)
                curveToRelative(-1.07f, -1.07f, -2.82f, -1.07f, -3.89f, 0.0f)
                lineToRelative(-5.59f, 5.59f)
                lineTo(14.53f, 12.53f)
                close()
            }
            materialPath {
                moveTo(10.06f, 6.88f)
                lineTo(9.47f, 7.47f)
                lineToRelative(1.06f, 1.06f)
                lineToRelative(0.59f, -0.59f)
                curveToRelative(1.07f, -1.07f, 1.07f, -2.82f, 0.0f, -3.89f)
                lineToRelative(-0.59f, -0.59f)
                lineTo(9.47f, 4.53f)
                lineToRelative(0.59f, 0.59f)
                curveTo(10.54f, 5.6f, 10.54f, 6.4f, 10.06f, 6.88f)
                close()
            }
            materialPath {
                moveTo(17.06F, 11.88F)
                lineToRelative(-1.59F, 1.59F)
                lineToRelative(1.06F, 1.06F)
                lineToRelative(1.59F, -1.59F)
                curveToRelative(0.49F, -0.49F, 1.28F, -0.49F, 1.77F, 0.0F)
                lineToRelative(1.61F, 1.61F)
                lineToRelative(1.06F, -1.06F)
                lineToRelative(-1.61F, -1.61F)
                curveTo(19.87F, 10.81F, 18.13F, 10.81F, 17.06F, 11.88F)
                close()
            }
            materialPath {
                moveTo(15.06f, 5.88f)
                lineToRelative(-3.59f, 3.59f)
                lineToRelative(1.06f, 1.06f)
                lineToRelative(3.59f, -3.59f)
                curveToRelative(1.07f, -1.07f, 1.07f, -2.82f, 0.0f, -3.89f)
                lineToRelative(-1.59f, -1.59f)
                lineToRelative(-1.06f, 1.06f)
                lineToRelative(1.59f, 1.59f)
                curveTo(15.54f, 4.6f, 15.54f, 5.4f, 15.06f, 5.88f)
                close()
            }
        }
        return _partyPopper!!
    }
private var _partyPopper: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.PartyPopper, contentDescription = null)