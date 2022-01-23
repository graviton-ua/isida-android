package ua.graviton.isida.domain.models

import ua.graviton.isida.data.bl.model.DataPackageDto

sealed class DeviceProperty<T>(
    val value: T,
    val info: DeviceCharacteristicInfo<T>,
) {

    object Unknown : DeviceProperty<Unit>(value = Unit, info = DeviceCharacteristicInfo.Unknown)

    data class SpT0(private val _value: Float) : DeviceProperty<Float>(
        value = _value,
        info = DeviceCharacteristicInfo.SpT0,
    )

    data class SpT1(private val _value: Float) : DeviceProperty<Float>(
        value = _value,
        info = DeviceCharacteristicInfo.SpT1,
    )

    data class SpRh0(private val _value: Float) : DeviceProperty<Float>(
        value = _value,
        info = DeviceCharacteristicInfo.SpRh0,
    )

    data class SpRh1(private val _value: Float) : DeviceProperty<Float>(
        value = _value,
        info = DeviceCharacteristicInfo.SpRh1,
    )

    data class K0(private val _value: Int) : DeviceProperty<Int>(
        value = _value,
        info = DeviceCharacteristicInfo.K0,
    )

    data class K1(private val _value: Int) : DeviceProperty<Int>(
        value = _value,
        info = DeviceCharacteristicInfo.K1,
    )
}

fun DataPackageDto.getProperty(id: String): DeviceProperty<*> = when (DeviceCharacteristicInfo.byId(id)) {
    DeviceCharacteristicInfo.K0 -> DeviceProperty.K0(K0)
    DeviceCharacteristicInfo.K1 -> DeviceProperty.K1(K1)
    DeviceCharacteristicInfo.SpRh0 -> DeviceProperty.SpRh0(spRh0)
    DeviceCharacteristicInfo.SpRh1 -> DeviceProperty.SpRh1(spRh1)
    DeviceCharacteristicInfo.SpT0 -> DeviceProperty.SpT0(spT0)
    DeviceCharacteristicInfo.SpT1 -> DeviceProperty.SpT1(spT1)
    DeviceCharacteristicInfo.Unknown -> DeviceProperty.Unknown
}