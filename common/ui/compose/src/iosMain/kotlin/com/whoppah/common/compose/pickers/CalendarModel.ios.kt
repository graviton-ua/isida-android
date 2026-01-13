package com.whoppah.common.compose.pickers

import androidx.compose.material3.CalendarLocale
import platform.Foundation.*

/** Returns a [CalendarModel] to be used by the date picker. */
internal actual fun createCalendarModel(locale: CalendarLocale): CalendarModel {
    return CalendarModelImpl(locale)
}

/**
 * Formats a UTC timestamp into a string with a given date format skeleton.
 */
internal actual fun formatWithSkeleton(
    utcTimeMillis: Long,
    skeleton: String,
    locale: CalendarLocale,
    cache: MutableMap<String, Any>,
): String {
    val pattern = NSDateFormatter.dateFormatFromTemplate(
        tmplate = skeleton,
        options = 0u,
        locale = locale
    ) ?: skeleton

    val formatter = cache.getOrPut("S:$pattern${locale.localeIdentifier}") {
        NSDateFormatter().apply {
            this.locale = locale
            this.dateFormat = pattern
            // Similar to Android, we force UTC/GMT for the formatting context of the timestamp
            this.timeZone = NSTimeZone.timeZoneWithAbbreviation("UTC")!!
        }
    } as NSDateFormatter

    val date = NSDate.dateWithTimeIntervalSince1970(utcTimeMillis / 1000.0)
    return formatter.stringFromDate(date)
}