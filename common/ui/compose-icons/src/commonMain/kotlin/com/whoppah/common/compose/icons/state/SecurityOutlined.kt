package com.whoppah.common.compose.icons.state

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.State.SecurityOutlined: ImageVector
    get() {
        if (_securityOutlined != null) return _securityOutlined!!
        _securityOutlined = materialIcon(name = "State.SecurityOutlined") {
            materialPath {
                moveTo(21.0F, 11.0F)
                curveTo(21.0F, 16.55F, 17.16F, 21.74F, 12.0F, 23.0F)
                curveTo(6.84F, 21.74F, 3.0F, 16.55F, 3.0F, 11.0F)
                verticalLineTo(5.0F)
                lineTo(12.0F, 1.0F)
                lineTo(21.0F, 5.0F)
                verticalLineTo(11.0F)

                moveTo(12.0F, 21.0F)
                curveTo(15.75F, 20.0F, 19.0F, 15.54F, 19.0F, 11.22F)
                verticalLineTo(6.3F)
                lineTo(12.0F, 3.18F)
                lineTo(5.0F, 6.3F)
                verticalLineTo(11.22F)
                curveTo(5.0F, 15.54F, 8.25F, 20.0F, 12.0F, 21.0F)

                moveTo(10.0F, 17.0F)
                lineTo(6.0F, 13.0F)
                lineTo(7.41F, 11.59F)
                lineTo(10.0F, 14.17F)
                lineTo(16.59F, 7.58F)
                lineTo(18.0F, 9.0F)
                close()
            }
        }
        return _securityOutlined!!
    }
private var _securityOutlined: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.State.SecurityOutlined, contentDescription = null)