package ua.graviton.isida.data.parsers.v1

import ua.graviton.isida.data.parsers.PacketParser
import ua.graviton.isida.data.parsers.PacketReader
import ua.graviton.isida.data.protocol.v1.ConfirmPacketV1

object ConfirmPacketV1Parser : PacketParser<ConfirmPacketV1> {
    override val version: Int = 1       // [0x01]
    override val length: Int = 2        // [0x02, 0x00]
    override val commandId: Int = 91    // [0x5B, 0x00]

    override fun parse(reader: PacketReader): Result<ConfirmPacketV1> = Result.runCatching {
        require(reader.data.size == length) { "Data size must be $length but was ${reader.data.size}" }
        ConfirmPacketV1(
            commandId = reader.u16(),
        )
    }
}