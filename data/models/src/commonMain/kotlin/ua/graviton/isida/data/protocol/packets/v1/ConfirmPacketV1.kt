package ua.graviton.isida.data.protocol.packets.v1

import ua.graviton.isida.data.protocol.packets.IsidaPacket

data class ConfirmPacketV1(
    val commandId: Int, // 1 байт ind=0  CommandID that was received and confirmed by device
) : IsidaPacket.V1