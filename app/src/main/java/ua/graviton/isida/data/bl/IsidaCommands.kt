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
        //TODO: There is 2 more states need to be added later
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
        val commandId = 55

        val spT0 = ((props.findIsInstance<DeviceProperty.SpT0>()?.value ?: deviceDataSnapshot.spT0) * 10).toInt().toShort().asByteArray()
        val spT1 = ((props.findIsInstance<DeviceProperty.SpT1>()?.value ?: deviceDataSnapshot.spT1) * 10).toInt().toShort().asByteArray()
        val spRh0 = ((props.findIsInstance<DeviceProperty.SpRh0>()?.value ?: deviceDataSnapshot.spRh0) * 10).toInt().toShort().asByteArray()
        val spRh1 = ((props.findIsInstance<DeviceProperty.SpRh1>()?.value ?: deviceDataSnapshot.spRh1) * 10).toInt().toShort().asByteArray()
        val k0 = (props.findIsInstance<DeviceProperty.K0>()?.value ?: deviceDataSnapshot.K0).toShort().asByteArray()
        val k1 = (props.findIsInstance<DeviceProperty.K1>()?.value ?: deviceDataSnapshot.K1).toShort().asByteArray()
        val ti0 = (props.findIsInstance<DeviceProperty.Ti0>()?.value ?: deviceDataSnapshot.Ti0).toShort().asByteArray()
        val ti1 = (props.findIsInstance<DeviceProperty.Ti1>()?.value ?: deviceDataSnapshot.Ti1).toShort().asByteArray()
        val minRun = (props.findIsInstance<DeviceProperty.MinRun>()?.value ?: deviceDataSnapshot.minRun).toShort().asByteArray()
        val maxRun = (props.findIsInstance<DeviceProperty.MaxRun>()?.value ?: deviceDataSnapshot.maxRun).toShort().asByteArray()
        val period = (props.findIsInstance<DeviceProperty.Period>()?.value ?: deviceDataSnapshot.period).toShort().asByteArray()
        val timeOut = ((props.findIsInstance<DeviceProperty.TimeOut>()?.value ?: deviceDataSnapshot.timeOut) * 60).toShort().asByteArray()
        val energyMeter = (props.findIsInstance<DeviceProperty.EnergyMeter>()?.value ?: deviceDataSnapshot.energyMeter).toShort().asByteArray()

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
        val newDeviceNumber = (props.findIsInstance<DeviceProperty.DeviceNumber>()?.value ?: deviceDataSnapshot.deviceNumber).toByte()
        val state = (props.findIsInstance<DeviceProperty.State>()?.value ?: deviceDataSnapshot.state).toByte()
        val extendMode = (props.findIsInstance<DeviceProperty.ExtendMode>()?.value ?: deviceDataSnapshot.extendMode).toByte()
        val relayMode = (props.findIsInstance<DeviceProperty.RelayMode>()?.value ?: deviceDataSnapshot.relayMode).toByte()
        val programm = (props.findIsInstance<DeviceProperty.Program>()?.value ?: deviceDataSnapshot.programm).toByte()
        val hysteresis = (props.findIsInstance<DeviceProperty.Hysteresis>()?.value ?: deviceDataSnapshot.hysteresis).toByte()
        val forceHeat = ((props.findIsInstance<DeviceProperty.ForceHeat>()?.value ?: deviceDataSnapshot.forceHeat) * 10).toInt().toByte()
        val turnTime = (props.findIsInstance<DeviceProperty.TurnTime>()?.value ?: deviceDataSnapshot.turnTime).toByte()
        val hihEnable = (props.findIsInstance<DeviceProperty.HihEnable>()?.value ?: deviceDataSnapshot.hihEnable).toByte()
        val kOffCurr = (props.findIsInstance<DeviceProperty.KOffCurr>()?.value ?: deviceDataSnapshot.kOffCurr).toByte()
        val coolOn = (props.findIsInstance<DeviceProperty.CoolOn>()?.value ?: deviceDataSnapshot.coolOn).toByte()
        val coolOff = (props.findIsInstance<DeviceProperty.CoolOff>()?.value ?: deviceDataSnapshot.coolOff).toByte()
        val zonality = ((props.findIsInstance<DeviceProperty.Zonality>()?.value ?: deviceDataSnapshot.zonality) * 10).toInt().toByte()

        val data = commandId.toShort().asByteArray() +
                spT0 + spT1 + spRh0 + spRh1 + k0 + k1 + ti0 + ti1 +
                minRun + maxRun + period + timeOut + energyMeter +
                byteArrayOf(
                    timer0, timer1, alarm0, alarm1, extOn0, extOn1, extOff0, extOff1, air0, air1,
                    spCO2, newDeviceNumber, state, extendMode, relayMode, programm, hysteresis, forceHeat,
                    turnTime, hihEnable, kOffCurr, coolOn, coolOff, zonality,
                )
        require(data.size == 52) { "Wrong data size" }

        return SendPackageDto(
            deviceType = deviceType,
            deviceNumber = deviceNumber,
            data = data
        )
    }


}

private inline fun <reified R> Array<*>.findIsInstance(): R? = filterIsInstance<R>().firstOrNull()