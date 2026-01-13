package com.whoppah.common.compose.icons.action

import androidx.compose.foundation.Image
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Action.Edit: ImageVector
    get() {
        if (_edit != null) return _edit!!
        _edit = materialIcon(name = "Action.Edit") {
            materialPath {
                verticalLineToRelative(0.0F)
                moveTo(20.71F, 7.44265F)
                curveTo(21.1F, 7.05265F, 21.1F, 6.40265F, 20.71F, 6.03265F)
                lineTo(18.37F, 3.69265F)
                curveTo(18.0F, 3.30265F, 17.35F, 3.30265F, 16.96F, 3.69265F)
                lineTo(15.12F, 5.52265F)
                lineTo(18.87F, 9.27265F)
                moveTo(3.0F, 17.6526F)
                verticalLineTo(21.4026F)
                horizontalLineTo(6.75F)
                lineTo(17.81F, 10.3326F)
                lineTo(14.06F, 6.58265F)
                lineTo(3.0F, 17.6526F)

                close()
            }
        }
        return _edit!!
    }

private var _edit: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Image(imageVector = WhIcons.Action.Edit, contentDescription = null)
}