package ua.graviton.isida.data.protocol.packets.v1

import ua.graviton.isida.data.protocol.packets.IsidaPacket

/**
 * Packet sent by the device to confirm receipt of a command.
 *
 * @property commandId The ID of the command that was successfully received and confirmed.
 */
data class ConfirmPacketV1(
    val commandId: Int, // 1 байт ind=0  CommandID that was received and confirmed by device
) : IsidaPacket.V1