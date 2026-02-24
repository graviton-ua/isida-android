package ua.isida.common.ui.compose.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

//private object Family {
//    val FilsonPro = FontFamily(
//        Font(R.font.filson_pro_regular, weight = FontWeight.Normal),
//        Font(R.font.filson_pro_bold, weight = FontWeight.Bold),
//    )
//    val NotoSans = FontFamily(
//        Font(R.font.noto_sans_regular, weight = FontWeight.Normal),
//        Font(R.font.noto_sans_bold, weight = FontWeight.Bold),
//    )
//}

@Composable
fun rememberWhoppahTypography(): IsidaTypography {
    // val filsonProRegular = Font(Res.font.filson_pro_regular, weight = FontWeight.Normal)
    // val filsonProBold = Font(Res.font.filson_pro_bold, weight = FontWeight.Bold)
    // val notoSansRegular = Font(Res.font.noto_sans_regular, weight = FontWeight.Normal)
    // val notoSansBold = Font(Res.font.noto_sans_bold, weight = FontWeight.Bold)

    // val filsonPro = remember(filsonProRegular, filsonProBold) {
    //     FontFamily(filsonProRegular, filsonProBold)
    // }
    // val notoSans = remember(notoSansRegular, notoSansBold) {
    //     FontFamily(notoSansRegular, notoSansBold)
    // }

    return remember {
        IsidaTypography(filsonPro = FontFamily.Default, notoSans = FontFamily.SansSerif)
    }
}

@Immutable
class IsidaTypography(
    filsonPro: FontFamily,
    notoSans: FontFamily,
) {
    val h1 = TextStyle(
        fontFamily = filsonPro,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
    )
    val h2 = TextStyle(
        fontFamily = filsonPro,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
    )
    val h3 = TextStyle(
        fontFamily = filsonPro,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
    )
    val h4 = TextStyle(
        fontFamily = filsonPro,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
    )
    val h5 = TextStyle(
        fontFamily = filsonPro,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
    )
    val h6 = TextStyle(
        fontFamily = filsonPro,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
    )
    val button = TextStyle(
        fontFamily = filsonPro,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
    )
    val label = TextStyle(
        fontFamily = filsonPro,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    )

    val body = TextStyle(
        fontFamily = notoSans,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    )
    val helper = TextStyle(
        fontFamily = notoSans,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    )
    val link = TextStyle(
        fontFamily = notoSans,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        textDecoration = TextDecoration.Underline,
    )

    @Composable @ReadOnlyComposable
    fun textLink(color: Color = IsidaTheme.colors.link): TextLinkStyles {
        return TextLinkStyles(
            style = link.copy(color = color).toSpanStyle(),
        )
    }

    val inputLabel = label

    @Deprecated(message = "Use label typography instead")
    val labelLegacy = TextStyle(
        fontFamily = filsonPro,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
    )

    val flavour1 = TextStyle(
        fontFamily = filsonPro,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    )

    val flavour2 = TextStyle(
        fontFamily = filsonPro,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    )


    fun asMaterialTypography(): Typography = Typography(
        displayLarge = h1,
        displayMedium = h2,
        displaySmall = h3,
        headlineLarge = h4,
        headlineMedium = h4,
        headlineSmall = h5,
        titleLarge = h6,
        titleMedium = body, //subtitle1
        titleSmall = body,  //subtitle2
        bodyLarge = body,   //body1
        bodyMedium = body,  //body2
        bodySmall = helper, //caption
        labelLarge = button,  //button
        labelMedium = button,
        labelSmall = helper,   //overline
    )
}