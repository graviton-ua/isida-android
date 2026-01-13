package com.whoppah.common.compose.icons.action

import androidx.compose.foundation.Image
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Action.UploadOutline: ImageVector
    get() {
        if (_uploadOutline != null) return _uploadOutline!!
        _uploadOutline = materialIcon(name = "Action.UploadOutline") {
            materialPath {
                verticalLineToRelative(0.0F)
                moveTo(9.0F, 10.4026F)
                verticalLineTo(16.4026F)
                horizontalLineTo(15.0F)
                verticalLineTo(10.4026F)
                horizontalLineTo(19.0F)
                lineTo(12.0F, 3.40259F)
                lineTo(5.0F, 10.4026F)
                horizontalLineTo(9.0F)

                moveTo(12.0F, 6.20259F)
                lineTo(14.2F, 8.40259F)
                horizontalLineTo(13.0F)
                verticalLineTo(14.4026F)
                horizontalLineTo(11.0F)
                verticalLineTo(8.40259F)
                horizontalLineTo(9.8F)
                lineTo(12.0F, 6.20259F)

                moveTo(19.0F, 18.4026F)
                horizontalLineTo(5.0F)
                verticalLineTo(20.4026F)
                horizontalLineTo(19.0F)
                verticalLineTo(18.4026F)

                close()
            }
        }
        return _uploadOutline!!
    }

private var _uploadOutline: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Image(imageVector = WhIcons.Action.UploadOutline, contentDescription = null)
}