package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.UmbrellaOutlined: ImageVector
    get() {
        if (_umbrellaOutlined != null) return _umbrellaOutlined!!
        _umbrellaOutlined = materialIcon(name = "Theme.UmbrellaOutlined") {
            materialPath {
                moveTo(21.0001F, 19.9726F)
                lineTo(19.5701F, 21.4026F)
                lineTo(13.1301F, 14.9626F)
                lineTo(14.5601F, 13.5326F)
                lineTo(21.0001F, 19.9726F)

                moveTo(13.1201F, 3.40259F)
                curveTo(10.5401F, 3.40259F, 7.96005F, 4.40259F, 6.00005F, 6.35259F)
                lineTo(5.97005F, 6.36259F)
                curveTo(2.00005F, 10.3126F, 2.00005F, 16.7226F, 5.97005F, 20.6726F)
                lineTo(20.2701F, 6.36259F)
                curveTo(18.3001F, 4.40259F, 15.7101F, 3.40259F, 13.1201F, 3.40259F)

                moveTo(6.14005F, 17.6726F)
                curveTo(5.40005F, 16.4326F, 5.00005F, 15.0126F, 5.00005F, 13.5226F)
                curveTo(5.00005F, 12.5926F, 5.16005F, 11.7026F, 5.46005F, 10.8526F)
                curveTo(5.65005F, 12.7626F, 6.35005F, 14.6426F, 7.53005F, 16.2926F)
                lineTo(6.14005F, 17.6726F)

                moveTo(9.00005F, 14.8326F)
                curveTo(7.63005F, 12.7826F, 7.12005F, 10.3326F, 7.60005F, 8.00259F)
                curveTo(8.18005F, 7.90259F, 8.76005F, 7.82259F, 9.35005F, 7.82259F)
                curveTo(11.1501F, 7.82259F, 12.9001F, 8.37259F, 14.4301F, 9.40259F)
                lineTo(9.00005F, 14.8326F)

                moveTo(10.4501F, 5.86259F)
                curveTo(11.3001F, 5.56259F, 12.1901F, 5.40259F, 13.1201F, 5.40259F)
                curveTo(14.6101F, 5.40259F, 16.0301F, 5.80259F, 17.2701F, 6.54259F)
                lineTo(15.8801F, 7.93259F)
                curveTo(14.2301F, 6.75259F, 12.3601F, 6.05259F, 10.4501F, 5.86259F)
                close()
            }
        }
        return _umbrellaOutlined!!
    }
private var _umbrellaOutlined: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.UmbrellaOutlined, contentDescription = null)