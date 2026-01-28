package ua.graviton.isida.data.serializers.v1

import ua.graviton.isida.data.protocol.commands.v1.UpdateSettingsCommandV1
import ua.graviton.isida.data.serializers.CommandWriter
import kotlin.reflect.KClass

object UpdateSettingsV1Serializer : CommandSerializerV1<UpdateSettingsCommandV1>() {
    override val length: Int = 40       // [0x28, 0x00]
    override val commandId: Int = 51    // [0x33, 0x00]

    override fun serializePayload(
        command: UpdateSettingsCommandV1,
        writer: CommandWriter
    ): Result<ByteArray> = Result.runCatching {
        // Write data to byte array
        writer.u8(command.pkoff0)

        writer.data
    }

    override fun commandKClass(): KClass<UpdateSettingsCommandV1> = UpdateSettingsCommandV1::class
}