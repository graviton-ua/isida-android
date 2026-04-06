package ua.isida.ui.setday

import androidx.compose.runtime.Stable
import ua.isida.data.protocol.packets.TableDay
import ua.isida.ui.properties.DeviceProperty

@Stable
internal data class SetDayViewState(
    val properties: List<DeviceProperty>,
    val dataIsValid: Boolean = true,
)

internal sealed interface SetDayViewEvent {
    data class OnSubmit(val index: Int, val day: TableDay) : SetDayViewEvent
}