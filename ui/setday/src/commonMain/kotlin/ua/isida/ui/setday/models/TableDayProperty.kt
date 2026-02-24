package ua.isida.ui.setday.models

import androidx.compose.runtime.Stable
import ua.isida.data.protocol.packets.TableDay
import ua.isida.ui.properties.DeviceProperty

@Stable
abstract class TableDayProperty<T : DeviceProperty>(
    protected val property: T,
) : DeviceProperty by property {
    abstract fun readValue(packet: TableDay)
    abstract fun copyAndUpdate(packet: TableDay): TableDay
}