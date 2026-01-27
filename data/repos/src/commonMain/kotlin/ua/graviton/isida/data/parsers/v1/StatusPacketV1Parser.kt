package ua.graviton.isida.data.parsers.v1

import ua.graviton.isida.data.parsers.PacketParser
import ua.graviton.isida.data.protocol.v1.StatusPacketV1

object StatusPacketV1Parser : PacketParser<StatusPacketV1> {
    override val version: Int = 0x01    // version 1
    override val length: Int = 60       // 60 bytes
    override val commandId: Int = 0x4D  // 77

    override fun canParse(data: ByteArray): Boolean {
        TODO("Not yet implemented")
    }

    override fun parse(data: ByteArray): Result<StatusPacketV1> {
        TODO("Not yet implemented")
    }
}