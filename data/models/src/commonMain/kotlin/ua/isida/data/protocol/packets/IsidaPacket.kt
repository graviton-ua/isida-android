package ua.isida.data.protocol.packets

/**
 * Common marker interface for any data packet received from the ISIDA device.
 */
sealed interface IsidaPacket {
    /** Marker interface for Version 1 packets. */
    interface V1 : IsidaPacket

    // As example for the future changes
    /** Marker interface for Version 2 packets. */
    interface V2 : IsidaPacket
}