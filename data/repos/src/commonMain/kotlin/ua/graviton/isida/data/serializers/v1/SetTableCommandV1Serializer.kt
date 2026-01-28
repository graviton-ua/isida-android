package ua.graviton.isida.data.serializers.v1

import ua.graviton.isida.data.protocol.commands.v1.SetTableCommandV1
import ua.graviton.isida.data.serializers.CommandWriter
import kotlin.reflect.KClass

object SetTableCommandV1Serializer : CommandSerializerV1<SetTableCommandV1>() {
    override val length: Int = 240      // [0xF0, 0x00]
    override val commandId: Int = 57    // [0x39, 0x00]

    override fun serializePayload(
        command: SetTableCommandV1,
        writer: CommandWriter
    ): Result<ByteArray> = Result.runCatching {
        // Write data to byte array
        writer.u8(command.someData)

        writer.data
    }

    override fun commandKClass(): KClass<SetTableCommandV1> = SetTableCommandV1::class
}