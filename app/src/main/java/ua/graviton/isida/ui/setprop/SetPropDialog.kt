package ua.graviton.isida.ui.setprop

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch
import ua.graviton.isida.domain.services.intentBLServiceSendCommand
import ua.graviton.isida.ui.compose.FreeDialogStyle
import ua.graviton.isida.ui.theme.IsidaTheme
import ua.graviton.isida.ui.utils.Crossfade
import ua.graviton.isida.ui.utils.rememberFlowWithLifecycle

data class SetPropDialogNavArgs(
    val id: String,
)

@Destination(
    navArgsDelegate = SetPropDialogNavArgs::class,
    style = FreeDialogStyle::class,
)
@Composable
fun SetPropDialog(
    navigateUp: () -> Unit,
) {
    SetPropDialog(
        viewModel = hiltViewModel(),
        navigateUp = navigateUp,
    )
}

@Composable
fun SetPropDialog(
    viewModel: SetPropViewModel,
    navigateUp: () -> Unit,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(viewModel.events) {
        scope.launch {
            viewModel.events.collect { event ->
                when (event) {
                    is SetPropEvent.Send -> {
                        with(context) { startService(intentBLServiceSendCommand(event.command)) }
                        navigateUp()
                    }
                }
            }
        }
    }

    val viewState by rememberFlowWithLifecycle(viewModel.state).collectAsState(initial = SetPropViewState.Init)

    SetPropDialog(viewState) { action ->
        when (action) {
            is SetPropAction.NavigateUp -> navigateUp()
            else -> viewModel.submitAction(action)
        }
    }
}

@Composable
private fun SetPropDialog(
    state: SetPropViewState,
    actioner: (SetPropAction) -> Unit,
) {
    Surface(
        color = MaterialTheme.colors.background,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
    ) {
        Crossfade(
            targetState = state,
            contentKey = { it.id },
        ) { localState ->
            when (localState) {
                is SetPropViewState.Empty -> StateEmpty(modifier = Modifier.fillMaxSize())
                is SetPropViewState.NoData -> StateNoData(modifier = Modifier.fillMaxSize())
                is SetPropViewState.NotFound -> StateNotFound(modifier = Modifier.fillMaxSize())
                is SetPropViewState.Success -> StateSuccess(
                    state = localState,
                    onStateUpdate = { actioner(SetPropAction.Update(it)) },
                    onSend = { actioner(SetPropAction.Send) },
                    onCancel = { actioner(SetPropAction.NavigateUp) },
                    modifier = Modifier.fillMaxSize(),
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
        modifier = modifier.padding(8.dp)
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
        modifier = modifier.padding(8.dp)
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
        modifier = modifier.padding(8.dp)
    ) {
        Text(text = "Property not found !")
    }
}

@Composable
private fun StateSuccess(
    state: SetPropViewState.Success,
    onStateUpdate: (SetPropAction.UpdateState) -> Unit,
    onSend: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(8.dp)
    ) {
        Text(text = state.propId)
        SetPropInput(
            state = state.inputState,
            onStateUpdate = onStateUpdate,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
        )
        DialogButtons(
            onSend = onSend,
            onCancel = onCancel,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun DialogButtons(
    onSend: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        OutlinedButton(
            onClick = onCancel,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { Text(text = "Cancel") }
        Spacer(modifier = Modifier.width(4.dp))
        Button(
            onClick = onSend,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { Text(text = "Apply") }
    }
}


@Preview(name = "Success state")
@Composable
private fun PreviewSuccess() {
    IsidaTheme {
        SetPropDialog(
            state = SetPropViewState.PreviewSuccess,
            actioner = {},
        )
    }
}

@Preview(name = "Not Found state")
@Composable
private fun PreviewNotFound() {
    IsidaTheme {
        SetPropDialog(
            state = SetPropViewState.NotFound,
            actioner = {},
        )
    }
}

@Preview(name = "No Data state")
@Composable
private fun PreviewNoData() {
    IsidaTheme {
        SetPropDialog(
            state = SetPropViewState.NoData,
            actioner = {},
        )
    }
}

@Preview(name = "Empty state")
@Composable
private fun PreviewEmpty() {
    IsidaTheme {
        SetPropDialog(
            state = SetPropViewState.Empty,
            actioner = {},
        )
    }
}