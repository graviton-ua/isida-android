package ua.graviton.isida.ui.devicemode

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import ua.graviton.isida.R
import ua.graviton.isida.data.bl.IsidaCommands
import ua.graviton.isida.domain.services.intentBLServiceSendCommand
import ua.graviton.isida.ui.compose.FreeDialogStyle
import ua.graviton.isida.ui.theme.IsidaColor
import ua.graviton.isida.ui.theme.IsidaTheme
import ua.graviton.isida.ui.utils.collectAsStateWithLifecycle

@Destination(
    style = FreeDialogStyle::class,
)
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

    LaunchedEffect(viewModel.events) {
        viewModel.events.collect { event ->
            when (event) {
                is DeviceModeEvent.Send -> {
                    with(context) { startService(intentBLServiceSendCommand(event.command)) }
                    navigateUp()
                }
            }
        }
    }

    val viewState by viewModel.state.collectAsStateWithLifecycle()

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
        modifier = Modifier.padding(horizontal = 16.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {

            RadioItemGroup(
                mode = state.mode,
                onModeSelect = { actioner(DeviceModeAction.SelectMode(it)) },
                modifier = Modifier.fillMaxWidth()
            )

            if (state.mode == IsidaCommands.DeviceMode.ENABLE)
                CheckItemGroup(
                    extras = state.extras,
                    toggleExtra = { actioner(DeviceModeAction.ToggleExtra(it)) },
                    enabled = state.mode == IsidaCommands.DeviceMode.ENABLE,
                    modifier = Modifier.fillMaxWidth()
                )

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                OutlinedButton(
                    onClick = { actioner(DeviceModeAction.NavigateUp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) { Text(text = "Cancel") }
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
            textColor = IsidaColor.Red500,
            selected = IsidaCommands.DeviceMode.DISABLE == mode,
            onClicked = { onModeSelect(IsidaCommands.DeviceMode.DISABLE) },
            modifier = Modifier.fillMaxWidth()
        )
        RadioItem(
            text = stringResource(id = R.string.radioButON),
            textColor = IsidaColor.Green500,
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
    Column(
        modifier = modifier
            .background(color = MaterialTheme.colors.surface),
    ) {
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
            state = DeviceModeViewState(mode = IsidaCommands.DeviceMode.ENABLE),
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