package ua.isida.data.serializers

import ua.isida.util.writeU16LE
import ua.isida.util.writeU8

class CommandWriter(val data: ByteArray) {
    constructor(size: Int) : this(ByteArray(size))

    var offset = 0

    fun u8(value: Int) {
        data.writeU8(offset, value)
        offset += 1
    }

    fun u16(value: Int) {
        data.writeU16LE(offset, value)
        offset += 2
    }

    fun bytes(bytes: ByteArray) {
        bytes.copyInto(data, offset)
        offset += bytes.size
    }
}