@file:OptIn(ExperimentalUnsignedTypes::class)

package ua.isida.util

object CRC16 {

    // fun crcSimple(buffer: ByteArray): Int {
    //     var crc: UInt = 0u
    //     buffer.toUByteArray().forEach { byte ->
    //         crc += byte
    //         crc = crc xor (crc shr 2)
    //     }
    //     return crc.toInt()
    // }

    /*** Расчет CRC16-CCITT для массива байтов */
    fun calculateCRC16(data: ByteArray): Int {
        var crc = 0xFFFF // Начальное значение

        for (b in data) {
            crc = crc xor (b.toInt() and 0xFF shl 8)
            for (i in 0 until 8) {
                crc = if (crc and 0x8000 != 0) {
                    crc shl 1 xor 0x1021
                } else {
                    crc shl 1
                }
            }
        }
        return crc and 0xFFFF
    }
}