package ua.graviton.isida.data.serializers.v1

import ua.graviton.isida.data.protocol.commands.IsidaCommand
import ua.graviton.isida.data.serializers.CommandEncoder

object CommandEncoderV1 : CommandEncoder<IsidaCommand.V1>() {
    override val serializers = listOf(DeviceModeV1Serializer, RequestTableV1Serializer, SetTableCommandV1Serializer, UpdateSettingsV1Serializer)
}