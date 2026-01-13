package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.Medal: ImageVector
    get() {
        if (_medal != null) return _medal!!
        _medal = materialIcon(name = "Theme.Medal") {
            materialPath {
                moveTo(20.0F, 2.40259F)
                horizontalLineTo(4.0F)
                verticalLineTo(4.40259F)
                lineTo(9.81F, 8.76259F)
                curveTo(6.14F, 9.97259F, 4.14F, 13.9326F, 5.35F, 17.6026F)
                curveTo(6.56F, 21.2726F, 10.5F, 23.2726F, 14.19F, 22.0626F)
                curveTo(17.86F, 20.8526F, 19.86F, 16.9026F, 18.65F, 13.2226F)
                curveTo(17.95F, 11.1126F, 16.3F, 9.45259F, 14.19F, 8.76259F)
                lineTo(20.0F, 4.40259F)
                verticalLineTo(2.40259F)

                moveTo(14.94F, 19.9026F)
                lineTo(12.0F, 18.1826F)
                lineTo(9.06F, 19.9026F)
                lineTo(9.84F, 16.5726F)
                lineTo(7.25F, 14.3326F)
                lineTo(10.66F, 14.0426F)
                lineTo(12.0F, 10.9026F)
                lineTo(13.34F, 14.0426F)
                lineTo(16.75F, 14.3326F)
                lineTo(14.16F, 16.5726F)
                lineTo(14.94F, 19.9026F)
                close()
            }
        }
        return _medal!!
    }
private var _medal: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.Medal, contentDescription = null)