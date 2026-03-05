package ua.isida.data.parsers

import ua.isida.util.readS16LE
import ua.isida.util.readS8
import ua.isida.util.readU16LE
import ua.isida.util.readU8

class PacketReader(val data: ByteArray) {
    var offset = 0

    fun u8(): Int {
        val value = data.readU8(offset)
        offset += 1
        return value
    }

    fun s8(): Int {
        val value = data.readS8(offset)
        offset += 1
        return value
    }

    fun u16(): Int {
        val value = data.readU16LE(offset)
        offset += 2
        return value
    }

    fun s16(): Int {
        val value = data.readS16LE(offset)
        offset += 2
        return value
    }
}