package ua.graviton.isida.ui.setprop

import ua.graviton.isida.data.bl.model.SendPackageDto

sealed class SetPropEvent {
    data class Send(val command: SendPackageDto) : SetPropEvent()
}