package ua.isida.ui.setprop

import org.jetbrains.compose.resources.stringResource
import ua.isida.common.ui.resources.Res
import ua.isida.common.ui.resources.btn_apply
import ua.isida.common.ui.resources.btn_cancel
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
import ua.isida.common.ui.compose.theme.AppTheme
import ua.isida.common.ui.compose.theme.IsidaTheme
import ua.isida.common.ui.compose.ui.WhDialog
import kotlinx.serialization.Serializable
import ua.isida.ui.properties.DeviceProperty
import ua.isida.ui.setprop.models.Air0
import ua.isida.ui.setprop.models.Air1
import ua.isida.ui.setprop.models.Alarm0
import ua.isida.ui.setprop.models.Alarm1
import ua.isida.ui.setprop.models.ExtOff0
import ua.isida.ui.setprop.models.ExtOff1
import ua.isida.ui.setprop.models.ExtOn0
import ua.isida.ui.setprop.models.ExtOn1
import ua.isida.ui.setprop.models.ExtendMode
import ua.isida.ui.setprop.models.FlapRestrictions
import ua.isida.ui.setprop.models.Hysteresis
import ua.isida.ui.setprop.models.Identif
import ua.isida.ui.setprop.models.Ikoff0
import ua.isida.ui.setprop.models.Ikoff1
import ua.isida.ui.setprop.models.KoffCurr
import ua.isida.ui.setprop.models.MaxRun
import ua.isida.ui.setprop.models.MinFan
import ua.isida.ui.setprop.models.MinRun
import ua.isida.ui.setprop.models.Period
import ua.isida.ui.setprop.models.Permission
import ua.isida.ui.setprop.models.Pkoff0
import ua.isida.ui.setprop.models.Pkoff1
import ua.isida.ui.setprop.models.Program
import ua.isida.ui.setprop.models.RelayMode
import ua.isida.ui.setprop.models.SpCO2
import ua.isida.ui.setprop.models.SpRh0
import ua.isida.ui.setprop.models.SpRh1
import ua.isida.ui.setprop.models.SpT0
import ua.isida.ui.setprop.models.SpT1
import ua.isida.ui.setprop.models.TurnOff
import ua.isida.ui.setprop.models.TurnOn
import ua.isida.ui.setprop.models.TurnTime
import ua.isida.ui.setprop.models.WaitCooling
import ua.isida.ui.setprop.models.Zonality

import ua.isida.common.ui.resources.audit_set_value
import ua.isida.common.ui.resources.home_tab_prop

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
    send: (String?, String?, String?) -> Unit,
) {
    val screenName = stringResource(Res.string.home_tab_prop)
    val valueLabel = stringResource(Res.string.audit_set_value)

    WhDialog {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .defaultMinSize(minHeight = 72.dp)
                .padding(8.dp)
        ) {
            val title = state.property.title()
            Text(
                text = title,
                style = IsidaTheme.typography.h4,
            )

            state.property.Content(
                modifier = Modifier.fillMaxWidth()
            )

            val description = state.property.description
            if (description != null) Text(text = description())

            val valid = state.property.isValid.collectAsStateWithLifecycle(initialValue = true)
            DialogButtons(
                onSend = { 
                    send(
                        screenName,
                        title,
                        valueLabel
                    ) 
                },
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
        ) { Text(text = stringResource(Res.string.btn_cancel)) }

        Button(
            onClick = onSend,
            enabled = validState.value,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { Text(text = stringResource(Res.string.btn_apply)) }
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
        Identif(), MinFan(),
    )
    override val values = properties.asSequence()
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(DevicePropertyPreviewParameterProvider::class) property: DeviceProperty,
) {
    AppTheme {
        LaunchedEffect(Unit) { property.validate() }
        SetPropDialog(
            state = SetPropViewState(property = property),
            navigateUp = {},
            send = { _, _, _ -> },
        )
    }
}