package ua.graviton.isida.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = neutral000,
    primaryVariant = neutral000,
    secondary = gelb,
    background = neutral085,
    surface = Color.White,
    error = error,

    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.White,
)

private val LightColorPalette = lightColors(
    primary = neutral000,
    primaryVariant = neutral000,
    secondary = gelb,
    background = neutral085,
    surface = Color.White,
    error = error,

    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.White,
)

@Composable
fun IsidaTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = IsidaTypography,
        shapes = IsidaShapes,
        content = content
    )
}