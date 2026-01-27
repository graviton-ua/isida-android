package ua.graviton.isida.data.parsers.v1

import ua.graviton.isida.data.parsers.PacketParser
import ua.graviton.isida.data.protocol.v1.StatusPacketV1

object StatusV1Parser : PacketParser<StatusPacketV1> {
    override val version: Int = 0x01    // version 1
    override val commandId: Int = 0x4D  // 77
    override val length: Int = 60

    override fun canParse(data: ByteArray): Boolean {
        TODO("Not yet implemented")
    }

    override fun parse(data: ByteArray): StatusPacketV1 {
        TODO("Not yet implemented")
    }
}