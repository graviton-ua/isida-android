package com.whoppah.common.compose.icons.action

import androidx.compose.foundation.Image
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Action.Export: ImageVector
    get() {
        if (_export != null) return _export!!
        _export = materialIcon(name = "Action.Export") {
            materialPath {
                verticalLineToRelative(0.0F)
                moveTo(23.0F, 12.4026F)
                lineTo(19.0F, 8.40259F)
                verticalLineTo(11.4026F)
                horizontalLineTo(10.0F)
                verticalLineTo(13.4026F)
                horizontalLineTo(19.0F)
                verticalLineTo(16.4026F)
                moveTo(1.0F, 18.4026F)
                verticalLineTo(6.40259F)
                curveTo(1.0F, 5.29259F, 1.9F, 4.40259F, 3.0F, 4.40259F)
                horizontalLineTo(15.0F)
                curveTo(15.5304F, 4.40259F, 16.0391F, 4.6133F, 16.4142F, 4.98837F)
                curveTo(16.7893F, 5.36345F, 17.0F, 5.87215F, 17.0F, 6.40259F)
                verticalLineTo(9.40259F)
                horizontalLineTo(15.0F)
                verticalLineTo(6.40259F)
                horizontalLineTo(3.0F)
                verticalLineTo(18.4026F)
                horizontalLineTo(15.0F)
                verticalLineTo(15.4026F)
                horizontalLineTo(17.0F)
                verticalLineTo(18.4026F)
                curveTo(17.0F, 18.933F, 16.7893F, 19.4417F, 16.4142F, 19.8168F)
                curveTo(16.0391F, 20.1919F, 15.5304F, 20.4026F, 15.0F, 20.4026F)
                horizontalLineTo(3.0F)
                curveTo(2.46957F, 20.4026F, 1.96086F, 20.1919F, 1.58579F, 19.8168F)
                curveTo(1.21071F, 19.4417F, 1.0F, 18.933F, 1.0F, 18.4026F)

                close()
            }
        }
        return _export!!
    }

private var _export: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Image(imageVector = WhIcons.Action.Export, contentDescription = null)
}