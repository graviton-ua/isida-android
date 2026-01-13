package com.whoppah.common.compose.icons.action

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Action.Settings: ImageVector
    get() {
        if (_settings != null) return _settings!!
        _settings = materialIcon(name = "Action.Settings") {
            materialPath {
                moveTo(8.0F, 13.0249F)
                curveTo(6.14F, 13.0249F, 4.59F, 14.3049F, 4.14F, 16.0249F)
                horizontalLineTo(2.0F)
                verticalLineTo(18.0249F)
                horizontalLineTo(4.14F)
                curveTo(4.59F, 19.7449F, 6.14F, 21.0249F, 8.0F, 21.0249F)
                curveTo(9.86F, 21.0249F, 11.41F, 19.7449F, 11.86F, 18.0249F)
                horizontalLineTo(22.0F)
                verticalLineTo(16.0249F)
                horizontalLineTo(11.86F)
                curveTo(11.41F, 14.3049F, 9.86F, 13.0249F, 8.0F, 13.0249F)

                moveTo(8.0F, 19.0249F)
                curveTo(6.9F, 19.0249F, 6.0F, 18.1249F, 6.0F, 17.0249F)
                curveTo(6.0F, 15.9249F, 6.9F, 15.0249F, 8.0F, 15.0249F)
                curveTo(9.1F, 15.0249F, 10.0F, 15.9249F, 10.0F, 17.0249F)
                curveTo(10.0F, 18.1249F, 9.1F, 19.0249F, 8.0F, 19.0249F)

                moveTo(19.86F, 6.0249F)
                curveTo(19.41F, 4.3049F, 17.86F, 3.0249F, 16.0F, 3.0249F)
                curveTo(14.14F, 3.0249F, 12.59F, 4.3049F, 12.14F, 6.0249F)
                horizontalLineTo(2.0F)
                verticalLineTo(8.0249F)
                horizontalLineTo(12.14F)
                curveTo(12.59F, 9.7449F, 14.14F, 11.0249F, 16.0F, 11.0249F)
                curveTo(17.86F, 11.0249F, 19.41F, 9.7449F, 19.86F, 8.0249F)
                horizontalLineTo(22.0F)
                verticalLineTo(6.0249F)
                horizontalLineTo(19.86F)

                moveTo(16.0F, 9.0249F)
                curveTo(14.9F, 9.0249F, 14.0F, 8.1249F, 14.0F, 7.0249F)
                curveTo(14.0F, 5.9249F, 14.9F, 5.0249F, 16.0F, 5.0249F)
                curveTo(17.1F, 5.0249F, 18.0F, 5.9249F, 18.0F, 7.0249F)
                curveTo(18.0F, 8.1249F, 17.1F, 9.0249F, 16.0F, 9.0249F)
                close()
            }
        }
        return _settings!!
    }
private var _settings: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Action.Settings, contentDescription = null)