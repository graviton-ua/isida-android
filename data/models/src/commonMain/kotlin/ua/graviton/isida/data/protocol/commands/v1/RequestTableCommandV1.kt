package ua.graviton.isida.data.protocol.commands.v1

import ua.graviton.isida.data.protocol.commands.IsidaCommand

data class RequestTableCommandV1(
    val number: Int // Payload 1 byte usually implies device address/number
) : IsidaCommand.V1