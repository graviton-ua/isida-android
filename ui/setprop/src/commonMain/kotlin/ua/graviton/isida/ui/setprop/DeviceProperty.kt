package ua.graviton.isida.ui.setprop

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.whoppah.common.compose.input.DefaultInputStateHelper
import com.whoppah.common.compose.input.DefaultNumberInputTextFieldStateHelper
import com.whoppah.common.compose.input.NumberInputTextFieldState
import com.whoppah.common.compose.input.PriceInputTransformation
import com.whoppah.common.compose.ui.WhRadioButton
import com.whoppah.common.compose.ui.WhTextField
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import ua.graviton.isida.data.protocol.packets.StatusPacket
import ua.graviton.isida.data.protocol.packets.v1.StatusPacketV1

/**
 * A "Smart" property interface that encapsulates:
 * 1. UI State (InputState)
 * 2. Presentation Data (Title, etc.)
 * 3. Business Logic (Validation & Command Mapping)
 */
@Stable
interface DeviceProperty {
    val title: @Composable () -> Unit

    @Composable
    fun Content(modifier: Modifier)

    val isValid: Flow<Boolean>
    fun validate(): Boolean

    suspend fun validateOnInputUpdate()

    suspend fun clearErrorOnInputUpdate()

    fun readValue(packet: StatusPacket)
    fun copyAndUpdate(packet: StatusPacket): StatusPacket
}

@Stable
abstract class SingleSelectionListDeviceProperty<T>(
    preSelected: T? = null,
    val list: List<T>,
    override val title: @Composable () -> Unit,
) : DeviceProperty {
    protected val inputHelper = DefaultInputStateHelper(initValue = preSelected)

    @Composable
    override fun Content(modifier: Modifier) {
        Column {
            list.forEach { item ->
                ItemList(
                    item = item,
                    isSelected = inputHelper.value == item,
                    onClick = { inputHelper.setValue(item) },
                    modifier = modifier
                )
            }
        }
    }

    @Composable
    open fun ItemList(
        item: T,
        isSelected: Boolean,
        onClick: () -> Unit,
        modifier: Modifier,
    ) {
        WhRadioButton(
            selected = isSelected,
            onClick = onClick,
            modifier = modifier,
        ) { Text(text = "Item: $item") }
    }

    override val isValid: Flow<Boolean> = flowOf(true)
    override fun validate(): Boolean = true

    override suspend fun validateOnInputUpdate() = inputHelper.validateOnInputUpdate()
    override suspend fun clearErrorOnInputUpdate() = inputHelper.clearErrorOnInputUpdate()
}

@Stable
abstract class NumberInputTextFieldDeviceProperty<T : Number>(
    initValue: T? = null,
    override val title: @Composable () -> Unit,
    private val allowDecimals: Boolean = false,
    private val onValidate: (String) -> NumberInputTextFieldState.Error? = { null },
) : DeviceProperty {
    protected val inputHelper = DefaultNumberInputTextFieldStateHelper(
        initValue = initValue,
        onValidate = onValidate,
    )

    @Composable
    override fun Content(modifier: Modifier) {
        WhTextField(
            state = inputHelper.state,
            inputTransformation = PriceInputTransformation(allowDecimals = allowDecimals),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = modifier,
        )
    }

    override val isValid: Flow<Boolean> = inputHelper.errorFlow.map { it == null }
    override fun validate(): Boolean = inputHelper.validate()?.let { false } ?: true

    override suspend fun validateOnInputUpdate() = inputHelper.validateOnInputUpdate()
    override suspend fun clearErrorOnInputUpdate() = inputHelper.clearErrorOnInputUpdate()
}

// ===================================================

@Stable
internal class SpT0(value: Float? = null) : NumberInputTextFieldDeviceProperty<Float>(
    initValue = value, allowDecimals = true,
    title = { Text(text = "SpT0") },
    onValidate = { value ->
        val floatValue = value.toFloatOrNull()
        when {
            floatValue == null -> NumberInputTextFieldState.Error.Invalid
            floatValue < 25f -> NumberInputTextFieldState.Error.CantBeLessThen("25")
            floatValue > 40f -> NumberInputTextFieldState.Error.CantBeMoreThen("40")
            else -> null
        }
    },
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.spT0
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.state.valueAsFloat?.let { packet.copy(spT0 = it) } ?: packet
        else -> packet
    }
}

@Stable
internal class SpT1(value: Float? = null) : NumberInputTextFieldDeviceProperty<Float>(
    initValue = value, allowDecimals = true,
    title = { Text(text = "SpT1") },
    onValidate = { value ->
        val floatValue = value.toFloatOrNull()
        when {
            floatValue == null -> NumberInputTextFieldState.Error.Invalid
            floatValue < 25f -> NumberInputTextFieldState.Error.CantBeLessThen("25")
            floatValue > 40f -> NumberInputTextFieldState.Error.CantBeMoreThen("40")
            else -> null
        }
    },
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.spT1
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.state.valueAsFloat?.let { packet.copy(spT1 = it) } ?: packet
        else -> packet
    }
}

@Stable
internal class SpRh0(value: Float? = null) : NumberInputTextFieldDeviceProperty<Float>(
    initValue = value, allowDecimals = true,
    title = { Text(text = "SpRh0") },
    onValidate = { value ->
        val floatValue = value.toFloatOrNull()
        when {
            floatValue == null -> NumberInputTextFieldState.Error.Invalid
            floatValue < 20f -> NumberInputTextFieldState.Error.CantBeLessThen("20")
            floatValue > 80f -> NumberInputTextFieldState.Error.CantBeMoreThen("80")
            else -> null
        }
    },
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.spRh0
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.state.valueAsFloat?.let { packet.copy(spRh0 = it) } ?: packet
        else -> packet
    }
}

@Stable
internal class SpRh1(value: Float? = null) : NumberInputTextFieldDeviceProperty<Float>(
    initValue = value, allowDecimals = true,
    title = { Text(text = "SpRh1") },
    onValidate = { value ->
        val floatValue = value.toFloatOrNull()
        when {
            floatValue == null -> NumberInputTextFieldState.Error.Invalid
            floatValue < 20f -> NumberInputTextFieldState.Error.CantBeLessThen("20")
            floatValue > 80f -> NumberInputTextFieldState.Error.CantBeMoreThen("80")
            else -> null
        }
    },
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.spRh1
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.state.valueAsFloat?.let { packet.copy(spRh1 = it) } ?: packet
        else -> packet
    }
}

@Stable
internal class Pkoff0(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { Text(text = "Pkoff0") },
    onValidate = {
        val intValue = it.toIntOrNull()
        when {
            intValue == null -> NumberInputTextFieldState.Error.Invalid
            intValue < 1 -> NumberInputTextFieldState.Error.CantBeLessThen("1")
            else -> null
        }
    }
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.pkoff0
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.state.valueAsInt?.let { packet.copy(pkoff0 = it) } ?: packet
        else -> packet
    }
}

@Stable
internal class Pkoff1(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { Text(text = "Pkoff1") },
    onValidate = {
        val intValue = it.toIntOrNull()
        when {
            intValue == null -> NumberInputTextFieldState.Error.Invalid
            intValue < 1 -> NumberInputTextFieldState.Error.CantBeLessThen("1")
            else -> null
        }
    }
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.pkoff1
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.state.valueAsInt?.let { packet.copy(pkoff1 = it) } ?: packet
        else -> packet
    }
}

@Stable
internal class Ikoff0(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { Text(text = "Ikoff0") },
    onValidate = {
        val intValue = it.toIntOrNull()
        when {
            intValue == null -> NumberInputTextFieldState.Error.Invalid
            intValue < 100 -> NumberInputTextFieldState.Error.CantBeLessThen("100")
            else -> null
        }
    }
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.ikoff0
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.state.valueAsInt?.let { packet.copy(ikoff0 = it) } ?: packet
        else -> packet
    }
}

@Stable
internal class Ikoff1(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { Text(text = "Ikoff1") },
    onValidate = {
        val intValue = it.toIntOrNull()
        when {
            intValue == null -> NumberInputTextFieldState.Error.Invalid
            intValue < 100 -> NumberInputTextFieldState.Error.CantBeLessThen("100")
            else -> null
        }
    }
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.ikoff1
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.state.valueAsInt?.let { packet.copy(ikoff1 = it) } ?: packet
        else -> packet
    }
}

@Stable
internal class MinRun(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { Text(text = "MinRun") },
    onValidate = {
        val intValue = it.toIntOrNull()
        when {
            intValue == null -> NumberInputTextFieldState.Error.Invalid
            intValue < 100 -> NumberInputTextFieldState.Error.CantBeLessThen("100")
            else -> null
        }
    }
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.minRun
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.state.valueAsInt?.let { packet.copy(minRun = it) } ?: packet
        else -> packet
    }
}

@Stable
internal class MaxRun(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { Text(text = "MaxRun") },
    onValidate = {
        val intValue = it.toIntOrNull()
        when {
            intValue == null -> NumberInputTextFieldState.Error.Invalid
            intValue < 1 -> NumberInputTextFieldState.Error.CantBeLessThen("1")
            else -> null
        }
    }
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.maxRun
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.state.valueAsInt?.let { packet.copy(maxRun = it) } ?: packet
        else -> packet
    }
}

@Stable
internal class Period(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { Text(text = "Period") },
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.period
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.state.valueAsInt?.let { packet.copy(period = it) } ?: packet
        else -> packet
    }
}

@Stable
internal class Timer0(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { Text(text = "Timer0") },
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.timer0
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.state.valueAsInt?.let { packet.copy(timer0 = it) } ?: packet
        else -> packet
    }
}

@Stable
internal class Timer1(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { Text(text = "Timer1") },
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.timer1
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.state.valueAsInt?.let { packet.copy(timer1 = it) } ?: packet
        else -> packet
    }
}

@Stable
internal class Alarm0(value: Float? = null) : NumberInputTextFieldDeviceProperty<Float>(
    initValue = value,
    title = { Text(text = "Alarm0") },
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.alarm0
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.state.valueAsFloat?.let { packet.copy(alarm0 = it) } ?: packet
        else -> packet
    }
}

@Stable
internal class Alarm1(value: Float? = null) : NumberInputTextFieldDeviceProperty<Float>(
    initValue = value,
    title = { Text(text = "Alarm1") },
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.alarm1
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.state.valueAsFloat?.let { packet.copy(alarm1 = it) } ?: packet
        else -> packet
    }
}

@Stable
internal class ExtOn0(value: Float? = null) : NumberInputTextFieldDeviceProperty<Float>(
    initValue = value,
    title = { Text(text = "ExtOn0") },
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.extOn0
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.state.valueAsFloat?.let { packet.copy(extOn0 = it) } ?: packet
        else -> packet
    }
}

@Stable
internal class ExtOn1(value: Float? = null) : NumberInputTextFieldDeviceProperty<Float>(
    initValue = value,
    title = { Text(text = "ExtOn1") },
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.extOn1
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.state.valueAsFloat?.let { packet.copy(extOn1 = it) } ?: packet
        else -> packet
    }
}

@Stable
internal class ExtOff0(value: Float? = null) : NumberInputTextFieldDeviceProperty<Float>(
    initValue = value,
    title = { Text(text = "ExtOff0") },
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.extOff0
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.state.valueAsFloat?.let { packet.copy(extOff0 = it) } ?: packet
        else -> packet
    }
}

@Stable
internal class ExtOff1(value: Float? = null) : NumberInputTextFieldDeviceProperty<Float>(
    initValue = value,
    title = { Text(text = "ExtOff1") },
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.extOff1
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.state.valueAsFloat?.let { packet.copy(extOff1 = it) } ?: packet
        else -> packet
    }
}

@Stable
internal class Air0(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { Text(text = "Air0") },
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.air0
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.state.valueAsInt?.let { packet.copy(air0 = it) } ?: packet
        else -> packet
    }
}

@Stable
internal class Air1(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { Text(text = "Air1") },
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.air1
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.state.valueAsInt?.let { packet.copy(air1 = it) } ?: packet
        else -> packet
    }
}

@Stable
internal class SpCO2(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { Text(text = "SpCO2") },
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.spCO2
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.state.valueAsInt?.let { packet.copy(spCO2 = it) } ?: packet
        else -> packet
    }
}

@Stable
internal class Identif(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { Text(text = "Identif") },
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.identif
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.state.valueAsInt?.let { packet.copy(identif = it) } ?: packet
        else -> packet
    }
}

@Stable
internal class State(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { Text(text = "State") },
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.state
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.state.valueAsInt?.let { packet.copy(state = it) } ?: packet
        else -> packet
    }
}

@Stable
internal class ExtendMode(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { Text(text = "ExtendMode") },
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.extendMode
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.state.valueAsInt?.let { packet.copy(extendMode = it) } ?: packet
        else -> packet
    }
}

@Stable
internal class RelayMode(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { Text(text = "RelayMode") },
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.relayMode
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.state.valueAsInt?.let { packet.copy(relayMode = it) } ?: packet
        else -> packet
    }
}

@Stable
internal class Program(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { Text(text = "Program") },
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.programm
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.state.valueAsInt?.let { packet.copy(programm = it) } ?: packet
        else -> packet
    }
}

@Stable
internal class Hysteresis(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { Text(text = "Hysteresis") },
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.hysteresis
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.state.valueAsInt?.let { packet.copy(hysteresis = it) } ?: packet
        else -> packet
    }
}

@Stable
internal class TurnTime(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { Text(text = "TurnTime") },
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.turnTime
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.state.valueAsInt?.let { packet.copy(turnTime = it) } ?: packet
        else -> packet
    }
}