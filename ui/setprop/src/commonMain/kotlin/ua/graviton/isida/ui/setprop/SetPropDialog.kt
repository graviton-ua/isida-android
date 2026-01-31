package ua.graviton.isida.ui.setprop

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.whoppah.common.compose.theme.WhoppahTheme
import kotlinx.serialization.Serializable

@Serializable
data class SetPropDialog(val id: String) : NavKey

@Composable
internal fun SetPropDialog(
    viewModel: SetPropViewModel,
    navigateUp: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SetPropDialog(
        state = state,
        navigateUp = navigateUp,
        send = viewModel::send,
    )
}

@Composable
private fun SetPropDialog(
    state: SetPropViewState,
    navigateUp: () -> Unit,
    send: () -> Unit,
) {
    Surface(
        color = WhoppahTheme.colors.background,
        shape = WhoppahTheme.shapes.medium,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        StateSuccess(
            state = state,
            onSend = send,
            onCancel = navigateUp,
        )
    }
}

@Composable
private fun StateEmpty(
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .defaultMinSize(minHeight = 72.dp)
            .padding(8.dp),
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun StateNoData(
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .defaultMinSize(minHeight = 72.dp)
            .padding(8.dp),
    ) {
        Text(text = "No data !")
    }
}

@Composable
private fun StateNotFound(
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .defaultMinSize(minHeight = 72.dp)
            .padding(8.dp),
    ) {
        Text(text = "Property not found !")
    }
}

@Composable
private fun StateSuccess(
    state: SetPropViewState,
    onSend: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .defaultMinSize(minHeight = 72.dp)
            .padding(8.dp)
    ) {
        Text(text = state.property.title())

        state.property.Content(
            modifier = Modifier.fillMaxWidth()
        )

        val valid = state.property.isValid.collectAsStateWithLifecycle(initialValue = true)
        DialogButtons(
            onSend = onSend,
            onCancel = onCancel,
            validState = valid,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun SetPropInput(
    property: DeviceProperty,
    modifier: Modifier = Modifier,
) {
    property.Content(modifier = modifier)
}

@Composable
private fun DialogButtons(
    onSend: () -> Unit,
    onCancel: () -> Unit,
    validState: State<Boolean>,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier,
    ) {
        OutlinedButton(
            onClick = onCancel,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { Text(text = "Cancel") }

        Button(
            onClick = onSend,
            enabled = validState.value,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { Text(text = "Apply") }
    }
}


@Preview
@Composable
private fun Preview() {
    WhoppahTheme {
        SetPropDialog(
            state = SetPropViewState(property = SpT0(value = null)),
            navigateUp = {},
            send = {},
        )
    }
}