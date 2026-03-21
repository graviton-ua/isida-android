package ua.isida.data.serializers.v1

import ua.isida.data.protocol.commands.v1.SettingRealTimeClockCommandV1
import ua.isida.data.serializers.CommandWriter
import kotlin.reflect.KClass

object SettingRealTimeClockV1Serializer : CommandSerializerV1<SettingRealTimeClockCommandV1>() {
    override val length: Int = 6
    override val commandId: Int = 5

    override fun serializePayload(
        command: SettingRealTimeClockCommandV1,
        writer: CommandWriter
    ): Result<ByteArray> = Result.runCatching {
        writer.u8(command.second)
        writer.u8(command.minute)
        writer.u8(command.hour)
        writer.u8(command.day)
        writer.u8(command.month)
        writer.u8(command.year)
        writer.data
    }

    override fun commandKClass(): KClass<SettingRealTimeClockCommandV1> = SettingRealTimeClockCommandV1::class
}
