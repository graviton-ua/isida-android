package ua.graviton.isida.domain.models

import ua.graviton.isida.data.bl.model.DataPackageDto

sealed class DeviceProperty<T>(
    val id: String,
    val value: T,
    val info: DeviceCharacteristicInfo<T>,
) {

    object Unknown : DeviceProperty<Unit>(id = "unknown", value = Unit, info = DeviceCharacteristicInfo.Unknown)

    data class SpT0(private val _value: Float) : DeviceProperty<Float>(id = "spT0", value = _value, info = DeviceCharacteristicInfo.SpT)
    data class SpT1(private val _value: Float) : DeviceProperty<Float>(id = "spT1", value = _value, info = DeviceCharacteristicInfo.SpT)
    data class SpRh0(private val _value: Float) : DeviceProperty<Float>(id = "spRh0", value = _value, info = DeviceCharacteristicInfo.SpRh)
    data class SpRh1(private val _value: Float) : DeviceProperty<Float>(id = "spRh1", value = _value, info = DeviceCharacteristicInfo.SpRh)
    data class K0(private val _value: Int) : DeviceProperty<Int>(id = "K0", value = _value, info = DeviceCharacteristicInfo.Min1)
    data class K1(private val _value: Int) : DeviceProperty<Int>(id = "K1", value = _value, info = DeviceCharacteristicInfo.Min1)
    data class Ti0(private val _value: Int) : DeviceProperty<Int>(id = "Ti0", value = _value, info = DeviceCharacteristicInfo.Min100)
    data class Ti1(private val _value: Int) : DeviceProperty<Int>(id = "Ti1", value = _value, info = DeviceCharacteristicInfo.Min100)
    data class MinRun(private val _value: Int) : DeviceProperty<Int>(id = "minRun", value = _value, info = DeviceCharacteristicInfo.Min100)
    data class MaxRun(private val _value: Int) : DeviceProperty<Int>(id = "maxRun", value = _value, info = DeviceCharacteristicInfo.Min1)
    data class Period(private val _value: Int) : DeviceProperty<Int>(id = "period", value = _value, info = DeviceCharacteristicInfo.Min10)
    data class TimeOut(private val _value: Int) : DeviceProperty<Int>(id = "timeOut", value = _value, info = DeviceCharacteristicInfo.Int1_100)
    data class EnergyMeter(private val _value: Int) : DeviceProperty<Int>(id = "energyMeter", value = _value, info = DeviceCharacteristicInfo.Max1)
    data class Timer0(private val _value: Int) : DeviceProperty<Int>(id = "timer0", value = _value, info = DeviceCharacteristicInfo.Int10_1000)
    data class Timer1(private val _value: Int) : DeviceProperty<Int>(id = "timer1", value = _value, info = DeviceCharacteristicInfo.Min0)
    data class Alarm0(private val _value: Float) : DeviceProperty<Float>(id = "alarm0", value = _value, info = DeviceCharacteristicInfo.Fl01_10)
    data class Alarm1(private val _value: Float) : DeviceProperty<Float>(id = "alarm1", value = _value, info = DeviceCharacteristicInfo.Fl01_10)
    data class ExtOn0(private val _value: Float) : DeviceProperty<Float>(id = "extOn0", value = _value, info = DeviceCharacteristicInfo.Fl01_10)
    data class ExtOn1(private val _value: Float) : DeviceProperty<Float>(id = "extOn1", value = _value, info = DeviceCharacteristicInfo.Fl01_10)
    data class ExtOff0(private val _value: Float) : DeviceProperty<Float>(id = "extOff0", value = _value, info = DeviceCharacteristicInfo.Fl01_10)
    data class ExtOff1(private val _value: Float) : DeviceProperty<Float>(id = "extOff1", value = _value, info = DeviceCharacteristicInfo.Fl01_10)
    data class Air0(private val _value: Int) : DeviceProperty<Int>(id = "air0", value = _value, info = DeviceCharacteristicInfo.Min1)
    data class Air1(private val _value: Int) : DeviceProperty<Int>(id = "air1", value = _value, info = DeviceCharacteristicInfo.Min0)
    data class SpCO2(private val _value: Int) : DeviceProperty<Int>(id = "spCO2", value = _value, info = DeviceCharacteristicInfo.Min1000)
    data class DeviceNumber(private val _value: Int) : DeviceProperty<Int>(id = "deviceNumber", value = _value, info = DeviceCharacteristicInfo.Id)
    data class State(private val _value: Int) : DeviceProperty<Int>(id = "state", value = _value, info = DeviceCharacteristicInfo.Min0)
    data class ExtendMode(private val _value: Int) : DeviceProperty<Int>(id = "extendMode", value = _value, info = DeviceCharacteristicInfo.Max5)
    data class RelayMode(private val _value: Int) : DeviceProperty<Int>(id = "relayMode", value = _value, info = DeviceCharacteristicInfo.Max3)
    data class Program(private val _value: Int) : DeviceProperty<Int>(id = "programm", value = _value, info = DeviceCharacteristicInfo.Max5)
    data class Hysteresis(private val _value: Int) : DeviceProperty<Int>(id = "hysteresis", value = _value, info = DeviceCharacteristicInfo.Min1)
    data class ForceHeat(private val _value: Float) : DeviceProperty<Float>(id = "forceHeat", value = _value, info = DeviceCharacteristicInfo.Fl01_10)
    data class TurnTime(private val _value: Int) : DeviceProperty<Int>(id = "turnTime", value = _value, info = DeviceCharacteristicInfo.Min1)
    data class HihEnable(private val _value: Int) : DeviceProperty<Int>(id = "hihEnable", value = _value, info = DeviceCharacteristicInfo.Max1)
    data class KOffCurr(private val _value: Int) : DeviceProperty<Int>(id = "kOffCurr", value = _value, info = DeviceCharacteristicInfo.Min10)
    data class CoolOn(private val _value: Int) : DeviceProperty<Int>(id = "coolOn", value = _value, info = DeviceCharacteristicInfo.Min0)
    data class CoolOff(private val _value: Int) : DeviceProperty<Int>(id = "coolOff", value = _value, info = DeviceCharacteristicInfo.Min0)
    data class Zonality(private val _value: Float) : DeviceProperty<Float>(id = "zonality", value = _value, info = DeviceCharacteristicInfo.Fl01_10)
}

fun DataPackageDto.asProperties(): List<DeviceProperty<*>> = listOf(
    DeviceProperty.SpT0(spT0),
    DeviceProperty.SpT1(spT1),
    DeviceProperty.SpRh0(spRh0),
    DeviceProperty.SpRh1(spRh1),
    DeviceProperty.K0(K0),
    DeviceProperty.K1(K1),
    DeviceProperty.Ti0(Ti0),
    DeviceProperty.Ti1(Ti1),
    DeviceProperty.MinRun(minRun),
    DeviceProperty.MaxRun(maxRun),
    DeviceProperty.Period(period),
    DeviceProperty.TimeOut(timeOut),
    DeviceProperty.EnergyMeter(energyMeter),
    DeviceProperty.Timer0(timer0),
    DeviceProperty.Timer1(timer1),
    DeviceProperty.Alarm0(alarm0),
    DeviceProperty.Alarm1(alarm1),
    DeviceProperty.ExtOn0(extOn0),
    DeviceProperty.ExtOn1(extOn1),
    DeviceProperty.ExtOff0(extOff0),
    DeviceProperty.ExtOff1(extOff1),
    DeviceProperty.Air0(air0),
    DeviceProperty.Air1(air1),
    DeviceProperty.SpCO2(spCO2),
    DeviceProperty.DeviceNumber(deviceNumber),
    DeviceProperty.State(state),
    DeviceProperty.ExtendMode(extendMode),
    DeviceProperty.RelayMode(relayMode),
    DeviceProperty.Program(programm),
    DeviceProperty.Hysteresis(hysteresis),
    DeviceProperty.ForceHeat(forceHeat),
    DeviceProperty.TurnTime(turnTime),
    DeviceProperty.HihEnable(hihEnable),
    DeviceProperty.KOffCurr(kOffCurr),
    DeviceProperty.CoolOn(coolOn),
    DeviceProperty.CoolOff(coolOff),
    DeviceProperty.Zonality(zonality),
)

fun DataPackageDto.getProperty(id: String): DeviceProperty<*> = asProperties().find { it.id == id } ?: DeviceProperty.Unknown