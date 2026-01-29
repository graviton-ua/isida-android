package ua.graviton.isida.data.parsers

import co.touchlab.kermit.Logger
import com.whoppah.util.CRC16
import com.whoppah.util.readU16LE
import com.whoppah.util.readU8
import ua.graviton.isida.data.protocol.packets.IsidaPacket

private val logger by lazy { Logger.withTag("PacketParser") }

interface PacketParser<T : IsidaPacket> {
    val version: Int
    val length: Int
    val commandId: Int

    fun canParse(data: ByteArray): Boolean {
        // Start(2) + Len(2) + Cmd(1) + Ver(1) + Data($length) + CRC(2) + End(2)
        val dataOffset = 6
        val crcSize = 2
        val endSize = 2
        val expectedSize = dataOffset + length + crcSize + endSize

        if (data.size < expectedSize) {
            logger.d { "Wrong size | expected: $expectedSize | actual size: ${data.size}" }
            return false
        }

        // Check length of data (2 bytes)
        val packetLength = data.readU16LE(2)
        if (packetLength != length) {
            logger.d { "Packet length | expected: $length | received: ${packetLength}" }
            return false
        }

        // Check command ID (1 byte)
        val packetCmdId = data.readU8(4)
        if (packetCmdId != commandId) {
            logger.d { "Command ID | expected: $commandId | received: ${packetCmdId}" }
            return false
        }

        // Check version of the packet (1 byte)
        if (data.readU8(5) != version) {
            logger.d { "Version | expected: $version | received: ${data.readU8(5)}" }
            return false
        }

        // 6. Check CRC
        // CRC covers [Start, Version, Length, CommandId, Data...]
        // From index 0 to (dataOffset + length) exclusive of CRC
        val dataForCrc = data.copyOfRange(6, dataOffset + length)
        val calculatedCrc = CRC16.crcSimple(dataForCrc)
        val packetCrc = data.readU16LE(dataOffset + length)
        //logger.d { "CRC | calculated: $calculatedCrc | received: ${packetCrc}" }

        // crcSimple returns an Int which might use more than 16 bits.
        // SendPackageDto uses .toShort() which truncates to lower 16 bits.
        // We mask with 0xFFFF to ensure we compare the same 16-bit value against the packet's CRC (0..65535).
        return (calculatedCrc and 0xFFFF) == packetCrc
    }

    fun parsePayload(data: ByteArray): Result<T> = parsePayload(PacketReader(data))
    fun parsePayload(reader: PacketReader): Result<T>
}