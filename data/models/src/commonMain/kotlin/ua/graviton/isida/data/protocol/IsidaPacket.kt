package ua.graviton.isida.data.protocol

/**
 * Common marker for any data received from the device.
 */
sealed interface IsidaPacket {
    interface V1 : IsidaPacket

    // As example for the future changes
    interface V2 : IsidaPacket
}