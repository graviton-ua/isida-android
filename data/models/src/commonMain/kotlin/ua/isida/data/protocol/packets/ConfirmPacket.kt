package ua.isida.data.protocol.packets

interface ConfirmPacket : IsidaPacket {
    val commandId: Int
}