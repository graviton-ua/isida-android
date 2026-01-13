package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.EmotionUnhappy: ImageVector
    get() {
        if (_emotionUnhappy != null) return _emotionUnhappy!!

        _emotionUnhappy = materialIcon(name = "Theme.EmotionUnhappy") {
            materialPath {
                moveTo(x = 12.0f, y = 2.40259f)
                curveTo(x1 = 6.47f, y1 = 2.40259f, x2 = 2.0f, y2 = 6.90259f, x3 = 2.0f, y3 = 12.4026f)
                curveTo(x1 = 2.0f, y1 = 17.9026f, x2 = 6.47f, y2 = 22.4026f, x3 = 12.0f, y3 = 22.4026f)
                curveTo(x1 = 17.5f, y1 = 22.4026f, x2 = 22.0f, y2 = 17.9026f, x3 = 22.0f, y3 = 12.4026f)
                curveTo(x1 = 22.0f, y1 = 6.90259f, x2 = 17.5f, y2 = 2.40259f, x3 = 12.0f, y3 = 2.40259f)
                close()
                moveTo(x = 12.0f, y = 20.4026f)
                curveTo(x1 = 7.58f, y1 = 20.4026f, x2 = 4.0f, y2 = 16.8226f, x3 = 4.0f, y3 = 12.4026f)
                curveTo(x1 = 4.0f, y1 = 7.98259f, x2 = 7.58f, y2 = 4.40259f, x3 = 12.0f, y3 = 4.40259f)
                curveTo(x1 = 16.42f, y1 = 4.40259f, x2 = 20.0f, y2 = 7.98259f, x3 = 20.0f, y3 = 12.4026f)
                curveTo(x1 = 20.0f, y1 = 16.8226f, x2 = 16.42f, y2 = 20.4026f, x3 = 12.0f, y3 = 20.4026f)
                close()
                moveTo(x = 15.5f, y = 11.4026f)
                curveTo(x1 = 16.33f, y1 = 11.4026f, x2 = 17.0f, y2 = 10.7326f, x3 = 17.0f, y3 = 9.90259f)
                curveTo(x1 = 17.0f, y1 = 9.07259f, x2 = 16.33f, y2 = 8.40259f, x3 = 15.5f, y3 = 8.40259f)
                curveTo(x1 = 14.67f, y1 = 8.40259f, x2 = 14.0f, y2 = 9.07259f, x3 = 14.0f, y3 = 9.90259f)
                curveTo(x1 = 14.0f, y1 = 10.7326f, x2 = 14.67f, y2 = 11.4026f, x3 = 15.5f, y3 = 11.4026f)
                close()
                moveTo(x = 8.5f, y = 11.4026f)
                curveTo(x1 = 9.33f, y1 = 11.4026f, x2 = 10.0f, y2 = 10.7326f, x3 = 10.0f, y3 = 9.90259f)
                curveTo(x1 = 10.0f, y1 = 9.07259f, x2 = 9.33f, y2 = 8.40259f, x3 = 8.5f, y3 = 8.40259f)
                curveTo(x1 = 7.67f, y1 = 8.40259f, x2 = 7.0f, y2 = 9.07259f, x3 = 7.0f, y3 = 9.90259f)
                curveTo(x1 = 7.0f, y1 = 10.7326f, x2 = 7.67f, y2 = 11.4026f, x3 = 8.5f, y3 = 11.4026f)
                close()
                moveTo(x = 12.0f, y = 13.9026f)
                curveTo(x1 = 9.67f, y1 = 13.9026f, x2 = 7.69f, y2 = 15.3626f, x3 = 6.89f, y3 = 17.4026f)
                horizontalLineTo(x = 17.11f)
                curveTo(x1 = 16.31f, y1 = 15.3626f, x2 = 14.33f, y2 = 13.9026f, x3 = 12.0f, y3 = 13.9026f)
                close()
            }
        }
        return _emotionUnhappy!!
    }

@Suppress("ObjectPropertyName")
private var _emotionUnhappy: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.EmotionUnhappy, contentDescription = null)