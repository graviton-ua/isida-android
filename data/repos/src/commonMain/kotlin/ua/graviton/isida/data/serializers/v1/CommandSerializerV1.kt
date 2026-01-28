package ua.graviton.isida.data.serializers.v1

import com.whoppah.extensions.asByteArray
import com.whoppah.util.CRC16
import com.whoppah.util.writeBytes
import com.whoppah.util.writeU16LE
import com.whoppah.util.writeU8
import ua.graviton.isida.data.protocol.commands.IsidaCommand
import ua.graviton.isida.data.serializers.CommandSerializer

abstract class CommandSerializerV1<T : IsidaCommand.V1> : CommandSerializer<T> {
    override val version: Int = 1

    override fun buildFrame(command: T): Result<ByteArray> {
        // 1. Prepare Payload (Currently in buffer)
        return serializePayload(command).mapCatching { payload ->
            // 2. Construct the "Core" packet (Start + Ver + Len + Cmd + Payload) for CRC calculation
            // Frame: 55 01 [LenL LenH] [CmdL] [Ver] [Payload...]
            val coreBytes = ByteArray(2 + 2 + 1 + 1 + payload.size)

            coreBytes.writeU8(0, 0x55)          // Start
            coreBytes.writeU8(1, 0x01)          // Start

            // Length (2 bytes LE)
            coreBytes.writeU16LE(2, length)

            // Command ID (1 byte)
            coreBytes.writeU8(4, commandId)

            // Version (1 byte)
            coreBytes.writeU8(5, version)

            // Payload
            coreBytes.writeBytes(6, payload)

            // 3. Calculate CRC
            // Ensure we mask strictly to 16 bits
            val crc = CRC16.crcSimple(payload) and 0xFFFF
            val crcBytes = crc.toShort().asByteArray()

            // 4. Add Footer
            // Frame: [Core] [CRC] 0D 0A
            coreBytes + crcBytes + byteArrayOf(0x0D.toByte(), 0x0A.toByte())
        }
    }
}