package ua.isida.ui.properties.types

import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import ua.isida.common.ui.compose.input.DefaultInputStateHelper
import ua.isida.common.ui.compose.input.InputState
import ua.isida.common.ui.compose.input.InputStateErrorScope
import ua.isida.common.ui.compose.theme.IsidaTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ua.isida.ui.properties.DeviceProperty
import kotlin.math.roundToInt
import kotlin.math.roundToLong

@Stable
abstract class SliderDeviceProperty<T : Number>(
    initValue: T? = null,
    val min: T, val max: T,
    @param:IntRange(from = 0) private val steps: Int = 0,
    override val title: @Composable () -> String,
    override val description: (@Composable () -> String)? = null,
    onValidate: InputStateErrorScope<Error>.(T?) -> Error? = { if (it == null) Error.Required else null },
) : DeviceProperty {

    constructor(
        initValue: T? = null,
        min: T, max: T,
        @FloatRange(from = 0.0) increment: Float,
        title: @Composable () -> String,
        description: (@Composable () -> String)? = null,
        onValidate: InputStateErrorScope<Error>.(T?) -> Error? = { if (it == null) Error.Required else null },
    ) : this(
        initValue = initValue, min = min, max = max,
        steps = (((max.toFloat() - min.toFloat()) / increment).toInt() - 1).coerceAtLeast(0),
        title = title, description = description,
        onValidate = onValidate,
    )

    val inputHelper = DefaultInputStateHelper(
        initValue = initValue,
        onValidate = onValidate,
        errorScope = SliderErrorScope,
    )

    @Suppress("UNCHECKED_CAST")
    @Composable
    override fun Content(modifier: Modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Current value: ${inputHelper.value}")
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "$min",
                    style = IsidaTheme.typography.h5,
                )
                Slider(
                    value = inputHelper.value?.toFloat() ?: 0f,
                    onValueChange = {
                        val value: T? = when (min) {
                            is Int -> it.roundToInt() as T
                            is Long -> it.roundToLong() as T
                            is Double -> it.toDouble() as T
                            is Float -> it as T
                            else -> it as T
                        }
                        inputHelper.setValue(value)
                    },
                    valueRange = min.toFloat()..max.toFloat(),
                    steps = steps,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "$max",
                    style = IsidaTheme.typography.h5,
                )
            }

            if (inputHelper.state.isError)
                Text(
                    text = inputHelper.state.errorState.value?.asLabel() ?: "",
                    style = IsidaTheme.typography.helper,
                    color = IsidaTheme.colors.error,
                )
        }
    }

    override val isValid: Flow<Boolean> = inputHelper.errorFlow.map { it == null }
    override fun validate(): Boolean = inputHelper.validate()?.let { false } ?: true

    override suspend fun validateOnInputUpdate() = inputHelper.validateOnInputUpdate()
    override suspend fun clearErrorOnInputUpdate() = inputHelper.clearErrorOnInputUpdate()

    interface Error : InputState.Error {
        object Required : Error {
            @Composable
            override fun asLabel(): String = "Required"
        }

        object Invalid : Error {
            @Composable
            override fun asLabel(): String = "Invalid"
        }

        data class Custom(private val onMessage: @Composable () -> String) : Error {
            @Composable
            override fun asLabel(): String = onMessage()
        }
    }

    object SliderErrorScope : InputStateErrorScope<Error> {
        override fun error(onMessage: @Composable (() -> String)): Error = Error.Custom(onMessage)
    }
}


private class SliderPreviewParameterProvider : PreviewParameterProvider<DeviceProperty> {

    @Stable
    private object TestIntNull : SliderDeviceProperty<Int>(
        initValue = null,
        title = { "Test Int" },
        min = 1, max = 5,
        onValidate = {
            if (it == null) error { "Custom required error" } else null
        },
    )

    @Stable
    private object TestInt : SliderDeviceProperty<Int>(
        initValue = 4,
        title = { "Test Int" },
        min = 1, max = 5, steps = 3,
    )

    @Stable
    private object TestFloat : SliderDeviceProperty<Float>(
        initValue = 3f,
        title = { "Test Float" },
        min = 1f, max = 5f,
    )

    @Stable
    private object TestFloatIncrement : SliderDeviceProperty<Float>(
        initValue = 3f,
        title = { "Test Float" },
        min = 1f, max = 5f, increment = 0.1f,
    )

    val properties = listOf<DeviceProperty>(
        TestIntNull, TestInt, TestFloat, TestFloatIncrement,
    )
    override val values = properties.asSequence()
}

// @Preview
// @Composable
// private fun Preview(
//     @PreviewParameter(SliderPreviewParameterProvider::class) property: DeviceProperty,
// ) {
//     WhoppahTheme {
//         LaunchedEffect(Unit) { property.validate() }
//         SetPropDialog(
//             state = SetPropViewState(property = property),
//             navigateUp = {},
//             send = {},
//         )
//     }
// }