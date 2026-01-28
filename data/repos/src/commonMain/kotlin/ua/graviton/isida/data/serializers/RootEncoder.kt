package ua.graviton.isida.data.serializers

import ua.graviton.isida.data.protocol.commands.IsidaCommand
import ua.graviton.isida.data.serializers.v1.CommandEncoderV1

object RootEncoder {

    inline fun <reified T : IsidaCommand> serialize(command: T): Result<ByteArray> {
        val encoder = when (command) {
            is IsidaCommand.V1 -> CommandEncoderV1
            else -> return Result.failure(IllegalStateException("Encoder not found for command: ${command::class}"))
        }

        return encoder.serialize(command)
    }
}