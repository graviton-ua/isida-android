package ua.graviton.isida.ui.setday

import androidx.compose.runtime.Stable
import ua.graviton.isida.data.protocol.packets.TableDay
import ua.graviton.isida.ui.properties.DeviceProperty

@Stable
data class SetDayViewState(
    val properties: List<DeviceProperty>,
    val dataIsValid: Boolean = true,
)

sealed interface SetDayViewEvent {
    data class OnSubmit(val index: Int, val day: TableDay) : SetDayViewEvent
}