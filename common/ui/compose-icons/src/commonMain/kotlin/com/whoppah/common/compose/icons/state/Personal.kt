package com.whoppah.common.compose.icons.state

import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whoppah.common.compose.icons.WhIcons
import com.whoppah.common.compose.icons.whoppahIcon

val WhIcons.State.Personal: ImageVector
    get() {
        if (_personal != null) return _personal!!
        _personal = whoppahIcon(
            name = "State.Personal",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 22f,
            viewportHeight = 22f,
        ) {
            materialPath {
                moveTo(8.0F, 10.75F)
                curveTo(7.31F, 10.75F, 6.75F, 11.31F, 6.75F, 12.0F)
                curveTo(6.75F, 12.69F, 7.31F, 13.25F, 8.0F, 13.25F)
                curveTo(8.69F, 13.25F, 9.25F, 12.69F, 9.25F, 12.0F)
                curveTo(9.25F, 11.31F, 8.69F, 10.75F, 8.0F, 10.75F)

                moveTo(14.0F, 10.75F)
                curveTo(13.31F, 10.75F, 12.75F, 11.31F, 12.75F, 12.0F)
                curveTo(12.75F, 12.69F, 13.31F, 13.25F, 14.0F, 13.25F)
                curveTo(14.69F, 13.25F, 15.25F, 12.69F, 15.25F, 12.0F)
                curveTo(15.25F, 11.31F, 14.69F, 10.75F, 14.0F, 10.75F)

                moveTo(11.0F, 1.0F)
                curveTo(5.48F, 1.0F, 1.0F, 5.48F, 1.0F, 11.0F)
                curveTo(1.0F, 16.52F, 5.48F, 21.0F, 11.0F, 21.0F)
                curveTo(16.52F, 21.0F, 21.0F, 16.52F, 21.0F, 11.0F)
                curveTo(21.0F, 5.48F, 16.52F, 1.0F, 11.0F, 1.0F)

                moveTo(11.0F, 19.0F)
                curveTo(6.59F, 19.0F, 3.0F, 15.41F, 3.0F, 11.0F)
                curveTo(3.0F, 10.71F, 3.02F, 10.42F, 3.05F, 10.14F)
                curveTo(5.41F, 9.09F, 7.28F, 7.16F, 8.26F, 4.77F)
                curveTo(10.07F, 7.33F, 13.05F, 9.0F, 16.42F, 9.0F)
                curveTo(17.2F, 9.0F, 17.95F, 8.91F, 18.67F, 8.74F)
                curveTo(18.88F, 9.45F, 19.0F, 10.21F, 19.0F, 11.0F)
                curveTo(19.0F, 15.41F, 15.41F, 19.0F, 11.0F, 19.0F)
                close()
            }
        }
        return _personal!!
    }
private var _personal: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.State.Personal, contentDescription = null)