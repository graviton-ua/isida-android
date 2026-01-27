package com.whoppah.extensions

import java.nio.ByteBuffer
import java.nio.ByteOrder

fun Short.asByteArray(order: ByteOrder = ByteOrder.LITTLE_ENDIAN): ByteArray =
    ByteBuffer.allocate(Short.SIZE_BYTES).order(order).putShort(this).array()

fun ByteArray.toInt(order: ByteOrder = ByteOrder.LITTLE_ENDIAN): Int = ByteBuffer.wrap(this).order(order).int

fun ByteArray.toHexString(separator: String = "") = asUByteArray().joinToString(separator) { it.toString(16).padStart(2, '0') }