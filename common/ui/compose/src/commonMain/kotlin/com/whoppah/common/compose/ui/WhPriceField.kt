package com.whoppah.common.compose.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import com.whoppah.common.compose.icons.WhIcons
import com.whoppah.common.compose.icons.state.Euro
import com.whoppah.common.compose.input.DefaultPriceInputTransformation
import com.whoppah.common.compose.input.NumberInputTextFieldState
import com.whoppah.common.compose.theme.WhoppahTheme

@Composable
fun WhPriceField(
    state: NumberInputTextFieldState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    labelPosition: TextFieldLabelPosition = TextFieldLabelPosition.Above(),
    label: @Composable (TextFieldLabelScope.() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    inputTransformation: InputTransformation = DefaultPriceInputTransformation,
    outputTransformation: OutputTransformation? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, autoCorrectEnabled = false),
    onKeyboardAction: KeyboardActionHandler? = null,
    lineLimits: TextFieldLineLimits = TextFieldLineLimits.SingleLine,
    onTextLayout: (Density.(getResult: () -> TextLayoutResult?) -> Unit)? = null,
    scrollState: ScrollState = rememberScrollState(),
    shape: Shape = WhoppahTheme.shapes.small,
    colors: TextFieldColors = WhTextFieldDefaults.outlinedTextFieldColors(),
    contentPadding: PaddingValues = WhTextFieldDefaults.outlinedTextFieldPadding(),
    interactionSource: MutableInteractionSource? = null,
    defaultMinWidth: Dp = TextFieldDefaults.MinWidth,
    defaultMinHeight: Dp = WhTextFieldDefaults.MinHeight,
) = WhTextField(
    state = state.fieldState,
    modifier = modifier,
    enabled = enabled,
    readOnly = readOnly,
    textStyle = textStyle,
    labelPosition = labelPosition,
    label = label,
    placeholder = placeholder,
    leadingIcon = { Icon(imageVector = WhIcons.State.Euro, contentDescription = null) },
    trailingIcon = trailingIcon,
    prefix = prefix,
    suffix = suffix,
    supportingText = supportingText ?: state.errorState.value?.let { { Text(text = it.asLabel()) } },
    isError = isError || state.isError,
    inputTransformation = inputTransformation,
    outputTransformation = outputTransformation,
    keyboardOptions = keyboardOptions,
    onKeyboardAction = onKeyboardAction,
    lineLimits = lineLimits,
    onTextLayout = onTextLayout,
    scrollState = scrollState,
    shape = shape,
    colors = colors,
    contentPadding = contentPadding,
    interactionSource = interactionSource,
    defaultMinWidth = defaultMinWidth,
    defaultMinHeight = defaultMinHeight,
)