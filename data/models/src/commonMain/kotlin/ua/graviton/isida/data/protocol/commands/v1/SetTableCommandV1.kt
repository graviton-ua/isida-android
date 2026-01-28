package ua.graviton.isida.data.protocol.commands.v1

import ua.graviton.isida.data.protocol.commands.IsidaCommand

data class SetTableCommandV1(
    val someData: Int,
) : IsidaCommand.V1