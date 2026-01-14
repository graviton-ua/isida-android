package com.whoppah.common.compose.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.font.FontFamily

val LocalWhoppahColor: ProvidableCompositionLocal<WhoppahColorsScheme> =
    staticCompositionLocalOf { WhoppahLightColorsScheme }

val LocalWhoppahTypography: ProvidableCompositionLocal<WhoppahTypography> =
    staticCompositionLocalOf { WhoppahTypography(filsonPro = FontFamily.Default, notoSans = FontFamily.Default) }

val LocalWhoppahShapes: ProvidableCompositionLocal<WhoppahShapes> =
    staticCompositionLocalOf { WhoppahShapes }

object WhoppahTheme {
    val colors: WhoppahColorsScheme
        @Composable @ReadOnlyComposable get() = LocalWhoppahColor.current

    val typography: WhoppahTypography
        @Composable @ReadOnlyComposable get() = LocalWhoppahTypography.current

    val shapes: WhoppahShapes
        @Composable @ReadOnlyComposable get() = LocalWhoppahShapes.current
}