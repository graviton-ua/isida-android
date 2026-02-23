package ua.graviton.isida.data.protocol.packets.v1

import kotlinx.serialization.Serializable
import ua.graviton.isida.data.protocol.packets.IsidaPacket
import ua.graviton.isida.data.protocol.packets.TableDay
import ua.graviton.isida.data.protocol.packets.TablePacket

/**
 * Packet containing the incubation program table.
 * Received in response to [ua.graviton.isida.data.protocol.commands.v1.RequestTableCommandV1].
 *
 * @property days List of daily settings for the incubation program.
 */
data class TablePacketV1(
    val days: List<TableDayV1>,
) : IsidaPacket.V1, TablePacket

/**
 * Represents the settings for a single day in the incubation program.
 *
 * @property spT0 Temperature setpoint for sensor #0 (Dry).
 * @property spT1 Temperature setpoint for sensor #1 (Wet).
 * @property spRh Humidity setpoint.
 * @property spFlp Flap position setpoint.
 * @property spTr Tray rotation setpoint.
 * @property spCO2 Cooling setpoint.
 */
@Serializable
data class TableDayV1(
    val spT0: Float,    // 2 bytes
    val spT1: Float,    // 2 bytes
    val spRh: Int,      // 1 byte
    val spFlp: Int,     // 1 byte
    val spTr: Int,      // 1 byte
    val spCO2: Int,     // 1 byte
) : TableDay