package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.HandingOver: ImageVector
    get() {
        if (_handingOver != null) return _handingOver!!
        _handingOver = materialIcon(name = "Theme.HandingOver") {
            materialPath {
                moveTo(15.8425F, 11.5525F)
                curveTo(18.203F, 11.5525F, 20.1188F, 9.63678F, 20.1188F, 7.27627F)
                curveTo(20.1188F, 4.91577F, 18.203F, 3.0F, 15.8425F, 3.0F)
                curveTo(13.482F, 3.0F, 11.5662F, 4.91577F, 11.5662F, 7.27627F)
                curveTo(11.5662F, 9.63678F, 13.482F, 11.5525F, 15.8425F, 11.5525F)

                moveTo(20.5036F, 16.342F)
                curveTo(20.1701F, 15.9999F, 19.751F, 15.8288F, 19.2635F, 15.8288F)
                horizontalLineTo(13.2767F)
                lineTo(11.4978F, 15.2045F)
                lineTo(11.78F, 14.4005F)
                lineTo(13.2767F, 14.9736F)
                horizontalLineTo(15.6715F)
                curveTo(15.9708F, 14.9736F, 16.2103F, 14.8538F, 16.407F, 14.6571F)
                curveTo(16.6037F, 14.4604F, 16.6978F, 14.2209F, 16.6978F, 13.9558F)
                curveTo(16.6978F, 13.494F, 16.4754F, 13.1775F, 16.0307F, 12.9979F)
                lineTo(9.81296F, 10.6973F)
                horizontalLineTo(8.14521F)
                verticalLineTo(18.3946F)
                lineTo(14.132F, 20.1051F)
                lineTo(20.9997F, 17.5393F)
                curveTo(21.0082F, 17.086F, 20.8372F, 16.6841F, 20.5036F, 16.342F)

                moveTo(6.4347F, 10.6973F)
                horizontalLineTo(3.0F)
                verticalLineTo(20.1051F)
                horizontalLineTo(6.4347F)
                verticalLineTo(10.6973F)
                close()
            }
        }
        return _handingOver!!
    }
private var _handingOver: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.HandingOver, contentDescription = null)