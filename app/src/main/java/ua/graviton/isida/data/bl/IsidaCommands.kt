package ua.graviton.isida.data.bl

import ua.graviton.isida.data.bl.model.DataPackageDto
import ua.graviton.isida.data.bl.model.SendPackageDto
import ua.graviton.isida.domain.models.DeviceProperty
import ua.graviton.isida.utils.asByteArray

object IsidaCommands {
    private const val deviceType = 0x09

    fun deviceMode(
        deviceNumber: Int,
        mode: DeviceMode,
        vararg extras: DeviceModeExtra,
    ): SendPackageDto {
        val commandId = 87

        val commandValue = when (mode) {
            DeviceMode.ENABLE -> extras.map { it.code }.foldRight(initial = mode.code) { left, right -> left or right }
            else -> mode.code
        }

        val data = commandId.toShort().asByteArray() + commandValue.toShort().asByteArray()

        return SendPackageDto(
            deviceType = deviceType,
            deviceNumber = deviceNumber,
            data = data
        )
    }

    enum class DeviceMode(val code: Int) {
        DISABLE(0x00),          // "ОТКЛЮЧИТЬ камеру"
        ONLY_ROTATION(0x80),    // "только ПОВОРОТ"
        ENABLE(0x01),           // "ВКЛЮЧИТЬ камеру"
    }

    enum class DeviceModeExtra(val code: Int) {
        EXTRA_1(0x40),   //"Мониторинг тихоход. вентилятора"    data = data | 0x40
        EXTRA_2(0x20),   //"Мониторинг поворота лотков"         data = data | 0x20
        EXTRA_3(0x08),   //"Горизонтальное положение лотков"    data = data | 0x08
        EXTRA_4(0x02),   //"Режим подгототка к ОХЛАЖДЕНИЮ"      data = data | 0x02
    }

    fun updateProperties(
        deviceNumber: Int,
        deviceDataSnapshot: DataPackageDto,
        vararg props: DeviceProperty<*>,
    ): SendPackageDto {
        val commandId = 1111    //TODO: I don't know command id!!!

        val data = commandId.toShort().asByteArray()

        return SendPackageDto(
            deviceType = deviceType,
            deviceNumber = deviceNumber,
            data = data
        )
    }
}