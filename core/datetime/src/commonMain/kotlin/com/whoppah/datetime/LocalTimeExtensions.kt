package com.whoppah.datetime

import kotlinx.datetime.LocalTime

/**
 * The time of midnight at the start of the day, '00:00'.
 */
val LocalTime.Companion.MIDNIGHT: LocalTime
    get() = LocalTime(0, 0, 0, 0)