package ua.graviton.isida.ui.devicemode

import ua.graviton.isida.data.protocol.DeviceMode
import ua.graviton.isida.data.protocol.DeviceModeExtra


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

sealed class DeviceModeAction {
    object NavigateUp : DeviceModeAction()

    data class SelectMode(val mode: DeviceMode) : DeviceModeAction()
    data class ToggleExtra(val extra: DeviceModeExtra) : DeviceModeAction()

    object ApplyMode : DeviceModeAction()
}