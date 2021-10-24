package ua.graviton.isida.ui.devicemode

import ua.graviton.isida.data.bl.model.IsidaCommands

sealed class DeviceModeAction {
    data class SelectMode(val mode: IsidaCommands.DeviceMode) : DeviceModeAction()
    data class ToggleExtra(val extra: IsidaCommands.DeviceModeExtra) : DeviceModeAction()

    object ApplyMode : DeviceModeAction()
}