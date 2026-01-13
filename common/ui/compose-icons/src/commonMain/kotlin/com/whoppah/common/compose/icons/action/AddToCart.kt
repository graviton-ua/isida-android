package com.whoppah.common.compose.icons.action

import androidx.compose.foundation.Image
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Action.AddToCart: ImageVector
    get() {
        if (_addToCart != null) return _addToCart!!
        _addToCart = materialIcon(name = "Action.AddToCart") {
            materialPath {
                verticalLineToRelative(0.0F)
                moveTo(13.3004F, 9.40259F)
                curveTo(13.3004F, 9.87402F, 12.9142F, 10.2597F, 12.4421F, 10.2597F)
                curveTo(11.97F, 10.2597F, 11.5838F, 9.87402F, 11.5838F, 9.40259F)
                verticalLineTo(7.6883F)
                horizontalLineTo(9.86712F)
                curveTo(9.39504F, 7.6883F, 9.00879F, 7.30259F, 9.00879F, 6.83116F)
                curveTo(9.00879F, 6.35973F, 9.39504F, 5.97402F, 9.86712F, 5.97402F)
                horizontalLineTo(11.5838F)
                verticalLineTo(4.25973F)
                curveTo(11.5838F, 3.7883F, 11.97F, 3.40259F, 12.4421F, 3.40259F)
                curveTo(12.9142F, 3.40259F, 13.3004F, 3.7883F, 13.3004F, 4.25973F)
                verticalLineTo(5.97402F)
                horizontalLineTo(15.0171F)
                curveTo(15.4892F, 5.97402F, 15.8754F, 6.35973F, 15.8754F, 6.83116F)
                curveTo(15.8754F, 7.30259F, 15.4892F, 7.6883F, 15.0171F, 7.6883F)
                horizontalLineTo(13.3004F)
                verticalLineTo(9.40259F)

                moveTo(6.44238F, 19.6883F)
                curveTo(6.44238F, 18.7454F, 7.2063F, 17.974F, 8.15046F, 17.974F)
                curveTo(9.09462F, 17.974F, 9.86712F, 18.7454F, 9.86712F, 19.6883F)
                curveTo(9.86712F, 20.6311F, 9.09462F, 21.4026F, 8.15046F, 21.4026F)
                curveTo(7.2063F, 21.4026F, 6.44238F, 20.6311F, 6.44238F, 19.6883F)

                moveTo(16.7333F, 17.974F)
                curveTo(15.7892F, 17.974F, 15.0252F, 18.7454F, 15.0252F, 19.6883F)
                curveTo(15.0252F, 20.6312F, 15.7892F, 21.4026F, 16.7333F, 21.4026F)
                curveTo(17.6775F, 21.4026F, 18.45F, 20.6312F, 18.45F, 19.6883F)
                curveTo(18.45F, 18.7454F, 17.6775F, 17.974F, 16.7333F, 17.974F)

                moveTo(15.4887F, 13.6883F)
                horizontalLineTo(9.09415F)
                lineTo(8.14998F, 15.4026F)
                horizontalLineTo(17.5916F)
                curveTo(18.0637F, 15.4026F, 18.45F, 15.7883F, 18.45F, 16.2597F)
                curveTo(18.45F, 16.7312F, 18.0637F, 17.1169F, 17.5916F, 17.1169F)
                horizontalLineTo(8.14998F)
                curveTo(6.84532F, 17.1169F, 6.02132F, 15.7197F, 6.6479F, 14.5712F)
                lineTo(7.80665F, 12.4797F)
                lineTo(4.71666F, 5.97401F)
                horizontalLineTo(3.85833F)
                curveTo(3.38625F, 5.97401F, 3.0F, 5.5883F, 3.0F, 5.11687F)
                curveTo(3.0F, 4.64544F, 3.38625F, 4.25972F, 3.85833F, 4.25972F)
                horizontalLineTo(5.26599F)
                curveTo(5.59216F, 4.25972F, 5.90116F, 4.4483F, 6.03849F, 4.7483F)
                lineTo(9.46323F, 11.974F)
                horizontalLineTo(15.4887F)
                lineTo(18.3985F, 6.71972F)
                curveTo(18.6216F, 6.3083F, 19.1452F, 6.16258F, 19.5572F, 6.38544F)
                curveTo(19.9692F, 6.61687F, 20.1237F, 7.13972F, 19.8919F, 7.55115F)
                lineTo(16.9908F, 12.8054F)
                curveTo(16.699F, 13.3369F, 16.1325F, 13.6883F, 15.4887F, 13.6883F)

                close()
            }
        }
        return _addToCart!!
    }

private var _addToCart: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Image(imageVector = WhIcons.Action.AddToCart, contentDescription = null)
}