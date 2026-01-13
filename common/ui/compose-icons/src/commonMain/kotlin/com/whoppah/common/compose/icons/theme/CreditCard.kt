package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.CreditCard: ImageVector
    get() {
        if (_creditCard != null) return _creditCard!!
        _creditCard = materialIcon(name = "Theme.CreditCard") {
            materialPath {
                moveTo(20.0F, 8.66309F)
                horizontalLineTo(4.0F)
                verticalLineTo(6.66309F)
                horizontalLineTo(20.0F)
                moveTo(20.0F, 18.6631F)
                horizontalLineTo(4.0F)
                verticalLineTo(12.6631F)
                horizontalLineTo(20.0F)
                moveTo(20.0F, 4.66309F)
                horizontalLineTo(4.0F)
                curveTo(2.89F, 4.66309F, 2.0F, 5.55309F, 2.0F, 6.66309F)
                verticalLineTo(18.6631F)
                curveTo(2.0F, 19.1935F, 2.21071F, 19.7022F, 2.58579F, 20.0773F)
                curveTo(2.96086F, 20.4524F, 3.46957F, 20.6631F, 4.0F, 20.6631F)
                horizontalLineTo(20.0F)
                curveTo(20.5304F, 20.6631F, 21.0391F, 20.4524F, 21.4142F, 20.0773F)
                curveTo(21.7893F, 19.7022F, 22.0F, 19.1935F, 22.0F, 18.6631F)
                verticalLineTo(6.66309F)
                curveTo(22.0F, 5.55309F, 21.1F, 4.66309F, 20.0F, 4.66309F)
                close()
            }
        }
        return _creditCard!!
    }
private var _creditCard: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.CreditCard, contentDescription = null)