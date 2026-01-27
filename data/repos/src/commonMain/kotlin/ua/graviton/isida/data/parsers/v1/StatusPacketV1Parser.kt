package ua.graviton.isida.data.parsers.v1

import ua.graviton.isida.data.parsers.PacketParser
import ua.graviton.isida.data.protocol.v1.StatusPacketV1

object StatusPacketV1Parser : PacketParser<StatusPacketV1> {
    override val version: Int = 1       // [0x01]
    override val length: Int = 60       // [0x3C, 0x00]
    override val commandId: Int = 77    // [0x4D, 0x00]

    override fun canParse(data: ByteArray): Boolean {
        TODO("Not yet implemented")
    }

    override fun parse(data: ByteArray): Result<StatusPacketV1> {
        TODO("Not yet implemented")
    }
}