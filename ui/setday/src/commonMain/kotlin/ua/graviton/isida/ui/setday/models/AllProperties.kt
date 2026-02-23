package ua.graviton.isida.ui.setday.models

import androidx.compose.runtime.Stable
import ua.graviton.isida.data.protocol.packets.TableDay
import ua.graviton.isida.data.protocol.packets.v1.TableDayV1
import ua.graviton.isida.ui.properties.*

//-------------------------- spT0 ------------------------------
@Stable
internal class SpT0(
    value: Float? = null,
) : TableDayProperty<DevicePropertySpT0>(property = DevicePropertySpT0(value = value)) {
    override fun readValue(packet: TableDay) {
        val value = when (packet) {
            is TableDayV1 -> packet.spT0
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: TableDay): TableDay = when (packet) {
        is TableDayV1 -> property.inputHelper.state.valueAsFloat?.let { packet.copy(spT0 = it) } ?: packet
        else -> packet
    }
}

//-------------------------- spT1 ------------------------------
@Stable
internal class SpT1(
    value: Float? = null,
) : TableDayProperty<DevicePropertySpT1>(property = DevicePropertySpT1(value = value)) {
    override fun readValue(packet: TableDay) {
        val value = when (packet) {
            is TableDayV1 -> packet.spT1
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: TableDay): TableDay = when (packet) {
        is TableDayV1 -> property.inputHelper.state.valueAsFloat?.let { packet.copy(spT1 = it) } ?: packet
        else -> packet
    }
}

//-------------------------- spRh1 ------------------------------
@Stable
internal class SpRh1(value: Int? = null) : TableDayProperty<DevicePropertySpRh1>(property = DevicePropertySpRh1(value = value)) {
    override fun readValue(packet: TableDay) {
        val value = when (packet) {
            is TableDayV1 -> packet.spRh
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: TableDay): TableDay = when (packet) {
        is TableDayV1 -> property.inputHelper.state.valueAsInt?.let { packet.copy(spRh = it) } ?: packet
        else -> packet
    }
}

//-------------------------- TurnPermission ------------------------------
@Stable
internal class TurnPermission(value: Int? = null) : TableDayProperty<DevicePropertyTurnPermission>(property = DevicePropertyTurnPermission(value = value)) {
    override fun readValue(packet: TableDay) {
        val value = when (packet) {
            is TableDayV1 -> packet.spTr
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: TableDay): TableDay = when (packet) {
        is TableDayV1 -> property.inputHelper.value?.let { packet.copy(spTr = it) } ?: packet
        else -> packet
    }
}

//----------------- Opening the Flap for the current day ------------------
@Stable
internal class FlapProgramDay(value: Int? = null) :
    TableDayProperty<DevicePropertyFlapProgramDay>(property = DevicePropertyFlapProgramDay(value = value)) {

    override fun readValue(packet: TableDay) {
        val value = when (packet) {
            is TableDayV1 -> packet.spFlp
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: TableDay): TableDay = when (packet) {
        is TableDayV1 -> {
            val newValue = (property.inputHelper.value as? Number)?.toInt()
            if (newValue != null) packet.copy(spFlp = newValue) else packet
        }
        else -> packet
    }
}

//-------------------------- SpCO2 -------------------------
@Stable
internal class PropertySpCO2(value: Int? = null) :
    TableDayProperty<DevicePropertySpCO2>(property = DevicePropertySpCO2(value = value)) {
    override fun readValue(packet: TableDay) {
        val value = when (packet) {
            is TableDayV1 -> packet.spCO2
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: TableDay): TableDay = when (packet) {
        is TableDayV1 -> {
            val newValue = (property.inputHelper.value as? Number)?.toInt()
            if (newValue != null) packet.copy(spCO2 = newValue) else packet
        }
        else -> packet
    }
}