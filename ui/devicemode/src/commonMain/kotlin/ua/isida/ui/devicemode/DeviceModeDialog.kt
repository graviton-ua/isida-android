package ua.isida.ui.devicemode

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import ua.isida.common.ui.compose.theme.ContentAlpha
import ua.isida.common.ui.compose.theme.IsidaColor
import ua.isida.common.ui.compose.theme.AppTheme
import ua.isida.common.ui.compose.theme.IsidaTheme
import ua.isida.common.ui.compose.ui.WhDialog
import ua.isida.common.ui.compose.ui.WhRadioButton
import ua.isida.common.ui.resources.*
import ua.isida.metrox.viewmodel.injectedViewModel
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import ua.isida.data.protocol.DeviceMode
import ua.isida.data.protocol.DeviceModeExtra

@Serializable
data object DeviceModeDialog : NavKey

@Composable
internal fun DeviceModeDialog(
    viewModel: DeviceModeViewModel = injectedViewModel(),
    navigateUp: () -> Unit,
) {
    LaunchedEffect(viewModel.events) {
        viewModel.events.collect { event ->
            when (event) {
                is DeviceModeViewEvent.OnApplied -> navigateUp()
            }
        }
    }

    val viewState by viewModel.state.collectAsStateWithLifecycle()
    DeviceModeDialog(
        state = viewState,
        navigateUp = navigateUp,
        actioner = viewModel::submitAction,
    )
}

@Composable
private fun DeviceModeDialog(
    state: DeviceModeViewState,
    navigateUp: () -> Unit,
    actioner: (DeviceModeAction) -> Unit,
) {
    WhDialog {
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

            if (state.mode == DeviceMode.ENABLE)
                CheckItemGroup(
                    extras = state.extras,
                    toggleExtra = { actioner(DeviceModeAction.ToggleExtra(it)) },
                    enabled = state.mode == DeviceMode.ENABLE,
                    modifier = Modifier.fillMaxWidth()
                )

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                OutlinedButton(
                    onClick = navigateUp,
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
    mode: DeviceMode?,
    onModeSelect: (DeviceMode) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        RadioItem(
            text = stringResource(Res.string.radioButOFF),
            textColor = IsidaColor.Red500,
            selected = DeviceMode.DISABLE == mode,
            onClicked = { onModeSelect(DeviceMode.DISABLE) },
            modifier = Modifier.fillMaxWidth()
        )
        RadioItem(
            text = stringResource(Res.string.radioButON),
            textColor = IsidaColor.Green500,
            selected = DeviceMode.ENABLE == mode,
            onClicked = { onModeSelect(DeviceMode.ENABLE) },
            modifier = Modifier.fillMaxWidth()
        )
        RadioItem(
            text = stringResource(Res.string.radioButTURN),
            selected = DeviceMode.ONLY_ROTATION == mode,
            onClicked = { onModeSelect(DeviceMode.ONLY_ROTATION) },
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
        WhRadioButton(
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
    extras: List<DeviceModeExtra>,
    toggleExtra: (DeviceModeExtra) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Column(
        modifier = modifier
            .background(color = IsidaTheme.colors.surface),
    ) {
        CheckItem(
            text = stringResource(Res.string.txtChip1),
            checked = extras.contains(DeviceModeExtra.EXTRA_1),
            onClicked = { toggleExtra(DeviceModeExtra.EXTRA_1) },
            enabled = enabled,
            modifier = Modifier.fillMaxWidth()
        )
        CheckItem(
            text = stringResource(Res.string.txtChip2),
            checked = extras.contains(DeviceModeExtra.EXTRA_2),
            onClicked = { toggleExtra(DeviceModeExtra.EXTRA_2) },
            enabled = enabled,
            modifier = Modifier.fillMaxWidth()
        )
        CheckItem(
            text = stringResource(Res.string.txtChip3),
            checked = extras.contains(DeviceModeExtra.EXTRA_3),
            onClicked = { toggleExtra(DeviceModeExtra.EXTRA_3) },
            enabled = enabled,
            modifier = Modifier.fillMaxWidth()
        )
        CheckItem(
            text = stringResource(Res.string.txtChip4),
            checked = extras.contains(DeviceModeExtra.EXTRA_4),
            onClicked = { toggleExtra(DeviceModeExtra.EXTRA_4) },
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
                true -> IsidaTheme.colors.onSurface
                false -> IsidaTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
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
    AppTheme {
        DeviceModeDialog(
            state = DeviceModeViewState.Empty,
            navigateUp = {},
            actioner = {}
        )
    }
}

@Preview(name = "Preview ONLY ROTATION")
@Composable
private fun Preview2() {
    AppTheme {
        DeviceModeDialog(
            state = DeviceModeViewState(mode = DeviceMode.ENABLE),
            navigateUp = {},
            actioner = {}
        )
    }
}

@Preview(name = "Radio group")
@Composable
fun PreviewRadioGroup() {
    AppTheme {
        RadioItemGroup(mode = null, onModeSelect = {})
    }
}

@Preview(name = "Check group")
@Composable
fun PreviewCheckGroup() {
    AppTheme {
        CheckItemGroup(extras = emptyList(), toggleExtra = {})
    }
}