package ua.graviton.isida.ui.devicemode

import ua.graviton.isida.data.models.SendPackageDto

sealed class DeviceModeEvent {
    data class Send(val command: SendPackageDto) : DeviceModeEvent()
}