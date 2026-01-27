package ua.graviton.isida.data.parsers.v1

import ua.graviton.isida.data.parsers.PacketDecoder
import ua.graviton.isida.data.protocol.IsidaPacket

object PacketDecoderV1 : PacketDecoder<IsidaPacket.V1>() {
    override val parsers = listOf(StatusPacketV1Parser, TablePacketV1Parser, ConfirmPacketV1Parser)
}