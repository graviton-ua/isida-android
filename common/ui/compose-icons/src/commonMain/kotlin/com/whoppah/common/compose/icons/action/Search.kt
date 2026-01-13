package com.whoppah.common.compose.icons.action

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Action.Search: ImageVector
    get() {
        if (_search != null) return _search!!
        _search = materialIcon(name = "Action.Search") {
            materialPath {
                moveTo(15.3645F, 15.2711F)
                horizontalLineTo(16.1986F)
                lineTo(20.6753F, 19.7663F)
                curveTo(21.1082F, 20.199F, 21.1082F, 20.906F, 20.6753F, 21.3386F)
                curveTo(20.2424F, 21.7712F, 19.535F, 21.7712F, 19.1021F, 21.3386F)
                lineTo(14.6148F, 16.8539F)
                verticalLineTo(16.0203F)
                lineTo(14.3298F, 15.7249F)
                curveTo(12.8516F, 16.9911F, 10.835F, 17.6454F, 8.69161F, 17.2866F)
                curveTo(5.75639F, 16.7906F, 3.41244F, 14.3425F, 3.05345F, 11.3879F)
                curveTo(2.50442F, 6.92436F, 6.26319F, 3.16779F, 10.7294F, 3.71651F)
                curveTo(13.6857F, 4.07528F, 16.1352F, 6.41786F, 16.6315F, 9.35136F)
                curveTo(16.9905F, 11.4934F, 16.3359F, 13.5089F, 15.0688F, 14.9862F)
                lineTo(15.3645F, 15.2711F)

                moveTo(5.11232F, 10.5226F)
                curveTo(5.11232F, 13.1501F, 7.23455F, 15.2711F, 9.86358F, 15.2711F)
                curveTo(12.4926F, 15.2711F, 14.6148F, 13.1501F, 14.6148F, 10.5226F)
                curveTo(14.6148F, 7.89515F, 12.4926F, 5.77417F, 9.86358F, 5.77417F)
                curveTo(7.23455F, 5.77417F, 5.11232F, 7.89515F, 5.11232F, 10.5226F)
                close()
            }
        }
        return _search!!
    }
private var _search: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Action.Search, contentDescription = null)