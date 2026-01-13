package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.MagicWand: ImageVector
    get() {
        if (_magicWand != null) return _magicWand!!

        _magicWand = materialIcon(name = "WhIcons.Theme.MagicWand") {
            materialPath {
                moveTo(x = 7.5025f, y = 5.6f)
                lineTo(x = 5.0025f, y = 7.0f)
                lineTo(x = 6.4025f, y = 4.5f)
                lineTo(x = 5.0025f, y = 2.0f)
                lineTo(x = 7.5025f, y = 3.4f)
                lineTo(x = 10.0025f, y = 2.0f)
                lineTo(x = 8.6025f, y = 4.5f)
                lineTo(x = 10.0025f, y = 7.0f)
                lineTo(x = 7.5025f, y = 5.6f)
                close()
                moveTo(x = 19.5025f, y = 15.4f)
                lineTo(x = 22.0025f, y = 14.0f)
                lineTo(x = 20.6025f, y = 16.5f)
                lineTo(x = 22.0025f, y = 19.0f)
                lineTo(x = 19.5025f, y = 17.6f)
                lineTo(x = 17.0025f, y = 19.0f)
                lineTo(x = 18.4025f, y = 16.5f)
                lineTo(x = 17.0025f, y = 14.0f)
                lineTo(x = 19.5025f, y = 15.4f)
                close()
                moveTo(x = 22.0025f, y = 2.0f)
                lineTo(x = 20.6025f, y = 4.5f)
                lineTo(x = 22.0025f, y = 7.0f)
                lineTo(x = 19.5025f, y = 5.6f)
                lineTo(x = 17.0025f, y = 7.0f)
                lineTo(x = 18.4025f, y = 4.5f)
                lineTo(x = 17.0025f, y = 2.0f)
                lineTo(x = 19.5025f, y = 3.4f)
                lineTo(x = 22.0025f, y = 2.0f)
                close()
                moveTo(x = 13.3425f, y = 12.78f)
                lineTo(x = 15.7825f, y = 10.34f)
                lineTo(x = 13.6625f, y = 8.22f)
                lineTo(x = 11.2225f, y = 10.66f)
                lineTo(x = 13.3425f, y = 12.78f)
                close()
                moveTo(x = 14.3725f, y = 7.29f)
                lineTo(x = 16.7125f, y = 9.63f)
                curveTo(x1 = 17.1025f, y1 = 10.0f, x2 = 17.1025f, y2 = 10.65f, x3 = 16.7125f, y3 = 11.04f)
                lineTo(x = 5.0425f, y = 22.71f)
                curveTo(x1 = 4.6525f, y1 = 23.1f, x2 = 4.0025f, y2 = 23.1f, x3 = 3.6325f, y3 = 22.71f)
                lineTo(x = 1.2925f, y = 20.37f)
                curveTo(x1 = 0.9025f, y1 = 20.0f, x2 = 0.9025f, y2 = 19.35f, x3 = 1.2925f, y3 = 18.96f)
                lineTo(x = 12.9625f, y = 7.29f)
                curveTo(x1 = 13.3525f, y1 = 6.9f, x2 = 14.0025f, y2 = 6.9f, x3 = 14.3725f, y3 = 7.29f)
                close()
            }
        }
        return _magicWand!!
    }

@Suppress("ObjectPropertyName")
private var _magicWand: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.MagicWand, contentDescription = null)