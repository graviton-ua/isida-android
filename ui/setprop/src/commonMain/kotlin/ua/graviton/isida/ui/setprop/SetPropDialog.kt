package ua.graviton.isida.ui.setprop

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
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
import com.whoppah.common.compose.ui.WhDialog
import kotlinx.serialization.Serializable
import ua.graviton.isida.ui.properties.DeviceProperty
import ua.graviton.isida.ui.setprop.models.*

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
                SetPropViewEvent.Sent -> navigateUp()
            }
        }
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    SetPropDialog(
        state = state,
        navigateUp = navigateUp,
        send = viewModel::send,
    )
}

@Composable
internal fun SetPropDialog(
    state: SetPropViewState,
    navigateUp: () -> Unit,
    send: () -> Unit,
) {
    WhDialog {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .defaultMinSize(minHeight = 72.dp)
                .padding(8.dp)
        ) {
            Text(
                text = state.property.title(),
                style = WhoppahTheme.typography.h4,
            )

            state.property.Content(
                modifier = Modifier.fillMaxWidth()
            )

            val description = state.property.description
            if (description != null) Text(text = description())

            val valid = state.property.isValid.collectAsStateWithLifecycle(initialValue = true)
            DialogButtons(
                onSend = send,
                onCancel = navigateUp,
                validState = valid,
                modifier = Modifier.fillMaxWidth(),
            )
        }
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


private class DevicePropertyPreviewParameterProvider : PreviewParameterProvider<DeviceProperty> {
    val properties = listOf(
        SpT0(value = 10f), SpT1(value = 25f), SpRh0(value = 24), SpRh1(),
        ExtendMode(), RelayMode(), Program(),
        MinRun(), MaxRun(), Period(), TurnOff(), TurnOn(),
        Alarm0(), Alarm1(), ExtOn0(), ExtOn1(), ExtOff0(), ExtOff1(),
        Air0(), Air1(), SpCO2(), KoffCurr(), Hysteresis(), Zonality(),
        TurnTime(), WaitCooling(), Permission(), FlapRestrictions(),
        Pkoff0(value = 1), Pkoff1(value = 99), Ikoff0(), Ikoff1(),
        Identif(),
    )
    override val values = properties.asSequence()
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(DevicePropertyPreviewParameterProvider::class) property: DeviceProperty,
) {
    WhoppahTheme {
        LaunchedEffect(Unit) { property.validate() }
        SetPropDialog(
            state = SetPropViewState(property = property),
            navigateUp = {},
            send = {},
        )
    }
}