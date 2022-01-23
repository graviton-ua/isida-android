package ua.graviton.isida.ui.setprop

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ua.graviton.isida.ui.setprop.SetPropViewState.InputState
import ua.graviton.isida.ui.theme.IsidaTheme


@Composable
fun SetPropInput(
    state: InputState,
    modifier: Modifier = Modifier,
): Unit = when (state) {
    is InputState.Unknown -> InputUnknown(modifier = modifier)
    is InputState.IntProperty -> InputInt(state = state, modifier = modifier)
    is InputState.FloatProperty -> InputFloat(state = state, modifier = modifier)
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
private fun InputInt(
    state: InputState.IntProperty,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        TextField(
            value = state.value.toString(),
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun InputFloat(
    state: InputState.FloatProperty,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        TextField(
            value = state.value.toString(),
            onValueChange = {},
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
        )
    }
}

@Preview(name = "Int property")
@Composable
private fun PreviewInt() {
    IsidaTheme {
        SetPropInput(
            state = InputState.IntProperty(12),
        )
    }
}

@Preview(name = "Float property")
@Composable
private fun PreviewFloat() {
    IsidaTheme {
        SetPropInput(
            state = InputState.FloatProperty(1.2f),
        )
    }
}