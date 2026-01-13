package com.whoppah.common.compose.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.whoppah.common.compose.theme.ContentAlpha
import com.whoppah.common.compose.theme.WhoppahPalette
import com.whoppah.common.compose.theme.WhoppahTheme

@Immutable
object WhTextFieldDefaults {
    @Composable
    fun outlinedTextFieldColors(
        textColor: Color = LocalContentColor.current,
        disabledTextColor: Color = WhoppahPalette.Grey800,
        containerColor: Color = Color.Transparent,
        disabledContainerColor: Color = WhoppahPalette.Grey50,
        cursorColor: Color = WhoppahTheme.colors.tertiary,
        errorCursorColor: Color = WhoppahTheme.colors.error,
        focusedBorderColor: Color = WhoppahTheme.colors.tertiary,
        unfocusedBorderColor: Color = WhoppahTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled),
        disabledBorderColor: Color = unfocusedBorderColor.copy(alpha = ContentAlpha.disabled),
        errorBorderColor: Color = WhoppahTheme.colors.error,
        leadingIconColor: Color = WhoppahTheme.colors.onSurface,
        disabledLeadingIconColor: Color = leadingIconColor.copy(alpha = ContentAlpha.disabled),
        errorLeadingIconColor: Color = leadingIconColor,
        trailingIconColor: Color = WhoppahTheme.colors.onSurface,
        disabledTrailingIconColor: Color = trailingIconColor.copy(alpha = ContentAlpha.disabled),
        errorTrailingIconColor: Color = WhoppahTheme.colors.error,
        focusedLabelColor: Color = WhoppahTheme.colors.tertiary,
        unfocusedLabelColor: Color = WhoppahTheme.colors.onSurface,
        disabledLabelColor: Color = unfocusedLabelColor,
        errorLabelColor: Color = WhoppahTheme.colors.error,
        placeholderColor: Color = WhoppahPalette.Grey500,
        disabledPlaceholderColor: Color = WhoppahPalette.Grey300,
    ): TextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = textColor,
        unfocusedTextColor = textColor,
        disabledTextColor = disabledTextColor,
        errorTextColor = textColor,

        focusedContainerColor = containerColor,
        unfocusedContainerColor = containerColor,
        disabledContainerColor = disabledContainerColor,

        cursorColor = cursorColor,
        errorCursorColor = errorCursorColor,
        focusedBorderColor = focusedBorderColor,
        unfocusedBorderColor = unfocusedBorderColor,
        disabledBorderColor = disabledBorderColor,
        errorBorderColor = errorBorderColor,
        focusedLeadingIconColor = leadingIconColor,
        unfocusedLeadingIconColor = leadingIconColor,
        disabledLeadingIconColor = disabledLeadingIconColor,
        errorLeadingIconColor = errorLeadingIconColor,
        focusedTrailingIconColor = trailingIconColor,
        unfocusedTrailingIconColor = trailingIconColor,
        disabledTrailingIconColor = disabledTrailingIconColor,
        errorTrailingIconColor = errorTrailingIconColor,

        focusedLabelColor = focusedLabelColor,
        unfocusedLabelColor = unfocusedLabelColor,
        disabledLabelColor = disabledLabelColor,
        errorLabelColor = errorLabelColor,

        focusedPlaceholderColor = placeholderColor,
        unfocusedPlaceholderColor = placeholderColor,
        disabledPlaceholderColor = disabledPlaceholderColor,
        errorPlaceholderColor = placeholderColor,
    )

    /**
     * The default min width applied for a [TextField] and [OutlinedTextField].
     * Note that you can override it by applying Modifier.heightIn directly on a text field.
     */
    val MinHeight = 44.dp

    /**
     * Default content padding applied to [OutlinedTextField].
     * See [PaddingValues] for more details.
     */
    fun outlinedTextFieldPadding(
        start: Dp = TextFieldPaddingHorizontal,
        top: Dp = TextFieldPaddingVertical,
        end: Dp = TextFieldPaddingHorizontal,
        bottom: Dp = TextFieldPaddingVertical
    ): PaddingValues = PaddingValues(start, top, end, bottom)
}

private val TextFieldPaddingHorizontal = 12.dp
private val TextFieldPaddingVertical = 10.dp