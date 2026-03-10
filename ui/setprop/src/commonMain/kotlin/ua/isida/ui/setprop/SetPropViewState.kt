package ua.isida.ui.setprop

import androidx.compose.runtime.Stable
import ua.isida.ui.properties.DeviceProperty

@Stable
data class SetPropViewState(
    val property: DeviceProperty,
    val node: Int? = null,
    val waitingForData: Boolean = true,
)

sealed interface SetPropViewEvent {
    // Means our command already been successfully sent to device
    object Sent : SetPropViewEvent
}