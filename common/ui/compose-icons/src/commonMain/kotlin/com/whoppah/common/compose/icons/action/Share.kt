package com.whoppah.common.compose.icons.action

import androidx.compose.foundation.Image
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Action.Share: ImageVector
    get() {
        if (_share != null) return _share!!
        _share = materialIcon(name = "Action.Share") {
            materialPath {
                verticalLineToRelative(0.0F)
                moveTo(18.0F, 16.4826F)
                curveTo(17.24F, 16.4826F, 16.56F, 16.7826F, 16.04F, 17.2526F)
                lineTo(8.91F, 13.1026F)
                curveTo(8.96F, 12.8726F, 9.0F, 12.6426F, 9.0F, 12.4026F)
                curveTo(9.0F, 12.1626F, 8.96F, 11.9326F, 8.91F, 11.7026F)
                lineTo(15.96F, 7.59259F)
                curveTo(16.5F, 8.09259F, 17.21F, 8.40259F, 18.0F, 8.40259F)
                curveTo(18.7956F, 8.40259F, 19.5587F, 8.08652F, 20.1213F, 7.52391F)
                curveTo(20.6839F, 6.9613F, 21.0F, 6.19824F, 21.0F, 5.40259F)
                curveTo(21.0F, 4.60694F, 20.6839F, 3.84388F, 20.1213F, 3.28127F)
                curveTo(19.5587F, 2.71866F, 18.7956F, 2.40259F, 18.0F, 2.40259F)
                curveTo(17.2044F, 2.40259F, 16.4413F, 2.71866F, 15.8787F, 3.28127F)
                curveTo(15.3161F, 3.84388F, 15.0F, 4.60694F, 15.0F, 5.40259F)
                curveTo(15.0F, 5.64259F, 15.04F, 5.87259F, 15.09F, 6.10259F)
                lineTo(8.04F, 10.2126F)
                curveTo(7.5F, 9.71259F, 6.79F, 9.40259F, 6.0F, 9.40259F)
                curveTo(5.20435F, 9.40259F, 4.44129F, 9.71866F, 3.87868F, 10.2813F)
                curveTo(3.31607F, 10.8439F, 3.0F, 11.6069F, 3.0F, 12.4026F)
                curveTo(3.0F, 13.1982F, 3.31607F, 13.9613F, 3.87868F, 14.5239F)
                curveTo(4.44129F, 15.0865F, 5.20435F, 15.4026F, 6.0F, 15.4026F)
                curveTo(6.79F, 15.4026F, 7.5F, 15.0926F, 8.04F, 14.5926F)
                lineTo(15.16F, 18.7426F)
                curveTo(15.11F, 18.9526F, 15.08F, 19.1726F, 15.08F, 19.4026F)
                curveTo(15.08F, 21.0126F, 16.39F, 22.3126F, 18.0F, 22.3126F)
                curveTo(19.61F, 22.3126F, 20.92F, 21.0126F, 20.92F, 19.4026F)
                curveTo(20.92F, 18.6282F, 20.6124F, 17.8854F, 20.0648F, 17.3378F)
                curveTo(19.5171F, 16.7902F, 18.7744F, 16.4826F, 18.0F, 16.4826F)

                close()
            }
        }
        return _share!!
    }

private var _share: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Image(imageVector = WhIcons.Action.Share, contentDescription = null)
}