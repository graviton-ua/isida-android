package ua.isida.data.protocol.commands.v1

import ua.isida.data.protocol.commands.IsidaCommand

/**
 * Command to set the device mode.
 * Used to turn the device ON/OFF or enable extra modes like automatic turn or cooling.
 *
 * @property mode The mode code to set (see [ua.graviton.isida.data.protocol.DeviceMode]).
 */
data class DeviceModeCommandV1(
    val mode: Int,  // Turn ON/OFF device
) : IsidaCommand.V1