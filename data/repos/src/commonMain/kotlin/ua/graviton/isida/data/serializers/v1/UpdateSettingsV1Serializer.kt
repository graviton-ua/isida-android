package ua.graviton.isida.data.serializers.v1

import ua.graviton.isida.data.protocol.commands.v1.UpdateSettingsCommandV1
import ua.graviton.isida.data.serializers.CommandWriter
import kotlin.reflect.KClass

object UpdateSettingsV1Serializer : CommandSerializerV1<UpdateSettingsCommandV1>() {
    override val length: Int = 40       // [0x28, 0x00]
    override val commandId: Int = 51    // [0x33]

    override fun serializePayload(
        command: UpdateSettingsCommandV1,
        writer: CommandWriter
    ): Result<ByteArray> = Result.runCatching {
        // Write data to byte array
        writer.u16((command.spT0 * 10).toInt())
        writer.u16((command.spT1 * 10).toInt())
        writer.u8(command.spRh0)
        writer.u8(command.spRh1)
        writer.u8(command.state)
        writer.u8(command.extendMode)
        writer.u8(command.relayMode)
        writer.u8(command.programm)
        writer.u8((command.minRun * 10).toInt())
        writer.u8(command.maxRun)
        writer.u8(command.period)
        writer.u8(command.timer0)
        writer.u8(command.timer1)
        writer.u8((command.alarm0 * 10).toInt())
        writer.u8((command.alarm1 * 10).toInt())
        writer.u8((command.extOn0 * 10).toInt())
        writer.u8((command.extOn1 * 10).toInt())
        writer.u8((command.extOff0 * 10).toInt())
        writer.u8((command.extOff1 * 10).toInt())
        writer.u8(command.air0)
        writer.u8(command.air1)
        writer.u8((command.spCO2 / 20f).toInt())
        writer.u8(command.koffCurr)
        val rawHysteresis = (command.hysteresis * 10).toInt() and 0x1F
        val rawPermission = (command.permission shl 6) and 0xC0
        writer.u8(rawPermission or rawHysteresis)
        val rawZonality = command.zonality shl 6
        val rawFlapristr = command.flapRestrictions - 37 and 0x3F
        writer.u8(rawZonality or rawFlapristr)
        writer.u8(command.turnTime * 60)
        writer.u8(command.waitCooling * 15)
        writer.u8(command.pkoff0)
        writer.u8(command.pkoff1)
        writer.u8(command.ikoff0)
        writer.u8(command.ikoff1)
        writer.u8(command.identif)
        writer.u8(command.minFan / 60)
        writer.u8(command.nothing1)
        writer.u8(command.ip0)
        writer.u8(command.ip1)
        writer.u8(command.ip2)
        writer.u8(command.ip3)
        writer.data
    }

    override fun commandKClass(): KClass<UpdateSettingsCommandV1> = UpdateSettingsCommandV1::class
}