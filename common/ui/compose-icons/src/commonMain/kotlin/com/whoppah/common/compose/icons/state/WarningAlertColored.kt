package com.whoppah.common.compose.icons.state

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons
import com.whoppah.common.compose.icons.whoppahIcon
import com.whoppah.common.compose.icons.whoppahPath

val WhIcons.State.WarningAlertColored: ImageVector
    get() {
        if (_warningAlertColored != null) return _warningAlertColored!!

        _warningAlertColored = whoppahIcon(
            name = "WhIcons.State.WarningAlertColored",
            viewportWidth = 20f,
            viewportHeight = 20f,
        ) {
            whoppahPath(
                fill = fillBrush,
                stroke = strokeBrush,
            ) {
                moveTo(x = 10.4316f, y = 2.40332f)
                lineTo(x = 17.7656f, y = 15.0703f)
                lineTo(x = 18.2002f, y = 15.8213f)
                horizontalLineTo(x = 1.79883f)
                lineTo(x = 2.2334f, y = 15.0703f)
                lineTo(x = 9.56641f, y = 2.40332f)
                lineTo(x = 9.99902f, y = 1.65625f)
                lineTo(x = 10.4316f, y = 2.40332f)
                close()
                moveTo(x = 9.83301f, y = 12.8213f)
                horizontalLineTo(x = 10.166f)
                verticalLineTo(y = 12.4873f)
                horizontalLineTo(x = 9.83301f)
                verticalLineTo(y = 12.8213f)
                close()
                moveTo(x = 9.83301f, y = 10.1543f)
                horizontalLineTo(x = 10.166f)
                verticalLineTo(y = 7.82129f)
                horizontalLineTo(x = 9.83301f)
                verticalLineTo(y = 10.1543f)
                close()
            }
        }
        return _warningAlertColored!!
    }

@Suppress("ObjectPropertyName")
private var _warningAlertColored: ImageVector? = null
private val fillBrush = SolidColor(Color(0xFFFE49AD))
private val strokeBrush = SolidColor(Color.White)

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.State.WarningAlertColored, contentDescription = null, tint = Color.Unspecified)