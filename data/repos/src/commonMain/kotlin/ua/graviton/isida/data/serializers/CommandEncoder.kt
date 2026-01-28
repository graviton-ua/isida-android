package ua.graviton.isida.data.serializers

import ua.graviton.isida.data.protocol.commands.IsidaCommand
import kotlin.reflect.KClass

abstract class CommandEncoder<T : IsidaCommand> {
    abstract val serializers: List<CommandSerializer<out T>>
    private val mapByClass: Map<KClass<out T>, CommandSerializer<out T>> = serializers.associateBy { it.commandKClass() }
    private val mapById: Map<Int, CommandSerializer<out T>> = serializers.associateBy { it.commandId }

    private fun getSerializerByCommandId(commandId: Int): CommandSerializer<out T>? = mapById[commandId]
    private fun getSerializerByType(command: T): CommandSerializer<out T>? = mapByClass[command::class]

    @OptIn(ExperimentalUnsignedTypes::class)
    open fun serialize(command: T): Result<ByteArray> {
        val serializer = getSerializerByType(command)
            ?: return Result.failure(IllegalStateException("Serializer not found for command: ${command::class}"))

        @Suppress("UNCHECKED_CAST")
        return (serializer as CommandSerializer<T>).buildFrame(command)
    }
}