package ua.isida.ui.setprop

import androidx.compose.runtime.Stable
import ua.isida.ui.properties.DeviceProperty

@Stable
internal data class SetPropViewState(
    val property: DeviceProperty,
    val node: Int? = null,
    val waitingForData: Boolean = true,
)

internal sealed interface SetPropViewEvent {
    // Means our command already been successfully sent to device
    object Sent : SetPropViewEvent
}