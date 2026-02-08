package ua.graviton.isida.data.serializers.v1

import ua.graviton.isida.data.protocol.commands.v1.SetTableCommandV1
import ua.graviton.isida.data.protocol.packets.v1.TableDayV1
import ua.graviton.isida.data.serializers.CommandWriter
import kotlin.reflect.KClass

object SetTableCommandV1Serializer : CommandSerializerV1<SetTableCommandV1>() {
    override val length: Int = 242      // [0xF0, 0x00]
    override val commandId: Int = 57    // [0x39]

    override fun serializePayload(
        command: SetTableCommandV1,
        writer: CommandWriter
    ): Result<ByteArray> = Result.runCatching {
        // Write data to byte array
        writer.u16(command.number)
        command.days.forEach { day -> TableDayV1Serializer.serializePayload(day, writer) }

        writer.data
    }

    override fun commandKClass(): KClass<SetTableCommandV1> = SetTableCommandV1::class
}

private object TableDayV1Serializer {
    fun serializePayload(day: TableDayV1, writer: CommandWriter) {
        writer.u16((day.spT0 * 10).toInt())
        writer.u16((day.spT1 * 10).toInt())
        writer.u8(day.spRh)
        writer.u8(day.spFlp)
        writer.u8(day.spTr)
        writer.u8(day.spCl)
    }
}