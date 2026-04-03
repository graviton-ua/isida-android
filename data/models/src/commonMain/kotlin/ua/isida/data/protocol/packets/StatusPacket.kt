package ua.isida.data.protocol.packets

/**
 * Interface representing a status packet sent by the device.
 * These packets are typically sent automatically by the device (e.g. every second)
 * and contain the current state, sensor readings, and settings.
 */
interface StatusPacket : IsidaPacket {

    // This method returns string that represents prefix for future logs for data classes of theis interface
    // Potentially this returns device number or model number  or group of something
    val logPrefix: String
}