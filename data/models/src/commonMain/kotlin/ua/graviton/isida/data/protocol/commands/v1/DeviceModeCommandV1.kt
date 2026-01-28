package ua.graviton.isida.data.protocol.commands.v1

import ua.graviton.isida.data.protocol.commands.IsidaCommand

data class DeviceModeCommandV1(
    val mode: Int,  // Turn ON/OFF device
) : IsidaCommand.V1