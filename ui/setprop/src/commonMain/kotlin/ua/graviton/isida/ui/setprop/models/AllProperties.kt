package ua.graviton.isida.ui.setprop.models

import androidx.compose.runtime.Stable
import com.whoppah.common.compose.input.NumberInputTextFieldState
import com.whoppah.common.resources.Res
import com.whoppah.common.resources.chickens
import com.whoppah.common.resources.error_01
import com.whoppah.common.resources.prop_CO2_lb
import com.whoppah.common.resources.prop_Hysteresis_lb
import com.whoppah.common.resources.prop_air0_lb
import com.whoppah.common.resources.prop_air1_lb
import com.whoppah.common.resources.prop_alarm0_lb
import com.whoppah.common.resources.prop_alarm1_lb
import com.whoppah.common.resources.dimen_celsius
import com.whoppah.common.resources.dimen_min
import com.whoppah.common.resources.dimen_percent
import com.whoppah.common.resources.dimen_ppm
import com.whoppah.common.resources.dimen_sec
import com.whoppah.common.resources.ducklings
import com.whoppah.common.resources.goose
import com.whoppah.common.resources.no
import com.whoppah.common.resources.prop_Rh1_lb
import com.whoppah.common.resources.prop_Rh2_lb
import com.whoppah.common.resources.prop_Rh_lb
import com.whoppah.common.resources.prop_extMode0
import com.whoppah.common.resources.prop_extMode1
import com.whoppah.common.resources.prop_extMode2
import com.whoppah.common.resources.prop_extMode3
import com.whoppah.common.resources.prop_extMode4
import com.whoppah.common.resources.prop_extMode5
import com.whoppah.common.resources.prop_extMode_lb
import com.whoppah.common.resources.prop_extOff0_lb
import com.whoppah.common.resources.prop_extOff1_lb
import com.whoppah.common.resources.prop_extOn0_lb
import com.whoppah.common.resources.prop_extOn1_lb
import com.whoppah.common.resources.prop_identif_lb
import com.whoppah.common.resources.prop_ikoff0_lb
import com.whoppah.common.resources.prop_ikoff1_lb
import com.whoppah.common.resources.prop_koffCurr_lb
import com.whoppah.common.resources.prop_maxImpulse_lb
import com.whoppah.common.resources.prop_minImpulse_lb
import com.whoppah.common.resources.prop_pkoff0_lb
import com.whoppah.common.resources.prop_pkoff1_lb
import com.whoppah.common.resources.prop_program_lb
import com.whoppah.common.resources.prop_relMode0
import com.whoppah.common.resources.prop_relMode1
import com.whoppah.common.resources.prop_relMode2
import com.whoppah.common.resources.prop_relMode3
import com.whoppah.common.resources.prop_relMode4
import com.whoppah.common.resources.prop_relMode_lb
import com.whoppah.common.resources.prop_repeatTime_lb
import com.whoppah.common.resources.prop_spRh0_lb
import com.whoppah.common.resources.prop_spRh1_lb
import com.whoppah.common.resources.prop_spT0_lb
import com.whoppah.common.resources.prop_spT1_lb
import com.whoppah.common.resources.prop_state_lb
import com.whoppah.common.resources.prop_turnOff_lb
import com.whoppah.common.resources.prop_turnOn_lb
import com.whoppah.common.resources.prop_turnTime_lb
import com.whoppah.common.resources.prop_waitCooling_lb
import com.whoppah.common.resources.prop_zoneFlap_lb
import com.whoppah.common.resources.quail
import org.jetbrains.compose.resources.stringResource
import ua.graviton.isida.data.protocol.packets.StatusPacket
import ua.graviton.isida.data.protocol.packets.v1.StatusPacketV1
import ua.graviton.isida.ui.setprop.models.types.NumberInputTextFieldDeviceProperty
import ua.graviton.isida.ui.setprop.models.types.SliderDeviceProperty
import ua.graviton.isida.ui.setprop.models.types.RadioListDeviceProperty

internal fun propertyFromId(id: String): DeviceProperty = when (id) {
    "spT0" -> SpT0()
    "spT1" -> SpT1()
    "permission" -> Permission()
    "spRh0" -> SpRh0()
    "spRh1" -> SpRh1()
    "extendMode" -> ExtendMode()
    "relayMode" -> RelayMode()
    "program" -> Program()
    "minRun" -> MinRun()
    "maxRun" -> MaxRun()
    "period" -> Period()
    "turnOff" -> TurnOff()
    "turnOn" -> TurnOn()
    "alarm0" -> Alarm0()
    "alarm1" -> Alarm1()
    "extOn0" -> ExtOn0()
    "extOn1" -> ExtOn1()
    "extOff0" -> ExtOff0()
    "extOff1" -> ExtOff1()
    "air0" -> Air0()
    "air1" -> Air1()
    "spCO2" -> SpCO2()
    "koffCurr" -> KoffCurr()
    "hysteresis" -> Hysteresis()
    "zonaFlap" -> ZonaFlap()
    "turnTime" -> TurnTime()
    "waitCooling" -> WaitCooling()
    "pkoff0" -> Pkoff0()
    "pkoff1" -> Pkoff1()
    "ikoff0" -> Ikoff0()
    "ikoff1" -> Ikoff1()
    "identif" -> Identif()

    else -> throw IllegalStateException("Unknown property id: $id")
}

//-------------------------- spT0 ------------------------------
@Stable
internal class SpT0(value: Float? = null) : SliderDeviceProperty<Float>(
    initValue = value, //allowDecimals = true,
    min = 35f, max = 40f, increment = 0.1f,
    title = { stringResource(Res.string.prop_spT0_lb)+ stringResource(Res.string.dimen_celsius) },
    // description = { "A little bit of descirption\n for this fantastic property" },
    onValidate = { floatValue ->
        //val floatValue = value.toFloatOrNull()
        when {
            floatValue == null -> error { "Some error message here + resource" + stringResource(Res.string.error_01) }
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

//-------------------------- spT1 ------------------------------
@Stable
internal class SpT1(value: Float? = null) : NumberInputTextFieldDeviceProperty<Float>(
    initValue = value, allowDecimals = true,
    title = { stringResource(Res.string.prop_spT1_lb)+ stringResource(Res.string.dimen_celsius)},
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

//-------------------------- Permission (маска 0xC0) ------------------------------
@Stable
internal class Permission(value: Int? = null) : RadioListDeviceProperty<Int>(
    initValue = value,        // нужно применить маску 0xC0 !!!!!!!!!!
    title = { stringResource(Res.string.prop_Rh_lb) },
    list = listOf(0, 1, 2),
    listItemTitleMap = { modeIndex ->
        val id = when (modeIndex) {
            0 -> Res.string.no
            1 -> Res.string.prop_Rh1_lb
            2 -> Res.string.prop_Rh2_lb
            else -> null
        }
        if (id != null) stringResource(id) else "Unknown"
    },
) {
        override fun readValue(packet: StatusPacket) {
            val value = when (packet) {
                is StatusPacketV1 -> packet.permission
                else -> null
            }
            inputHelper.setValue(value)
        }

        override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
            is StatusPacketV1 -> inputHelper.value?.let {
                packet.copy(permission = it) } ?: packet
            else -> packet
        }
    }

//-------------------------- spRh0 ------------------------------
@Stable
internal class SpRh0(value: Float? = null) : SliderDeviceProperty<Float>(
    initValue = value,
    min = 20f, max = 80f, steps = 59,
    title = { stringResource(Res.string.prop_spRh0_lb)+ stringResource(Res.string.dimen_percent) },
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

//-------------------------- spRh1 ------------------------------
@Stable
internal class SpRh1(value: Float? = null) : NumberInputTextFieldDeviceProperty<Float>(
    initValue = value, allowDecimals = true,
    title = { stringResource(Res.string.prop_spRh1_lb)+ stringResource(Res.string.dimen_percent) },
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

//-------------------------- ExtendMode ------------------------------
@Stable
internal class ExtendMode(value: Int? = null) : RadioListDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_extMode_lb) },
    list = listOf(0, 1, 2, 3, 4, 5),
    listItemTitleMap = { modeIndex ->
            val id = when (modeIndex) {
                0 -> Res.string.prop_extMode0
                1 -> Res.string.prop_extMode1
                2 -> Res.string.prop_extMode2
                3 -> Res.string.prop_extMode3
                4 -> Res.string.prop_extMode4
                5 -> Res.string.prop_extMode5
                else -> null
            }
            if (id != null) stringResource(id) else "Unknown"
    },
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.extendMode
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.value?.let { packet.copy(extendMode = it) } ?: packet
        else -> packet
    }
}

//-------------------------- RelayMode ------------------------------
@Stable
internal class RelayMode(value: Int? = null) : RadioListDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_relMode_lb) },
    list = listOf(0, 1, 2, 3, 4),
    listItemTitleMap = { modeIndex ->
            val id = when (modeIndex) {
                0 -> Res.string.prop_relMode0
                1 -> Res.string.prop_relMode1
                2 -> Res.string.prop_relMode2
                3 -> Res.string.prop_relMode3
                4 -> Res.string.prop_relMode4
                else -> null
            }
        if (id != null) stringResource(id) else "Unknown"
    },
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

//-------------------------- Program ------------------------------
@Stable
internal class Program(value: Int? = null) : RadioListDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_program_lb) },
    list = listOf(0, 1, 2, 3, 4),
    listItemTitleMap = {
        when(it) {
            0-> stringResource(Res.string.no)
            else-> {
                val label = stringResource(Res.string.prop_program_lb)
                "$label $it"
            }
        }
    },
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.programm
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.value?.let { packet.copy(programm = it) } ?: packet
        else -> packet
    }
}


//-------------------------- MinRun ------------------------------
@Stable
internal class MinRun(value: Float? = null) : NumberInputTextFieldDeviceProperty<Float>(
    initValue = value,
    title = { stringResource(Res.string.prop_minImpulse_lb)+ stringResource(Res.string.dimen_sec) },
    onValidate = {
        val intValue = it.toIntOrNull()
        when {
            intValue == null -> NumberInputTextFieldState.Error.Invalid
            // intValue < 100 -> NumberInputTextFieldState.Error.CantBeLessThen("100")
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
        is StatusPacketV1 -> inputHelper.state.valueAsFloat?.let { packet.copy(minRun = it) } ?: packet
        else -> packet
    }
}

//-------------------------- MaxRun ------------------------------
@Stable
internal class MaxRun(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_maxImpulse_lb)+ stringResource(Res.string.dimen_sec) },
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

//-------------------------- Period ------------------------------
@Stable
internal class Period(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_repeatTime_lb)+ stringResource(Res.string.dimen_min) },
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

//-------------------------- TurnOff ------------------------------
@Stable
internal class TurnOff(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_turnOff_lb)+ stringResource(Res.string.dimen_min) },
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

//-------------------------- TurnOn ------------------------------
@Stable
internal class TurnOn(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_turnOn_lb)+ stringResource(Res.string.dimen_min) },
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

//-------------------------- Alarm0 ------------------------------
@Stable
internal class Alarm0(value: Float? = null) : NumberInputTextFieldDeviceProperty<Float>(
    initValue = value,
    title = { stringResource(Res.string.prop_alarm0_lb)+ stringResource(Res.string.dimen_celsius) },
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

//-------------------------- Alarm1 ------------------------------
@Stable
internal class Alarm1(value: Float? = null) : NumberInputTextFieldDeviceProperty<Float>(
    initValue = value,
    title = { stringResource(Res.string.prop_alarm1_lb)+ stringResource(Res.string.dimen_celsius) },
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

//-------------------------- ExtOn0 ------------------------------
@Stable
internal class ExtOn0(value: Float? = null) : NumberInputTextFieldDeviceProperty<Float>(
    initValue = value,
    title = { stringResource(Res.string.prop_extOn0_lb)+ stringResource(Res.string.dimen_celsius) },
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

//-------------------------- ExtOn1 ------------------------------
@Stable
internal class ExtOn1(value: Float? = null) : NumberInputTextFieldDeviceProperty<Float>(
    initValue = value,
    title = { stringResource(Res.string.prop_extOn1_lb)+ stringResource(Res.string.dimen_celsius) },
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

//-------------------------- ExtOff0 ------------------------------
@Stable
internal class ExtOff0(value: Float? = null) : NumberInputTextFieldDeviceProperty<Float>(
    initValue = value,
    title = { stringResource(Res.string.prop_extOff0_lb)+ stringResource(Res.string.dimen_celsius) },
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

//-------------------------- ExtOff1 ------------------------------
@Stable
internal class ExtOff1(value: Float? = null) : NumberInputTextFieldDeviceProperty<Float>(
    initValue = value,
    title = { stringResource(Res.string.prop_extOff1_lb)+ stringResource(Res.string.dimen_celsius) },
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

//-------------------------- Air0 ------------------------------
@Stable
internal class Air0(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_air0_lb)+ stringResource(Res.string.dimen_celsius) },
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

//-------------------------- Air1 ------------------------------
@Stable
internal class Air1(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_air1_lb)+ stringResource(Res.string.dimen_celsius) },
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

//-------------------------- SpCO2 ------------------------------
@Stable
internal class SpCO2(value: Float? = null) : SliderDeviceProperty<Float>(
    initValue = (value ?: 20f),
    min = 400f, max = 5000f, increment = 100f,
    title = { stringResource(Res.string.prop_CO2_lb)+ stringResource(Res.string.dimen_ppm) },
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.spCO2 * 20f
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.value?.let { packet.copy(spCO2 = it) } ?: packet
        else -> packet
    }
}

//-------------------------- KoffCurr ------------------------------
@Stable
internal class KoffCurr(value: Float? = null) : SliderDeviceProperty<Float>(
    initValue = value,
    min = 0f, max = 200f, increment = 10f,
    title = { stringResource(Res.string.prop_koffCurr_lb) },
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.koffCurr.toFloat()
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.value?.let { packet.copy(koffCurr = it.toInt()) } ?: packet
        else -> packet
    }
}

//-------------------------- Hysteresis (маска 0x3F) ------------------------------
@Stable
internal class Hysteresis(value: Float? = null) : SliderDeviceProperty<Float>(
    initValue = (value ?: 0.2f),        // нужно применить маску 0x3F !!!!!!!!!!
    min = 0.2f, max = 6.0f, increment = 0.2f,
    title = { stringResource(Res.string.prop_Hysteresis_lb)+ stringResource(Res.string.dimen_celsius) },
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> (packet.hysteresis)
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.value?.let { packet.copy(hysteresis = it) } ?: packet
        else -> packet
    }
}

//-------------------------- TurnTime ------------------------------
@Stable
internal class TurnTime(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_turnTime_lb)+ stringResource(Res.string.dimen_sec) },
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

//-------------------------- ZonaFlap ----------------------------
@Stable
internal class ZonaFlap(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_zoneFlap_lb)+ stringResource(Res.string.dimen_celsius) },
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.zonaFlap
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.state.valueAsInt?.let { packet.copy(zonaFlap = it) } ?: packet
        else -> packet
    }
}

//-------------------------- WaitCooling -------------------------
@Stable
internal class WaitCooling(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_waitCooling_lb)+ stringResource(Res.string.dimen_sec) },
) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.waitCooling
            else -> null
        }
        inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> inputHelper.state.valueAsInt?.let { packet.copy(waitCooling = it) } ?: packet
        else -> packet
    }
}

//-------------------------- Pkoff0 ------------------------------
@Stable
internal class Pkoff0(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_pkoff0_lb) },
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

//-------------------------- Pkoff1 ------------------------------
@Stable
internal class Pkoff1(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_pkoff1_lb) },
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

//-------------------------- Ikoff0 ------------------------------
@Stable
internal class Ikoff0(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_ikoff0_lb) },
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

//-------------------------- Ikoff1 ------------------------------
@Stable
internal class Ikoff1(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_ikoff1_lb) },
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

//-------------------------- Identif ------------------------------
@Stable
internal class Identif(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_identif_lb) },
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