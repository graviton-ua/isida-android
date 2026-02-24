package ua.isida.common.ui.compose.theme

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import ua.isida.common.ui.compose.ui.WhBrushButtonColors

interface IsidaColorsScheme {
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
    fun IsidaColorsScheme.contentColorFor(backgroundColor: Color): Color =
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
object IsidaLightColorsScheme : IsidaColorsScheme {
    override val primary: Color = IsidaPalette.Brand500
    override val onPrimary: Color = IsidaPalette.White
    override val primaryContainer: Color = IsidaPalette.Flavour100
    override val onPrimaryContainer: Color = primary

    override val secondary: Color = IsidaPalette.Attention500
    override val onSecondary: Color = IsidaPalette.White
    override val secondaryContainer: Color = IsidaPalette.Attention50
    override val onSecondaryContainer: Color = secondary

    override val tertiary: Color = IsidaPalette.Curious500
    override val onTertiary: Color = IsidaPalette.White
    override val tertiaryContainer: Color = IsidaPalette.Curious50
    override val onTertiaryContainer: Color = tertiary

    override val background: Color = IsidaPalette.White
    override val onBackground: Color = IsidaPalette.Black

    override val surface: Color = background
    override val onSurface: Color = onBackground
    override val surfaceVariant: Color = IsidaPalette.Flavour50
    override val onSurfaceVariant: Color = IsidaPalette.Grey800

    override val error: Color = IsidaPalette.Attention500
    override val onError: Color = IsidaPalette.White

    override val outline: Color = IsidaPalette.Grey500
    override val outlineVariant: Color = IsidaPalette.Grey300
    override val scrim: Color = IsidaPalette.Black

    override val brenger: Color = IsidaPalette.Brenger
    override val link: Color = primary

    override val toastContainer: Color = IsidaPalette.Brand900
    override val toastContent: Color = IsidaPalette.White
    override val toastAction: Color = IsidaPalette.Flavour500
    override val toastSuccessContainer: Color = IsidaPalette.Brand700
    override val toastSuccessContent: Color = IsidaPalette.White
    override val toastSuccessAction: Color = IsidaPalette.Flavour500
    override val toastErrorContainer: Color = IsidaPalette.Attention900
    override val toastErrorContent: Color = IsidaPalette.White
    override val toastErrorAction: Color = IsidaPalette.Attention300

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
object IsidaDarkColorsScheme : IsidaColorsScheme {
    override val primary: Color = IsidaPalette.Brand500
    override val onPrimary: Color = IsidaPalette.White
    override val primaryContainer: Color = IsidaPalette.Flavour100
    override val onPrimaryContainer: Color = primary

    override val secondary: Color = IsidaPalette.Attention500
    override val onSecondary: Color = IsidaPalette.White
    override val secondaryContainer: Color = IsidaPalette.Attention50
    override val onSecondaryContainer: Color = secondary

    override val tertiary: Color = IsidaPalette.Curious500
    override val onTertiary: Color = IsidaPalette.White
    override val tertiaryContainer: Color = IsidaPalette.Curious50
    override val onTertiaryContainer: Color = tertiary

    override val background: Color = IsidaPalette.White
    override val onBackground: Color = IsidaPalette.Black

    override val surface: Color = background
    override val onSurface: Color = onBackground
    override val surfaceVariant: Color = IsidaPalette.Flavour50
    override val onSurfaceVariant: Color = IsidaPalette.Grey800

    override val error: Color = IsidaPalette.Attention500
    override val onError: Color = IsidaPalette.White

    override val outline: Color = IsidaPalette.Grey500
    override val outlineVariant: Color = IsidaPalette.Grey300
    override val scrim: Color = IsidaPalette.Black

    override val brenger: Color = IsidaPalette.Brenger
    override val link: Color = primary

    override val toastContainer: Color = IsidaPalette.Brand900
    override val toastContent: Color = IsidaPalette.White
    override val toastAction: Color = IsidaPalette.Flavour500
    override val toastSuccessContainer: Color = IsidaPalette.Brand700
    override val toastSuccessContent: Color = IsidaPalette.White
    override val toastSuccessAction: Color = IsidaPalette.Flavour500
    override val toastErrorContainer: Color = IsidaPalette.Attention900
    override val toastErrorContent: Color = IsidaPalette.White
    override val toastErrorAction: Color = IsidaPalette.Attention300

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