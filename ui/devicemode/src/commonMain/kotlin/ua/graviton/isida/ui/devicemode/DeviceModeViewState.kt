package ua.graviton.isida.ui.devicemode

import androidx.compose.runtime.Immutable
import ua.graviton.isida.data.protocol.DeviceMode
import ua.graviton.isida.data.protocol.DeviceModeExtra

@Immutable
data class DeviceModeViewState(
    val deviceId: Int? = null,
    val mode: DeviceMode? = null,
    val extras: List<DeviceModeExtra> = emptyList(),

    val applyEnabled: Boolean = false
) {

    companion object {
        val Empty = DeviceModeViewState()
    }
}

sealed interface DeviceModeViewEvent {
    data object OnApplied : DeviceModeViewEvent
}

sealed class DeviceModeAction {
    data class SelectMode(val mode: DeviceMode) : DeviceModeAction()
    data class ToggleExtra(val extra: DeviceModeExtra) : DeviceModeAction()

    object ApplyMode : DeviceModeAction()
}