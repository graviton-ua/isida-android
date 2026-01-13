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
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import com.whoppah.common.compose.icons.WhIcons
import com.whoppah.common.compose.icons.action.EditCalendar
import com.whoppah.common.compose.input.DateInputState
import com.whoppah.common.compose.input.DefaultDateInputTransformation
import com.whoppah.common.compose.input.MaskedOutputTransformation
import com.whoppah.common.compose.theme.WhoppahTheme
import com.whoppah.common.resources.Res
import com.whoppah.common.resources.common_accept
import com.whoppah.common.resources.common_cancel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import org.jetbrains.compose.resources.stringResource
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalMaterial3Api::class, FlowPreview::class, ExperimentalTime::class)
@Composable
fun WhDateField(
    state: DateInputState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    labelPosition: TextFieldLabelPosition = TextFieldLabelPosition.Above(),
    label: @Composable (TextFieldLabelScope.() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = { Text(text = "DD-MM-YYYY") },
    leadingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    inputTransformation: InputTransformation = DefaultDateInputTransformation,
    outputTransformation: OutputTransformation = DateFieldDefaults.dateOutput,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var showModal by remember { mutableStateOf(false) }

    LaunchedEffect(state.fieldState) {
        snapshotFlow { state.fieldState.text.toString() }
            .debounce(300L)
            .distinctUntilChanged()
            .map { runCatching { LocalDate.parse(it, state.dateFormatter) } }
            .collect { result -> result.onSuccess { state.setDate(it) } }
    }

    WhTextField(
        state = state.fieldState,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        labelPosition = labelPosition,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = {
            IconButton(onClick = {
                showModal = true
                focusManager.clearFocus()
                keyboardController?.hide()
            }) { Icon(imageVector = WhIcons.Action.EditCalendar, contentDescription = "calendar") }
        },
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

    if (showModal) {
        val date = state.date.getOrNull()
        DatePickerModal(
            selected = remember(date) { date?.atStartOfDayIn(TimeZone.UTC)?.toEpochMilliseconds() },
            onDateSelected = {
                state.setDate(it)
                showModal = false
            },
            onDismiss = { showModal = false }
        )
    }
}

internal object DateFieldDefaults {
    private const val DATE_MASK = "##-##-####"

    @OptIn(FormatStringsInDatetimeFormats::class)
    internal val dateFormatter: DateTimeFormat<LocalDate> = LocalDate.Format { byUnicodePattern("ddMMyyyy") }

    internal val dateOutput = MaskedOutputTransformation(mask = DATE_MASK, placeholder = '#')
}

/**
 * A composable function that displays a modal dialog with a Material Design Date Picker.
 *
 * This modal allows the user to select a specific date. The dialog includes "Accept" and "Cancel"
 * buttons for confirming or dismissing the selection. The date is handled as milliseconds
 * since the UTC epoch.
 *
 * @param selected  The initially selected date in milliseconds(start of the day) since the UTC epoch. If null, no date is pre-selected.
 * @param onDateSelected    A lambda that is invoked when the user presses the "Accept" button,
 *                          providing the selected date in milliseconds(start of the day).
 * @param onDismiss A lambda that is invoked when the dialog is dismissed, either by pressing
 *                  the "Cancel" button, the back button, or clicking outside the dialog.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
private fun DatePickerModal(
    selected: Long? = null,
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selected,
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text(stringResource(Res.string.common_accept))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(Res.string.common_cancel))
            }
        },
    ) {
        DatePicker(
            state = datePickerState,
            showModeToggle = false,
        )
    }
}


//@Preview(showBackground = true)
//@Composable
//private fun Preview() {
//    WhoppahTheme {
//        WhDateField(
//            state = DateInputState.Preview,
//            label = { Text(text = "Birthdate") },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//        )
//    }
//}