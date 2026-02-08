package ua.graviton.isida.ui.setprop

import androidx.compose.runtime.Stable
import ua.graviton.isida.ui.properties.DeviceProperty

@Stable
data class SetPropViewState(
    val property: DeviceProperty,
    val waitingForData: Boolean = true,
)

sealed interface SetPropViewEvent {
    // Means our command already been successfully sent to device
    object Sent : SetPropViewEvent
}