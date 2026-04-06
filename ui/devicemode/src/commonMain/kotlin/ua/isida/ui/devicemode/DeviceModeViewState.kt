package ua.isida.ui.devicemode

import androidx.compose.runtime.Immutable
import ua.isida.data.protocol.DeviceMode
import ua.isida.data.protocol.DeviceModeExtra

@Immutable
internal data class DeviceModeViewState(
    val deviceId: Int? = null,
    val mode: DeviceMode? = null,
    val extras: List<DeviceModeExtra> = emptyList(),

    val applyEnabled: Boolean = false
) {

    companion object {
        val Empty = DeviceModeViewState()
    }
}

internal sealed interface DeviceModeViewEvent {
    data object OnApplied : DeviceModeViewEvent
}

internal sealed class DeviceModeAction {
    data class SelectMode(val mode: DeviceMode) : DeviceModeAction()
    data class ToggleExtra(val extra: DeviceModeExtra) : DeviceModeAction()

    object ApplyMode : DeviceModeAction()
}