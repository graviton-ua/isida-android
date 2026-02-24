package ua.isida.common.ui.compose.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.*
import androidx.compose.foundation.text.input.TextFieldLineLimits.SingleLine
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material3.*
import androidx.compose.material3.OutlinedTextFieldDefaults.Container
import androidx.compose.material3.OutlinedTextFieldDefaults.FocusedBorderThickness
import androidx.compose.material3.OutlinedTextFieldDefaults.UnfocusedBorderThickness
import androidx.compose.material3.OutlinedTextFieldDefaults.colors
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.material3.OutlinedTextFieldDefaults.shape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import ua.isida.common.ui.compose.input.InputTextFieldState
import ua.isida.common.ui.compose.theme.IsidaTheme
import ua.isida.common.ui.compose.ui.textfield.CommonDecorationBox
import ua.isida.common.ui.compose.ui.textfield.minimizedLabelHalfHeight
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

/**
 * A wrapper for the Material3 OutlinedTextField that provides a backward-compatible
 * interface using `value` and `onValueChange`, while internally leveraging the new
 * `TextFieldState`-based implementation.
 *
 * This component is intended as a transitional tool to facilitate gradual migration
 * of large codebases. For all new development, using OutlinedTextField with a
 * hoisted `TextFieldState` is the recommended approach.
 */
@Composable
fun WhTextFieldLegacy(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    labelPosition: TextFieldLabelPosition = TextFieldLabelPosition.Above(),
    label: @Composable (TextFieldLabelScope.() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    helper: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = IsidaTheme.shapes.small,
    colors: TextFieldColors = WhTextFieldDefaults.outlinedTextFieldColors(),
    contentPadding: PaddingValues = WhTextFieldDefaults.outlinedTextFieldPadding(),
    defaultMinWidth: Dp = TextFieldDefaults.MinWidth,
    defaultMinHeight: Dp = WhTextFieldDefaults.MinHeight,
) {
    // Internal state for the new TextField component.
    val state = rememberTextFieldState(initialText = value)

    // This effect synchronizes the internal state with external `value` changes.
    // It's crucial for cases where the state is updated programmatically from the ViewModel.
    LaunchedEffect(value) {
        // Only update if the external value is different from the internal text.
        // This check prevents an infinite recomposition loop.
        if (value != state.text.toString()) {
            state.edit {
                replace(0, length, value)
            }
        }
    }

    // This effect listens for changes from user input (internal state) and
    // propagates them up to the `onValueChange` callback.
    LaunchedEffect(state) {
        snapshotFlow { state.text.toString() }
            .distinctUntilChanged()
            // Filter out the initial value to avoid an unnecessary callback on composition.
            .filter { it != value }
            .collect { text ->
                onValueChange(text)
            }
    }

    // Map the legacy KeyboardActions to the new onKeyboardAction lambda.
    val onKeyboardAction: KeyboardActionHandler? = remember(keyboardActions, keyboardOptions.imeAction) {
        when (keyboardOptions.imeAction) {
            ImeAction.Done -> keyboardActions.onDone?.let { KeyboardActionHandler { it.invoke() } }
            ImeAction.Go -> keyboardActions.onGo?.let { KeyboardActionHandler { it.invoke() } }
            ImeAction.Next -> keyboardActions.onNext?.let { KeyboardActionHandler { it.invoke() } }
            ImeAction.Previous -> keyboardActions.onPrevious?.let { KeyboardActionHandler { it.invoke() } }
            ImeAction.Search -> keyboardActions.onSearch?.let { KeyboardActionHandler { it.invoke() } }
            ImeAction.Send -> keyboardActions.onSend?.let { KeyboardActionHandler { it.invoke() } }
            else -> null
        }
    }

    // Map the legacy line limit parameters to the new TextFieldLineLimits class.
    val lineLimits = if (singleLine) {
        SingleLine
    } else {
        TextFieldLineLimits.MultiLine(
            minHeightInLines = minLines,
            maxHeightInLines = maxLines
        )
    }

    WhTextField(
        state = state,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        labelPosition = labelPosition,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        prefix = prefix,
        suffix = suffix,
        supportingText = helper,
        isError = isError,
        keyboardOptions = keyboardOptions,
        onKeyboardAction = onKeyboardAction,
        lineLimits = lineLimits,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
        scrollState = rememberScrollState(),
        contentPadding = contentPadding,
        defaultMinWidth = defaultMinWidth,
        defaultMinHeight = defaultMinHeight,
    )
}

@Composable
fun WhTextField(
    state: InputTextFieldState<*>,
    modifier: Modifier = Modifier,
    enabled: Boolean? = null,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    labelPosition: TextFieldLabelPosition = TextFieldLabelPosition.Above(),
    label: @Composable (TextFieldLabelScope.() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    inputTransformation: InputTransformation? = null,
    outputTransformation: OutputTransformation? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    lineLimits: TextFieldLineLimits = SingleLine,
    onTextLayout: (Density.(getResult: () -> TextLayoutResult?) -> Unit)? = null,
    scrollState: ScrollState = rememberScrollState(),
    shape: Shape = IsidaTheme.shapes.small,
    colors: TextFieldColors = WhTextFieldDefaults.outlinedTextFieldColors(),
    contentPadding: PaddingValues = WhTextFieldDefaults.outlinedTextFieldPadding(),
    interactionSource: MutableInteractionSource? = null,
    defaultMinWidth: Dp = TextFieldDefaults.MinWidth,
    defaultMinHeight: Dp = WhTextFieldDefaults.MinHeight,
) = WhTextField(
    state = state.fieldState,
    modifier = modifier,
    enabled = enabled ?: state.enabledState.value,
    readOnly = readOnly,
    textStyle = textStyle,
    labelPosition = labelPosition,
    label = label,
    placeholder = placeholder,
    leadingIcon = leadingIcon,
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

@Composable
fun WhTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    labelPosition: TextFieldLabelPosition = TextFieldLabelPosition.Above(),
    label: @Composable (TextFieldLabelScope.() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    inputTransformation: InputTransformation? = null,
    outputTransformation: OutputTransformation? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    lineLimits: TextFieldLineLimits = SingleLine,
    onTextLayout: (Density.(getResult: () -> TextLayoutResult?) -> Unit)? = null,
    scrollState: ScrollState = rememberScrollState(),
    shape: Shape = IsidaTheme.shapes.small,
    colors: TextFieldColors = WhTextFieldDefaults.outlinedTextFieldColors(),
    contentPadding: PaddingValues = WhTextFieldDefaults.outlinedTextFieldPadding(),
    interactionSource: MutableInteractionSource? = null,
    defaultMinWidth: Dp = TextFieldDefaults.MinWidth,
    defaultMinHeight: Dp = WhTextFieldDefaults.MinHeight,
) {
    @Suppress("NAME_SHADOWING")
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    // If color is not provided via the text style, use content color as a default
    val textColor =
        textStyle.color.takeOrElse {
            val focused = interactionSource.collectIsFocusedAsState().value
            colors.whTextColor(enabled, isError, focused)
        }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    CompositionLocalProvider(LocalTextSelectionColors provides colors.textSelectionColors) {
        BasicTextField(
            state = state,
            modifier =
                modifier
                    .then(
                        if (label != null && labelPosition !is TextFieldLabelPosition.Above) {
                            Modifier
                                // Merge semantics at the beginning of the modifier chain to ensure
                                // padding is considered part of the text field.
                                .semantics(mergeDescendants = true) {}
                                .padding(top = minimizedLabelHalfHeight())
                        } else {
                            Modifier
                        }
                    )
                    //.defaultErrorSemantics(isError, getString(Strings.DefaultErrorMessage))
                    .defaultMinSize(
                        minWidth = defaultMinWidth,
                        minHeight = defaultMinHeight,
                    ),
            enabled = enabled,
            readOnly = readOnly,
            textStyle = mergedTextStyle,
            cursorBrush = SolidColor(colors.whCursorColor(isError)),
            keyboardOptions = keyboardOptions,
            onKeyboardAction = onKeyboardAction,
            lineLimits = lineLimits,
            onTextLayout = onTextLayout,
            interactionSource = interactionSource,
            inputTransformation = inputTransformation,
            outputTransformation = outputTransformation,
            scrollState = scrollState,
            decorator =
                WhOutlinedTextFieldDefaults.decorator(
                    state = state,
                    enabled = enabled,
                    lineLimits = lineLimits,
                    outputTransformation = outputTransformation,
                    interactionSource = interactionSource,
                    labelPosition = labelPosition,
                    label = label,
                    placeholder = placeholder,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                    prefix = prefix,
                    suffix = suffix,
                    supportingText = supportingText,
                    isError = isError,
                    colors = colors,
                    contentPadding = contentPadding,
                    container = {
                        Container(
                            enabled = enabled,
                            isError = isError,
                            interactionSource = interactionSource,
                            colors = colors,
                            shape = shape,
                        )
                    },
                ),
        )
    }
}

@Composable
fun WhSecureTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    labelPosition: TextFieldLabelPosition = TextFieldLabelPosition.Above(),
    label: @Composable (TextFieldLabelScope.() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    inputTransformation: InputTransformation? = null,
    textObfuscationMode: TextObfuscationMode = TextObfuscationMode.RevealLastTyped,
    textObfuscationCharacter: Char = DefaultObfuscationCharacter,
    keyboardOptions: KeyboardOptions = SecureTextFieldKeyboardOptions,
    onKeyboardAction: KeyboardActionHandler? = null,
    onTextLayout: (Density.(getResult: () -> TextLayoutResult?) -> Unit)? = null,
    shape: Shape = IsidaTheme.shapes.small,
    colors: TextFieldColors = WhTextFieldDefaults.outlinedTextFieldColors(),
    contentPadding: PaddingValues = WhTextFieldDefaults.outlinedTextFieldPadding(),
    interactionSource: MutableInteractionSource? = null,
    defaultMinWidth: Dp = TextFieldDefaults.MinWidth,
    defaultMinHeight: Dp = WhTextFieldDefaults.MinHeight,
) {
    @Suppress("NAME_SHADOWING")
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    // If color is not provided via the text style, use content color as a default
    val textColor =
        textStyle.color.takeOrElse {
            val focused = interactionSource.collectIsFocusedAsState().value
            colors.whTextColor(enabled, isError, focused)
        }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    CompositionLocalProvider(LocalTextSelectionColors provides colors.textSelectionColors) {
        BasicSecureTextField(
            state = state,
            modifier =
                modifier
                    .then(
                        if (label != null && labelPosition !is TextFieldLabelPosition.Above) {
                            Modifier
                                // Merge semantics at the beginning of the modifier chain to ensure
                                // padding is considered part of the text field.
                                .semantics(mergeDescendants = true) {}
                                .padding(top = minimizedLabelHalfHeight())
                        } else {
                            Modifier
                        }
                    )
                    //.defaultErrorSemantics(isError, getString(Strings.DefaultErrorMessage))
                    .defaultMinSize(
                        minWidth = defaultMinWidth,
                        minHeight = defaultMinHeight,
                    ),
            enabled = enabled,
            readOnly = readOnly,
            textStyle = mergedTextStyle,
            cursorBrush = SolidColor(colors.whCursorColor(isError)),
            keyboardOptions = keyboardOptions,
            onKeyboardAction = onKeyboardAction,
            onTextLayout = onTextLayout,
            interactionSource = interactionSource,
            inputTransformation = inputTransformation,
            textObfuscationMode = textObfuscationMode,
            textObfuscationCharacter = textObfuscationCharacter,
            decorator =
                WhOutlinedTextFieldDefaults.decorator(
                    state = state,
                    enabled = enabled,
                    lineLimits = SingleLine,
                    outputTransformation = null,
                    interactionSource = interactionSource,
                    labelPosition = labelPosition,
                    label = label,
                    placeholder = placeholder,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                    prefix = prefix,
                    suffix = suffix,
                    supportingText = supportingText,
                    isError = isError,
                    colors = colors,
                    contentPadding = contentPadding,
                    container = {
                        Container(
                            enabled = enabled,
                            isError = isError,
                            interactionSource = interactionSource,
                            colors = colors,
                            shape = shape,
                        )
                    },
                ),
        )
    }
}

@Composable
fun WhTextFieldFake(
    value: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textStyle: TextStyle = LocalTextStyle.current,
    labelPosition: TextFieldLabelPosition = TextFieldLabelPosition.Above(),
    label: @Composable (TextFieldLabelScope.() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    lineLimits: TextFieldLineLimits = SingleLine,
    shape: Shape = IsidaTheme.shapes.small,
    colors: TextFieldColors = WhTextFieldDefaults.outlinedTextFieldColors(),
    contentPadding: PaddingValues = WhTextFieldDefaults.outlinedTextFieldPadding(),
    interactionSource: MutableInteractionSource? = null,
    defaultMinWidth: Dp = TextFieldDefaults.MinWidth,
    defaultMinHeight: Dp = WhTextFieldDefaults.MinHeight,
) {
    @Suppress("NAME_SHADOWING")
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    // If color is not provided via the text style, use content color as a default
    val textColor =
        textStyle.color.takeOrElse {
            val focused = interactionSource.collectIsFocusedAsState().value
            colors.whTextColor(enabled, isError, focused)
        }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    Box(
        modifier = modifier
            .then(
                if (label != null && labelPosition !is TextFieldLabelPosition.Above) {
                    Modifier
                        // Merge semantics at the beginning of the modifier chain to ensure
                        // padding is considered part of the text field.
                        .semantics(mergeDescendants = true) {}
                        .padding(top = minimizedLabelHalfHeight())
                } else {
                    Modifier
                }
            )
            //.defaultErrorSemantics(isError, getString(Strings.DefaultErrorMessage))
            .defaultMinSize(
                minWidth = defaultMinWidth,
                minHeight = defaultMinHeight,
            ),
        propagateMinConstraints = true,
    ) {
        CommonDecorationBox(
            visualText = value,
            innerTextField = { Text(text = value, style = mergedTextStyle) },
            labelPosition = labelPosition,
            label = label,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            prefix = prefix,
            suffix = suffix,
            supportingText = supportingText,
            singleLine = lineLimits == SingleLine,
            enabled = enabled,
            isError = isError,
            interactionSource = interactionSource,
            contentPadding = contentPadding,
            colors = colors,
            container = {
                Container(
                    enabled = enabled,
                    isError = isError,
                    interactionSource = interactionSource,
                    colors = colors,
                    shape = shape,
                    focusedBorderThickness = FocusedBorderThickness,
                    unfocusedBorderThickness = UnfocusedBorderThickness,
                )
            },
        )
    }
}

@Immutable
object WhOutlinedTextFieldDefaults {
    @Composable
    fun decorator(
        state: TextFieldState,
        enabled: Boolean,
        lineLimits: TextFieldLineLimits,
        outputTransformation: OutputTransformation?,
        interactionSource: InteractionSource,
        labelPosition: TextFieldLabelPosition = TextFieldLabelPosition.Attached(),
        label: @Composable (TextFieldLabelScope.() -> Unit)? = null,
        placeholder: @Composable (() -> Unit)? = null,
        leadingIcon: @Composable (() -> Unit)? = null,
        trailingIcon: @Composable (() -> Unit)? = null,
        prefix: @Composable (() -> Unit)? = null,
        suffix: @Composable (() -> Unit)? = null,
        supportingText: @Composable (() -> Unit)? = null,
        isError: Boolean = false,
        colors: TextFieldColors = colors(),
        contentPadding: PaddingValues = contentPadding(),
        container: @Composable () -> Unit = {
            Container(
                enabled = enabled,
                isError = isError,
                interactionSource = interactionSource,
                colors = colors,
                shape = shape,
                focusedBorderThickness = FocusedBorderThickness,
                unfocusedBorderThickness = UnfocusedBorderThickness,
            )
        },
    ): TextFieldDecorator = object : TextFieldDecorator {
        @Composable
        override fun Decoration(innerTextField: @Composable (() -> Unit)) {
            val visualText =
                if (outputTransformation == null) state.text
                else {
                    // TODO: use constructor to create TextFieldBuffer from TextFieldState when
                    // available
                    lateinit var buffer: TextFieldBuffer
                    state.edit { buffer = this }
                    // after edit completes, mutations on buffer are ineffective
                    with(outputTransformation) { buffer.transformOutput() }
                    buffer.asCharSequence()
                }

            CommonDecorationBox(
                visualText = visualText,
                innerTextField = innerTextField,
                placeholder = placeholder,
                labelPosition = labelPosition,
                label = label,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                prefix = prefix,
                suffix = suffix,
                supportingText = supportingText,
                singleLine = lineLimits == SingleLine,
                enabled = enabled,
                isError = isError,
                interactionSource = interactionSource,
                colors = colors,
                contentPadding = contentPadding,
                container = container,
            )
        }
    }
}

@Stable
private fun TextFieldColors.whTextColor(
    enabled: Boolean,
    isError: Boolean,
    focused: Boolean,
): Color =
    when {
        !enabled -> disabledTextColor
        isError -> errorTextColor
        focused -> focusedTextColor
        else -> unfocusedTextColor
    }

@Stable
private fun TextFieldColors.whCursorColor(isError: Boolean): Color = if (isError) errorCursorColor else cursorColor

private val SecureTextFieldKeyboardOptions =
    KeyboardOptions(autoCorrectEnabled = false, keyboardType = KeyboardType.Password)

private const val DefaultObfuscationCharacter: Char = '\u2022'


//internal data class PreviewTextFieldState(
//    val state: InputState<*>,
//    val label: @Composable (TextFieldLabelScope.() -> Unit)? = null,
//    val placeholder: @Composable (() -> Unit)? = @Composable { Text(text = "Placeholder") },
//    val helper: @Composable (() -> Unit)? = null,
//    val lineLimits: TextFieldLineLimits = SingleLine,
//)
//
//private abstract class TextFieldPreviewParameterProvider : PreviewParameterProvider<PreviewTextFieldState> {
//    open val texts: List<String> = listOf(
//        "",
//        "short text",
//        "sosdf gme lonsdf gsdfg tesdfgsdfxt sogsdfgsd fme losdfgsdfgng text ssdfgsdfome long text some long text some long text some long text some long text some long text",
//    )
//
//    open val errors: List<TextInputState.Error?> = listOf(
//        null, TextInputState.Error.Required, TextInputState.Error.Custom("Custom error message")
//    )
//
//    open val fieldStates: List<TextInputState> = texts.flatMapIndexed { i, text ->
//        errors.map { error ->
//            DefaultTextInputState(initialText = text, initialError = error, initialEnabled = i % 2 == 0)
//        }
//    }
//
//    open val labels: List<@Composable (TextFieldLabelScope.() -> Unit)?> = listOf(
//        null,
//        @Composable { Text(text = "Your label") }
//    )
//    open val helpers: List<@Composable (() -> Unit)?> = listOf(
//        null,
//        @Composable { Text(text = "Enter your helper text here") }
//    )
//
//    open val states: List<PreviewTextFieldState> = fieldStates.flatMap { state ->
//        labels.flatMap { label ->
//            helpers.map { helper ->
//                PreviewTextFieldState(state = state, label = label, helper = helper)
//            }
//        }
//    }
//
//    override val values = states.asSequence()
//}
//
//private class SingleLinePreviewParameterProvider : TextFieldPreviewParameterProvider()
//
//@Preview(name = "SingleLine", showBackground = true)
//@Composable
//private fun PreviewSingleLine(
//    @PreviewParameter(SingleLinePreviewParameterProvider::class) state: PreviewTextFieldState
//) {
//    WhoppahTheme {
//        WhTextField(
//            state = state.state,
//            label = state.label,
//            supportingText = state.helper,
//            placeholder = state.placeholder,
//            lineLimits = state.lineLimits,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp),
//        )
//    }
//}
//
//
//private class MultiLinePreviewParameterProvider : TextFieldPreviewParameterProvider() {
//    private val lineLimits = TextFieldLineLimits.MultiLine(minHeightInLines = 3, maxHeightInLines = 5)
//    override val values = states.map { it.copy(lineLimits = lineLimits) }.asSequence()
//}
//
//@Preview(name = "MultiLine", showBackground = true)
//@Composable
//private fun PreviewMultiLine(
//    @PreviewParameter(MultiLinePreviewParameterProvider::class) state: PreviewTextFieldState
//) {
//    WhoppahTheme {
//        WhTextField(
//            state = state.state,
//            label = state.label,
//            supportingText = state.helper,
//            placeholder = state.placeholder,
//            lineLimits = state.lineLimits,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp),
//        )
//    }
//}