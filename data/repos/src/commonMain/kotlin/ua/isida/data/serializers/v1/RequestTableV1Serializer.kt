package ua.isida.data.serializers.v1

import ua.isida.data.protocol.commands.v1.RequestTableCommandV1
import ua.isida.data.serializers.CommandWriter
import kotlin.reflect.KClass

object RequestTableV1Serializer : CommandSerializerV1<RequestTableCommandV1>() {
    override val length: Int = 1        // [0x01, 0x00]
    override val commandId: Int = 31    // [0x1F]

    override fun serializePayload(
        command: RequestTableCommandV1,
        writer: CommandWriter
    ): Result<ByteArray> = Result.runCatching {
        // Write data to byte array
        writer.u8(command.number)

        writer.data
    }

    override fun commandKClass(): KClass<RequestTableCommandV1> = RequestTableCommandV1::class
}