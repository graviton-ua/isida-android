package com.whoppah.common.compose.icons.theme

import androidx.compose.foundation.Image
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.Money: ImageVector
    get() {
        if (_money != null) return _money!!
        _money = materialIcon(name = "Theme.Money") {
            materialPath {
                verticalLineToRelative(0.0F)
                moveTo(5.63636F, 4.40259F)
                horizontalLineTo(22.0F)
                verticalLineTo(16.4026F)
                horizontalLineTo(5.63636F)
                verticalLineTo(4.40259F)

                moveTo(13.8182F, 7.40259F)
                curveTo(14.5415F, 7.40259F, 15.2352F, 7.71866F, 15.7467F, 8.28127F)
                curveTo(16.2581F, 8.84388F, 16.5455F, 9.60694F, 16.5455F, 10.4026F)
                curveTo(16.5455F, 11.1982F, 16.2581F, 11.9613F, 15.7467F, 12.5239F)
                curveTo(15.2352F, 13.0865F, 14.5415F, 13.4026F, 13.8182F, 13.4026F)
                curveTo(13.0949F, 13.4026F, 12.4012F, 13.0865F, 11.8897F, 12.5239F)
                curveTo(11.3782F, 11.9613F, 11.0909F, 11.1982F, 11.0909F, 10.4026F)
                curveTo(11.0909F, 9.60694F, 11.3782F, 8.84388F, 11.8897F, 8.28127F)
                curveTo(12.4012F, 7.71866F, 13.0949F, 7.40259F, 13.8182F, 7.40259F)

                moveTo(9.27273F, 6.40259F)
                curveTo(9.27273F, 6.93302F, 9.08117F, 7.44173F, 8.74019F, 7.8168F)
                curveTo(8.39922F, 8.19187F, 7.93676F, 8.40259F, 7.45455F, 8.40259F)
                verticalLineTo(12.4026F)
                curveTo(7.93676F, 12.4026F, 8.39922F, 12.6133F, 8.74019F, 12.9884F)
                curveTo(9.08117F, 13.3634F, 9.27273F, 13.8722F, 9.27273F, 14.4026F)
                horizontalLineTo(18.3636F)
                curveTo(18.3636F, 13.8722F, 18.5552F, 13.3634F, 18.8962F, 12.9884F)
                curveTo(19.2371F, 12.6133F, 19.6996F, 12.4026F, 20.1818F, 12.4026F)
                verticalLineTo(8.40259F)
                curveTo(19.6996F, 8.40259F, 19.2371F, 8.19187F, 18.8962F, 7.8168F)
                curveTo(18.5552F, 7.44173F, 18.3636F, 6.93302F, 18.3636F, 6.40259F)
                horizontalLineTo(9.27273F)

                moveTo(2.0F, 8.40259F)
                horizontalLineTo(3.81818F)
                verticalLineTo(18.4026F)
                horizontalLineTo(18.3636F)
                verticalLineTo(20.4026F)
                horizontalLineTo(2.0F)
                verticalLineTo(8.40259F)

                close()
            }
        }
        return _money!!
    }

private var _money: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Image(imageVector = WhIcons.Theme.Money, contentDescription = null)
}