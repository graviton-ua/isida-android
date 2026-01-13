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
    val pvT3: Float,
    val pvRh: Float,
    val pvCO2_1: Int,
    val pvCO2_2: Int,
    val pvCO2_3: Int,
    val pvTimer: Int,
    val pvTmrCount: Int,
    val pvFlap: Int,
    val power: Int,
    val fuses: Int,
    val errors: Int,
    val warning: Int,
    val cost0: Int,
    val cost1: Int,
    val date: Int,
    val hours: Int,
)