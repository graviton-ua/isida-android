package com.whoppah.common.compose.ui

import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import com.whoppah.common.compose.theme.WhoppahColorsScheme
import com.whoppah.common.compose.theme.WhoppahPalette
import com.whoppah.common.compose.theme.WhoppahTheme

object WhButtonDefaults {
    //region Filled
    /**
     * Creates a [ButtonColors] that represents the default colors used in
     * a [Button] in different states.
     *
     * @see WhButtonDefaults.buttonColorsSecondary for secondary colors
     * @see WhButtonDefaults.buttonColorsTertiary for tertiary colors
     */
    @Composable fun buttonColors() = WhoppahTheme.colors.defaultButtonColors

    /**
     * Creates a [ButtonColors] that represents the default colors used in
     * a [Button] in different states.
     *
     * @param containerColor the background color of this button when enabled.
     * @param contentColor the content color of this button when enabled.
     * @param disabledContainerColor the background color of this button when not enabled.
     * @param disabledContentColor the content color of this button when not enabled.
     */
    @Composable
    fun buttonColors(
        containerColor: Color = Color.Unspecified,
        contentColor: Color = Color.Unspecified,
        disabledContainerColor: Color = Color.Unspecified,
        disabledContentColor: Color = Color.Unspecified,
    ): ButtonColors = WhoppahTheme.colors.defaultButtonColors.copy(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )

    private val WhoppahColorsScheme.defaultButtonColors: ButtonColors
        get() {
            return defaultButtonColorsCached ?: ButtonColors(
                containerColor = primary,
                contentColor = contentColorFor(primary),
                disabledContainerColor = WhoppahPalette.Grey100,
                disabledContentColor = WhoppahPalette.Grey500,
            ).also { defaultButtonColorsCached = it }
        }


    /**
     * Creates a [ButtonColors] that represents the default colors used in a  [Button]
     * in different states.
     *
     * @see WhButtonDefaults.buttonColors for primary colors
     * @see WhButtonDefaults.buttonColorsSecondary for secondary colors
     */
    @Composable fun buttonColorsInvert() = WhoppahTheme.colors.defaultButtonColorsInvert

    /**
     * Creates a [ButtonColors] that represents the default colors used in a  [Button]
     * in different states.
     *
     * @param containerColor the background color of this button when enabled.
     * @param contentColor the content color of this button when enabled.
     * @param disabledContainerColor the background color of this button when not enabled.
     * @param disabledContentColor the content color of this button when not enabled.
     */
    @Composable
    fun buttonColorsInvert(
        containerColor: Color = Color.Unspecified,
        contentColor: Color = Color.Unspecified,
        disabledContainerColor: Color = Color.Unspecified,
        disabledContentColor: Color = Color.Unspecified,
    ): ButtonColors = WhoppahTheme.colors.defaultButtonColorsInvert.copy(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )

    private val WhoppahColorsScheme.defaultButtonColorsInvert: ButtonColors
        get() {
            return defaultButtonColorsInvertCached ?: ButtonColors(
                containerColor = contentColorFor(primary),
                contentColor = primary,
                disabledContainerColor = WhoppahPalette.Grey100,
                disabledContentColor = WhoppahPalette.Grey500,
            ).also { defaultButtonColorsInvertCached = it }
        }

    /**
     * Creates a [ButtonColors] that represents the default colors used in
     * a secondary [Button] in different states.
     *
     * @see WhButtonDefaults.buttonColors for primary colors
     * @see WhButtonDefaults.buttonColorsTertiary for tertiary colors
     */
    @Composable fun buttonColorsSecondary() = WhoppahTheme.colors.defaultButtonColorsSecondary

    /**
     * Creates a [ButtonColors] that represents the default secondary colors used in
     * a [Button] in different states.
     *
     * @param containerColor the background color of this [Button] when enabled
     * @param contentColor the content color of this [Button] when enabled
     * @param disabledContainerColor the background color of this [Button] when not enabled
     * @param disabledContentColor the content color of this [Button] when not enabled
     */
    @Composable
    fun buttonColorsSecondary(
        containerColor: Color = Color.Unspecified,
        contentColor: Color = Color.Unspecified,
        disabledContainerColor: Color = Color.Unspecified,
        disabledContentColor: Color = Color.Unspecified,
    ): ButtonColors = WhoppahTheme.colors.defaultButtonColorsSecondary.copy(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )

    private val WhoppahColorsScheme.defaultButtonColorsSecondary: ButtonColors
        get() {
            return defaultButtonColorsSecondaryCached ?: ButtonColors(
                containerColor = secondary,
                contentColor = contentColorFor(secondary),
                disabledContainerColor = WhoppahPalette.Grey100,
                disabledContentColor = WhoppahPalette.Grey500,
            ).also { defaultButtonColorsSecondaryCached = it }
        }


    /**
     * Creates a [ButtonColors] that represents the default colors used in a tertiary [Button]
     * in different states.
     *
     * @see WhButtonDefaults.buttonColors for primary colors
     * @see WhButtonDefaults.buttonColorsSecondary for secondary colors
     */
    @Composable fun buttonColorsTertiary() = WhoppahTheme.colors.defaultButtonColorsTertiary

    /**
     * Creates a [ButtonColors] that represents the default colors used in a tertiary [Button]
     * in different states.
     *
     * @param containerColor the background color of this button when enabled.
     * @param contentColor the content color of this button when enabled.
     * @param disabledContainerColor the background color of this button when not enabled.
     * @param disabledContentColor the content color of this button when not enabled.
     */
    @Composable
    fun buttonColorsTertiary(
        containerColor: Color = Color.Unspecified,
        contentColor: Color = Color.Unspecified,
        disabledContainerColor: Color = Color.Unspecified,
        disabledContentColor: Color = Color.Unspecified,
    ): ButtonColors = WhoppahTheme.colors.defaultButtonColorsTertiary.copy(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )

    private val WhoppahColorsScheme.defaultButtonColorsTertiary: ButtonColors
        get() {
            return defaultButtonColorsTertiaryCached ?: ButtonColors(
                containerColor = tertiary,
                contentColor = contentColorFor(tertiary),
                disabledContainerColor = WhoppahPalette.Grey100,
                disabledContentColor = WhoppahPalette.Grey500,
            ).also { defaultButtonColorsTertiaryCached = it }
        }


    /**
     * Creates a [ButtonColors] that represents the default colors used in a tertiary [Button]
     * in different states.
     *
     * @see WhButtonDefaults.buttonColors for primary colors
     * @see WhButtonDefaults.buttonColorsSecondary for secondary colors
     */
    @Composable fun buttonColorsTertiaryInvert() = WhoppahTheme.colors.defaultButtonColorsTertiaryInvert

    /**
     * Creates a [ButtonColors] that represents the default colors used in a tertiary [Button]
     * in different states.
     *
     * @param containerColor the background color of this button when enabled.
     * @param contentColor the content color of this button when enabled.
     * @param disabledContainerColor the background color of this button when not enabled.
     * @param disabledContentColor the content color of this button when not enabled.
     */
    @Composable
    fun buttonColorsTertiaryInvert(
        containerColor: Color = Color.Unspecified,
        contentColor: Color = Color.Unspecified,
        disabledContainerColor: Color = Color.Unspecified,
        disabledContentColor: Color = Color.Unspecified,
    ): ButtonColors = WhoppahTheme.colors.defaultButtonColorsTertiaryInvert.copy(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )

    private val WhoppahColorsScheme.defaultButtonColorsTertiaryInvert: ButtonColors
        get() {
            return defaultButtonColorsTertiaryInvertCached ?: ButtonColors(
                containerColor = contentColorFor(tertiary),
                contentColor = tertiary,
                disabledContainerColor = WhoppahPalette.Grey100,
                disabledContentColor = WhoppahPalette.Grey500,
            ).also { defaultButtonColorsTertiaryInvertCached = it }
        }


    @Composable fun buttonColorsAi() = WhoppahTheme.colors.defaultButtonColorsAi

    @Composable
    fun buttonColorsAi(
        containerBrush: Brush? = null,
        contentColor: Color = Color.Unspecified,
        disabledContainerBrush: Brush? = null,
        disabledContentColor: Color = Color.Unspecified,
    ): WhBrushButtonColors = WhoppahTheme.colors.defaultButtonColorsAi.copy(
        containerBrush = containerBrush,
        contentColor = contentColor,
        disabledContainerBrush = disabledContainerBrush,
        disabledContentColor = disabledContentColor,
    )

    private val WhoppahColorsScheme.defaultButtonColorsAi: WhBrushButtonColors
        get() {
            return defaultButtonColorsAiCached ?: WhBrushButtonColors(
                containerBrush = WhoppahPalette.AIGradient,
                contentColor = onPrimary,
                disabledContainerBrush = SolidColor(WhoppahPalette.Grey100),
                disabledContentColor = WhoppahPalette.Grey500,
            ).also { defaultButtonColorsAiCached = it }
        }
    //endregion

    //region Outlined
    @Composable fun outlinedButtonColors() = WhoppahTheme.colors.defaultOutlinedButtonColors

    @Composable
    fun outlinedButtonColors(
        containerColor: Color = Color.Unspecified,
        contentColor: Color = Color.Unspecified,
        disabledContainerColor: Color = Color.Unspecified,
        disabledContentColor: Color = Color.Unspecified,
    ): ButtonColors = WhoppahTheme.colors.defaultOutlinedButtonColors.copy(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )

    private val WhoppahColorsScheme.defaultOutlinedButtonColors: ButtonColors
        get() {
            return defaultOutlinedButtonColorsCached ?: ButtonColors(
                containerColor = Color.Transparent,
                contentColor = primary,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = WhoppahPalette.Grey500,
            ).also { defaultOutlinedButtonColorsCached = it }
        }

    @Composable fun outlinedButtonColorsSecondary() = WhoppahTheme.colors.defaultOutlinedButtonColorsSecondary

    @Composable
    fun outlinedButtonColorsSecondary(
        containerColor: Color = Color.Unspecified,
        contentColor: Color = Color.Unspecified,
        disabledContainerColor: Color = Color.Unspecified,
        disabledContentColor: Color = Color.Unspecified,
    ): ButtonColors = WhoppahTheme.colors.defaultOutlinedButtonColorsSecondary.copy(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )

    private val WhoppahColorsScheme.defaultOutlinedButtonColorsSecondary: ButtonColors
        get() {
            return defaultOutlinedButtonColorsSecondaryCached ?: ButtonColors(
                containerColor = Color.Transparent,
                contentColor = secondary,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = WhoppahPalette.Grey500,
            ).also { defaultOutlinedButtonColorsSecondaryCached = it }
        }


    @Composable fun outlinedButtonColorsTertiary() = WhoppahTheme.colors.defaultOutlinedButtonColorsTertiary

    @Composable
    fun outlinedButtonColorsTertiary(
        containerColor: Color = Color.Unspecified,
        contentColor: Color = Color.Unspecified,
        disabledContainerColor: Color = Color.Unspecified,
        disabledContentColor: Color = Color.Unspecified,
    ): ButtonColors = WhoppahTheme.colors.defaultOutlinedButtonColorsTertiary.copy(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )

    private val WhoppahColorsScheme.defaultOutlinedButtonColorsTertiary: ButtonColors
        get() {
            return defaultOutlinedButtonColorsTertiaryCached ?: ButtonColors(
                containerColor = Color.Transparent,
                contentColor = tertiary,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = WhoppahPalette.Grey500,
            ).also { defaultOutlinedButtonColorsTertiaryCached = it }
        }
    //endregion

    //region Text
    @Composable fun textButtonColors() = outlinedButtonColors()

    @Composable
    fun textButtonColors(
        containerColor: Color = Color.Unspecified,
        contentColor: Color = Color.Unspecified,
        disabledContainerColor: Color = Color.Unspecified,
        disabledContentColor: Color = Color.Unspecified,
    ): ButtonColors = outlinedButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )

    @Composable fun textButtonColorsSecondary() = outlinedButtonColorsSecondary()

    @Composable
    fun textButtonColorsSecondary(
        containerColor: Color = Color.Unspecified,
        contentColor: Color = Color.Unspecified,
        disabledContainerColor: Color = Color.Unspecified,
        disabledContentColor: Color = Color.Unspecified,
    ): ButtonColors = outlinedButtonColorsSecondary(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )


    @Composable fun textButtonColorsTertiary() = outlinedButtonColorsTertiary()

    @Composable
    fun textButtonColorsTertiary(
        containerColor: Color = Color.Unspecified,
        contentColor: Color = Color.Unspecified,
        disabledContainerColor: Color = Color.Unspecified,
        disabledContentColor: Color = Color.Unspecified,
    ): ButtonColors = outlinedButtonColorsTertiary(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )


    @Composable fun textButtonColorsNeutral() = outlinedButtonColors(
        contentColor = WhoppahTheme.colors.onBackground,
    )
    //endregion
}