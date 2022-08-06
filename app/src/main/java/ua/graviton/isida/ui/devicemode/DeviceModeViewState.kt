package ua.graviton.isida.ui.devicemode

import ua.graviton.isida.data.bl.IsidaCommands

data class DeviceModeViewState(
    val deviceId: Int? = null,
    val mode: IsidaCommands.DeviceMode? = null,
    val extras: List<IsidaCommands.DeviceModeExtra> = emptyList(),

    val applyEnabled: Boolean = false
) {

    companion object {
        val Empty = DeviceModeViewState()
    }
}