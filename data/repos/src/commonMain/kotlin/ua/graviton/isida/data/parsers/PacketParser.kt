package ua.graviton.isida.data.parsers

import com.whoppah.util.CRC16
import com.whoppah.util.readU16LE
import com.whoppah.util.readU8
import ua.graviton.isida.data.protocol.packets.IsidaPacket

interface PacketParser<T : IsidaPacket> {
    val version: Int
    val length: Int
    val commandId: Int

    fun canParse(data: ByteArray): Boolean {
        // Start(1) + Ver(1) + Len(2) + Cmd(2) + Data($length) + CRC(2) + End(2)
        val dataOffset = 6
        val crcSize = 2
        val endSize = 2
        val expectedSize = dataOffset + length + crcSize + endSize

        if (data.size < expectedSize) return false

        // Check version of the packet
        if (data.readU8(1) != version) return false

        // Check length of data (2 bytes)
        val packetLength = data.readU16LE(2)
        if (packetLength != length) return false

        // Check command ID (2 bytes)
        val packetCmdId = data.readU16LE(4)
        if (packetCmdId != commandId) return false

        // 6. Check CRC
        // CRC covers [Start, Version, Length, CommandId, Data...]
        // From index 0 to (dataOffset + length) exclusive of CRC
        val dataForCrc = data.copyOfRange(0, dataOffset + length)
        val calculatedCrc = CRC16.crcSimple(dataForCrc)
        val packetCrc = data.readU16LE(dataOffset + length)

        // crcSimple returns an Int which might use more than 16 bits.
        // SendPackageDto uses .toShort() which truncates to lower 16 bits.
        // We mask with 0xFFFF to ensure we compare the same 16-bit value against the packet's CRC (0..65535).
        return (calculatedCrc and 0xFFFF) == packetCrc
    }

    fun parsePayload(data: ByteArray): Result<T> = parsePayload(PacketReader(data))
    fun parsePayload(reader: PacketReader): Result<T>
}