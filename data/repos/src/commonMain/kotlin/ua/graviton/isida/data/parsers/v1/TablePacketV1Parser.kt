package ua.graviton.isida.data.parsers.v1

import ua.graviton.isida.data.parsers.PacketParser
import ua.graviton.isida.data.parsers.PacketReader
import ua.graviton.isida.data.protocol.v1.TablePacketV1

object TablePacketV1Parser : PacketParser<TablePacketV1> {
    override val version: Int = 1       // [0x01]
    override val length: Int = 240      // [0xF0, 0x00]
    override val commandId: Int = 83    // [0x53, 0x00]

    override fun parse(reader: PacketReader): Result<TablePacketV1> = Result.runCatching {
        require(reader.data.size == length) { "Data size must be $length but was ${reader.data.size}" }
        throw NotImplementedError()
        // TablePacketV1(
        //     commandId = reader.u16(),
        // )
    }
}