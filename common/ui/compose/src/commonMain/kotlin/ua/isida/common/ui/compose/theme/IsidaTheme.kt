package ua.isida.common.ui.compose.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.font.FontFamily

val LocalIsidaColor: ProvidableCompositionLocal<IsidaColorsScheme> =
    staticCompositionLocalOf { IsidaLightColorsScheme }

val LocalIsidaTypography: ProvidableCompositionLocal<IsidaTypography> =
    staticCompositionLocalOf { IsidaTypography(filsonPro = FontFamily.Default, notoSans = FontFamily.Default) }

val LocalIsidaShapes: ProvidableCompositionLocal<IsidaShapes> =
    staticCompositionLocalOf { IsidaShapes }

object IsidaTheme {
    val colors: IsidaColorsScheme
        @Composable @ReadOnlyComposable get() = LocalIsidaColor.current

    val typography: IsidaTypography
        @Composable @ReadOnlyComposable get() = LocalIsidaTypography.current

    val shapes: IsidaShapes
        @Composable @ReadOnlyComposable get() = LocalIsidaShapes.current
}