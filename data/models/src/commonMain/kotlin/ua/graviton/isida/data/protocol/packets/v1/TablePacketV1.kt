package ua.graviton.isida.data.protocol.packets.v1

import ua.graviton.isida.data.protocol.packets.IsidaPacket

data class TablePacketV1(
    val ss: Int = 0, // TODO: Define structure for table of incubation
) : IsidaPacket.V1