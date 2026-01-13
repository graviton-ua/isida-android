package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.SignsOfUse: ImageVector
    get() {
        if (_signsOfUse != null) return _signsOfUse!!
        _signsOfUse = materialIcon(name = "Theme.SignsOfUse") {
            materialPath {
                moveTo(13.0F, 20.0F)
                horizontalLineTo(18.0F)
                verticalLineTo(22.0F)
                horizontalLineTo(6.0F)
                verticalLineTo(20.0F)
                horizontalLineTo(11.0F)
                verticalLineTo(13.97F)
                curveTo(8.19F, 13.7F, 6.0F, 11.34F, 6.0F, 8.46F)
                curveTo(6.0F, 8.15F, 6.03F, 7.85F, 6.08F, 7.55F)
                lineTo(7.0F, 2.0F)
                horizontalLineTo(13.54F)
                lineTo(12.33F, 4.41F)
                lineTo(11.79F, 5.5F)
                horizontalLineTo(13.79F)
                lineTo(12.33F, 8.41F)
                lineTo(11.79F, 9.5F)
                horizontalLineTo(14.0F)
                lineTo(13.0F, 12.75F)
                lineTo(15.67F, 9.09F)
                lineTo(16.46F, 8.0F)
                horizontalLineTo(14.21F)
                lineTo(15.67F, 5.09F)
                lineTo(16.21F, 4.0F)
                horizontalLineTo(14.21F)
                lineTo(15.21F, 2.0F)
                horizontalLineTo(17.0F)
                lineTo(17.93F, 7.55F)
                curveTo(18.0F, 7.85F, 18.0F, 8.15F, 18.0F, 8.46F)
                curveTo(18.0F, 11.34F, 15.81F, 13.7F, 13.0F, 13.97F)
                verticalLineTo(20.0F)
                close()
            }
        }
        return _signsOfUse!!
    }
private var _signsOfUse: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.SignsOfUse, contentDescription = null)