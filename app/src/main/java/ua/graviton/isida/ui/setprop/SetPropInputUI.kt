package ua.graviton.isida.ui.setprop

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import ua.graviton.isida.domain.models.DeviceProperty
import ua.graviton.isida.ui.theme.IsidaTheme

@Composable
fun SetPropInput(
    property: DeviceProperty<*>,
    onPropertyChanged: (DeviceProperty<*>?) -> Unit,
    modifier: Modifier = Modifier,
): Unit = when (property) {
    is DeviceProperty.Air0 -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toInt()) }) },
        modifier = modifier,
    )
    is DeviceProperty.Air1 -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toInt()) }) },
        modifier = modifier,
    )
    is DeviceProperty.Alarm0 -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toInt()) }) },
        modifier = modifier,
    )
    is DeviceProperty.Alarm1 -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toInt()) }) },
        modifier = modifier,
    )
    is DeviceProperty.CoolOff -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toInt()) }) },
        modifier = modifier,
    )
    is DeviceProperty.CoolOn -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toInt()) }) },
        modifier = modifier,
    )
    is DeviceProperty.DeviceNumber -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toInt()) }) },
        modifier = modifier,
    )
    is DeviceProperty.EnergyMeter -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toInt()) }) },
        modifier = modifier,
    )
    is DeviceProperty.ExtOff0 -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toInt()) }) },
        modifier = modifier,
    )
    is DeviceProperty.ExtOff1 -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toInt()) }) },
        modifier = modifier,
    )
    is DeviceProperty.ExtOn0 -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toInt()) }) },
        modifier = modifier,
    )
    is DeviceProperty.ExtOn1 -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toInt()) }) },
        modifier = modifier,
    )
    is DeviceProperty.ExtendMode -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toInt()) }) },
        modifier = modifier,
    )
    is DeviceProperty.ForceHeat -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toInt()) }) },
        modifier = modifier,
    )
    is DeviceProperty.HihEnable -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toInt()) }) },
        modifier = modifier,
    )
    is DeviceProperty.Hysteresis -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toInt()) }) },
        modifier = modifier,
    )
    is DeviceProperty.K0 -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toInt()) }) },
        modifier = modifier,
    )
    is DeviceProperty.K1 -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toInt()) }) },
        modifier = modifier,
    )
    is DeviceProperty.KOffCurr -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toInt()) }) },
        modifier = modifier,
    )
    is DeviceProperty.MaxRun -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toInt()) }) },
        modifier = modifier,
    )
    is DeviceProperty.MinRun -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toInt()) }) },
        modifier = modifier,
    )
    is DeviceProperty.Period -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toInt()) }) },
        modifier = modifier,
    )
    is DeviceProperty.Program -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toInt()) }) },
        modifier = modifier,
    )
    is DeviceProperty.RelayMode -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toInt()) }) },
        modifier = modifier,
    )
    is DeviceProperty.SpCO2 -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toInt()) }) },
        modifier = modifier,
    )
    is DeviceProperty.SpRh0 -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toFloat()) }) },
        modifier = modifier,
    )
    is DeviceProperty.SpRh1 -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toFloat()) }) },
        modifier = modifier,
    )
    is DeviceProperty.SpT0 -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toFloat()) }) },
        modifier = modifier,
    )
    is DeviceProperty.SpT1 -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toFloat()) }) },
        modifier = modifier,
    )
    is DeviceProperty.State -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toInt()) }) },
        modifier = modifier,
    )
    is DeviceProperty.Ti0 -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toInt()) }) },
        modifier = modifier,
    )
    is DeviceProperty.Ti1 -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toInt()) }) },
        modifier = modifier,
    )
    is DeviceProperty.TimeOut -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toInt()) }) },
        modifier = modifier,
    )
    is DeviceProperty.Timer0 -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toInt()) }) },
        modifier = modifier,
    )
    is DeviceProperty.Timer1 -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toInt()) }) },
        modifier = modifier,
    )
    is DeviceProperty.TurnTime -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toInt()) }) },
        modifier = modifier,
    )
    is DeviceProperty.Zonality -> NumberInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it.toInt()) }) },
        modifier = modifier,
    )
    else -> InputUnknown(modifier = modifier)
}

@Composable
private fun InputUnknown(
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        Text(text = "Unknown property")
    }
}

@Composable
private fun NumberInput(
    init: Number,
    onChanged: (Number?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    var textFieldValue by remember(init) {
        when (init) {
            is Float -> String.format("%.1f", init)
            else -> init.toString()
        }.let { mutableStateOf(TextFieldValue(text = it, selection = TextRange(it.length))) }
    }
    OutlinedTextField(
        value = textFieldValue,
        onValueChange = { value ->
            textFieldValue = value
            val numberValue = when (init) {
                is Float -> value.text.toFloatOrNull()
                else -> value.text.toIntOrNull()
            }
            onChanged(numberValue)
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
            keyboardController?.hide()
        }),
        modifier = modifier,
    )
}


@Preview(name = "Unknown property")
@Composable
private fun PreviewUnknown() {
    IsidaTheme {
        SetPropInput(
            property = DeviceProperty.Unknown,
            onPropertyChanged = {},
        )
    }
}

@Preview(name = "Number property")
@Composable
private fun PreviewNumber() {
    IsidaTheme {
        SetPropInput(
            property = DeviceProperty.Air0(29),
            onPropertyChanged = {},
        )
    }
}