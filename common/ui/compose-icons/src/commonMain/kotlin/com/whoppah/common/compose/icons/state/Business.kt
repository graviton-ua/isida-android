package com.whoppah.common.compose.icons.state

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.State.Business: ImageVector
    get() {
        if (_business != null) return _business!!
        _business = materialIcon(name = "State.Business") {
            materialPath {
                moveTo(10.0F, 2.0F)
                horizontalLineTo(14.0F)
                curveTo(14.5304F, 2.0F, 15.0391F, 2.21071F, 15.4142F, 2.58579F)
                curveTo(15.7893F, 2.96086F, 16.0F, 3.46957F, 16.0F, 4.0F)
                verticalLineTo(6.0F)
                horizontalLineTo(20.0F)
                curveTo(20.5304F, 6.0F, 21.0391F, 6.21071F, 21.4142F, 6.58579F)
                curveTo(21.7893F, 6.96086F, 22.0F, 7.46957F, 22.0F, 8.0F)
                verticalLineTo(19.0F)
                curveTo(22.0F, 19.5304F, 21.7893F, 20.0391F, 21.4142F, 20.4142F)
                curveTo(21.0391F, 20.7893F, 20.5304F, 21.0F, 20.0F, 21.0F)
                horizontalLineTo(4.0F)
                curveTo(2.89F, 21.0F, 2.0F, 20.1F, 2.0F, 19.0F)
                verticalLineTo(8.0F)
                curveTo(2.0F, 6.89F, 2.89F, 6.0F, 4.0F, 6.0F)
                horizontalLineTo(8.0F)
                verticalLineTo(4.0F)
                curveTo(8.0F, 2.89F, 8.89F, 2.0F, 10.0F, 2.0F)

                moveTo(14.0F, 6.0F)
                verticalLineTo(4.0F)
                horizontalLineTo(10.0F)
                verticalLineTo(6.0F)
                horizontalLineTo(14.0F)
                close()
            }
        }
        return _business!!
    }
private var _business: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.State.Business, contentDescription = null)