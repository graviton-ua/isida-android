package com.whoppah.common.compose.icons.state

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.State.WarningAlert: ImageVector
    get() {
        if (_warningAlert != null) return _warningAlert!!

        _warningAlert = materialIcon(name = "WhIcons.State.WarningAlert") {
            materialPath {
                moveTo(x = 13.0f, y = 13.0f)
                horizontalLineTo(x = 11.0f)
                verticalLineTo(y = 8.0f)
                horizontalLineTo(x = 13.0f)
                verticalLineTo(y = 13.0f)
                close()
                moveTo(x = 13.0f, y = 17.0f)
                horizontalLineTo(x = 11.0f)
                verticalLineTo(y = 15.0f)
                horizontalLineTo(x = 13.0f)
                verticalLineTo(y = 17.0f)
                close()
                moveTo(x = 1.0f, y = 20.0f)
                horizontalLineTo(x = 23.0f)
                lineTo(x = 12.0f, y = 1.0f)
                lineTo(x = 1.0f, y = 20.0f)
                close()
            }
        }
        return _warningAlert!!
    }

@Suppress("ObjectPropertyName")
private var _warningAlert: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.State.WarningAlert, contentDescription = null)