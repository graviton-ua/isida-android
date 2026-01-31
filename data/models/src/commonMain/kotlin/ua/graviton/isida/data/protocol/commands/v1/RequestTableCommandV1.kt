package ua.graviton.isida.data.protocol.commands.v1

import ua.graviton.isida.data.protocol.commands.IsidaCommand

/**
 * Command to request the incubation program table from the device.
 * The device is expected to respond with a [ua.graviton.isida.data.protocol.packets.v1.TablePacketV1].
 * This table typically contains 30 days of incubation data (temperature, humidity, etc.).
 *
 * @property number Payload byte, usually implies device address/number.
 */
data class RequestTableCommandV1(
    val number: Int // Payload 1 byte usually implies device address/number
) : IsidaCommand.V1