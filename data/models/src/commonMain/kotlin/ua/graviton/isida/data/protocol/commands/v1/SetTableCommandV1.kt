package ua.graviton.isida.data.protocol.commands.v1

import ua.graviton.isida.data.protocol.commands.IsidaCommand
import ua.graviton.isida.data.protocol.commands.SetTableCommand
import ua.graviton.isida.data.protocol.packets.v1.TableDayV1

/**
 * Command to upload a new or updated incubation program table to the device.
 *
 * @property days The list of daily program settings to upload.
 */
data class SetTableCommandV1(
    val number: Int,
    val days: List<TableDayV1>,
) : IsidaCommand.V1, SetTableCommand