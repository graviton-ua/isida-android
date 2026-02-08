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
internal class SpRh1(value: Float? = null) : TableDayProperty<DevicePropertySpRh1>(property = DevicePropertySpRh1(value = value)) {
    override fun readValue(packet: TableDay) {
        val value = when (packet) {
            is TableDayV1 -> packet.spRh
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: TableDay): TableDay = when (packet) {
        //TODO: Float shouldn't be casted to INT !!!!!!!!!
        is TableDayV1 -> property.inputHelper.state.valueAsFloat?.let { packet.copy(spRh = it.toInt()) } ?: packet
        else -> packet
    }
}

//-------------------------- TurnTime ------------------------------
@Stable
internal class TurnTime(value: Int? = null) : TableDayProperty<DevicePropertyTurnTime>(property = DevicePropertyTurnTime(value = value)) {
    override fun readValue(packet: TableDay) {
        val value = when (packet) {
            is TableDayV1 -> packet.spTr
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: TableDay): TableDay = when (packet) {
        is TableDayV1 -> property.inputHelper.state.valueAsInt?.let { packet.copy(spTr = it) } ?: packet
        else -> packet
    }
}

//-------------------------- Flap restrictions ----------------------------
@Stable
internal class FlapRestrictions(value: Int? = null) :
    TableDayProperty<DevicePropertyFlapRestrictions>(property = DevicePropertyFlapRestrictions(value = value)) {
    override fun readValue(packet: TableDay) {
        val value = when (packet) {
            is TableDayV1 -> packet.spFlp
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: TableDay): TableDay = when (packet) {
        is TableDayV1 -> property.inputHelper.state.valueAsInt?.let { packet.copy(spFlp = it) } ?: packet
        else -> packet
    }
}

//-------------------------- WaitCooling -------------------------
@Stable
internal class WaitCooling(value: Int? = null) :
    TableDayProperty<DevicePropertyWaitCooling>(property = DevicePropertyWaitCooling(value = value)) {
    override fun readValue(packet: TableDay) {
        val value = when (packet) {
            is TableDayV1 -> packet.spCl
            else -> null
        }
        property.inputHelper.setValue(value)
    }

    override fun copyAndUpdate(packet: TableDay): TableDay = when (packet) {
        is TableDayV1 -> property.inputHelper.state.valueAsInt?.let { packet.copy(spCl = it) } ?: packet
        else -> packet
    }
}