package ua.graviton.isida.ui.setprop.models

import androidx.compose.runtime.Stable
import ua.graviton.isida.data.protocol.packets.StatusPacket
import ua.graviton.isida.ui.properties.DeviceProperty

@Stable
abstract class StatusPacketProperty<T : DeviceProperty>(
    protected val property: T,
) : DeviceProperty by property {
    abstract fun readValue(packet: StatusPacket)
    abstract fun copyAndUpdate(packet: StatusPacket): StatusPacket
}