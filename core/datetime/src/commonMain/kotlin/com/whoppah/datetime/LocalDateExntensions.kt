@file:OptIn(ExperimentalTime::class)

package com.whoppah.datetime

import kotlinx.datetime.*
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

fun LocalDate.Companion.now(): LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())

fun LocalDate.atStartOfDay(): LocalDateTime = LocalDateTime(this, LocalTime.MIDNIGHT)