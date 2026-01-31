package ua.graviton.isida.data.protocol.commands.v1

import ua.graviton.isida.data.protocol.commands.IsidaCommand
import ua.graviton.isida.data.protocol.packets.v1.TableDayV1

data class SetTableCommandV1(
    val days: List<TableDayV1>,
) : IsidaCommand.V1