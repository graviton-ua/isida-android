package com.whoppah.common.compose.icons.action

import androidx.compose.foundation.Image
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Action.Attachment: ImageVector
    get() {
        if (_attachment != null) return _attachment!!
        _attachment = materialIcon(name = "Action.Attachment") {
            materialPath {
                verticalLineToRelative(0.0F)
                moveTo(16.5F, 6.40259F)
                verticalLineTo(17.9026F)
                curveTo(16.5F, 18.9635F, 16.0786F, 19.9809F, 15.3284F, 20.731F)
                curveTo(14.5783F, 21.4812F, 13.5609F, 21.9026F, 12.5F, 21.9026F)
                curveTo(11.4391F, 21.9026F, 10.4217F, 21.4812F, 9.67157F, 20.731F)
                curveTo(8.92143F, 19.9809F, 8.5F, 18.9635F, 8.5F, 17.9026F)
                verticalLineTo(5.40259F)
                curveTo(8.5F, 4.73955F, 8.76339F, 4.10366F, 9.23223F, 3.63482F)
                curveTo(9.70107F, 3.16598F, 10.337F, 2.90259F, 11.0F, 2.90259F)
                curveTo(11.663F, 2.90259F, 12.2989F, 3.16598F, 12.7678F, 3.63482F)
                curveTo(13.2366F, 4.10366F, 13.5F, 4.73955F, 13.5F, 5.40259F)
                verticalLineTo(15.9026F)
                curveTo(13.5F, 16.1678F, 13.3946F, 16.4222F, 13.2071F, 16.6097F)
                curveTo(13.0196F, 16.7972F, 12.7652F, 16.9026F, 12.5F, 16.9026F)
                curveTo(12.2348F, 16.9026F, 11.9804F, 16.7972F, 11.7929F, 16.6097F)
                curveTo(11.6054F, 16.4222F, 11.5F, 16.1678F, 11.5F, 15.9026F)
                verticalLineTo(6.40259F)
                horizontalLineTo(10.0F)
                verticalLineTo(15.9026F)
                curveTo(10.0F, 16.5656F, 10.2634F, 17.2015F, 10.7322F, 17.6704F)
                curveTo(11.2011F, 18.1392F, 11.837F, 18.4026F, 12.5F, 18.4026F)
                curveTo(13.163F, 18.4026F, 13.7989F, 18.1392F, 14.2678F, 17.6704F)
                curveTo(14.7366F, 17.2015F, 15.0F, 16.5656F, 15.0F, 15.9026F)
                verticalLineTo(5.40259F)
                curveTo(15.0F, 4.34172F, 14.5786F, 3.32431F, 13.8284F, 2.57416F)
                curveTo(13.0783F, 1.82402F, 12.0609F, 1.40259F, 11.0F, 1.40259F)
                curveTo(9.93913F, 1.40259F, 8.92172F, 1.82402F, 8.17157F, 2.57416F)
                curveTo(7.42143F, 3.32431F, 7.0F, 4.34172F, 7.0F, 5.40259F)
                verticalLineTo(17.9026F)
                curveTo(7.0F, 19.3613F, 7.57946F, 20.7602F, 8.61091F, 21.7917F)
                curveTo(9.64236F, 22.8231F, 11.0413F, 23.4026F, 12.5F, 23.4026F)
                curveTo(13.9587F, 23.4026F, 15.3576F, 22.8231F, 16.3891F, 21.7917F)
                curveTo(17.4205F, 20.7602F, 18.0F, 19.3613F, 18.0F, 17.9026F)
                verticalLineTo(6.40259F)
                horizontalLineTo(16.5F)

                close()
            }
        }
        return _attachment!!
    }

private var _attachment: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Image(imageVector = WhIcons.Action.Attachment, contentDescription = null)
}