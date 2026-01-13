package com.whoppah.common.compose.icons.theme

import androidx.compose.foundation.Image
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.BriefcaseOutlined: ImageVector
    get() {
        if (_briefcaseOutlined != null) return _briefcaseOutlined!!
        _briefcaseOutlined = materialIcon(name = "Theme.BriefcaseOutlined") {
            materialPath {
                verticalLineToRelative(0.0F)
                moveTo(19.2F, 6.98154F)
                curveTo(19.722F, 6.98154F, 20.145F, 7.16048F, 20.478F, 7.50943F)
                curveTo(20.82F, 7.87627F, 21.0F, 8.2789F, 21.0F, 8.77101F)
                verticalLineTo(18.6131F)
                curveTo(21.0F, 19.1052F, 20.82F, 19.5079F, 20.478F, 19.8747F)
                curveTo(20.145F, 20.2236F, 19.722F, 20.4026F, 19.2F, 20.4026F)
                horizontalLineTo(4.8F)
                curveTo(4.278F, 20.4026F, 3.855F, 20.2236F, 3.522F, 19.8747F)
                curveTo(3.18F, 19.5079F, 3.0F, 19.1052F, 3.0F, 18.6131F)
                verticalLineTo(8.77101F)
                curveTo(3.0F, 8.2789F, 3.18F, 7.87627F, 3.522F, 7.50943F)
                curveTo(3.855F, 7.16048F, 4.278F, 6.98154F, 4.8F, 6.98154F)
                horizontalLineTo(8.4F)
                verticalLineTo(5.19206F)
                curveTo(8.4F, 4.67311F, 8.58F, 4.25259F, 8.922F, 3.92154F)
                curveTo(9.255F, 3.58154F, 9.678F, 3.40259F, 10.2F, 3.40259F)
                horizontalLineTo(13.8F)
                curveTo(14.322F, 3.40259F, 14.745F, 3.58154F, 15.078F, 3.92154F)
                curveTo(15.42F, 4.25259F, 15.6F, 4.67311F, 15.6F, 5.19206F)
                verticalLineTo(6.98154F)
                horizontalLineTo(19.2F)

                moveTo(4.8F, 8.77101F)
                verticalLineTo(18.6131F)
                horizontalLineTo(19.2F)
                verticalLineTo(8.77101F)
                horizontalLineTo(4.8F)

                moveTo(13.8F, 6.98154F)
                verticalLineTo(5.19206F)
                horizontalLineTo(10.2F)
                verticalLineTo(6.98154F)
                horizontalLineTo(13.8F)

                close()
            }
        }
        return _briefcaseOutlined!!
    }

private var _briefcaseOutlined: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Image(imageVector = WhIcons.Theme.BriefcaseOutlined, contentDescription = null)
}