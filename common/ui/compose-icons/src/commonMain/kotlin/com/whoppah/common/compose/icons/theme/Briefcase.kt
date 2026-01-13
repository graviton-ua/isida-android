package com.whoppah.common.compose.icons.theme

import androidx.compose.foundation.Image
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.Briefcase: ImageVector
    get() {
        if (_briefcase != null) return _briefcase!!
        _briefcase = materialIcon(name = "Theme.Briefcase") {
            materialPath {
                verticalLineToRelative(0.0F)
                moveTo(10.2F, 3.40259F)
                horizontalLineTo(13.8F)
                curveTo(14.2774F, 3.40259F, 14.7352F, 3.59112F, 15.0728F, 3.92671F)
                curveTo(15.4104F, 4.2623F, 15.6F, 4.71746F, 15.6F, 5.19206F)
                verticalLineTo(6.98154F)
                horizontalLineTo(19.2F)
                curveTo(19.6774F, 6.98154F, 20.1352F, 7.17007F, 20.4728F, 7.50566F)
                curveTo(20.8104F, 7.84125F, 21.0F, 8.29641F, 21.0F, 8.77101F)
                verticalLineTo(18.6131F)
                curveTo(21.0F, 19.0877F, 20.8104F, 19.5429F, 20.4728F, 19.8785F)
                curveTo(20.1352F, 20.2141F, 19.6774F, 20.4026F, 19.2F, 20.4026F)
                horizontalLineTo(4.8F)
                curveTo(3.801F, 20.4026F, 3.0F, 19.5973F, 3.0F, 18.6131F)
                verticalLineTo(8.77101F)
                curveTo(3.0F, 7.77785F, 3.801F, 6.98154F, 4.8F, 6.98154F)
                horizontalLineTo(8.4F)
                verticalLineTo(5.19206F)
                curveTo(8.4F, 4.1989F, 9.201F, 3.40259F, 10.2F, 3.40259F)

                moveTo(13.8F, 6.98154F)
                verticalLineTo(5.19206F)
                horizontalLineTo(10.2F)
                verticalLineTo(6.98154F)
                horizontalLineTo(13.8F)

                close()
            }
        }
        return _briefcase!!
    }

private var _briefcase: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Image(imageVector = WhIcons.Theme.Briefcase, contentDescription = null)
}