package ua.isida.common.ui.compose.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ua.isida.common.ui.compose.theme.ContentAlpha
import ua.isida.common.ui.compose.theme.IsidaPalette
import ua.isida.common.ui.compose.theme.IsidaTheme

@Immutable
object AppTextFieldDefaults {
    @Composable
    fun outlinedTextFieldColors(
        textColor: Color = LocalContentColor.current,
        disabledTextColor: Color = IsidaPalette.Grey800,
        containerColor: Color = Color.Transparent,
        disabledContainerColor: Color = IsidaPalette.Grey50,
        cursorColor: Color = IsidaTheme.colors.tertiary,
        errorCursorColor: Color = IsidaTheme.colors.error,
        focusedBorderColor: Color = IsidaTheme.colors.tertiary,
        unfocusedBorderColor: Color = IsidaTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled),
        disabledBorderColor: Color = unfocusedBorderColor.copy(alpha = ContentAlpha.disabled),
        errorBorderColor: Color = IsidaTheme.colors.error,
        leadingIconColor: Color = IsidaTheme.colors.onSurface,
        disabledLeadingIconColor: Color = leadingIconColor.copy(alpha = ContentAlpha.disabled),
        errorLeadingIconColor: Color = leadingIconColor,
        trailingIconColor: Color = IsidaTheme.colors.onSurface,
        disabledTrailingIconColor: Color = trailingIconColor.copy(alpha = ContentAlpha.disabled),
        errorTrailingIconColor: Color = IsidaTheme.colors.error,
        focusedLabelColor: Color = IsidaTheme.colors.tertiary,
        unfocusedLabelColor: Color = IsidaTheme.colors.onSurface,
        disabledLabelColor: Color = unfocusedLabelColor,
        errorLabelColor: Color = IsidaTheme.colors.error,
        placeholderColor: Color = IsidaPalette.Grey500,
        disabledPlaceholderColor: Color = IsidaPalette.Grey300,
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