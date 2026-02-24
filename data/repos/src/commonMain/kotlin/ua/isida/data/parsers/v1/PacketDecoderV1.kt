package ua.isida.data.parsers.v1

import ua.isida.data.parsers.PacketDecoder
import ua.isida.data.protocol.packets.IsidaPacket

object PacketDecoderV1 : PacketDecoder<IsidaPacket.V1>() {
    override val parsers = listOf(StatusPacketV1Parser, TablePacketV1Parser, ConfirmPacketV1Parser)
}