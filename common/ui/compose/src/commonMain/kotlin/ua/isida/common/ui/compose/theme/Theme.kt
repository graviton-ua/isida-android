package ua.isida.common.ui.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember

@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    // For now we not gonna support dark theme
    val localIsidaColors: IsidaColorsScheme = if (darkTheme) IsidaDarkColorsScheme else IsidaLightColorsScheme

    val typography = rememberIsidaTypography()

    CompositionLocalProvider(
        LocalIsidaColor provides localIsidaColors,
        LocalIsidaTypography provides typography,
        LocalIsidaShapes provides IsidaShapes,
    ) {
        MaterialTheme(
            colorScheme = LocalIsidaColor.current.asMaterialColors(),
            typography = LocalIsidaTypography.current.asMaterialTypography(),
            shapes = LocalIsidaShapes.current.asMaterialShapes(),
        ) {
            val selectionColors = LocalTextSelectionColors.current
            val newSelectionColors = remember(selectionColors) {
                TextSelectionColors(handleColor = localIsidaColors.tertiary, backgroundColor = selectionColors.backgroundColor)
            }
            CompositionLocalProvider(
                LocalTextSelectionColors provides newSelectionColors,
                content = content,
            )
        }
    }
}