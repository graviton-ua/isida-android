package ua.graviton.isida.ui.setday

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.whoppah.common.compose.theme.WhoppahTheme
import com.whoppah.common.compose.ui.WhScaffold
import kotlinx.serialization.Serializable
import ua.graviton.isida.data.protocol.packets.TableDay

@Serializable
data class SetDayScreen(
    val index: Int,
    val day: TableDay,
) : NavKey

data class SetDayScreenResult(
    val index: Int,
    val day: TableDay,
)

@Composable
internal fun SetDayScreen(
    viewModel: SetDayViewModel,
    navigateUp: () -> Unit,
    onSubmit: (SetDayScreenResult) -> Unit,
) {
    LaunchedEffect(viewModel.events) {
        viewModel.events.collect { event ->
            when (event) {
                SetDayViewEvent.Sent -> navigateUp()
            }
        }
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    SetDayScreen(
        state = state,
        navigateUp = navigateUp,
        send = viewModel::send,
    )
}

@Composable
internal fun SetDayScreen(
    state: SetDayViewState,
    navigateUp: () -> Unit,
    send: () -> Unit,
) {
    WhScaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Update day") },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                contentPadding = WindowInsets.statusBars.asPaddingValues(),
                modifier = Modifier.fillMaxWidth()
            )
        },
    ) { paddings ->
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(paddings),
        ) {
        }
    }
}


private class SetDayPreviewParameterProvider : PreviewParameterProvider<SetDayViewState> {
    val properties = listOf(
        SetDayViewState(waitingForData = true),
        SetDayViewState(waitingForData = false),
    )
    override val values = properties.asSequence()
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(SetDayPreviewParameterProvider::class) state: SetDayViewState,
) {
    WhoppahTheme {
        SetDayScreen(
            state = state,
            navigateUp = {},
            send = {},
        )
    }
}