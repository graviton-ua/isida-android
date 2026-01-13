package com.whoppah.common.compose.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.whoppah.common.compose.theme.ContentAlpha
import com.whoppah.common.compose.theme.WhoppahPalette
import com.whoppah.common.compose.theme.WhoppahTheme

@Stable
interface WhSelectableColors {
    @Composable
    fun contentDefaultColor(enabled: Boolean): State<Color>

    @Composable
    fun contentColor(enabled: Boolean, selected: Boolean): State<Color>

    @Composable
    fun borderColor(enabled: Boolean, selected: Boolean): State<Color>

    @Composable
    fun backgroundColor(enabled: Boolean, selected: Boolean): State<Color>

    @Composable
    fun leadingElementColor(enabled: Boolean, selected: Boolean): State<Color>


    @Composable
    fun trailingElementColor(enabled: Boolean, selected: Boolean): State<Color>
}

@Immutable
object WhSelectableDefaults {

    @Composable
    fun selectableColors(
        contentColor: Color = LocalContentColor.current,
        selectedContentColor: Color = WhoppahTheme.colors.primary,
        disabledTextColor: Color = contentColor.copy(ContentAlpha.disabled),
        backgroundColor: Color = WhoppahTheme.colors.background,
        selectedBackgroundColor: Color = WhoppahPalette.Flavour50,
        borderColor: Color = WhoppahPalette.Grey200,
        selectedBorderColor: Color = WhoppahTheme.colors.primary,
        disabledBorderColor: Color = borderColor.copy(alpha = ContentAlpha.disabled),
        leadingElementColor: Color = WhoppahTheme.colors.primary,
        disabledLeadingElementColor: Color = leadingElementColor.copy(alpha = ContentAlpha.disabled),
        selectedLeadingElementColor: Color = WhoppahTheme.colors.primary,
        trailingElementColor: Color = WhoppahTheme.colors.primary,
        disabledTrailingElementColor: Color = trailingElementColor.copy(alpha = ContentAlpha.disabled),
        selectedTrailingElementColor: Color = WhoppahTheme.colors.primary,
    ): WhSelectableColors =
        DefaultWhSelectableColors(
            contentColor = contentColor,
            selectedContentColor = selectedContentColor,
            disabledContentColor = disabledTextColor,
            backgroundColor = backgroundColor,
            selectedBackgroundColor = selectedBackgroundColor,
            borderColor = borderColor,
            selectedBorderColor = selectedBorderColor,
            disabledBorderColor = disabledBorderColor,
            leadingElementColor = leadingElementColor,
            disabledLeadingElementColor = disabledLeadingElementColor,
            selectedLeadingElementColor = selectedLeadingElementColor,
            trailingElementColor = trailingElementColor,
            disabledTrailingElementColor = disabledTrailingElementColor,
            selectedTrailingElementColor = selectedTrailingElementColor
        )
}

@Immutable
private class DefaultWhSelectableColors(
    private val contentColor: Color,
    private val selectedContentColor: Color,
    private val disabledContentColor: Color,
    private val backgroundColor: Color,
    private val selectedBackgroundColor: Color,
    private val borderColor: Color,
    private val selectedBorderColor: Color,
    private val disabledBorderColor: Color,
    private val leadingElementColor: Color,
    private val disabledLeadingElementColor: Color,
    private val selectedLeadingElementColor: Color,
    private val trailingElementColor: Color,
    private val disabledTrailingElementColor: Color,
    private val selectedTrailingElementColor: Color,
) : WhSelectableColors {
    @Composable
    override fun contentDefaultColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) contentColor else disabledContentColor)
    }

    @Composable
    override fun contentColor(enabled: Boolean, selected: Boolean): State<Color> {
        val targetValue = when {
            !enabled -> disabledContentColor
            selected -> selectedContentColor
            else -> contentColor
        }
        return if (enabled) {
            animateColorAsState(targetValue, tween(durationMillis = AnimationDuration), label = "wh_selectable_border")
        } else {
            rememberUpdatedState(targetValue)
        }
    }

    @Composable
    override fun borderColor(enabled: Boolean, selected: Boolean): State<Color> {
        val targetValue = when {
            !enabled -> disabledBorderColor
            selected -> selectedBorderColor
            else -> borderColor
        }
        return if (enabled) {
            animateColorAsState(targetValue, tween(durationMillis = AnimationDuration), label = "wh_selectable_border")
        } else {
            rememberUpdatedState(targetValue)
        }
    }

    @Composable
    override fun backgroundColor(enabled: Boolean, selected: Boolean): State<Color> {
        val targetValue = when {
            !enabled -> backgroundColor
            selected -> selectedBackgroundColor
            else -> backgroundColor
        }
        return if (enabled) {
            animateColorAsState(targetValue, tween(durationMillis = AnimationDuration), label = "wh_selectable_background")
        } else {
            rememberUpdatedState(targetValue)
        }
    }

    @Composable
    override fun leadingElementColor(enabled: Boolean, selected: Boolean): State<Color> {
        return rememberUpdatedState(
            when {
                !enabled -> disabledLeadingElementColor
                selected -> selectedLeadingElementColor
                else -> leadingElementColor
            }
        )
    }

    @Composable
    override fun trailingElementColor(enabled: Boolean, selected: Boolean): State<Color> {
        return rememberUpdatedState(
            when {
                !enabled -> disabledTrailingElementColor
                selected -> selectedTrailingElementColor
                else -> trailingElementColor
            }
        )
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as DefaultWhSelectableColors

        if (contentColor != other.contentColor) return false
        if (disabledContentColor != other.disabledContentColor) return false
        if (borderColor != other.borderColor) return false
        if (disabledBorderColor != other.disabledBorderColor) return false
        if (selectedBorderColor != other.selectedBorderColor) return false
        if (backgroundColor != other.backgroundColor) return false
        if (selectedBackgroundColor != other.selectedBackgroundColor) return false
        if (leadingElementColor != other.leadingElementColor) return false
        if (disabledLeadingElementColor != other.disabledLeadingElementColor) return false
        if (selectedLeadingElementColor != other.selectedLeadingElementColor) return false
        if (trailingElementColor != other.trailingElementColor) return false
        if (disabledTrailingElementColor != other.disabledTrailingElementColor) return false
        if (selectedTrailingElementColor != other.selectedTrailingElementColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = contentColor.hashCode()
        result = 31 * result + disabledContentColor.hashCode()
        result = 31 * result + borderColor.hashCode()
        result = 31 * result + disabledBorderColor.hashCode()
        result = 31 * result + selectedBorderColor.hashCode()
        result = 31 * result + backgroundColor.hashCode()
        result = 31 * result + selectedBackgroundColor.hashCode()
        result = 31 * result + trailingElementColor.hashCode()
        result = 31 * result + disabledTrailingElementColor.hashCode()
        result = 31 * result + selectedTrailingElementColor.hashCode()
        return result
    }
}

private const val AnimationDuration = 150