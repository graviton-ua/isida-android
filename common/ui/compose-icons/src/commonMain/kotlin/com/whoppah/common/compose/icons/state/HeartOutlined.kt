package com.whoppah.common.compose.icons.state

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.State.HeartOutlined: ImageVector
    get() {
        if (_heartOutlined != null) return _heartOutlined!!
        _heartOutlined = materialIcon(name = "State.HeartOutlined") {
            materialPath {
                moveTo(12.0003F, 5.79557F)
                curveTo(13.7596F, 3.69435F, 17.0183F, 2.83755F, 19.6573F, 4.67356F)
                curveTo(21.0568F, 5.65277F, 21.9365F, 7.30518F, 21.9964F, 9.0392F)
                curveTo(22.1264F, 12.9968F, 18.6977F, 16.1691F, 13.4497F, 21.0243F)
                lineTo(13.3398F, 21.1263F)
                curveTo(12.5801F, 21.8403F, 11.4105F, 21.8403F, 10.6508F, 21.1365F)
                lineTo(10.5508F, 21.0447F)
                lineTo(10.4905F, 20.9887F)
                curveTo(5.27649F, 16.1549F, 1.86471F, 12.9918F, 2.00412F, 9.0494F)
                curveTo(2.06409F, 7.30518F, 2.94376F, 5.65277F, 4.34322F, 4.67356F)
                curveTo(6.9822F, 2.82735F, 10.241F, 3.69435F, 12.0003F, 5.79557F)

                moveTo(12.001F, 19.6269F)
                lineTo(12.101F, 19.5249F)
                curveTo(16.8592F, 15.1287F, 19.998F, 12.2216F, 19.998F, 9.2738F)
                curveTo(19.998F, 7.23379F, 18.4985F, 5.70377F, 16.4993F, 5.70377F)
                curveTo(14.9599F, 5.70377F, 13.4605F, 6.71358F, 12.9407F, 8.11099F)
                horizontalLineTo(11.0714F)
                curveTo(10.5416F, 6.71358F, 9.04218F, 5.70377F, 7.50277F, 5.70377F)
                curveTo(5.50354F, 5.70377F, 4.00412F, 7.23379F, 4.00412F, 9.2738F)
                curveTo(4.00412F, 12.2216F, 7.14291F, 15.1287F, 11.9011F, 19.5249F)
                lineTo(12.001F, 19.6269F)
                close()
            }
        }
        return _heartOutlined!!
    }
private var _heartOutlined: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.State.HeartOutlined, contentDescription = null)