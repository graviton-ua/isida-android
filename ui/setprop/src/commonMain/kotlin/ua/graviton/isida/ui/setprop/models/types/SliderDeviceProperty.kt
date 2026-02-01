package ua.graviton.isida.ui.setprop.models.types

import androidx.annotation.IntRange
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.whoppah.common.compose.input.DefaultInputStateHelper
import com.whoppah.common.compose.input.InputState
import com.whoppah.common.compose.theme.WhoppahTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ua.graviton.isida.data.protocol.packets.StatusPacket
import ua.graviton.isida.ui.setprop.SetPropDialog
import ua.graviton.isida.ui.setprop.SetPropViewState
import ua.graviton.isida.ui.setprop.models.DeviceProperty

@Stable
internal abstract class SliderDeviceProperty<T : Number>(
    initValue: T? = null,
    val min: T, val max: T,
    @param:IntRange(from = 0) private val steps: Int = 0,
    override val title: @Composable () -> String,
    override val description: (@Composable () -> String)? = null,
    private val onValidate: (T?) -> Error? = { if (it == null) Error.Required else null },
) : DeviceProperty {
    protected val inputHelper = DefaultInputStateHelper(initValue = initValue, onValidate = onValidate)

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
                    style = WhoppahTheme.typography.h5,
                )
                Slider(
                    value = inputHelper.value?.toFloat() ?: 0f,
                    onValueChange = { inputHelper.setValue(it as T?) },
                    valueRange = min.toFloat()..max.toFloat(),
                    steps = steps,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "$max",
                    style = WhoppahTheme.typography.h5,
                )
            }

            if (inputHelper.state.isError)
                Text(
                    text = inputHelper.state.errorState.value?.asLabel() ?: "",
                    style = WhoppahTheme.typography.helper,
                    color = WhoppahTheme.colors.error,
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
    }
}


private class SliderPreviewParameterProvider : PreviewParameterProvider<DeviceProperty> {

    @Stable
    private object TestIntNull : SliderDeviceProperty<Int>(
        initValue = null,
        title = { "Test Int" },
        min = 1, max = 5,
    ) {
        override fun readValue(packet: StatusPacket) = Unit
        override fun copyAndUpdate(packet: StatusPacket): StatusPacket = packet
    }

    @Stable
    private object TestInt : SliderDeviceProperty<Int>(
        initValue = 4,
        title = { "Test Int" },
        min = 1, max = 5,
        steps = 3,
    ) {
        override fun readValue(packet: StatusPacket) = Unit
        override fun copyAndUpdate(packet: StatusPacket): StatusPacket = packet
    }

    @Stable
    private object TestFloat : SliderDeviceProperty<Float>(
        initValue = 3f,
        title = { "Test Float" },
        min = 1f, max = 5f,
    ) {
        override fun readValue(packet: StatusPacket) = Unit
        override fun copyAndUpdate(packet: StatusPacket): StatusPacket = packet
    }

    val properties = listOf<DeviceProperty>(
        TestIntNull, TestInt, TestFloat,
    )
    override val values = properties.asSequence()
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(SliderPreviewParameterProvider::class) property: DeviceProperty,
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