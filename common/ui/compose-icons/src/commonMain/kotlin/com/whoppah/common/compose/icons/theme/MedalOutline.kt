package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.MedalOutline: ImageVector
    get() {
        if (_medalOutline != null) return _medalOutline!!
        _medalOutline = materialIcon(name = "Theme.MedalOutline") {
            materialPath {
                moveTo(14.94F, 19.9026F)
                lineTo(12.0F, 18.1726F)
                lineTo(9.06001F, 19.9026F)
                lineTo(9.84001F, 16.5626F)
                lineTo(7.25001F, 14.3226F)
                lineTo(10.66F, 14.0326F)
                lineTo(12.0F, 10.9026F)
                lineTo(13.34F, 14.0326F)
                lineTo(16.75F, 14.3226F)
                lineTo(14.16F, 16.5626F)
                moveTo(20.0F, 2.40259F)
                horizontalLineTo(4.00001F)
                verticalLineTo(4.40259F)
                lineTo(8.86001F, 8.04259F)
                curveTo(7.16442F, 8.76617F, 5.77077F, 10.0532F, 4.91475F, 11.6859F)
                curveTo(4.05873F, 13.3186F, 3.79287F, 15.1969F, 4.16215F, 17.0031F)
                curveTo(4.53143F, 18.8092F, 5.51317F, 20.4325F, 6.94135F, 21.5982F)
                curveTo(8.36952F, 22.7639F, 10.1565F, 23.4006F, 12.0F, 23.4006F)
                curveTo(13.8435F, 23.4006F, 15.6305F, 22.7639F, 17.0587F, 21.5982F)
                curveTo(18.4869F, 20.4325F, 19.4686F, 18.8092F, 19.8379F, 17.0031F)
                curveTo(20.2071F, 15.1969F, 19.9413F, 13.3186F, 19.0853F, 11.6859F)
                curveTo(18.2293F, 10.0532F, 16.8356F, 8.76617F, 15.14F, 8.04259F)
                lineTo(20.0F, 4.40259F)
                moveTo(18.0F, 15.4026F)
                curveTo(18.0005F, 16.5389F, 17.6784F, 17.6519F, 17.0711F, 18.6123F)
                curveTo(16.4638F, 19.5726F, 15.5962F, 20.3408F, 14.5694F, 20.8274F)
                curveTo(13.5426F, 21.314F, 12.3987F, 21.499F, 11.2709F, 21.3609F)
                curveTo(10.143F, 21.2229F, 9.07757F, 20.7674F, 8.19847F, 20.0474F)
                curveTo(7.31937F, 19.3275F, 6.66277F, 18.3727F, 6.30507F, 17.2942F)
                curveTo(5.94737F, 16.2157F, 5.90327F, 15.0578F, 6.1779F, 13.9552F)
                curveTo(6.45253F, 12.8526F, 7.03461F, 11.8507F, 7.85641F, 11.066F)
                curveTo(8.6782F, 10.2813F, 9.70593F, 9.74606F, 10.82F, 9.52259F)
                curveTo(11.5985F, 9.36254F, 12.4015F, 9.36254F, 13.18F, 9.52259F)
                curveTo(14.5389F, 9.79517F, 15.7616F, 10.5298F, 16.6402F, 11.6017F)
                curveTo(17.5189F, 12.6736F, 17.9994F, 14.0166F, 18.0F, 15.4026F)

                moveTo(12.63F, 7.40259F)
                horizontalLineTo(11.37F)
                lineTo(7.37001F, 4.40259F)
                horizontalLineTo(16.71F)
                lineTo(12.63F, 7.40259F)
                close()
            }
        }
        return _medalOutline!!
    }
private var _medalOutline: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.MedalOutline, contentDescription = null)