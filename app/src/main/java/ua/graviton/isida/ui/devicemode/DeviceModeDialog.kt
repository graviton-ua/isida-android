package ua.graviton.isida.ui.devicemode

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ua.graviton.isida.R
import ua.graviton.isida.data.bl.model.IsidaCommands
import ua.graviton.isida.domain.services.intentBLServiceSendCommand
import ua.graviton.isida.ui.theme.Green500
import ua.graviton.isida.ui.theme.IsidaTheme
import ua.graviton.isida.ui.theme.Red500
import ua.graviton.isida.ui.utils.collectAsStateWithLifecycle
import ua.graviton.isida.ui.utils.rememberFlowWithLifecycle

@Composable
fun DeviceModeDialog(
    navigateUp: () -> Unit,
) {
    DeviceModeDialog(
        viewModel = hiltViewModel(),
        navigateUp = navigateUp,
    )
}

@Composable
fun DeviceModeDialog(
    viewModel: DeviceModeViewModel,
    navigateUp: () -> Unit,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(viewModel.events) {
        scope.launch {
            viewModel.events.collect { event ->
                when (event) {
                    is DeviceModeEvent.Send -> {
                        with(context) { startService(intentBLServiceSendCommand(event.command)) }
                        navigateUp()
                    }
                }
            }
        }
    }

    val viewState by rememberFlowWithLifecycle(viewModel.state).collectAsState(initial = DeviceModeViewState.Empty)

    DeviceModeDialog(viewState) { action ->
        when (action) {
            is DeviceModeAction.NavigateUp -> navigateUp()
            else -> viewModel.submitAction(action)
        }
    }
}

@Composable
private fun DeviceModeDialog(
    state: DeviceModeViewState,
    actioner: (DeviceModeAction) -> Unit,
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

            RadioItemGroup(
                mode = state.mode,
                onModeSelect = { actioner(DeviceModeAction.SelectMode(it)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Surface(
                color = MaterialTheme.colors.surface,
                shape = RectangleShape,
            ) {
                CheckItemGroup(
                    extras = state.extras,
                    toggleExtra = { actioner(DeviceModeAction.ToggleExtra(it)) },
                    enabled = state.mode == IsidaCommands.DeviceMode.ENABLE,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row {
                OutlinedButton(
                    onClick = { actioner(DeviceModeAction.NavigateUp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) { Text(text = "Cancel") }
                Spacer(modifier = Modifier.width(4.dp))
                Button(
                    onClick = { actioner(DeviceModeAction.ApplyMode) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) { Text(text = "Apply") }
            }
        }
    }
}


@Composable
private fun RadioItemGroup(
    mode: IsidaCommands.DeviceMode?,
    onModeSelect: (IsidaCommands.DeviceMode) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        RadioItem(
            text = stringResource(id = R.string.radioButOFF),
            textColor = Red500,
            selected = IsidaCommands.DeviceMode.DISABLE == mode,
            onClicked = { onModeSelect(IsidaCommands.DeviceMode.DISABLE) },
            modifier = Modifier.fillMaxWidth()
        )
        RadioItem(
            text = stringResource(id = R.string.radioButON),
            textColor = Green500,
            selected = IsidaCommands.DeviceMode.ENABLE == mode,
            onClicked = { onModeSelect(IsidaCommands.DeviceMode.ENABLE) },
            modifier = Modifier.fillMaxWidth()
        )
        RadioItem(
            text = stringResource(id = R.string.radioButTURN),
            selected = IsidaCommands.DeviceMode.ONLY_ROTATION == mode,
            onClicked = { onModeSelect(IsidaCommands.DeviceMode.ONLY_ROTATION) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun RadioItem(
    text: String,
    selected: Boolean,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable { onClicked() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
            modifier = Modifier
        )
        Text(
            text = text,
            color = textColor ?: LocalContentColor.current,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp)
        )
    }
}


@Composable
private fun CheckItemGroup(
    extras: List<IsidaCommands.DeviceModeExtra>,
    toggleExtra: (IsidaCommands.DeviceModeExtra) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Column(modifier = modifier) {
        CheckItem(
            text = stringResource(id = R.string.txtChip1),
            checked = extras.contains(IsidaCommands.DeviceModeExtra.EXTRA_1),
            onClicked = { toggleExtra(IsidaCommands.DeviceModeExtra.EXTRA_1) },
            enabled = enabled,
            modifier = Modifier.fillMaxWidth()
        )
        CheckItem(
            text = stringResource(id = R.string.txtChip2),
            checked = extras.contains(IsidaCommands.DeviceModeExtra.EXTRA_2),
            onClicked = { toggleExtra(IsidaCommands.DeviceModeExtra.EXTRA_2) },
            enabled = enabled,
            modifier = Modifier.fillMaxWidth()
        )
        CheckItem(
            text = stringResource(id = R.string.txtChip3),
            checked = extras.contains(IsidaCommands.DeviceModeExtra.EXTRA_3),
            onClicked = { toggleExtra(IsidaCommands.DeviceModeExtra.EXTRA_3) },
            enabled = enabled,
            modifier = Modifier.fillMaxWidth()
        )
        CheckItem(
            text = stringResource(id = R.string.txtChip4),
            checked = extras.contains(IsidaCommands.DeviceModeExtra.EXTRA_4),
            onClicked = { toggleExtra(IsidaCommands.DeviceModeExtra.EXTRA_4) },
            enabled = enabled,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun CheckItem(
    text: String,
    checked: Boolean,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable(enabled = enabled) { onClicked() }
            .padding(horizontal = 16.dp, vertical = 6.dp),
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = null,
            enabled = enabled,
            modifier = Modifier
        )
        Text(
            text = text,
            color = when (enabled) {
                true -> MaterialTheme.colors.onSurface
                false -> MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp)
        )
    }
}


@Preview(name = "Preview nothing selected")
@Composable
private fun Preview1() {
    IsidaTheme {
        DeviceModeDialog(
            state = DeviceModeViewState.Empty,
            actioner = {}
        )
    }
}

@Preview(name = "Preview ONLY ROTATION")
@Composable
private fun Preview2() {
    IsidaTheme {
        DeviceModeDialog(
            state = DeviceModeViewState(mode = IsidaCommands.DeviceMode.ONLY_ROTATION),
            actioner = {}
        )
    }
}

@Preview(name = "Radio group")
@Composable
fun PreviewRadioGroup() {
    IsidaTheme {
        RadioItemGroup(mode = null, onModeSelect = {})
    }
}

@Preview(name = "Check group")
@Composable
fun PreviewCheckGroup() {
    IsidaTheme {
        CheckItemGroup(extras = emptyList(), toggleExtra = {})
    }
}