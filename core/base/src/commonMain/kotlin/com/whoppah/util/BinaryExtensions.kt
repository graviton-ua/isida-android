package com.whoppah.util

@OptIn(ExperimentalUnsignedTypes::class)
fun UByteArray.read2BytesAsInt(index: Int): Int = this[index].toInt() + this[index + 1].toInt() * 256

// --- READ OPERATIONS (ByteArray -> Value) ---

/**
 * Reads a single unsigned byte.
 * Returns Int (0..255).
 */
fun ByteArray.readU8(index: Int): Int {
    return this[index].toInt() and 0xFF
}

/**
 * Reads 2 bytes as an Unsigned Short (Little Endian).
 * Returns Int (0..65535).
 * Use this for values like pvT0, pvT1 where the logical value is positive.
 */
fun ByteArray.readU16LE(index: Int): Int {
    val low = this[index].toInt() and 0xFF
    val high = this[index + 1].toInt() and 0xFF
    return low or (high shl 8)
}

/**
 * Reads 4 bytes as Unsigned Int (Little Endian).
 * Returns Long (to avoid overflow of signed Int).
 */
fun ByteArray.readU32LE(index: Int): Long {
    var result = 0L
    result = result or ((this[index].toInt() and 0xFF).toLong())
    result = result or ((this[index + 1].toInt() and 0xFF).toLong() shl 8)
    result = result or ((this[index + 2].toInt() and 0xFF).toLong() shl 16)
    result = result or ((this[index + 3].toInt() and 0xFF).toLong() shl 24)
    return result
}

// --- WRITE OPERATIONS (Value -> ByteArray) ---

/**
 * Writes an integer (0..255) into the array at [index].
 * Truncates if value > 255.
 */
fun ByteArray.writeU8(index: Int, value: Int) {
    this[index] = value.toByte()
}

/**
 * Writes an integer (0..65535) as 2 bytes (Little Endian).
 * [value] is Int to allow full 0-65535 range.
 */
fun ByteArray.writeU16LE(index: Int, value: Int) {
    // Low Byte
    this[index] = value.toByte()
    // High Byte (shift right 8 bits, then take byte)
    this[index + 1] = (value ushr 8).toByte()
}

/**
 * Writes a Float value that represents a "Div 10" fixed point.
 * Example: Input 25.5 -> Writes 255 (0xFF 0x00).
 */
fun ByteArray.writeFloatAsU16LE(index: Int, value: Float) {
    val intValue = (value * 10).toInt()
    writeU16LE(index, intValue)
}

/**
 * Converts an Int to a 2-byte array (Little Endian).
 * Useful for 0-65535 values.
 */
fun Int.to2ByteArrayLE(): ByteArray {
    val buffer = ByteArray(2)
    buffer[0] = this.toByte()
    buffer[1] = (this ushr 8).toByte()
    return buffer
}