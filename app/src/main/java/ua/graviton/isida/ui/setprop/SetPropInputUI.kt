package ua.graviton.isida.ui.setprop

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import ua.graviton.isida.R
import ua.graviton.isida.domain.models.DeviceCharacteristicInfo
import ua.graviton.isida.domain.models.DeviceProperty
import ua.graviton.isida.ui.theme.IsidaTheme
import java.util.*

@Composable
fun SetPropInput(
    property: DeviceProperty<*>,
    onPropertyChanged: (DeviceProperty<*>?) -> Unit,
    modifier: Modifier = Modifier,
): Unit = when (property) {
    is DeviceProperty.Air0 -> DefaultIntInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.Air1 -> DefaultIntInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.Alarm0 -> DefaultFloatInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.Alarm1 -> DefaultFloatInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.CoolOff -> DefaultIntInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.CoolOn -> DefaultIntInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.DeviceNumber -> DefaultIntInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.EnergyMeter -> DefaultIntInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.ExtOff0 -> DefaultFloatInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.ExtOff1 -> DefaultFloatInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.ExtOn0 -> DefaultFloatInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.ExtOn1 -> DefaultFloatInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.ExtendMode -> DefaultIntInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.ForceHeat -> DefaultFloatInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.HihEnable -> DefaultIntInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.Hysteresis -> DefaultIntInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.K0 -> DefaultIntInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.K1 -> DefaultIntInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.KOffCurr -> DefaultIntInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.MaxRun -> DefaultIntInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.MinRun -> DefaultIntInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.Period -> DefaultIntInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.Program -> DefaultIntInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.RelayMode -> DefaultIntInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.SpCO2 -> DefaultIntInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.SpRh0 -> DefaultFloatInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.SpRh1 -> DefaultFloatInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.SpT0 -> DefaultFloatInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.SpT1 -> DefaultFloatInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.State -> DefaultIntInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.Ti0 -> DefaultIntInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.Ti1 -> DefaultIntInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.TimeOut -> DefaultIntInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.Timer0 -> DefaultIntInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.Timer1 -> DefaultIntInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.TurnTime -> DefaultIntInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.Zonality -> DefaultIntInput(
        init = property.value,
        onChanged = { value -> onPropertyChanged(value?.let { property.copy(_value = it) }) },
        info = property.info,
        modifier = modifier,
    )
    is DeviceProperty.Unknown -> InputUnknown(modifier = modifier)
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

//@Composable
//private inline fun <reified T : Number> NumberInput(
//    init: T,
//    crossinline onChanged: (T?) -> Unit,
//    info: DeviceCharacteristicInfo<T>,
//    modifier: Modifier = Modifier,
//) {
//    val focusManager = LocalFocusManager.current
//    val keyboardController = LocalSoftwareKeyboardController.current
//
//    val convertedState = remember { mutableStateOf<T?>(null) }
//    val errorState = remember { mutableStateOf(false) }
//    val infoValidState = remember {
//        derivedStateOf {
//            convertedState.value?.let { converted -> info.limits.all { it.isValid(converted) } } ?: true
//        }
//    }
//
//    var textFieldValue by remember(init) {
//        when (init) {
//            is Float -> String.format(locale = Locale.ENGLISH, "%.1f", init)
//            else -> init.toString()
//        }.let { mutableStateOf(TextFieldValue(text = it, selection = TextRange(it.length))) }
//    }
//    OutlinedTextField(
//        value = textFieldValue,
//        onValueChange = { value ->
//            textFieldValue = value
//            val converted: T? = value.text.convertAndCast()
//            convertedState.value = converted
//            errorState.value = converted?.let { false } ?: true
//            onChanged(converted)
//        },
//        isError = errorState.value || !infoValidState.value,
//        singleLine = true,
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
//        keyboardActions = KeyboardActions(onDone = {
//            focusManager.clearFocus()
//            keyboardController?.hide()
//        }),
//        modifier = modifier,
//    )
//}

@Composable
private fun DefaultIntInput(
    init: Int,
    onChanged: (Int?) -> Unit,
    info: DeviceCharacteristicInfo<Int>,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier = modifier) {

        val convertedState = remember { mutableStateOf<Int?>(null) }
        val errorState = remember { mutableStateOf(false) }
        val infoValidState = remember {
            derivedStateOf {
                convertedState.value?.let { converted -> info.limits.all { it.isValid(converted) } } ?: true
            }
        }

        var textFieldValue by remember(init) {
            init.toString().let { mutableStateOf(TextFieldValue(text = it, selection = TextRange(it.length))) }
        }
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = { value ->
                textFieldValue = value
                val converted: Int? = value.text.toIntOrNull()
                convertedState.value = converted
                errorState.value = converted?.let { false } ?: true
                onChanged(converted)
            },
            isError = errorState.value || !infoValidState.value,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
                keyboardController?.hide()
            }),
            modifier = Modifier.fillMaxWidth(),
        )

        InputInfo(
            info = info,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun DefaultFloatInput(
    init: Float,
    onChanged: (Float?) -> Unit,
    info: DeviceCharacteristicInfo<Float>,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier = modifier) {

        val convertedState = remember { mutableStateOf<Float?>(null) }
        val errorState = remember { mutableStateOf(false) }
        val infoValidState = remember {
            derivedStateOf {
                convertedState.value?.let { converted -> info.limits.all { it.isValid(converted) } } ?: true
            }
        }

        var textFieldValue by remember(init) {
            String.format(locale = Locale.ENGLISH, "%.1f", init)
                .let { mutableStateOf(TextFieldValue(text = it, selection = TextRange(it.length))) }
        }
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = { value ->
                textFieldValue = value
                val converted: Float? = value.text.toFloatOrNull()
                convertedState.value = converted
                errorState.value = converted?.let { false } ?: true
                onChanged(converted)
            },
            isError = errorState.value || !infoValidState.value,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
                keyboardController?.hide()
            }),
            modifier = Modifier.fillMaxWidth(),
        )

        InputInfo(
            info = info,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun <T> InputInfo(
    info: DeviceCharacteristicInfo<T>,
    modifier: Modifier = Modifier,
) {
    if (info.limits.isEmpty()) return
    Column(
        modifier = modifier,
    ) {
        Text(text = "Rules here")
        info.limits.forEach {
            Limit(limit = it, modifier = Modifier)
        }
    }
}

@Composable
private fun <T> Limit(
    limit: DeviceCharacteristicInfo.Limit<T>,
    modifier: Modifier = Modifier,
) {
    val text = when (limit) {
        is DeviceCharacteristicInfo.Limit.Max -> stringResource(R.string.prop_info_limit_max, limit.max.toString())
        is DeviceCharacteristicInfo.Limit.Min -> stringResource(R.string.prop_info_limit_min, limit.min.toString())
        is DeviceCharacteristicInfo.Limit.MinMax -> stringResource(R.string.prop_info_limit_min_max, limit.min.toString(), limit.max.toString())
    }
    Text(
        text = text,
        modifier = modifier,
    )
}

//private inline fun <reified T : Number> String.convertAndCast(): T? {
//    return when (T::class) {
//        Float::class -> toFloatOrNull()
//        Int::class -> toIntOrNull()
//        else -> toIntOrNull()
//    } as? T
//}


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
            property = DeviceProperty.K1(29),
            onPropertyChanged = {},
        )
    }
}