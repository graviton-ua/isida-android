package ua.isida.data.parsers

import ua.isida.util.readU8
import ua.isida.data.protocol.packets.IsidaPacket

abstract class PacketDecoder<T : IsidaPacket> {
    abstract val parsers: List<PacketParser<out T>>
    private val map: Map<Int, PacketParser<out T>> by lazy { parsers.associateBy { it.commandId } }

    private fun getParserByCommandId(commandId: Int): PacketParser<out T>? = map[commandId]

    @OptIn(ExperimentalUnsignedTypes::class)
    open fun parse(data: ByteArray): Result<T> {
        if (data.size < 10) return Result.failure(IllegalStateException("Invalid packet size: ${data.size} | Packet size should be minimum 10 bytes (if body is 0 size)"))

        // Command ID is usually placed at index 4 and takes 1 byte
        val commandId: Int = data.readU8(4)
        val parser = getParserByCommandId(commandId)
            ?: return Result.failure(IllegalStateException("Parser not found for commandId: $commandId | data: $data"))

        val validPacket = parser.canParse(data)
        if (!validPacket) {
            val hexData = data.joinToString(" ") { it.toUByte().toString(16).uppercase().padStart(2, '0') }
            return Result.failure(IllegalStateException("Invalid packet for commandId: $commandId | data: [$hexData]"))
        }

        return parser.parsePayload(data.copyOfRange(fromIndex = 6, toIndex = data.size - 4))
    }
}