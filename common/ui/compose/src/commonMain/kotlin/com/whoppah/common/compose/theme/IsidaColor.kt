package com.whoppah.common.compose.theme

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

object IsidaColor {
    @Stable val BlueGrey100 = Color(0xFFCFD8DC)
    @Stable val Blue500 = Color(0xFF5085FC)
    @Stable val Blue100 = Color(0xFFB4CCFF)

    @Stable val Red100 = Color(0xFFFFD7D5)
    @Stable val Red500 = Color(0xFFF66F63)
    @Stable val Red900 = Color(0xFFB71C1C)
    @Stable val Green100 = Color(0xFFBEF8C2)

    @Stable val Green500 = Color(0xFF7AF880)

    @Stable val Yellow500 = Color(0xFFFFEB3B)
    @Stable val Yellow900 = Color(0xFFFCE704)
    @Stable val Indigo100 = Color(0xFF737AA0)

    @Stable val Indigo800 = Color(0xFF283593)

    // Legacy
    //TODO: Remove these colors in future updates
    @Stable val Power = Color(0xFFE91E63)

    val neutral000 = Color.Black // Color(0xFF000000)
    val neutral035 = Color(0xFF585858)
    val neutral055 = Color(0xFF8C8C8C)
    val neutral075 = Color(0xFFBFBFBF)
    val neutral085 = Color(0xFFDADADA)
    val neutral095 = Color(0xFFF2F2F2)
    val neutral100 = Color.White // Color(0xFFFFFFFF)
    val error = Color(0xFFFF0000)
    val error050 = Color(0xFFFF8080)
    val signal = Color(0xFFFFE600)
    val success = Color(0xFF8FFF00)

    // Content colors
    val lila = Color(0xFF99BDF3)
    val orange = Color(0xFFFFA36F)
    val green = Color(0xFF99F3D3)
    val rose = Color(0xFFF39999)
    val gelb = Color(0xFFFFCC00)
}