package ua.graviton.isida.ui.setprop

import ua.graviton.isida.domain.models.DeviceProperty

sealed class SetPropViewState(val id: String) {
    object Empty : SetPropViewState("empty")
    object NoData : SetPropViewState("no_data")
    object NotFound : SetPropViewState("not_found")
    data class Success(
        val property: DeviceProperty<*>,
    ) : SetPropViewState("success")

    companion object {
        val PreviewSuccess = Success(
            property = DeviceProperty.K1(12),
        )
    }
}