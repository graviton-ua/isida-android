package ua.isida.data.protocol.commands.v1

import ua.isida.data.protocol.commands.IsidaCommand
import ua.isida.data.protocol.commands.SettingRealTimeClockCommand

/**
 * Command to set the real time clock on the device.
 * Setting values to 00:00:00 01/01/01 triggers "Start Incubation" on the device.
 *
 * @property second The second (0-59)
 * @property minute The minute (0-59)
 * @property hour The hour (0-23)
 * @property day The day of the month (1-31)
 * @property month The month (1-12)
 * @property year The year (0-99)
 */
data class SettingRealTimeClockCommandV1(
    val second: Int,
    val minute: Int,
    val hour: Int,
    val day: Int,
    val month: Int,
    val year: Int
) : IsidaCommand.V1, SettingRealTimeClockCommand
