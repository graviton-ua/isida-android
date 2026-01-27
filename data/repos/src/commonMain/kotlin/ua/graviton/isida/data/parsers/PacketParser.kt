package ua.graviton.isida.data.parsers

import ua.graviton.isida.data.protocol.IsidaPacket

interface PacketParser<T : IsidaPacket> {
    val version: Int
    val commandId: Int
    val length: Int
    fun canParse(data: ByteArray): Boolean
    fun parse(data: ByteArray): T
}