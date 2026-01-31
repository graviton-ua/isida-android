package ua.graviton.isida.data.protocol.packets.v1

import ua.graviton.isida.data.protocol.packets.IsidaPacket

data class TablePacketV1(
    val days: List<TableDayV1>,
) : IsidaPacket.V1

data class TableDayV1(
    val spT0: Float,    // 2 bytes
    val spT1: Float,    // 2 bytes
    val spRh: Int,      // 1 byte
    val spFlp: Int,     // 1 byte
    val spTr: Int,      // 1 byte
    val spCl: Int,      // 1 byte
)