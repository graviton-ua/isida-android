package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.Category: ImageVector
    get() {
        if (_category != null) return _category!!
        _category = materialIcon(name = "Theme.Category") {
            materialPath {
                moveTo(12.0F, 2.0F)
                lineToRelative(-5.5F, 9.0F)
                horizontalLineToRelative(11.0F)
                close()
            }
            materialPath {
                moveTo(17.5f, 17.5f)
                moveToRelative(-4.5f, 0.0f)
                arcToRelative(4.5f, 4.5f, 0.0f, isMoreThanHalf = true, isPositiveArc = true, dx1 = 9.0f, dy1 = 0.0f)
                arcToRelative(4.5f, 4.5f, 0.0f, isMoreThanHalf = true, isPositiveArc = true, dx1 = -9.0f, dy1 = 0.0f)
                close()
            }
            materialPath {
                moveTo(3.0f, 13.5f)
                horizontalLineToRelative(8.0f)
                verticalLineToRelative(8.0f)
                horizontalLineTo(3.0f)
                close()
            }
        }
        return _category!!
    }
private var _category: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.Category, contentDescription = null)