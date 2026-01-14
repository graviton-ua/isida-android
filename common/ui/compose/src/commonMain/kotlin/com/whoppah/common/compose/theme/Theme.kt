package com.whoppah.common.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember

@Composable
fun WhoppahTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    // For now we not gonna support dark theme
    val localWhoppahColors: WhoppahColorsScheme = if (darkTheme) WhoppahDarkColorsScheme else WhoppahLightColorsScheme

    val typography = rememberWhoppahTypography()

    CompositionLocalProvider(
        LocalWhoppahColor provides localWhoppahColors,
        LocalWhoppahTypography provides typography,
        LocalWhoppahShapes provides WhoppahShapes,
    ) {
        MaterialTheme(
            colorScheme = LocalWhoppahColor.current.asMaterialColors(),
            typography = LocalWhoppahTypography.current.asMaterialTypography(),
            shapes = LocalWhoppahShapes.current.asMaterialShapes(),
        ) {
            val selectionColors = LocalTextSelectionColors.current
            val newSelectionColors = remember(selectionColors) {
                TextSelectionColors(handleColor = localWhoppahColors.tertiary, backgroundColor = selectionColors.backgroundColor)
            }
            CompositionLocalProvider(
                LocalTextSelectionColors provides newSelectionColors,
                content = content,
            )
        }
    }
}