package com.whoppah.common.compose.icons.navigation

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Navigation.ArrowRepeat: ImageVector
    get() {
        if (_arrowRepeat != null) return _arrowRepeat!!
        _arrowRepeat = materialIcon(name = "Navigation.ArrowRepeat") {
            materialPath {
                moveTo(17.6476F, 6.35292F)
                curveTo(16.0159F, 4.72292F, 13.7036F, 3.78292F, 11.161F, 4.04292F)
                curveTo(7.48722F, 4.41292F, 4.46414F, 7.39292F, 4.05372F, 11.0629F)
                curveTo(3.50316F, 15.9129F, 7.25699F, 20.0029F, 11.9918F, 20.0029F)
                curveTo(15.1851F, 20.0029F, 17.9279F, 18.1329F, 19.2092F, 15.4429F)
                curveTo(19.5295F, 14.7729F, 19.049F, 14.0029F, 18.3083F, 14.0029F)
                curveTo(17.9379F, 14.0029F, 17.5875F, 14.2029F, 17.4274F, 14.5329F)
                curveTo(16.2962F, 16.9629F, 13.5834F, 18.5029F, 10.6204F, 17.8429F)
                curveTo(8.39815F, 17.3529F, 6.60633F, 15.5429F, 6.13584F, 13.3229F)
                curveTo(5.29499F, 9.44292F, 8.248F, 6.00292F, 11.9918F, 6.00292F)
                curveTo(13.6535F, 6.00292F, 15.135F, 6.69292F, 16.2161F, 7.78292F)
                lineTo(14.7046F, 9.29292F)
                curveTo(14.0739F, 9.92292F, 14.5144F, 11.0029F, 15.4053F, 11.0029F)
                horizontalLineTo(18.999F)
                curveTo(19.5495F, 11.0029F, 20.0F, 10.5529F, 20.0F, 10.0029F)
                verticalLineTo(6.41292F)
                curveTo(20.0F, 5.52292F, 18.9189F, 5.07292F, 18.2883F, 5.70292F)
                lineTo(17.6476F, 6.35292F)
                close()
            }
        }
        return _arrowRepeat!!
    }
private var _arrowRepeat: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Navigation.ArrowRepeat, contentDescription = null)