package ua.graviton.isida.ui.setprop

import ua.graviton.isida.domain.models.DeviceProperty

sealed class SetPropAction {
    object NavigateUp : SetPropAction()
    object Send : SetPropAction()

    data class UpdateProperty(val property: DeviceProperty<*>?) : SetPropAction()
}