package ua.graviton.isida.data.serializers

import ua.graviton.isida.data.protocol.commands.IsidaCommand
import kotlin.reflect.KClass

interface CommandSerializer<T : IsidaCommand> {
    val version: Int
    val length: Int
    val commandId: Int

    fun serializePayload(command: T): Result<ByteArray> = serializePayload(command, CommandWriter(length))
    fun serializePayload(command: T, writer: CommandWriter): Result<ByteArray>

    fun buildFrame(command: T): Result<ByteArray>

    fun commandKClass(): KClass<T>
}