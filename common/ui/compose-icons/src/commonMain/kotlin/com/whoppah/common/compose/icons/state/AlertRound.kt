package com.whoppah.common.compose.icons.state

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.State.AlertRound: ImageVector
    get() {
        if (_accessTime != null) return _accessTime!!
        _accessTime = materialIcon(name = "State.AlertRound") {
            materialPath {
                moveTo(11.0F, 15.0178F)
                horizontalLineTo(13.0F)
                verticalLineTo(17.0178F)
                horizontalLineTo(11.0F)
                verticalLineTo(15.0178F)

                moveTo(11.0F, 7.01782F)
                horizontalLineTo(13.0F)
                verticalLineTo(13.0178F)
                horizontalLineTo(11.0F)
                verticalLineTo(7.01782F)

                moveTo(12.0F, 2.01782F)
                curveTo(6.47F, 2.01782F, 2.0F, 6.51782F, 2.0F, 12.0178F)
                curveTo(2.0F, 14.67F, 3.05357F, 17.2135F, 4.92893F, 19.0889F)
                curveTo(5.85752F, 20.0175F, 6.95991F, 20.7541F, 8.17317F, 21.2566F)
                curveTo(9.38642F, 21.7592F, 10.6868F, 22.0178F, 12.0F, 22.0178F)
                curveTo(14.6522F, 22.0178F, 17.1957F, 20.9643F, 19.0711F, 19.0889F)
                curveTo(20.9464F, 17.2135F, 22.0F, 14.67F, 22.0F, 12.0178F)
                curveTo(22.0F, 10.7046F, 21.7413F, 9.40424F, 21.2388F, 8.19099F)
                curveTo(20.7362F, 6.97773F, 19.9997F, 5.87534F, 19.0711F, 4.94675F)
                curveTo(18.1425F, 4.01817F, 17.0401F, 3.28157F, 15.8268F, 2.77903F)
                curveTo(14.6136F, 2.27648F, 13.3132F, 2.01782F, 12.0F, 2.01782F)

                moveTo(12.0F, 20.0178F)
                curveTo(9.87827F, 20.0178F, 7.84344F, 19.175F, 6.34315F, 17.6747F)
                curveTo(4.84285F, 16.1744F, 4.0F, 14.1396F, 4.0F, 12.0178F)
                curveTo(4.0F, 9.89609F, 4.84285F, 7.86126F, 6.34315F, 6.36097F)
                curveTo(7.84344F, 4.86068F, 9.87827F, 4.01782F, 12.0F, 4.01782F)
                curveTo(14.1217F, 4.01782F, 16.1566F, 4.86068F, 17.6569F, 6.36097F)
                curveTo(19.1571F, 7.86126F, 20.0F, 9.89609F, 20.0F, 12.0178F)
                curveTo(20.0F, 14.1396F, 19.1571F, 16.1744F, 17.6569F, 17.6747F)
                curveTo(16.1566F, 19.175F, 14.1217F, 20.0178F, 12.0F, 20.0178F)
                close()
            }
        }
        return _accessTime!!
    }
private var _accessTime: ImageVector? = null

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.State.AlertRound, contentDescription = null)