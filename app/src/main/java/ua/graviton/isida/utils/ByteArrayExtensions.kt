package ua.graviton.isida.utils

import java.nio.ByteBuffer
import java.nio.ByteOrder

fun Short.asByteArray(order: ByteOrder = ByteOrder.BIG_ENDIAN): ByteArray = ByteBuffer.allocate(Short.SIZE_BYTES).order(order).putShort(this).array()
fun Int.asByteArray(order: ByteOrder = ByteOrder.BIG_ENDIAN): ByteArray = ByteBuffer.allocate(Int.SIZE_BYTES).order(order).putInt(this).array()
fun Long.asByteArray(order: ByteOrder = ByteOrder.BIG_ENDIAN): ByteArray = ByteBuffer.allocate(Long.SIZE_BYTES).order(order).putLong(this).array()
fun Float.asByteArray(order: ByteOrder = ByteOrder.BIG_ENDIAN): ByteArray = ByteBuffer.allocate(Float.SIZE_BYTES).order(order).putFloat(this).array()
fun Double.asByteArray(order: ByteOrder = ByteOrder.BIG_ENDIAN): ByteArray =
    ByteBuffer.allocate(Double.SIZE_BYTES).order(order).putDouble(this).array()

fun ByteArray.toShort(order: ByteOrder = ByteOrder.LITTLE_ENDIAN): Short = ByteBuffer.wrap(this).order(order).short
fun ByteArray.toInt(order: ByteOrder = ByteOrder.LITTLE_ENDIAN): Int = ByteBuffer.wrap(this).order(order).int
fun ByteArray.toLong(order: ByteOrder = ByteOrder.LITTLE_ENDIAN): Long = ByteBuffer.wrap(this).order(order).long
fun ByteArray.toFloat(order: ByteOrder = ByteOrder.LITTLE_ENDIAN): Float = ByteBuffer.wrap(this).order(order).float
fun ByteArray.toDouble(order: ByteOrder = ByteOrder.LITTLE_ENDIAN): Double = ByteBuffer.wrap(this).order(order).double