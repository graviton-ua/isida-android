package ua.graviton.isida.data.parsers

import co.touchlab.kermit.Logger
import ua.isida.util.readU8
import ua.graviton.isida.data.parsers.v1.PacketDecoderV1
import ua.graviton.isida.data.protocol.packets.IsidaPacket

object RootDecoder {
    private val logger by lazy { Logger.withTag("RootDecoder") }
    private val decoders = mapOf<Int, PacketDecoder<out IsidaPacket>>(
        1 to PacketDecoderV1,
    )

    fun parse(data: ByteArray): Result<IsidaPacket> {
        //logger.d { "Received bytes: [${data.toHexString(separator = ", ")}]" }
        //TODO: Verify length of the packet before reading the version

        // Version is usually placed at index 5 and takes 1 byte
        val version: Int = data.readU8(5)
        val decoder = decoders[version]
            ?: return Result.failure(IllegalStateException("Decoder not found for version: $version | data: $data"))

        return decoder.parse(data)
    }
}