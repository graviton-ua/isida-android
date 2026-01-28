package ua.graviton.isida.data.protocol.commands.v1

import ua.graviton.isida.data.protocol.commands.IsidaCommand

data class UpdateSettingsCommandV1(
    // We can duplicate the fields from DataPackageDto here,
    // or pass the entire DataPackageDto if the device expects the full snapshot back.
    val spT0: Float,
    val spT1: Float,
    val spRh0: Float,
    val spRh1: Float,
    val pkoff0: Int,
    val pkoff1: Int,
    val ikoff0: Int,
    val ikoff1: Int,
    val minRun: Int,
    val maxRun: Int,
    val period: Int,
    val timer0: Int,
    val timer1: Int,
    val alarm0: Float,
    val alarm1: Float,
    val extOn0: Float,
    val extOn1: Float,
    val extOff0: Float,
    val extOff1: Float,
    val air0: Int,
    val air1: Int,
    val spCO2: Int,
    val deviceNumber: Int,
    val state: Int,
    val extendMode: Int,
    val relayMode: Int,
    val programm: Int,
    val hysteresis: Int,
    val turnTime: Int
) : IsidaCommand.V1