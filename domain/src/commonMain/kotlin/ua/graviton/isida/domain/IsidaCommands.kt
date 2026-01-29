package ua.graviton.isida.domain

import com.whoppah.extensions.asByteArray
import ua.graviton.isida.data.models.DataPackageDto
import ua.graviton.isida.data.models.SendPackageDto
import ua.graviton.isida.domain.models.DeviceProperty

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
        DISABLE(0x00),          // "камера ОТКЛЮЧЕНА"
        ENABLE(0x01),           // "камера ВКЛЮЧИНА"
        WAITING_COOLING(0x02),  // "подгототка к ОХЛАЖДЕНИЮ"
        WAITING_ON(0x04),       // "подгототка к ЗАПУСКУ"
        HORIZON_ON(0x08),       // "ВКЛЮЧЕН ГОРИЗОНТ"
        HORIZON_SET(0x10),      // "ГОРИЗОНТ УСТАНОВЛЕН"
        TRAY_ROTATION_ON(0x20), // "ВКЛЮЧЕН мониторинг поворота лотков"
        FAN_MONITORING_ON(0x40),// "ВКЛЮЧЕН мониторинг тихоходного вентилятора"
        ONLY_ROTATION(0x80),    // "только ПОВОРОТ"
    }

    enum class OutputBit(val code: Int){
        OUT_Wetting(0x02),  //УВЛАЖНИТЕЛЬ
        OUT_Flap(0x04),     //Заслонка воздухообмена
        OUT_Extend(0x08),   //Вспомогательный канал
        OUT_Trays(0x10),    //Поворот лотков
    }

    enum class DeviceModeExtra(val code: Int) {
        // TODO: There is 2 more states need to be added later
        EXTRA_1(0x40),   //"Мониторинг тихоход. вентилятора"    data = data | 0x40
        EXTRA_2(0x20),   //"Мониторинг поворота лотков"         data = data | 0x20
        EXTRA_3(0x08),   //"Горизонтальное положение лотков"    data = data | 0x08
        EXTRA_4(0x02),   //"Режим подгототка к ОХЛАЖДЕНИЮ"      data = data | 0x02
    }

    enum class Errors(val code: Int) {
        ERROR_01(code = 0x01),  //ОШИБКА ДАТЧИКА температуры
        ERROR_02(code = 0x02),  //ОШИБКА ДАТЧИКА влажности
        ERROR_04(code = 0x04),  //ПРОБОЙ СИМИСТОРА
        ERROR_08(code = 0x08),  //НЕИСПРАВНА цепь НАГРЕВАТЕЛЯ
        ERROR_10(code = 0x10),  //ПЕРЕГРЕВ СИМИСТОРА
        ERROR_20(code = 0x20),  //ОШИБКА модуля СО2 или FLAP
        ERROR_40(code = 0x40),  //ОШИБКА модуля Холла или Поворотов
    }

    enum class Warning(val code: Int) {
        WARNING_01(code = 0x01),  //ОТКЛОНЕНИЕ по температуре
        WARNING_02(code = 0x02),  //ОТКЛОНЕНИЕ по влажности
        WARNING_04(code = 0x04),  //Произведена подмена датчика
        WARNING_08(code = 0x08),  //Большой перепад температуры
        WARNING_10(code = 0x10),  //Неправильная конфигурация датчиков
        WARNING_20(code = 0x20),  //неиспользуется
        WARNING_40(code = 0x40),  //неиспользуется
    }

    fun updateProperties(
        deviceNumber: Int,
        deviceDataSnapshot: DataPackageDto,
        vararg props: DeviceProperty<*>,
    ): SendPackageDto {
        val commandId = 55

        val spT0 = ((props.findIsInstance<DeviceProperty.SpT0>()?.value ?: deviceDataSnapshot.spT0) * 10).toInt().toShort().asByteArray()
        val spT1 = ((props.findIsInstance<DeviceProperty.SpT1>()?.value ?: deviceDataSnapshot.spT1) * 10).toInt().toShort().asByteArray()
        val spRh0 = ((props.findIsInstance<DeviceProperty.SpRh0>()?.value ?: deviceDataSnapshot.spRh0) * 10).toInt().toShort().asByteArray()
        val spRh1 = ((props.findIsInstance<DeviceProperty.SpRh1>()?.value ?: deviceDataSnapshot.spRh1) * 10).toInt().toShort().asByteArray()
        val pkoff0 = (props.findIsInstance<DeviceProperty.Pkoff0>()?.value ?: deviceDataSnapshot.pkoff0).toShort().asByteArray()
        val pkoff1 = (props.findIsInstance<DeviceProperty.Pkoff1>()?.value ?: deviceDataSnapshot.pkoff1).toShort().asByteArray()
        val ikoff0 = (props.findIsInstance<DeviceProperty.Ikoff0>()?.value ?: deviceDataSnapshot.ikoff0).toShort().asByteArray()
        val ikoff1 = (props.findIsInstance<DeviceProperty.Ikoff1>()?.value ?: deviceDataSnapshot.ikoff1).toShort().asByteArray()
        val minRun = (props.findIsInstance<DeviceProperty.MinRun>()?.value ?: deviceDataSnapshot.minRun).toShort().asByteArray()
        val maxRun = (props.findIsInstance<DeviceProperty.MaxRun>()?.value ?: deviceDataSnapshot.maxRun).toShort().asByteArray()
        val period = (props.findIsInstance<DeviceProperty.Period>()?.value ?: deviceDataSnapshot.period).toShort().asByteArray()

        val timer0 = (props.findIsInstance<DeviceProperty.Timer0>()?.value ?: deviceDataSnapshot.timer0).toByte()
        val timer1 = (props.findIsInstance<DeviceProperty.Timer1>()?.value ?: deviceDataSnapshot.timer1).toByte()
        val alarm0 = ((props.findIsInstance<DeviceProperty.Alarm0>()?.value ?: deviceDataSnapshot.alarm0) * 10).toInt().toByte()
        val alarm1 = ((props.findIsInstance<DeviceProperty.Alarm1>()?.value ?: deviceDataSnapshot.alarm1) * 10).toInt().toByte()
        val extOn0 = ((props.findIsInstance<DeviceProperty.ExtOn0>()?.value ?: deviceDataSnapshot.extOn0) * 10).toInt().toByte()
        val extOn1 = ((props.findIsInstance<DeviceProperty.ExtOn1>()?.value ?: deviceDataSnapshot.extOn1) * 10).toInt().toByte()
        val extOff0 = ((props.findIsInstance<DeviceProperty.ExtOff0>()?.value ?: deviceDataSnapshot.extOff0) * 10).toInt().toByte()
        val extOff1 = ((props.findIsInstance<DeviceProperty.ExtOff1>()?.value ?: deviceDataSnapshot.extOff1) * 10).toInt().toByte()
        val air0 = (props.findIsInstance<DeviceProperty.Air0>()?.value ?: deviceDataSnapshot.air0).toByte()
        val air1 = (props.findIsInstance<DeviceProperty.Air1>()?.value ?: deviceDataSnapshot.air1).toByte()
        val spCO2 = (props.findIsInstance<DeviceProperty.SpCO2>()?.value ?: deviceDataSnapshot.spCO2).toByte()
        val newIdentif = (props.findIsInstance<DeviceProperty.Identif>()?.value ?: deviceDataSnapshot.node).toByte()
        val state = (props.findIsInstance<DeviceProperty.State>()?.value ?: deviceDataSnapshot.state).toByte()
        val extendMode = (props.findIsInstance<DeviceProperty.ExtendMode>()?.value ?: deviceDataSnapshot.extendMode).toByte()
        val relayMode = (props.findIsInstance<DeviceProperty.RelayMode>()?.value ?: deviceDataSnapshot.relayMode).toByte()
        val programm = (props.findIsInstance<DeviceProperty.Program>()?.value ?: deviceDataSnapshot.programm).toByte()
        val hysteresis = (props.findIsInstance<DeviceProperty.Hysteresis>()?.value ?: deviceDataSnapshot.hysteresis).toByte()
        val turnTime = (props.findIsInstance<DeviceProperty.TurnTime>()?.value ?: deviceDataSnapshot.turnTime).toByte()

        val data = commandId.toShort().asByteArray() +
                spT0 + spT1 + spRh0 + spRh1 + pkoff0 + pkoff1 + ikoff0 + ikoff1 + minRun + maxRun + period +
                byteArrayOf(
                    timer0, timer1, alarm0, alarm1, extOn0, extOn1, extOff0, extOff1, air0, air1,
                    spCO2, newIdentif, state, extendMode, relayMode, programm, hysteresis, turnTime,
                )
        require(data.size == 44) { "Wrong data size" }

        return SendPackageDto(
            deviceType = deviceType,
            deviceNumber = deviceNumber,
            data = data
        )
    }


}

private inline fun <reified R> Array<*>.findIsInstance(): R? = filterIsInstance<R>().firstOrNull()