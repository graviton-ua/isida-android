package com.whoppah.common.compose.icons.action

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Action.Cart: ImageVector
    get() {
        if (_cart != null) return _cart!!
        _cart = materialIcon(name = "Action.Cart") {
            materialPath {
                moveTo(19.0F, 20.4026F)
                curveTo(19.0F, 21.5126F, 18.11F, 22.4026F, 17.0F, 22.4026F)
                curveTo(15.89F, 22.4026F, 15.0F, 21.5026F, 15.0F, 20.4026F)
                curveTo(15.0F, 19.2926F, 15.89F, 18.4026F, 17.0F, 18.4026F)
                curveTo(18.11F, 18.4026F, 19.0F, 19.3026F, 19.0F, 20.4026F)

                moveTo(7.0F, 18.4026F)
                curveTo(5.89F, 18.4026F, 5.0F, 19.2926F, 5.0F, 20.4026F)
                curveTo(5.0F, 21.5026F, 5.89F, 22.4026F, 7.0F, 22.4026F)
                curveTo(8.11F, 22.4026F, 9.0F, 21.5126F, 9.0F, 20.4026F)
                curveTo(9.0F, 19.2926F, 8.11F, 18.4026F, 7.0F, 18.4026F)

                moveTo(7.2F, 15.0326F)
                lineTo(7.17F, 15.1526F)
                curveTo(7.17F, 15.2926F, 7.28F, 15.4026F, 7.42F, 15.4026F)
                horizontalLineTo(19.0F)
                verticalLineTo(17.4026F)
                horizontalLineTo(7.0F)
                curveTo(5.89F, 17.4026F, 5.0F, 16.5026F, 5.0F, 15.4026F)
                curveTo(5.0F, 15.0526F, 5.09F, 14.7226F, 5.24F, 14.4426F)
                lineTo(6.6F, 11.9926F)
                lineTo(3.0F, 4.40259F)
                horizontalLineTo(1.0F)
                verticalLineTo(2.40259F)
                horizontalLineTo(4.27F)
                lineTo(5.21F, 4.40259F)
                horizontalLineTo(20.0F)
                curveTo(20.55F, 4.40259F, 21.0F, 4.85259F, 21.0F, 5.40259F)
                curveTo(21.0F, 5.57259F, 20.95F, 5.74259F, 20.88F, 5.90259F)
                lineTo(17.3F, 12.3726F)
                curveTo(16.96F, 12.9826F, 16.3F, 13.4026F, 15.55F, 13.4026F)
                horizontalLineTo(8.1F)
                lineTo(7.2F, 15.0326F)

                moveTo(8.5F, 11.4026F)
                horizontalLineTo(10.0F)
                verticalLineTo(9.40259F)
                horizontalLineTo(7.56F)
                lineTo(8.5F, 11.4026F)

                moveTo(11.0F, 9.40259F)
                verticalLineTo(11.4026F)
                horizontalLineTo(14.0F)
                verticalLineTo(9.40259F)
                horizontalLineTo(11.0F)

                moveTo(14.0F, 8.40259F)
                verticalLineTo(6.40259F)
                horizontalLineTo(11.0F)
                verticalLineTo(8.40259F)
                horizontalLineTo(14.0F)

                moveTo(17.11F, 9.40259F)
                horizontalLineTo(15.0F)
                verticalLineTo(11.4026F)
                horizontalLineTo(16.0F)
                lineTo(17.11F, 9.40259F)

                moveTo(18.78F, 6.40259F)
                horizontalLineTo(15.0F)
                verticalLineTo(8.40259F)
                horizontalLineTo(17.67F)
                lineTo(18.78F, 6.40259F)

                moveTo(6.14F, 6.40259F)
                lineTo(7.08F, 8.40259F)
                horizontalLineTo(10.0F)
                verticalLineTo(6.40259F)
                horizontalLineTo(6.14F)
                close()
            }
        }
        return _cart!!
    }
private var _cart: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Action.Cart, contentDescription = null)