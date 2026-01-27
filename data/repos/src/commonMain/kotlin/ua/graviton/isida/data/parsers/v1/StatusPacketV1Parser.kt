package ua.graviton.isida.data.parsers.v1

import com.whoppah.util.CRC16
import com.whoppah.util.readU16LE
import com.whoppah.util.readU8
import ua.graviton.isida.data.parsers.PacketParser
import ua.graviton.isida.data.parsers.PacketReader
import ua.graviton.isida.data.protocol.v1.StatusPacketV1

object StatusPacketV1Parser : PacketParser<StatusPacketV1> {
    override val version: Int = 1       // [0x01]
    override val length: Int = 60       // [0x3C, 0x00]
    override val commandId: Int = 77    // [0x4D, 0x00]

    override fun canParse(data: ByteArray): Boolean {
        // Start(1) + Ver(1) + Len(2) + Cmd(2) + Data(60) + CRC(2) + End(2) = 70
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

    override fun parse(reader: PacketReader): Result<StatusPacketV1> = Result.runCatching {
        require(reader.data.size == length) { "Data size must be $length but was ${reader.data.size}" }
        StatusPacketV1(
            model = reader.u8(),
            node = reader.u8(),
            pvT0 = reader.u16() / 10f,
            pvT1 = reader.u16() / 10f,
            pvT2 = reader.u16() / 10f,
            pvRh = reader.u8(),
            pvCO2 = reader.u8(),
            pvTimer = reader.u8(),
            pvFan = reader.u8(),
            pvFlap = reader.u8(),
            power = reader.u8(),
            fuses = reader.u8(),
            errors = reader.u8(),
            warning = reader.u8(),
            output = reader.u8(),
            dayHour = reader.u8(),
            minSec = reader.u8(),
            spT0 = reader.u16() / 10f,
            spT1 = reader.u16() / 10f,
            spRh0 = reader.u8() / 10f,
            spRh1 = reader.u8() / 10f,
            state = reader.u8(),
            extendMode = reader.u8(),
            relayMode = reader.u8(),
            programm = reader.u8(),
            minRun = reader.u8(),
            maxRun = reader.u8(),
            period = reader.u8(),
            timer0 = reader.u8(),
            timer1 = reader.u8(),
            alarm0 = reader.u8() / 10f,
            alarm1 = reader.u8() / 10f,
            extOn0 = reader.u8() / 10f,
            extOn1 = reader.u8() / 10f,
            extOff0 = reader.u8() / 10f,
            extOff1 = reader.u8() / 10f,
            air0 = reader.u8(),
            air1 = reader.u8(),
            spCO2 = reader.u8(),
            koffCurr = reader.u8(),
            hysteresis = reader.u8(),
            zonaFlap = reader.u8(),
            turnTime = reader.u8(),
            waitCooling = reader.u8(),
            pkoff0 = reader.u8(),
            pkoff1 = reader.u8(),
            ikoff0 = reader.u8(),
            ikoff1 = reader.u8(),
            identif = reader.u8(),
            ip0 = reader.u8(),
            ip1 = reader.u8(),
            ip2 = reader.u8(),
            ip3 = reader.u8(),
            nothing0 = reader.u8(),
            nothing1 = reader.u8(),
        )
    }
}