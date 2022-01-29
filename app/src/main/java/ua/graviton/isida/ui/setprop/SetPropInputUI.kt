package ua.graviton.isida.ui.setprop

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import ua.graviton.isida.ui.setprop.SetPropAction.UpdateState
import ua.graviton.isida.ui.setprop.SetPropViewState.InputState
import ua.graviton.isida.ui.theme.IsidaTheme


@Composable
fun SetPropInput(
    state: InputState,
    onStateUpdate: (UpdateState) -> Unit,
    modifier: Modifier = Modifier,
): Unit = when (state) {
    is InputState.Unknown -> InputUnknown(modifier = modifier)
    is InputState.NumberInput -> NumberInput(state = state, onStateUpdate = onStateUpdate, modifier = modifier)
}

@Composable
private fun InputUnknown(
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        Text(text = "Unknown property")
    }
}

@Composable
private fun NumberInput(
    state: InputState.NumberInput,
    onStateUpdate: (UpdateState) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = state.value,
            onValueChange = { onStateUpdate(UpdateState.NumberInput(it)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
                keyboardController?.hide()
            }),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}


@Preview(name = "Unknown property")
@Composable
private fun PreviewUnknown() {
    IsidaTheme {
        SetPropInput(
            state = InputState.Unknown,
            onStateUpdate = {},
        )
    }
}

@Preview(name = "Number property")
@Composable
private fun PreviewNumber() {
    IsidaTheme {
        SetPropInput(
            state = InputState.NumberInput("12"),
            onStateUpdate = {},
        )
    }
}