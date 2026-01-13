package com.whoppah.common.compose.icons.action

import androidx.compose.foundation.Image
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Action.Copy: ImageVector
    get() {
        if (_copy != null) return _copy!!
        _copy = materialIcon(name = "Action.Copy") {
            materialPath {
                verticalLineToRelative(0.0F)
                moveTo(16.0F, 20.4026F)
                horizontalLineTo(8.0F)
                curveTo(7.20435F, 20.4026F, 6.44129F, 20.0865F, 5.87868F, 19.5239F)
                curveTo(5.31607F, 18.9613F, 5.0F, 18.1982F, 5.0F, 17.4026F)
                verticalLineTo(7.40259F)
                curveTo(5.0F, 7.13737F, 4.89464F, 6.88302F, 4.70711F, 6.69548F)
                curveTo(4.51957F, 6.50795F, 4.26522F, 6.40259F, 4.0F, 6.40259F)
                curveTo(3.73478F, 6.40259F, 3.48043F, 6.50795F, 3.29289F, 6.69548F)
                curveTo(3.10536F, 6.88302F, 3.0F, 7.13737F, 3.0F, 7.40259F)
                verticalLineTo(17.4026F)
                curveTo(3.0F, 18.7287F, 3.52678F, 20.0004F, 4.46447F, 20.9381F)
                curveTo(5.40215F, 21.8758F, 6.67392F, 22.4026F, 8.0F, 22.4026F)
                horizontalLineTo(16.0F)
                curveTo(16.2652F, 22.4026F, 16.5196F, 22.2972F, 16.7071F, 22.1097F)
                curveTo(16.8946F, 21.9222F, 17.0F, 21.6678F, 17.0F, 21.4026F)
                curveTo(17.0F, 21.1374F, 16.8946F, 20.883F, 16.7071F, 20.6955F)
                curveTo(16.5196F, 20.5079F, 16.2652F, 20.4026F, 16.0F, 20.4026F)

                moveTo(21.0F, 9.34259F)
                curveTo(20.9896F, 9.25072F, 20.9695F, 9.16022F, 20.94F, 9.07259F)
                verticalLineTo(8.98259F)
                curveTo(20.8919F, 8.87977F, 20.8278F, 8.78525F, 20.75F, 8.70259F)
                lineTo(14.75F, 2.70259F)
                curveTo(14.6673F, 2.6248F, 14.5728F, 2.56067F, 14.47F, 2.51259F)
                horizontalLineTo(14.38F)
                lineTo(14.06F, 2.40259F)
                horizontalLineTo(10.0F)
                curveTo(9.20435F, 2.40259F, 8.44129F, 2.71866F, 7.87868F, 3.28127F)
                curveTo(7.31607F, 3.84388F, 7.0F, 4.60694F, 7.0F, 5.40259F)
                verticalLineTo(15.4026F)
                curveTo(7.0F, 16.1982F, 7.31607F, 16.9613F, 7.87868F, 17.5239F)
                curveTo(8.44129F, 18.0865F, 9.20435F, 18.4026F, 10.0F, 18.4026F)
                horizontalLineTo(18.0F)
                curveTo(18.7956F, 18.4026F, 19.5587F, 18.0865F, 20.1213F, 17.5239F)
                curveTo(20.6839F, 16.9613F, 21.0F, 16.1982F, 21.0F, 15.4026F)
                verticalLineTo(9.40259F)
                curveTo(21.0F, 9.40259F, 21.0F, 9.40259F, 21.0F, 9.34259F)

                moveTo(15.0F, 5.81259F)
                lineTo(17.59F, 8.40259F)
                horizontalLineTo(16.0F)
                curveTo(15.7348F, 8.40259F, 15.4804F, 8.29723F, 15.2929F, 8.10969F)
                curveTo(15.1054F, 7.92216F, 15.0F, 7.6678F, 15.0F, 7.40259F)
                verticalLineTo(5.81259F)

                moveTo(19.0F, 15.4026F)
                curveTo(19.0F, 15.6678F, 18.8946F, 15.9222F, 18.7071F, 16.1097F)
                curveTo(18.5196F, 16.2972F, 18.2652F, 16.4026F, 18.0F, 16.4026F)
                horizontalLineTo(10.0F)
                curveTo(9.73478F, 16.4026F, 9.48043F, 16.2972F, 9.29289F, 16.1097F)
                curveTo(9.10536F, 15.9222F, 9.0F, 15.6678F, 9.0F, 15.4026F)
                verticalLineTo(5.40259F)
                curveTo(9.0F, 5.13737F, 9.10536F, 4.88302F, 9.29289F, 4.69548F)
                curveTo(9.48043F, 4.50794F, 9.73478F, 4.40259F, 10.0F, 4.40259F)
                horizontalLineTo(13.0F)
                verticalLineTo(7.40259F)
                curveTo(13.0F, 8.19824F, 13.3161F, 8.9613F, 13.8787F, 9.52391F)
                curveTo(14.4413F, 10.0865F, 15.2044F, 10.4026F, 16.0F, 10.4026F)
                horizontalLineTo(19.0F)
                verticalLineTo(15.4026F)

                close()
            }
        }
        return _copy!!
    }

private var _copy: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Image(imageVector = WhIcons.Action.Copy, contentDescription = null)
}