package com.whoppah.common.compose.icons.navigation

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Navigation.Cross: ImageVector
    get() {
        if (_cross != null) return _cross!!

        _cross = materialIcon(name = "WhIcons.Navigation.Cross") {
            materialPath {
                moveTo(x = 18.7f, y = 5.31782f)
                curveTo(x1 = 18.3f, y1 = 4.91782f, x2 = 17.7f, y2 = 4.91782f, x3 = 17.3f, y3 = 5.31782f)
                lineTo(x = 12.0f, y = 10.6178f)
                lineTo(x = 6.7f, y = 5.31782f)
                curveTo(x1 = 6.3f, y1 = 4.91782f, x2 = 5.7f, y2 = 4.91782f, x3 = 5.3f, y3 = 5.31782f)
                curveTo(x1 = 4.9f, y1 = 5.71782f, x2 = 4.9f, y2 = 6.31782f, x3 = 5.3f, y3 = 6.71782f)
                lineTo(x = 10.6f, y = 12.0178f)
                lineTo(x = 5.3f, y = 17.3178f)
                curveTo(x1 = 4.9f, y1 = 17.7178f, x2 = 4.9f, y2 = 18.3178f, x3 = 5.3f, y3 = 18.7178f)
                curveTo(x1 = 5.5f, y1 = 18.9178f, x2 = 5.7f, y2 = 19.0178f, x3 = 6.0f, y3 = 19.0178f)
                curveTo(x1 = 6.3f, y1 = 19.0178f, x2 = 6.5f, y2 = 18.9178f, x3 = 6.7f, y3 = 18.7178f)
                lineTo(x = 12.0f, y = 13.4178f)
                lineTo(x = 17.3f, y = 18.7178f)
                curveTo(x1 = 17.5f, y1 = 18.9178f, x2 = 17.8f, y2 = 19.0178f, x3 = 18.0f, y3 = 19.0178f)
                curveTo(x1 = 18.2f, y1 = 19.0178f, x2 = 18.5f, y2 = 18.9178f, x3 = 18.7f, y3 = 18.7178f)
                curveTo(x1 = 19.1f, y1 = 18.3178f, x2 = 19.1f, y2 = 17.7178f, x3 = 18.7f, y3 = 17.3178f)
                lineTo(x = 13.4f, y = 12.0178f)
                lineTo(x = 18.7f, y = 6.71782f)
                curveTo(x1 = 19.1f, y1 = 6.31782f, x2 = 19.1f, y2 = 5.71782f, x3 = 18.7f, y3 = 5.31782f)
                close()
            }
        }
        return _cross!!
    }

@Suppress("ObjectPropertyName")
private var _cross: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Navigation.Cross, contentDescription = null)