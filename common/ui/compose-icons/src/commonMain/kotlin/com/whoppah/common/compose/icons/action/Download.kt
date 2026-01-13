package com.whoppah.common.compose.icons.action

import androidx.compose.foundation.Image
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Action.Download: ImageVector
    get() {
        if (_download != null) return _download!!
        _download = materialIcon(name = "Action.Download") {
            materialPath {
                verticalLineToRelative(0.0F)
                moveTo(19.0F, 9.40259F)
                horizontalLineTo(15.0F)
                verticalLineTo(3.40259F)
                horizontalLineTo(9.0F)
                verticalLineTo(9.40259F)
                horizontalLineTo(5.0F)
                lineTo(12.0F, 16.4026F)
                lineTo(19.0F, 9.40259F)

                moveTo(5.0F, 18.4026F)
                verticalLineTo(20.4026F)
                horizontalLineTo(19.0F)
                verticalLineTo(18.4026F)
                horizontalLineTo(5.0F)

                close()
            }
        }
        return _download!!
    }

private var _download: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Image(imageVector = WhIcons.Action.Download, contentDescription = null)
}