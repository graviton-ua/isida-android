package com.whoppah.common.compose.theme

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.whoppah.common.compose.ui.WhBrushButtonColors

interface WhoppahColorsScheme {
    val primary: Color
    val onPrimary: Color
    val primaryContainer: Color
    val onPrimaryContainer: Color

    val secondary: Color
    val onSecondary: Color
    val secondaryContainer: Color
    val onSecondaryContainer: Color

    val tertiary: Color
    val onTertiary: Color
    val tertiaryContainer: Color
    val onTertiaryContainer: Color

    val background: Color
    val onBackground: Color

    val surface: Color
    val onSurface: Color
    val surfaceVariant: Color
    val onSurfaceVariant: Color

    val error: Color
    val onError: Color

    val outline: Color
    val outlineVariant: Color
    val scrim: Color

    val brenger: Color
    val link: Color

    val toastContainer: Color
    val toastContent: Color
    val toastAction: Color
    val toastSuccessContainer: Color
    val toastSuccessContent: Color
    val toastSuccessAction: Color
    val toastErrorContainer: Color
    val toastErrorContent: Color
    val toastErrorAction: Color

    val isLight: Boolean


    var defaultButtonColorsCached: ButtonColors?
    var defaultButtonColorsInvertCached: ButtonColors?
    var defaultButtonColorsSecondaryCached: ButtonColors?
    var defaultButtonColorsTertiaryCached: ButtonColors?
    var defaultButtonColorsTertiaryInvertCached: ButtonColors?
    var defaultButtonColorsAiCached: WhBrushButtonColors?
    var defaultOutlinedButtonColorsCached: ButtonColors?
    var defaultOutlinedButtonColorsSecondaryCached: ButtonColors?
    var defaultOutlinedButtonColorsTertiaryCached: ButtonColors?


    @Stable
    fun WhoppahColorsScheme.contentColorFor(backgroundColor: Color): Color =
        when (backgroundColor) {
            primary -> onPrimary
            secondary -> onSecondary
            tertiary -> onTertiary
            background -> onBackground
            error -> onError
            primaryContainer -> onPrimaryContainer
            secondaryContainer -> onSecondaryContainer
            tertiaryContainer -> onTertiaryContainer
            //errorContainer -> onErrorContainer
            //inverseSurface -> inverseOnSurface
            surface -> onSurface
            surfaceVariant -> onSurfaceVariant
            //surfaceBright -> onSurface
            //surfaceContainer -> onSurface
            //surfaceContainerHigh -> onSurface
            //surfaceContainerHighest -> onSurface
            //surfaceContainerLow -> onSurface
            //surfaceContainerLowest -> onSurface
            //surfaceDim -> onSurface
            //primaryFixed -> onPrimaryFixed
            //primaryFixedDim -> onPrimaryFixed
            //secondaryFixed -> onSecondaryFixed
            //secondaryFixedDim -> onSecondaryFixed
            //tertiaryFixed -> onTertiaryFixed
            //tertiaryFixedDim -> onTertiaryFixed
            else -> Color.Unspecified
        }


    fun asMaterialColors(): ColorScheme = lightColorScheme(
        primary = primary,
        onPrimary = onPrimary,
        primaryContainer = primaryContainer,
        onPrimaryContainer = onPrimaryContainer,
        inversePrimary = primary,

        secondary = secondary,
        onSecondary = onSecondary,
        secondaryContainer = secondaryContainer,
        onSecondaryContainer = onSecondaryContainer,

        tertiary = tertiary,
        onTertiary = onTertiary,
        tertiaryContainer = tertiaryContainer,
        onTertiaryContainer = onTertiaryContainer,

        background = background,
        onBackground = onBackground,

        surface = surface,
        onSurface = onSurface,
        surfaceVariant = surfaceVariant,
        onSurfaceVariant = onSurfaceVariant,
        surfaceTint = surface,
        inverseSurface = surface,
        inverseOnSurface = onSurface,

        error = error,
        onError = onError,
        errorContainer = error,
        onErrorContainer = onError,

        outline = outline,
        outlineVariant = outlineVariant,
        scrim = scrim,

        surfaceContainer = surface,
        surfaceContainerHigh = surface,
        surfaceContainerHighest = surface,
        surfaceContainerLow = surface,
        surfaceContainerLowest = surface,
    )
}


// Light theme
object WhoppahLightColorsScheme : WhoppahColorsScheme {
    override val primary: Color = WhoppahPalette.Brand500
    override val onPrimary: Color = WhoppahPalette.White
    override val primaryContainer: Color = WhoppahPalette.Flavour100
    override val onPrimaryContainer: Color = primary

    override val secondary: Color = WhoppahPalette.Attention500
    override val onSecondary: Color = WhoppahPalette.White
    override val secondaryContainer: Color = WhoppahPalette.Attention50
    override val onSecondaryContainer: Color = secondary

    override val tertiary: Color = WhoppahPalette.Curious500
    override val onTertiary: Color = WhoppahPalette.White
    override val tertiaryContainer: Color = WhoppahPalette.Curious50
    override val onTertiaryContainer: Color = tertiary

    override val background: Color = WhoppahPalette.White
    override val onBackground: Color = WhoppahPalette.Black

    override val surface: Color = background
    override val onSurface: Color = onBackground
    override val surfaceVariant: Color = WhoppahPalette.Flavour50
    override val onSurfaceVariant: Color = WhoppahPalette.Grey800

    override val error: Color = WhoppahPalette.Attention500
    override val onError: Color = WhoppahPalette.White

    override val outline: Color = WhoppahPalette.Grey500
    override val outlineVariant: Color = WhoppahPalette.Grey300
    override val scrim: Color = WhoppahPalette.Black

    override val brenger: Color = WhoppahPalette.Brenger
    override val link: Color = primary

    override val toastContainer: Color = WhoppahPalette.Brand900
    override val toastContent: Color = WhoppahPalette.White
    override val toastAction: Color = WhoppahPalette.Flavour500
    override val toastSuccessContainer: Color = WhoppahPalette.Brand700
    override val toastSuccessContent: Color = WhoppahPalette.White
    override val toastSuccessAction: Color = WhoppahPalette.Flavour500
    override val toastErrorContainer: Color = WhoppahPalette.Attention900
    override val toastErrorContent: Color = WhoppahPalette.White
    override val toastErrorAction: Color = WhoppahPalette.Attention300

    override val isLight: Boolean = true


    override var defaultButtonColorsCached: ButtonColors? = null
    override var defaultButtonColorsInvertCached: ButtonColors? = null
    override var defaultButtonColorsSecondaryCached: ButtonColors? = null
    override var defaultButtonColorsTertiaryCached: ButtonColors? = null
    override var defaultButtonColorsTertiaryInvertCached: ButtonColors? = null
    override var defaultButtonColorsAiCached: WhBrushButtonColors? = null
    override var defaultOutlinedButtonColorsCached: ButtonColors? = null
    override var defaultOutlinedButtonColorsSecondaryCached: ButtonColors? = null
    override var defaultOutlinedButtonColorsTertiaryCached: ButtonColors? = null
}

// Dark theme
object WhoppahDarkColorsScheme : WhoppahColorsScheme {
    override val primary: Color = WhoppahPalette.Brand500
    override val onPrimary: Color = WhoppahPalette.White
    override val primaryContainer: Color = WhoppahPalette.Flavour100
    override val onPrimaryContainer: Color = primary

    override val secondary: Color = WhoppahPalette.Attention500
    override val onSecondary: Color = WhoppahPalette.White
    override val secondaryContainer: Color = WhoppahPalette.Attention50
    override val onSecondaryContainer: Color = secondary

    override val tertiary: Color = WhoppahPalette.Curious500
    override val onTertiary: Color = WhoppahPalette.White
    override val tertiaryContainer: Color = WhoppahPalette.Curious50
    override val onTertiaryContainer: Color = tertiary

    override val background: Color = WhoppahPalette.White
    override val onBackground: Color = WhoppahPalette.Black

    override val surface: Color = background
    override val onSurface: Color = onBackground
    override val surfaceVariant: Color = WhoppahPalette.Flavour50
    override val onSurfaceVariant: Color = WhoppahPalette.Grey800

    override val error: Color = WhoppahPalette.Attention500
    override val onError: Color = WhoppahPalette.White

    override val outline: Color = WhoppahPalette.Grey500
    override val outlineVariant: Color = WhoppahPalette.Grey300
    override val scrim: Color = WhoppahPalette.Black

    override val brenger: Color = WhoppahPalette.Brenger
    override val link: Color = primary

    override val toastContainer: Color = WhoppahPalette.Brand900
    override val toastContent: Color = WhoppahPalette.White
    override val toastAction: Color = WhoppahPalette.Flavour500
    override val toastSuccessContainer: Color = WhoppahPalette.Brand700
    override val toastSuccessContent: Color = WhoppahPalette.White
    override val toastSuccessAction: Color = WhoppahPalette.Flavour500
    override val toastErrorContainer: Color = WhoppahPalette.Attention900
    override val toastErrorContent: Color = WhoppahPalette.White
    override val toastErrorAction: Color = WhoppahPalette.Attention300

    override val isLight: Boolean = true


    override var defaultButtonColorsCached: ButtonColors? = null
    override var defaultButtonColorsInvertCached: ButtonColors? = null
    override var defaultButtonColorsSecondaryCached: ButtonColors? = null
    override var defaultButtonColorsTertiaryCached: ButtonColors? = null
    override var defaultButtonColorsTertiaryInvertCached: ButtonColors? = null
    override var defaultButtonColorsAiCached: WhBrushButtonColors? = null
    override var defaultOutlinedButtonColorsCached: ButtonColors? = null
    override var defaultOutlinedButtonColorsSecondaryCached: ButtonColors? = null
    override var defaultOutlinedButtonColorsTertiaryCached: ButtonColors? = null
}