package ua.graviton.isida.data.parsers

import com.whoppah.util.readU16LE
import com.whoppah.util.readU8

class PacketReader(val data: ByteArray) {
    var offset = 0

    fun u8(): Int {
        val value = data.readU8(offset)
        offset += 1
        return value
    }

    fun u16(): Int {
        val value = data.readU16LE(offset)
        offset += 2
        return value
    }
}