package com.whoppah.common.compose.icons.navigation

import androidx.compose.foundation.Image
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Navigation.LocationPinOutlined: ImageVector
    get() {
        if (_locationPinOutlined != null) return _locationPinOutlined!!
        _locationPinOutlined = materialIcon(name = "Navigation.LocationPinOutlined") {
            materialPath {
                verticalLineToRelative(0.0F)
                moveTo(12.0F, 6.66333F)
                curveTo(12.663F, 6.66333F, 13.2989F, 6.92672F, 13.7678F, 7.39556F)
                curveTo(14.2366F, 7.8644F, 14.5F, 8.50029F, 14.5F, 9.16333F)
                curveTo(14.5F, 9.49163F, 14.4353F, 9.81672F, 14.3097F, 10.12F)
                curveTo(14.1841F, 10.4234F, 13.9999F, 10.699F, 13.7678F, 10.9311F)
                curveTo(13.5356F, 11.1632F, 13.26F, 11.3474F, 12.9567F, 11.473F)
                curveTo(12.6534F, 11.5987F, 12.3283F, 11.6633F, 12.0F, 11.6633F)
                curveTo(11.337F, 11.6633F, 10.7011F, 11.3999F, 10.2322F, 10.9311F)
                curveTo(9.76339F, 10.4623F, 9.5F, 9.82637F, 9.5F, 9.16333F)
                curveTo(9.5F, 8.50029F, 9.76339F, 7.8644F, 10.2322F, 7.39556F)
                curveTo(10.7011F, 6.92672F, 11.337F, 6.66333F, 12.0F, 6.66333F)

                moveTo(12.0F, 2.16333F)
                curveTo(13.8565F, 2.16333F, 15.637F, 2.90083F, 16.9497F, 4.21358F)
                curveTo(18.2625F, 5.52634F, 19.0F, 7.30681F, 19.0F, 9.16333F)
                curveTo(19.0F, 14.4133F, 12.0F, 22.1633F, 12.0F, 22.1633F)
                curveTo(12.0F, 22.1633F, 5.0F, 14.4133F, 5.0F, 9.16333F)
                curveTo(5.0F, 7.30681F, 5.7375F, 5.52634F, 7.05025F, 4.21358F)
                curveTo(8.36301F, 2.90083F, 10.1435F, 2.16333F, 12.0F, 2.16333F)

                moveTo(12.0F, 4.16333F)
                curveTo(10.6739F, 4.16333F, 9.40215F, 4.69011F, 8.46447F, 5.6278F)
                curveTo(7.52678F, 6.56548F, 7.0F, 7.83725F, 7.0F, 9.16333F)
                curveTo(7.0F, 10.1633F, 7.0F, 12.1633F, 12.0F, 18.8733F)
                curveTo(17.0F, 12.1633F, 17.0F, 10.1633F, 17.0F, 9.16333F)
                curveTo(17.0F, 7.83725F, 16.4732F, 6.56548F, 15.5355F, 5.6278F)
                curveTo(14.5979F, 4.69011F, 13.3261F, 4.16333F, 12.0F, 4.16333F)

                close()
            }
        }
        return _locationPinOutlined!!
    }

private var _locationPinOutlined: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Image(imageVector = WhIcons.Navigation.LocationPinOutlined, contentDescription = null)
}