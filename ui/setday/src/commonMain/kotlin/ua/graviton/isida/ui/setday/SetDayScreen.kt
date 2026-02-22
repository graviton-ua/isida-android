package ua.graviton.isida.ui.setday

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
import ua.graviton.isida.ui.properties.DeviceProperty
import ua.graviton.isida.ui.setday.models.*

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
                is SetDayViewEvent.OnSubmit -> onSubmit(SetDayScreenResult(event.index, event.day))
            }
        }
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    SetDayScreen(
        state = state,
        navigateUp = navigateUp,
        submit = viewModel::submit,
    )
}

@Composable
internal fun SetDayScreen(
    state: SetDayViewState,
    navigateUp: () -> Unit,
    submit: () -> Unit,
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
        bottomBar = {
            DialogButtons(
                onSend = submit,
                onCancel = navigateUp,
                validState = state.dataIsValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            )
        },
    ) { paddings ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(paddings)
                .verticalScroll(state = rememberScrollState())
                .padding(8.dp),
        ) {
            state.properties.forEachIndexed { index, property ->
                SingleProperty(
                    property = property,
                    modifier = Modifier,
                )
            }
        }
    }
}


@Composable
internal fun SingleProperty(
    property: DeviceProperty,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier,
    ) {
        Text(
            text = property.title(),
            style = WhoppahTheme.typography.h4,
        )

        property.Content(
            modifier = Modifier.fillMaxWidth()
        )

        val description = property.description
        if (description != null) Text(text = description())
    }
}

@Composable
private fun DialogButtons(
    onSend: () -> Unit,
    onCancel: () -> Unit,
    validState: Boolean,
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
            enabled = validState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { Text(text = "Apply") }
    }
}


private class SetDayPreviewParameterProvider : PreviewParameterProvider<SetDayViewState> {
    val properties = listOf(
        SetDayViewState(
            properties = listOf(
                SpT0(value = 10f), SpT1(), SpRh1(),
                TurnPermission(), FlapProgramDay(), WaitCooling(),
            ),
            dataIsValid = true,
        ),
        SetDayViewState(
            properties = listOf(
                SpT0(value = 10f), SpT1(), SpRh1(),
                TurnPermission(), FlapProgramDay(), WaitCooling(),
            ),
            dataIsValid = false,
        ),
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
            submit = {},
        )
    }
}