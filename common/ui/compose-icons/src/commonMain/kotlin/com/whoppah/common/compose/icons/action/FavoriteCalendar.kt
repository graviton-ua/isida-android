package com.whoppah.common.compose.icons.action

import androidx.compose.foundation.Image
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Action.FavoriteCalendar: ImageVector
    get() {
        if (_favoriteCalendar != null) return _favoriteCalendar!!
        _favoriteCalendar = materialIcon(name = "Action.FavoriteCalendar") {
            materialPath {
                verticalLineToRelative(0.0F)
                moveTo(19.0F, 20.4026F)
                verticalLineTo(9.40259F)
                horizontalLineTo(5.0F)
                verticalLineTo(20.4026F)
                horizontalLineTo(19.0F)

                moveTo(16.0F, 2.40259F)
                horizontalLineTo(18.0F)
                verticalLineTo(4.40259F)
                horizontalLineTo(19.0F)
                curveTo(19.5304F, 4.40259F, 20.0391F, 4.6133F, 20.4142F, 4.98837F)
                curveTo(20.7893F, 5.36345F, 21.0F, 5.87215F, 21.0F, 6.40259F)
                verticalLineTo(20.4026F)
                curveTo(21.0F, 20.933F, 20.7893F, 21.4417F, 20.4142F, 21.8168F)
                curveTo(20.0391F, 22.1919F, 19.5304F, 22.4026F, 19.0F, 22.4026F)
                horizontalLineTo(5.0F)
                curveTo(4.46957F, 22.4026F, 3.96086F, 22.1919F, 3.58579F, 21.8168F)
                curveTo(3.21071F, 21.4417F, 3.0F, 20.933F, 3.0F, 20.4026F)
                verticalLineTo(6.40259F)
                curveTo(3.0F, 5.87215F, 3.21071F, 5.36345F, 3.58579F, 4.98837F)
                curveTo(3.96086F, 4.6133F, 4.46957F, 4.40259F, 5.0F, 4.40259F)
                horizontalLineTo(6.0F)
                verticalLineTo(2.40259F)
                horizontalLineTo(8.0F)
                verticalLineTo(4.40259F)
                horizontalLineTo(16.0F)
                verticalLineTo(2.40259F)

                moveTo(12.0F, 18.5726F)
                lineTo(11.42F, 18.0426F)
                curveTo(9.36F, 16.1726F, 8.0F, 14.9426F, 8.0F, 13.4326F)
                curveTo(8.0F, 12.2026F, 8.97F, 11.2326F, 10.2F, 11.2326F)
                curveTo(10.9F, 11.2326F, 11.56F, 11.5526F, 12.0F, 12.0626F)
                curveTo(12.44F, 11.5526F, 13.1F, 11.2326F, 13.8F, 11.2326F)
                curveTo(15.03F, 11.2326F, 16.0F, 12.2026F, 16.0F, 13.4326F)
                curveTo(16.0F, 14.9426F, 14.64F, 16.1726F, 12.58F, 18.0426F)
                lineTo(12.0F, 18.5726F)

                close()
            }
        }
        return _favoriteCalendar!!
    }

private var _favoriteCalendar: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Image(imageVector = WhIcons.Action.FavoriteCalendar, contentDescription = null)
}