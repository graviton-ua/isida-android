package ua.graviton.isida.ui.setprop.models

import androidx.compose.runtime.Stable
import com.whoppah.common.compose.input.NumberInputTextFieldState
import ua.graviton.isida.data.protocol.packets.StatusPacket
import ua.graviton.isida.data.protocol.packets.v1.StatusPacketV1
import ua.graviton.isida.ui.setprop.models.types.NumberInputTextFieldDeviceProperty
import ua.graviton.isida.ui.setprop.models.types.SliderDeviceProperty
import ua.graviton.isida.ui.setprop.models.types.RadioListDeviceProperty

internal fun propertyFromId(id: String): DeviceProperty = when (id) {
    "spT0" -> SpT0()
    "spT1" -> SpT1()
    "spRh0" -> SpRh0()
    "spRh1" -> SpRh1()
    "pkoff0" -> Pkoff0()
    "pkoff1" -> Pkoff1()
    "ikoff0" -> Ikoff0()
    "ikoff1" -> Ikoff1()
    "minRun" -> MinRun()
    "maxRun" -> MaxRun()
    "period" -> Period()
    "timer0" -> Timer0()
    "timer1" -> Timer1()
    "alarm0" -> Alarm0()
    "alarm1" -> Alarm1()
    "extOn0" -> ExtOn0()
    "extOn1" -> ExtOn1()
    "extOff0" -> ExtOff0()
    "extOff1" -> ExtOff1()
    "air0" -> Air0()
    "air1" -> Air1()
    "spCO2" -> SpCO2()
    "identif" -> Identif()
    "state" -> State()
    "extendMode" -> ExtendMode()
    "relayMode" -> RelayMode()
    "program" -> Program()
    "hysteresis" -> Hysteresis()
    "turnTime" -> TurnTime()
    else -> throw IllegalStateException("Unknown property id: $id")
}

@Stable
internal class SpT0(value: Float? = null) : SliderDeviceProperty<Float>(
    initValue = value, //allowDecimals = true,
    min = 25f, max = 40f,
    title = { "Andrew hello SpT0" },
    description = { "A little bit of descirption\n for this fantastic property" },
    onValidate = { floatValue ->
        //val floatValue = value.toFloatOrNull()
        when {
            floatValue == null -> SliderDeviceProperty.Error.Invalid
            // floatValue == 56f -> SliderDeviceProperty.Error.Custom("Пример своей собственной ошибки")
            // floatValue < 25f -> SliderDeviceProperty.Error.CantBeLessThen("25")
            // floatValue > 40f -> SliderDeviceProperty.Error.CantBeMoreThen("40")
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
        is StatusPacketV1 -> inputHelper.value?.let { packet.copy(spT0 = it) } ?: packet
        else -> packet
    }
}

@Stable
internal class SpT1(value: Float? = null) : NumberInputTextFieldDeviceProperty<Float>(
    initValue = value, allowDecimals = true,
    title = { "SpT1" },
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
internal class SpRh0(value: Float? = null) : SliderDeviceProperty<Float>(
    initValue = value,
    min = 20f, max = 80f, steps = 59,
    title = { "SpRh0" },
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.spRh0
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.value?.let { packet.copy(spRh0 = it) } ?: packet
        else -> packet
    }
}

@Stable
internal class SpRh1(value: Float? = null) : NumberInputTextFieldDeviceProperty<Float>(
    initValue = value, allowDecimals = true,
    title = { "SpRh1" },
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
    title = { "Pkoff0" },
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
    title = { "Pkoff1" },
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
    title = { "Ikoff0" },
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
    title = { "Ikoff1" },
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
    title = { "MinRun" },
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
    title = { "MaxRun" },
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
    title = { "Period" },
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
    title = { "Timer0" },
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
    title = { "Timer1" },
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
    title = { "Alarm0" },
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
    title = { "Alarm1" },
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
    title = { "ExtOn0" },
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
    title = { "ExtOn1" },
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
    title = { "ExtOff0" },
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
    title = { "ExtOff1" },
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
    title = { "Air0" },
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
    title = { "Air1" },
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
    title = { "SpCO2" },
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
    title = { "Identif" },
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
    title = { "State" },
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
    title = { "ExtendMode" },
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
internal class RelayMode(value: Int? = null) : RadioListDeviceProperty<Int>(
    initValue = value,
    title = { "RelayMode" },
    list = listOf(1, 2, 3, 4, 5),
    listItemTitleMap = { "Example of item title $it" },
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.relayMode
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.value?.let { packet.copy(relayMode = it) } ?: packet
        else -> packet
    }
}

@Stable
internal class Program(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { "Program" },
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
    title = { "Hysteresis" },
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
    title = { "TurnTime" },
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