package ua.graviton.isida.data.parsers

import ua.graviton.isida.data.protocol.IsidaPacket

interface PacketParser<T : IsidaPacket> {
    val version: Int
    val length: Int
    val commandId: Int

    fun canParse(data: ByteArray): Boolean
    open fun parse(data: ByteArray): Result<T> = parse(PacketReader(data))
    fun parse(reader: PacketReader): Result<T>
}