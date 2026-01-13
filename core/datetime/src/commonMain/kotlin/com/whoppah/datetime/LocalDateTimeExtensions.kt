package com.whoppah.datetime

import kotlinx.datetime.*
import kotlinx.datetime.format.DateTimeFormat
import kotlin.time.Clock

// Constants for readability and to avoid magic numbers
private const val NANOS_PER_SECOND = 1_000_000_000L
private const val NANOS_PER_MINUTE = 60_000_000_000L
private const val NANOS_PER_HOUR = 3_600_000_000_000L
private const val NANOS_PER_DAY = 86_400_000_000_000L

fun LocalDateTime.Companion.now(): LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

fun LocalDateTime.plus(value: Int, unit: DateTimeUnit): LocalDateTime {
    return when (unit) {
        is DateTimeUnit.TimeBased -> plusNanos(value * unit.nanoseconds)
        is DateTimeUnit.DateBased -> LocalDateTime(date = date.plus(value, unit), time = time)
    }
}

fun LocalDateTime.minus(value: Int, unit: DateTimeUnit): LocalDateTime {
    return when (unit) {
        is DateTimeUnit.TimeBased -> plusNanos(value * -unit.nanoseconds)
        is DateTimeUnit.DateBased -> LocalDateTime(date = date.minus(value, unit), time = time)
    }
}

/**
 * Adds nanoseconds to the LocalDateTime.
 * This handles carrying over to days if the time overflows/underflows (e.g., passing midnight).
 */
private fun LocalDateTime.plusNanos(nanosToAdd: Long): LocalDateTime {
    if (nanosToAdd == 0L) return this

    val currentNanosOfDay = this.time.toNanosecondOfDay()
    val newNanosOfDayTotal = currentNanosOfDay + nanosToAdd

    // 1. Calculate how many whole days we are adding/removing
    // We use floorDiv so that negative numbers round down correctly towards negative infinity
    val daysToAdd = newNanosOfDayTotal.floorDiv(NANOS_PER_DAY)

    // 2. Calculate the new nanosecond of the day (0 to 86,399,999,999,999)
    // We use mod so that negative results wrap around correctly (e.g., -1 nano becomes 23:59:59...)
    val newNanosOfDay = newNanosOfDayTotal.mod(NANOS_PER_DAY)

    // Optimization: If the day didn't change and time is same (rare case of adding exactly 24h via nanos), return this
    if (daysToAdd == 0L && newNanosOfDay == currentNanosOfDay) {
        return this
    }

    // 3. Construct the new objects
    val newTime = if (newNanosOfDay == currentNanosOfDay) {
        this.time
    } else {
        LocalTime.fromNanosecondOfDay(newNanosOfDay)
    }

    val newDate = if (daysToAdd == 0L) {
        this.date
    } else {
        this.date.plus(daysToAdd.toInt(), DateTimeUnit.DAY)
    }

    return LocalDateTime(newDate, newTime)
}


fun LocalDateTime.format(formatter: DateTimeFormat<LocalDateTime>): String = formatter.format(this)