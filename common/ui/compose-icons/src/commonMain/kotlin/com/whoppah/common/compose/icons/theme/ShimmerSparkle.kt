package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.ShimmerSparkle: ImageVector
    get() {
        if (_shimmerSparkle != null) return _shimmerSparkle!!

        _shimmerSparkle = materialIcon(name = "WhIcons.Theme.ShimmerSparkle") {
            materialPath {
                moveTo(x = 10.6f, y = 10.6f)
                lineTo(x = 9.0f, y = 16.0f)
                lineTo(x = 7.4f, y = 10.6f)
                lineTo(x = 2.0f, y = 9.0f)
                lineTo(x = 7.4f, y = 7.4f)
                lineTo(x = 9.0f, y = 2.0f)
                lineTo(x = 10.6f, y = 7.4f)
                lineTo(x = 16.0f, y = 9.0f)
                lineTo(x = 10.6f, y = 10.6f)
                close()
                moveTo(x = 17.0f, y = 15.2f)
                lineTo(x = 21.0f, y = 13.0f)
                lineTo(x = 18.8f, y = 17.0f)
                lineTo(x = 21.0f, y = 21.0f)
                lineTo(x = 17.0f, y = 18.8f)
                lineTo(x = 13.0f, y = 21.0f)
                lineTo(x = 15.2f, y = 17.0f)
                lineTo(x = 13.0f, y = 13.0f)
                lineTo(x = 17.0f, y = 15.2f)
                close()
                moveTo(x = 10.0f, y = 17.0f)
                lineTo(x = 8.3f, y = 20.0f)
                lineTo(x = 10.0f, y = 23.0f)
                lineTo(x = 7.0f, y = 21.3f)
                lineTo(x = 4.0f, y = 23.0f)
                lineTo(x = 5.7f, y = 20.0f)
                lineTo(x = 4.0f, y = 17.0f)
                lineTo(x = 7.0f, y = 18.7f)
                lineTo(x = 10.0f, y = 17.0f)
                close()
            }
        }
        return _shimmerSparkle!!
    }

@Suppress("ObjectPropertyName")
private var _shimmerSparkle: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.ShimmerSparkle, contentDescription = null)