package ua.graviton.isida.ui.setprop.models

import androidx.compose.runtime.Stable
import ua.graviton.isida.data.protocol.packets.StatusPacket
import ua.graviton.isida.data.protocol.packets.v1.StatusPacketV1
import ua.graviton.isida.ui.properties.*

fun propertyFromId(id: String): StatusPacketProperty<*> = when (id) {
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
    "zonality" -> Zonality()
    "flapRestrictions" -> FlapRestrictions()
    "turnTime" -> TurnTime()
    "waitCooling" -> WaitCooling()
    "pkoff0" -> Pkoff0()
    "pkoff1" -> Pkoff1()
    "ikoff0" -> Ikoff0()
    "ikoff1" -> Ikoff1()
    "identif" -> Identif()
    "gearbox" -> GearBox()

    else -> throw IllegalStateException("Unknown property id: $id")
}

//-------------------------- spT0 ------------------------------
@Stable
internal class SpT0(
    value: Float? = null,
) : StatusPacketProperty<DevicePropertySpT0>(property = DevicePropertySpT0(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.spT0
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.state.valueAsFloat?.let { packet.copy(spT0 = it) } ?: packet
        else -> packet
    }
}

//-------------------------- spT1 ------------------------------
@Stable
internal class SpT1(
    value: Float? = null,
) : StatusPacketProperty<DevicePropertySpT1>(property = DevicePropertySpT1(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.spT1
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.state.valueAsFloat?.let { packet.copy(spT1 = it) } ?: packet
        else -> packet
    }
}

//-------------------------- Permission (маска 0xC0) ------------------------------
@Stable
internal class Permission(
    value: Int? = null,
) : StatusPacketProperty<DevicePropertyPermission>(property = DevicePropertyPermission(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.permission
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.value?.let {
            packet.copy(permission = it)
        } ?: packet

        else -> packet
    }
}

//-------------------------- spRh0 ------------------------------
@Stable
internal class SpRh0(value: Int? = null) : StatusPacketProperty<DevicePropertySpRh0>(property = DevicePropertySpRh0(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.spRh0
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.state.valueAsInt?.let { packet.copy(spRh0 = it) } ?: packet
        else -> packet
    }
}

//-------------------------- spRh1 ------------------------------
@Stable
internal class SpRh1(value: Int? = null) : StatusPacketProperty<DevicePropertySpRh1>(property = DevicePropertySpRh1(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.spRh1
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.state.valueAsInt?.let { packet.copy(spRh1 = it) } ?: packet
        else -> packet
    }
}

//-------------------------- ExtendMode ------------------------------
@Stable
internal class ExtendMode(value: Int? = null) : StatusPacketProperty<DevicePropertyExtendMode>(property = DevicePropertyExtendMode(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.extendMode
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.value?.let { packet.copy(extendMode = it) } ?: packet
        else -> packet
    }
}

//-------------------------- RelayMode ------------------------------
@Stable
internal class RelayMode(value: Int? = null) : StatusPacketProperty<DevicePropertyRelayMode>(property = DevicePropertyRelayMode(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.relayMode
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.value?.let { packet.copy(relayMode = it) } ?: packet
        else -> packet
    }
}

//-------------------------- Program ------------------------------
@Stable
internal class Program(value: Int? = null) : StatusPacketProperty<DevicePropertyProgram>(property = DevicePropertyProgram(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.programm
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.value?.let { packet.copy(programm = it) } ?: packet
        else -> packet
    }
}


//-------------------------- MinRun ------------------------------
@Stable
internal class MinRun(value: Float? = null) : StatusPacketProperty<DevicePropertyMinRun>(property = DevicePropertyMinRun(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.minRun
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.state.valueAsFloat?.let { packet.copy(minRun = it) } ?: packet
        else -> packet
    }
}

//-------------------------- MaxRun ------------------------------
@Stable
internal class MaxRun(value: Int? = null) : StatusPacketProperty<DevicePropertyMaxRun>(property = DevicePropertyMaxRun(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.maxRun
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.state.valueAsInt?.let { packet.copy(maxRun = it) } ?: packet
        else -> packet
    }
}

//-------------------------- Period ------------------------------
@Stable
internal class Period(value: Int? = null) : StatusPacketProperty<DevicePropertyPeriod>(property = DevicePropertyPeriod(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.period
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.state.valueAsInt?.let { packet.copy(period = it) } ?: packet
        else -> packet
    }
}

//-------------------------- TurnOff ------------------------------
@Stable
internal class TurnOff(value: Int? = null) : StatusPacketProperty<DevicePropertyTurnOff>(property = DevicePropertyTurnOff(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.timer0
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.state.valueAsInt?.let { packet.copy(timer0 = it) } ?: packet
        else -> packet
    }
}

//-------------------------- TurnOn ------------------------------
@Stable
internal class TurnOn(value: Int? = null) : StatusPacketProperty<DevicePropertyTurnOn>(property = DevicePropertyTurnOn(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.timer1
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.state.valueAsInt?.let { packet.copy(timer1 = it) } ?: packet
        else -> packet
    }
}

//-------------------------- Alarm0 ------------------------------
@Stable
internal class Alarm0(value: Float? = null) : StatusPacketProperty<DevicePropertyAlarm0>(property = DevicePropertyAlarm0(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.alarm0
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.state.valueAsFloat?.let { packet.copy(alarm0 = it) } ?: packet
        else -> packet
    }
}

//-------------------------- Alarm1 ------------------------------
@Stable
internal class Alarm1(value: Float? = null) : StatusPacketProperty<DevicePropertyAlarm1>(property = DevicePropertyAlarm1(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.alarm1
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.state.valueAsFloat?.let { packet.copy(alarm1 = it) } ?: packet
        else -> packet
    }
}

//-------------------------- ExtOn0 ------------------------------
@Stable
internal class ExtOn0(value: Float? = null) : StatusPacketProperty<DevicePropertyExtOn0>(property = DevicePropertyExtOn0(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.extOn0
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.state.valueAsFloat?.let { packet.copy(extOn0 = it) } ?: packet
        else -> packet
    }
}

//-------------------------- ExtOn1 ------------------------------
@Stable
internal class ExtOn1(value: Float? = null) : StatusPacketProperty<DevicePropertyExtOn1>(property = DevicePropertyExtOn1(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.extOn1
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.state.valueAsFloat?.let { packet.copy(extOn1 = it) } ?: packet
        else -> packet
    }
}

//-------------------------- ExtOff0 ------------------------------
@Stable
internal class ExtOff0(value: Float? = null) : StatusPacketProperty<DevicePropertyExtOff0>(property = DevicePropertyExtOff0(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.extOff0
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.state.valueAsFloat?.let { packet.copy(extOff0 = it) } ?: packet
        else -> packet
    }
}

//-------------------------- ExtOff1 ------------------------------
@Stable
internal class ExtOff1(value: Float? = null) : StatusPacketProperty<DevicePropertyExtOff1>(property = DevicePropertyExtOff1(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.extOff1
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.state.valueAsFloat?.let { packet.copy(extOff1 = it) } ?: packet
        else -> packet
    }
}

//-------------------------- Air0 ------------------------------
@Stable
internal class Air0(value: Int? = null) : StatusPacketProperty<DevicePropertyAir0>(property = DevicePropertyAir0(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.air0
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.state.valueAsInt?.let { packet.copy(air0 = it) } ?: packet
        else -> packet
    }
}

//-------------------------- Air1 ------------------------------
@Stable
internal class Air1(value: Int? = null) : StatusPacketProperty<DevicePropertyAir1>(property = DevicePropertyAir1(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.air1
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.state.valueAsInt?.let { packet.copy(air1 = it) } ?: packet
        else -> packet
    }
}

//-------------------------- SpCO2 ------------------------------
@Stable
internal class SpCO2(value: Int? = null) : StatusPacketProperty<DevicePropertySpCO2>(property = DevicePropertySpCO2(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.spCO2
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.value?.let { packet.copy(spCO2 = it) } ?: packet
        else -> packet
    }
}

//-------------------------- Hysteresis (маска 0x3F) ------------------------------
@Stable
internal class Hysteresis(value: Float? = null) : StatusPacketProperty<DevicePropertyHysteresis>(property = DevicePropertyHysteresis(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> (packet.hysteresis)
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.value?.let { packet.copy(hysteresis = it) } ?: packet
        else -> packet
    }
}

//-------------------------- TurnTime ------------------------------
@Stable
internal class TurnTime(value: Int? = null) : StatusPacketProperty<DevicePropertyTurnTime>(property = DevicePropertyTurnTime(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.turnTime
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.value?.let { packet.copy(turnTime = it) } ?: packet
        else -> packet
    }
}

//-------------------------- Zonality ----------------------------
@Stable
internal class Zonality(value: Int? = null) : StatusPacketProperty<DevicePropertyZonality>(property = DevicePropertyZonality(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.zonality
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.value?.let { packet.copy(zonality = it) } ?: packet
        else -> packet
    }
}

//-------------------------- Flap restrictions ----------------------------
@Stable
internal class FlapRestrictions(value: Int? = null) :
    StatusPacketProperty<DevicePropertyFlapRestrictions>(property = DevicePropertyFlapRestrictions(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.flapRestrictions
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.value?.let { packet.copy(flapRestrictions = it) } ?: packet
        else -> packet
    }
}

//-------------------------- WaitCooling -------------------------
@Stable
internal class WaitCooling(value: Int? = null) :
    StatusPacketProperty<DevicePropertyWaitCooling>(property = DevicePropertyWaitCooling(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.waitCooling
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.value?.let { packet.copy(waitCooling = it) } ?: packet
        else -> packet
    }
}

//-------------------------- Pkoff0 ------------------------------
@Stable
internal class Pkoff0(value: Int? = null) : StatusPacketProperty<DevicePropertyPkoff0>(property = DevicePropertyPkoff0(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.pkoff0
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.state.valueAsInt?.let { packet.copy(pkoff0 = it) } ?: packet
        else -> packet
    }
}

//-------------------------- Pkoff1 ------------------------------
@Stable
internal class Pkoff1(value: Int? = null) : StatusPacketProperty<DevicePropertyPkoff1>(property = DevicePropertyPkoff1(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.pkoff1
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.state.valueAsInt?.let { packet.copy(pkoff1 = it) } ?: packet
        else -> packet
    }
}

//-------------------------- Ikoff0 ------------------------------
@Stable
internal class Ikoff0(value: Int? = null) : StatusPacketProperty<DevicePropertyIkoff0>(property = DevicePropertyIkoff0(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.ikoff0
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.state.valueAsInt?.let { packet.copy(ikoff0 = it) } ?: packet
        else -> packet
    }
}

//-------------------------- Ikoff1 ------------------------------
@Stable
internal class Ikoff1(value: Int? = null) : StatusPacketProperty<DevicePropertyIkoff1>(property = DevicePropertyIkoff1(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.ikoff1
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.state.valueAsInt?.let { packet.copy(ikoff1 = it) } ?: packet
        else -> packet
    }
}

//-------------------------- Identif ------------------------------
@Stable
internal class Identif(value: Int? = null) : StatusPacketProperty<DevicePropertyIdentif>(property = DevicePropertyIdentif(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.identif
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.state.valueAsInt?.let { packet.copy(identif = it) } ?: packet
        else -> packet
    }
}

//-------------------------- KoffCurr ------------------------------
@Stable
internal class KoffCurr(value: Int? = null) : StatusPacketProperty<DevicePropertyKoffCurr>(property = DevicePropertyKoffCurr(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.koffCurr
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.state.valueAsInt?.let { packet.copy(koffCurr = it) } ?: packet
        else -> packet
    }
}

//-------------------------- GearBox ------------------------------
@Stable
internal class GearBox(value: Int? = null) : StatusPacketProperty<DevicePropertyGearBox>(property = DevicePropertyGearBox(value = value)) {
    override fun readValue(packet: StatusPacket) {
        val value = when (packet) {
            is StatusPacketV1 -> packet.gearbox
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: StatusPacket): StatusPacket = when (packet) {
        is StatusPacketV1 -> property.inputHelper.state.valueAsInt?.let { packet.copy(gearbox = it) } ?: packet
        else -> packet
    }
}
