package ua.isida.ui.setprop.models

import androidx.compose.runtime.Stable
import ua.isida.data.protocol.packets.StatusPacket
import ua.isida.ui.properties.DeviceProperty

@Stable
abstract class StatusPacketProperty<T : DeviceProperty>(
    protected val property: T,
) : DeviceProperty by property {
    abstract fun readValue(packet: StatusPacket)
    abstract fun copyAndUpdate(packet: StatusPacket): StatusPacket
    abstract fun getInputValue(): String
}