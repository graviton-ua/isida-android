package ua.graviton.isida.data.db.models

import kotlin.time.Clock
import kotlin.time.Instant

data class DeviceDataEntity(
    val id: Long = 0,
    val createdAt: Instant = Clock.System.now(),

    val deviceId: Int,
    val pvT0: Float,
    val pvT1: Float,
    val pvT2: Float,
    val pvRh: Int,
    val pvCO2: Int,
    val pvTimer: Int,
    val pvFlap: Int,
    val power: Int,
    val fuses: Int,
    val errors: Int,
    val warning: Int,
)