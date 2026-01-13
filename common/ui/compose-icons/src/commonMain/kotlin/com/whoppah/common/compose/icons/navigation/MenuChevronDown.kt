package com.whoppah.common.compose.icons.navigation

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Navigation.MenuChevronDown: ImageVector
    get() {
        if (_menuChevronDown != null) return _menuChevronDown!!

        _menuChevronDown = materialIcon(name = "WhIcons.Navigation.MenuChevronDown") {
            materialPath {
                moveTo(x = 7.0f, y = 10.0178f)
                lineTo(x = 12.0f, y = 15.0178f)
                lineTo(x = 17.0f, y = 10.0178f)
                horizontalLineTo(x = 7.0f)
                close()
            }
        }
        return _menuChevronDown!!
    }

@Suppress("ObjectPropertyName")
private var _menuChevronDown: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Navigation.MenuChevronDown, contentDescription = null)