package ua.graviton.isida.data.parsers

import com.whoppah.util.readU16LE
import ua.graviton.isida.data.protocol.IsidaPacket

abstract class PacketDecoder<T : IsidaPacket> {
    abstract val parsers: List<PacketParser<out T>>
    private val map: Map<Int, PacketParser<out T>> = parsers.associateBy { it.commandId }

    private fun getParserByCommandId(commandId: Int): PacketParser<out T>? = map[commandId]

    @OptIn(ExperimentalUnsignedTypes::class)
    open fun parse(data: ByteArray): Result<T> {
        //TODO: Verify length of the packet before reading the commandId

        // Command ID is usually placed at index 4 and takes 2 bytes, [4,5]
        val commandId: Int = data.readU16LE(4)
        val parser = getParserByCommandId(commandId)
            ?: return Result.failure(IllegalStateException("Parser not found for commandId: $commandId | data: $data"))

        val validPacket = parser.canParse(data)
        if (!validPacket) return Result.failure(IllegalStateException("Invalid packet for commandId: $commandId | data: $data"))

        return parser.parse(data)
    }
}