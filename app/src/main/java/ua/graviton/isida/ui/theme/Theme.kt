package ua.graviton.isida.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = IsidaColor.neutral000,
    primaryVariant = IsidaColor.neutral000,
    secondary = IsidaColor.gelb,
    background = IsidaColor.neutral085,
    surface = Color.White,
    error = IsidaColor.error,

    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.White,
)

private val LightColorPalette = lightColors(
    primary = IsidaColor.neutral000,
    primaryVariant = IsidaColor.neutral000,
    secondary = IsidaColor.gelb,
    background = IsidaColor.neutral085,
    surface = Color.White,
    error = IsidaColor.error,

    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.White,
)

@Composable
fun IsidaTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    //val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colors = LightColorPalette,
        typography = IsidaTypography,
        shapes = IsidaShapes,
        content = content
    )
}