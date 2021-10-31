package ua.graviton.isida.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val IsidaTypography = Typography(
    defaultFontFamily = FontFamily.Default,

//    button = TextStyle(
//        fontFamily = FavoriteExtended,
//        fontWeight = FontWeight.W700,
//        fontSize = 16.sp,
//        lineHeight = 23.sp,
//    ),
    caption = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    )
)