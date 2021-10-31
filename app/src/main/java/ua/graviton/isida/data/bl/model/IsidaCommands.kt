package ua.graviton.isida.data.bl.model

import timber.log.Timber

object IsidaCommands {
    private const val deviceType = 9

    fun deviceMode(
        deviceNumber: Int,
        mode: DeviceMode,
        vararg extras: DeviceModeExtra,
    ): SendPackageDto {
        val commandId = 87

        val commandValue = when (mode) {
            DeviceMode.ENABLE -> {
                var result = mode.code
                extras.forEach { result = result or it.code }
                result
            }
            else -> mode.code
        }

        //Timber.d("Command value: $commandValue")
        //if (commandValue == commandValue or DeviceModeExtra.EXTRA_1.code) Timber.d("Command extra flag 1 applied")
        //if (commandValue == commandValue or DeviceModeExtra.EXTRA_2.code) Timber.d("Command extra flag 2 applied")
        //if (commandValue == commandValue or DeviceModeExtra.EXTRA_3.code) Timber.d("Command extra flag 3 applied")
        //if (commandValue == commandValue or DeviceModeExtra.EXTRA_4.code) Timber.d("Command extra flag 4 applied")

        return SendPackageDto(
            deviceType = deviceType,
            deviceNumber = deviceNumber,
            data = when (mode) {
                DeviceMode.ENABLE -> ByteArray(4).apply {
                    this[0] = (commandId % 256).toByte()
                    this[1] = (commandId / 256).toByte()
                    this[2] = (commandValue % 256).toByte()
                    this[3] = (commandValue / 256).toByte()
                }
                else -> ByteArray(2).apply {
                    this[0] = (commandId % 256).toByte()
                    this[1] = (commandId / 256).toByte()
                }
            }
        )
    }

    enum class DeviceMode(val code: Int) {
        DISABLE(0x00),          // "ОТКЛЮЧИТЬ камеру"
        ONLY_ROTATION(0x80),    // "только ПОВОРОТ"
        ENABLE(0x01),           // "ВКЛЮЧИТЬ камеру"
    }

    enum class DeviceModeExtra(val code: Int) {
        EXTRA_1(0x40),   //"Мониторинг тихоход. вентилятора" data = data | 0x40
        EXTRA_2(0x20),   //"Мониторинг поворота лотков" data = data | 0x20
        EXTRA_3(0x08),   //"Горизонтальное положение лотков" data = data | 0x08
        EXTRA_4(0x02),   //"Режим подгототка к ОХЛАЖДЕНИЮ" data = data | 0x02
    }
}