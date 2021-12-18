package ua.graviton.isida.ui.setprop

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ua.graviton.isida.domain.services.intentBLServiceSendCommand
import ua.graviton.isida.ui.utils.rememberFlowWithLifecycle

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

    val viewState by rememberFlowWithLifecycle(viewModel.state).collectAsState(initial = SetPropViewState.Empty)

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
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

        }
    }
}