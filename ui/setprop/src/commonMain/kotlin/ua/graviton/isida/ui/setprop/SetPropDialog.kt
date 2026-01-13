package ua.graviton.isida.ui.setprop

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.whoppah.common.compose.theme.WhoppahTheme
import com.whoppah.common.compose.ui.Crossfade
import kotlinx.serialization.Serializable
import ua.graviton.isida.domain.models.DeviceProperty

@Serializable
data class SetPropDialog(val id: String) : NavKey

@Composable
internal fun SetPropDialog(
    viewModel: SetPropViewModel,
    navigateUp: () -> Unit,
) {
    LaunchedEffect(viewModel.events) {
        viewModel.events.collect { event ->
            when (event) {
                is SetPropEvent.Send -> {
                    //with(context) { startService(intentBLServiceSendCommand(event.command)) }
                    //navigateUp()
                }
            }
        }
    }

    val viewState by viewModel.state.collectAsStateWithLifecycle()

    SetPropDialog(
        state = viewState,
        actioner = { action ->
            when (action) {
                is SetPropAction.NavigateUp -> navigateUp()
                else -> viewModel.submitAction(action)
            }
        },
    )
}

@Composable
private fun SetPropDialog(
    state: SetPropViewState,
    actioner: (SetPropAction) -> Unit,
) {
    Surface(
        color = WhoppahTheme.colors.background,
        shape = WhoppahTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .padding(horizontal = 16.dp)
    ) {
        Crossfade(
            targetState = state,
            contentKey = { it.id },
        ) { localState ->
            when (localState) {
                is SetPropViewState.Empty -> StateEmpty(modifier = Modifier.fillMaxWidth())
                is SetPropViewState.NoData -> StateNoData(modifier = Modifier.fillMaxWidth())
                is SetPropViewState.NotFound -> StateNotFound(modifier = Modifier.fillMaxWidth())
                is SetPropViewState.Success -> StateSuccess(
                    state = localState,
                    onPropertyChanged = { actioner(SetPropAction.UpdateProperty(it)) },
                    onSend = { actioner(SetPropAction.Send) },
                    onCancel = { actioner(SetPropAction.NavigateUp) },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
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
    state: SetPropViewState.Success,
    onPropertyChanged: (DeviceProperty<*>?) -> Unit,
    onSend: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .defaultMinSize(minHeight = 72.dp)
            .padding(8.dp)
    ) {
        Text(text = state.property.id)
        val validState = remember { mutableStateOf(true) }
        SetPropInput(
            property = state.property,
            onPropertyChanged = onPropertyChanged,
            validState = validState,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
        )
        DialogButtons(
            onSend = onSend,
            onCancel = onCancel,
            validState = validState,
            modifier = Modifier.fillMaxWidth(),
        )
    }
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


@Preview(name = "Success state")
@Composable
private fun PreviewSuccess() {
    WhoppahTheme {
        SetPropDialog(
            state = SetPropViewState.PreviewSuccess,
            actioner = {},
        )
    }
}

@Preview(name = "Not Found state")
@Composable
private fun PreviewNotFound() {
    WhoppahTheme {
        SetPropDialog(
            state = SetPropViewState.NotFound,
            actioner = {},
        )
    }
}

@Preview(name = "No Data state")
@Composable
private fun PreviewNoData() {
    WhoppahTheme {
        SetPropDialog(
            state = SetPropViewState.NoData,
            actioner = {},
        )
    }
}

@Preview(name = "Empty state")
@Composable
private fun PreviewEmpty() {
    WhoppahTheme {
        SetPropDialog(
            state = SetPropViewState.Empty,
            actioner = {},
        )
    }
}