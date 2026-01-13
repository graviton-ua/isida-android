package com.whoppah.common.compose.ui.textfield

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Stable
internal fun TextFieldColors.labelColor(enabled: Boolean, isError: Boolean, focused: Boolean): Color = when {
    !enabled -> disabledLabelColor
    isError -> errorLabelColor
    focused -> focusedLabelColor
    else -> unfocusedLabelColor
}

@Stable
internal fun TextFieldColors.placeholderColor(enabled: Boolean, isError: Boolean, focused: Boolean): Color = when {
    !enabled -> disabledPlaceholderColor
    isError -> errorPlaceholderColor
    focused -> focusedPlaceholderColor
    else -> unfocusedPlaceholderColor
}

@Stable
internal fun TextFieldColors.prefixColor(enabled: Boolean, isError: Boolean, focused: Boolean): Color = when {
    !enabled -> disabledPrefixColor
    isError -> errorPrefixColor
    focused -> focusedPrefixColor
    else -> unfocusedPrefixColor
}

@Stable
internal fun TextFieldColors.suffixColor(enabled: Boolean, isError: Boolean, focused: Boolean): Color = when {
    !enabled -> disabledSuffixColor
    isError -> errorSuffixColor
    focused -> focusedSuffixColor
    else -> unfocusedSuffixColor
}

@Stable
internal fun TextFieldColors.leadingIconColor(enabled: Boolean, isError: Boolean, focused: Boolean): Color = when {
    !enabled -> disabledLeadingIconColor
    isError -> errorLeadingIconColor
    focused -> focusedLeadingIconColor
    else -> unfocusedLeadingIconColor
}

@Stable
internal fun TextFieldColors.trailingIconColor(enabled: Boolean, isError: Boolean, focused: Boolean): Color = when {
    !enabled -> disabledTrailingIconColor
    isError -> errorTrailingIconColor
    focused -> focusedTrailingIconColor
    else -> unfocusedTrailingIconColor
}

@Stable
internal fun TextFieldColors.supportingTextColor(enabled: Boolean, isError: Boolean, focused: Boolean): Color = when {
    !enabled -> disabledSupportingTextColor
    isError -> errorSupportingTextColor
    focused -> focusedSupportingTextColor
    else -> unfocusedSupportingTextColor
}

internal fun TextFieldDefaults.supportingTextPadding(
    start: Dp = SupportingHorizontalPadding,
    top: Dp = SupportingTopPadding,
    end: Dp = SupportingHorizontalPadding,
    bottom: Dp = 0.dp,
): PaddingValues = PaddingValues(start, top, end, bottom)