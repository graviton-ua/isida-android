package ua.graviton.isida.data.parsers

import com.whoppah.util.readU8
import ua.graviton.isida.data.parsers.v1.PacketDecoderV1
import ua.graviton.isida.data.protocol.IsidaPacket

object RootDecoder {
    private val decoders = mapOf<Int, PacketDecoder<out IsidaPacket>>(
        0x01 to PacketDecoderV1,
    )

    fun parse(data: ByteArray): Result<IsidaPacket> {
        //TODO: Verify length of the packet before reading the version

        // Version is usually placed at index 1 and takes 1 byte1, data[1]
        val version: Int = data.readU8(1)
        val decoder = decoders[version]
            ?: return Result.failure(IllegalStateException("Decoder not found for version: $version | data: $data"))

        return decoder.parse(data)
    }
}