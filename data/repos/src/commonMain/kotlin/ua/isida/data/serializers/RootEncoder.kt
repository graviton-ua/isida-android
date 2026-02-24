package ua.isida.data.serializers

import co.touchlab.kermit.Logger
import ua.isida.extensions.toHexString
import ua.isida.data.protocol.commands.IsidaCommand
import ua.isida.data.serializers.v1.CommandEncoderV1

object RootEncoder {
    val logger by lazy { Logger.withTag("RootEncoder") }

    inline fun <reified T : IsidaCommand> serialize(command: T): Result<ByteArray> {
        val encoder = when (command) {
            is IsidaCommand.V1 -> CommandEncoderV1
            else -> return Result.failure(IllegalStateException("Encoder not found for command: ${command::class}"))
        }

        return encoder.serialize(command)
            .onSuccess {
                logger.d { "Send bytes: [${it.toHexString(separator = ", ")}]" }
            }
    }
}