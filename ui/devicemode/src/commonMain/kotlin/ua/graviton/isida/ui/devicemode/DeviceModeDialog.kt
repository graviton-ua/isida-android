package ua.graviton.isida.ui.devicemode

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
import com.whoppah.common.compose.theme.ContentAlpha
import com.whoppah.common.compose.theme.IsidaColor
import com.whoppah.common.compose.theme.WhoppahTheme
import com.whoppah.common.compose.ui.WhDialog
import com.whoppah.common.compose.ui.WhRadioButton
import com.whoppah.common.resources.*
import com.whoppah.metrox.viewmodel.injectedViewModel
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import ua.graviton.isida.domain.IsidaCommands

@Serializable
data object DeviceModeDialog : NavKey

@Composable
internal fun DeviceModeDialog(
    viewModel: DeviceModeViewModel = injectedViewModel(),
    navigateUp: () -> Unit,
) {
    val viewState by viewModel.state.collectAsStateWithLifecycle()

    DeviceModeDialog(
        state = viewState,
        actioner = { action ->
            when (action) {
                is DeviceModeAction.NavigateUp -> navigateUp()
                else -> viewModel.submitAction(action)
            }
        }
    )
}

@Composable
private fun DeviceModeDialog(
    state: DeviceModeViewState,
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
            text = stringResource(Res.string.radioButOFF),
            textColor = IsidaColor.Red500,
            selected = IsidaCommands.DeviceMode.DISABLE == mode,
            onClicked = { onModeSelect(IsidaCommands.DeviceMode.DISABLE) },
            modifier = Modifier.fillMaxWidth()
        )
        RadioItem(
            text = stringResource(Res.string.radioButON),
            textColor = IsidaColor.Green500,
            selected = IsidaCommands.DeviceMode.ENABLE == mode,
            onClicked = { onModeSelect(IsidaCommands.DeviceMode.ENABLE) },
            modifier = Modifier.fillMaxWidth()
        )
        RadioItem(
            text = stringResource(Res.string.radioButTURN),
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
    extras: List<IsidaCommands.DeviceModeExtra>,
    toggleExtra: (IsidaCommands.DeviceModeExtra) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Column(
        modifier = modifier
            .background(color = WhoppahTheme.colors.surface),
    ) {
        CheckItem(
            text = stringResource(Res.string.txtChip1),
            checked = extras.contains(IsidaCommands.DeviceModeExtra.EXTRA_1),
            onClicked = { toggleExtra(IsidaCommands.DeviceModeExtra.EXTRA_1) },
            enabled = enabled,
            modifier = Modifier.fillMaxWidth()
        )
        CheckItem(
            text = stringResource(Res.string.txtChip2),
            checked = extras.contains(IsidaCommands.DeviceModeExtra.EXTRA_2),
            onClicked = { toggleExtra(IsidaCommands.DeviceModeExtra.EXTRA_2) },
            enabled = enabled,
            modifier = Modifier.fillMaxWidth()
        )
        CheckItem(
            text = stringResource(Res.string.txtChip3),
            checked = extras.contains(IsidaCommands.DeviceModeExtra.EXTRA_3),
            onClicked = { toggleExtra(IsidaCommands.DeviceModeExtra.EXTRA_3) },
            enabled = enabled,
            modifier = Modifier.fillMaxWidth()
        )
        CheckItem(
            text = stringResource(Res.string.txtChip4),
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
                true -> WhoppahTheme.colors.onSurface
                false -> WhoppahTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
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
    WhoppahTheme {
        DeviceModeDialog(
            state = DeviceModeViewState.Empty,
            actioner = {}
        )
    }
}

@Preview(name = "Preview ONLY ROTATION")
@Composable
private fun Preview2() {
    WhoppahTheme {
        DeviceModeDialog(
            state = DeviceModeViewState(mode = IsidaCommands.DeviceMode.ENABLE),
            actioner = {}
        )
    }
}

@Preview(name = "Radio group")
@Composable
fun PreviewRadioGroup() {
    WhoppahTheme {
        RadioItemGroup(mode = null, onModeSelect = {})
    }
}

@Preview(name = "Check group")
@Composable
fun PreviewCheckGroup() {
    WhoppahTheme {
        CheckItemGroup(extras = emptyList(), toggleExtra = {})
    }
}