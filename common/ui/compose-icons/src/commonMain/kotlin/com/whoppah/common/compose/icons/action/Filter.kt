package com.whoppah.common.compose.icons.action

import androidx.compose.foundation.Image
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Action.Filter: ImageVector
    get() {
        if (_filter != null) return _filter!!
        _filter = materialIcon(name = "Action.Filter") {
            materialPath {
                verticalLineToRelative(0.0F)
                moveTo(15.0001F, 20.2826F)
                curveTo(15.0401F, 20.5826F, 14.9401F, 20.9026F, 14.7101F, 21.1126F)
                curveTo(14.3201F, 21.5026F, 13.6901F, 21.5026F, 13.3001F, 21.1126F)
                lineTo(9.2901F, 17.1026F)
                curveTo(9.0601F, 16.8726F, 8.9601F, 16.5626F, 9.0001F, 16.2726F)
                verticalLineTo(11.1526F)
                lineTo(4.2101F, 5.02259F)
                curveTo(3.8701F, 4.59259F, 3.9501F, 3.96259F, 4.3801F, 3.62259F)
                curveTo(4.5701F, 3.48259F, 4.7801F, 3.40259F, 5.0001F, 3.40259F)
                horizontalLineTo(19.0001F)
                curveTo(19.2201F, 3.40259F, 19.4301F, 3.48259F, 19.6201F, 3.62259F)
                curveTo(20.0501F, 3.96259F, 20.1301F, 4.59259F, 19.7901F, 5.02259F)
                lineTo(15.0001F, 11.1526F)
                verticalLineTo(20.2826F)

                moveTo(7.0401F, 5.40259F)
                lineTo(11.0001F, 10.4626F)
                verticalLineTo(15.9826F)
                lineTo(13.0001F, 17.9826F)
                verticalLineTo(10.4526F)
                lineTo(16.9601F, 5.40259F)
                horizontalLineTo(7.0401F)

                close()
            }
        }
        return _filter!!
    }

private var _filter: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Image(imageVector = WhIcons.Action.Filter, contentDescription = null)
}