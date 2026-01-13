package com.whoppah.common.compose.icons.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Theme.AwardOutlined: ImageVector
    get() {
        if (_awardOutlined != null) return _awardOutlined!!
        _awardOutlined = materialIcon(name = "Theme.AwardOutlined") {
            materialPath {
                moveTo(7.0F, 21.4026F)
                verticalLineTo(19.4026F)
                horizontalLineTo(11.0F)
                verticalLineTo(16.3026F)
                curveTo(10.1833F, 16.1193F, 9.45417F, 15.7734F, 8.8125F, 15.2651F)
                curveTo(8.17083F, 14.7568F, 7.7F, 14.1193F, 7.4F, 13.3526F)
                curveTo(6.15F, 13.2026F, 5.10417F, 12.6568F, 4.2625F, 11.7151F)
                curveTo(3.42083F, 10.7734F, 3.0F, 9.66925F, 3.0F, 8.40259F)
                verticalLineTo(7.40259F)
                curveTo(3.0F, 6.85259F, 3.19583F, 6.38175F, 3.5875F, 5.99009F)
                curveTo(3.97917F, 5.59842F, 4.45F, 5.40259F, 5.0F, 5.40259F)
                horizontalLineTo(7.0F)
                verticalLineTo(3.40259F)
                horizontalLineTo(17.0F)
                verticalLineTo(5.40259F)
                horizontalLineTo(19.0F)
                curveTo(19.55F, 5.40259F, 20.0208F, 5.59842F, 20.4125F, 5.99009F)
                curveTo(20.8042F, 6.38175F, 21.0F, 6.85259F, 21.0F, 7.40259F)
                verticalLineTo(8.40259F)
                curveTo(21.0F, 9.66925F, 20.5792F, 10.7734F, 19.7375F, 11.7151F)
                curveTo(18.8958F, 12.6568F, 17.85F, 13.2026F, 16.6F, 13.3526F)
                curveTo(16.3F, 14.1193F, 15.8292F, 14.7568F, 15.1875F, 15.2651F)
                curveTo(14.5458F, 15.7734F, 13.8167F, 16.1193F, 13.0F, 16.3026F)
                verticalLineTo(19.4026F)
                horizontalLineTo(17.0F)
                verticalLineTo(21.4026F)
                horizontalLineTo(7.0F)

                moveTo(7.0F, 11.2026F)
                verticalLineTo(7.40259F)
                horizontalLineTo(5.0F)
                verticalLineTo(8.40259F)
                curveTo(5.0F, 9.03592F, 5.18333F, 9.60675F, 5.55F, 10.1151F)
                curveTo(5.91667F, 10.6234F, 6.4F, 10.9859F, 7.0F, 11.2026F)

                moveTo(12.0F, 14.4026F)
                curveTo(12.8333F, 14.4026F, 13.5417F, 14.1109F, 14.125F, 13.5276F)
                curveTo(14.7083F, 12.9443F, 15.0F, 12.2359F, 15.0F, 11.4026F)
                verticalLineTo(5.40259F)
                horizontalLineTo(9.0F)
                verticalLineTo(11.4026F)
                curveTo(9.0F, 12.2359F, 9.29167F, 12.9443F, 9.875F, 13.5276F)
                curveTo(10.4583F, 14.1109F, 11.1667F, 14.4026F, 12.0F, 14.4026F)

                moveTo(17.0F, 11.2026F)
                curveTo(17.6F, 10.9859F, 18.0833F, 10.6234F, 18.45F, 10.1151F)
                curveTo(18.8167F, 9.60675F, 19.0F, 9.03592F, 19.0F, 8.40259F)
                verticalLineTo(7.40259F)
                horizontalLineTo(17.0F)
                verticalLineTo(11.2026F)
                close()
            }
        }
        return _awardOutlined!!
    }
private var _awardOutlined: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Theme.AwardOutlined, contentDescription = null)