package ua.isida.data.serializers.v1

import ua.isida.data.protocol.commands.v1.DeviceModeCommandV1
import ua.isida.data.serializers.CommandWriter
import kotlin.reflect.KClass

object DeviceModeV1Serializer : CommandSerializerV1<DeviceModeCommandV1>() {
    override val length: Int = 1        // [0x01, 0x00]
    override val commandId: Int = 87    // [0x57]

    override fun serializePayload(
        command: DeviceModeCommandV1,
        writer: CommandWriter
    ): Result<ByteArray> = Result.runCatching {
        // Write data to byte array
        writer.u8(command.mode)

        writer.data
    }

    override fun commandKClass(): KClass<DeviceModeCommandV1> = DeviceModeCommandV1::class
}