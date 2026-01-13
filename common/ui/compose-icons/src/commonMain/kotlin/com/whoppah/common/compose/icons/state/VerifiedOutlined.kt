package com.whoppah.common.compose.icons.state

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.State.VerifiedOutlined: ImageVector
    //get() = Icons.Default.VerifiedOutlined
    get() {
        if (_verifiedOutlined != null) return _verifiedOutlined!!
        _verifiedOutlined = materialIcon(name = "State.VerifiedOutlined") {
            materialPath {
                moveTo(23.0F, 12.0178F)
                lineTo(20.6F, 9.21782F)
                lineTo(20.9F, 5.51782F)
                lineTo(17.3F, 4.71782F)
                lineTo(15.4F, 1.51782F)
                lineTo(12.0F, 3.01782F)
                lineTo(8.6F, 1.51782F)
                lineTo(6.7F, 4.71782F)
                lineTo(3.1F, 5.51782F)
                lineTo(3.4F, 9.21782F)
                lineTo(1.0F, 12.0178F)
                lineTo(3.4F, 14.8178F)
                lineTo(3.1F, 18.5178F)
                lineTo(6.7F, 19.3178F)
                lineTo(8.6F, 22.5178F)
                lineTo(12.0F, 21.0178F)
                lineTo(15.4F, 22.5178F)
                lineTo(17.3F, 19.3178F)
                lineTo(20.9F, 18.5178F)
                lineTo(20.6F, 14.8178F)
                lineTo(23.0F, 12.0178F)

                moveTo(18.7F, 16.9178F)
                lineTo(16.0F, 17.5178F)
                lineTo(14.6F, 19.9178F)
                lineTo(12.0F, 18.8178F)
                lineTo(9.4F, 19.9178F)
                lineTo(8.0F, 17.5178F)
                lineTo(5.3F, 16.9178F)
                lineTo(5.5F, 14.1178F)
                lineTo(3.7F, 12.0178F)
                lineTo(5.5F, 9.91782F)
                lineTo(5.3F, 7.11782F)
                lineTo(8.0F, 6.51782F)
                lineTo(9.4F, 4.11782F)
                lineTo(12.0F, 5.21782F)
                lineTo(14.6F, 4.11782F)
                lineTo(16.0F, 6.51782F)
                lineTo(18.7F, 7.11782F)
                lineTo(18.5F, 9.91782F)
                lineTo(20.3F, 12.0178F)
                lineTo(18.5F, 14.1178F)
                lineTo(18.7F, 16.9178F)

                moveTo(16.6F, 7.61782F)
                lineTo(18.0F, 9.01782F)
                lineTo(10.0F, 17.0178F)
                lineTo(6.0F, 13.0178F)
                lineTo(7.4F, 11.6178F)
                lineTo(10.0F, 14.2178F)
                lineTo(16.6F, 7.61782F)
                close()
            }
        }
        return _verifiedOutlined!!
    }
private var _verifiedOutlined: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.State.VerifiedOutlined, contentDescription = null)