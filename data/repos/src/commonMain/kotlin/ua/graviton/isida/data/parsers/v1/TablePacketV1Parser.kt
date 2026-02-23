package ua.graviton.isida.data.parsers.v1

import ua.graviton.isida.data.parsers.PacketParser
import ua.graviton.isida.data.parsers.PacketReader
import ua.graviton.isida.data.protocol.packets.v1.TableDayV1
import ua.graviton.isida.data.protocol.packets.v1.TablePacketV1

object TablePacketV1Parser : PacketParser<TablePacketV1> {
    override val version: Int = 1       // [0x01]
    override val length: Int = 240      // [0xF0, 0x00]
    override val commandId: Int = 83    // [0x53]

    override fun parsePayload(reader: PacketReader): Result<TablePacketV1> = Result.runCatching {
        require(reader.data.size == length) { "Data size must be $length but was ${reader.data.size}" }
        TablePacketV1(
            // We have 240 bytes length, 8 bytes single day, so 240 / 8 = 30
            // We have to parse 30 days of data
            days = (0 until 30).map { TableDayV1Parser.parsePayload(reader) },
        )
    }
}

private object TableDayV1Parser {
    fun parsePayload(reader: PacketReader): TableDayV1 {
        return TableDayV1(
            spT0 = reader.u16() / 10f,
            spT1 = reader.u16() / 10f,
            spRh = reader.u8(),
            spFlp = reader.u8(),
            spTr = reader.u8(),
            spCO2 = reader.u8() * 20,
        )
    }
}