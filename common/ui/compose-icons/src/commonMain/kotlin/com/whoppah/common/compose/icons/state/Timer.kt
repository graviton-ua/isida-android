package com.whoppah.common.compose.icons.state

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.State.Timer: ImageVector
    get() {
        if (_timer != null) return _timer!!

        _timer = materialIcon(name = "WhIcons.State.Timer") {
            materialPath {
                moveTo(x = 4.94f, y = 7.35f)
                curveTo(x1 = 4.55f, y1 = 6.96f, x2 = 4.55f, y2 = 6.32f, x3 = 4.94f, y3 = 5.93f)
                curveTo(x1 = 5.33f, y1 = 5.54f, x2 = 5.96f, y2 = 5.54f, x3 = 6.35f, y3 = 5.93f)
                lineTo(x = 13.07f, y = 11.31f)
                lineTo(x = 13.42f, y = 11.59f)
                curveTo(x1 = 14.2f, y1 = 12.37f, x2 = 14.2f, y2 = 13.64f, x3 = 13.42f, y3 = 14.42f)
                curveTo(x1 = 12.64f, y1 = 15.2f, x2 = 11.37f, y2 = 15.2f, x3 = 10.59f, y3 = 14.42f)
                lineTo(x = 10.31f, y = 14.07f)
                lineTo(x = 4.94f, y = 7.35f)
                close()
                moveTo(x = 12.0f, y = 21.0f)
                curveTo(x1 = 14.1217f, y1 = 21.0f, x2 = 16.1566f, y2 = 20.1571f, x3 = 17.6569f, y3 = 18.6569f)
                curveTo(x1 = 19.1571f, y1 = 17.1566f, x2 = 20.0f, y2 = 15.1217f, x3 = 20.0f, y3 = 13.0f)
                curveTo(x1 = 20.0f, y1 = 10.79f, x2 = 19.1f, y2 = 8.79f, x3 = 17.66f, y3 = 7.34f)
                lineTo(x = 19.07f, y = 5.93f)
                curveTo(x1 = 20.88f, y1 = 7.74f, x2 = 22.0f, y2 = 10.24f, x3 = 22.0f, y3 = 13.0f)
                curveTo(x1 = 22.0f, y1 = 15.6522f, x2 = 20.9464f, y2 = 18.1957f, x3 = 19.0711f, y3 = 20.0711f)
                curveTo(x1 = 17.1957f, y1 = 21.9464f, x2 = 14.6522f, y2 = 23.0f, x3 = 12.0f, y3 = 23.0f)
                curveTo(x1 = 10.6868f, y1 = 23.0f, x2 = 9.38642f, y2 = 22.7413f, x3 = 8.17317f, y3 = 22.2388f)
                curveTo(x1 = 6.95991f, y1 = 21.7362f, x2 = 5.85752f, y2 = 20.9997f, x3 = 4.92893f, y3 = 20.0711f)
                curveTo(x1 = 3.05357f, y1 = 18.1957f, x2 = 2.0f, y2 = 15.6522f, x3 = 2.0f, y3 = 13.0f)
                horizontalLineTo(x = 4.0f)
                curveTo(x1 = 4.0f, y1 = 15.1217f, x2 = 4.84285f, y2 = 17.1566f, x3 = 6.34315f, y3 = 18.6569f)
                curveTo(x1 = 7.84344f, y1 = 20.1571f, x2 = 9.87827f, y2 = 21.0f, x3 = 12.0f, y3 = 21.0f)
                close()
                moveTo(x = 12.0f, y = 2.0f)
                curveTo(x1 = 12.5304f, y1 = 2.0f, x2 = 13.0391f, y2 = 2.21071f, x3 = 13.4142f, y3 = 2.58579f)
                curveTo(x1 = 13.7893f, y1 = 2.96086f, x2 = 14.0f, y2 = 3.46957f, x3 = 14.0f, y3 = 4.0f)
                curveTo(x1 = 14.0f, y1 = 4.53043f, x2 = 13.7893f, y2 = 5.03914f, x3 = 13.4142f, y3 = 5.41421f)
                curveTo(x1 = 13.0391f, y1 = 5.78929f, x2 = 12.5304f, y2 = 6.0f, x3 = 12.0f, y3 = 6.0f)
                curveTo(x1 = 11.4696f, y1 = 6.0f, x2 = 10.9609f, y2 = 5.78929f, x3 = 10.5858f, y3 = 5.41421f)
                curveTo(x1 = 10.2107f, y1 = 5.03914f, x2 = 10.0f, y2 = 4.53043f, x3 = 10.0f, y3 = 4.0f)
                curveTo(x1 = 10.0f, y1 = 3.46957f, x2 = 10.2107f, y2 = 2.96086f, x3 = 10.5858f, y3 = 2.58579f)
                curveTo(x1 = 10.9609f, y1 = 2.21071f, x2 = 11.4696f, y2 = 2.0f, x3 = 12.0f, y3 = 2.0f)
                close()
            }
        }
        return _timer!!
    }
private var _timer: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.State.Timer, contentDescription = null)