package ua.graviton.isida.ui.devicemode

import ua.graviton.isida.data.models.SendPackageDto
import ua.graviton.isida.domain.IsidaCommands

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

sealed class DeviceModeAction {
    object NavigateUp : DeviceModeAction()

    data class SelectMode(val mode: IsidaCommands.DeviceMode) : DeviceModeAction()
    data class ToggleExtra(val extra: IsidaCommands.DeviceModeExtra) : DeviceModeAction()

    object ApplyMode : DeviceModeAction()
}

sealed class DeviceModeEvent {
    data class Send(val command: SendPackageDto) : DeviceModeEvent()
}