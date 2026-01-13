package com.whoppah.common.compose.pickers

import android.icu.text.DateFormat
import android.icu.text.DisplayContext
import android.icu.util.TimeZone
import android.os.Build
import androidx.compose.material3.CalendarLocale
import java.util.Date

/** Returns a [CalendarModel] to be used by the date picker. */
internal actual fun createCalendarModel(locale: CalendarLocale): CalendarModel {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        CalendarModelImpl(locale)
    } else {
        LegacyCalendarModelImpl(locale)
    }
}

/**
 * Formats a UTC timestamp into a string with a given date format skeleton.
 *
 * A skeleton is similar to, and uses the same format characters as described in
 * [Unicode Technical Standard #35](https://unicode.org/reports/tr35/tr35-dates.html#Date_Field_Symbol_Table)
 *
 * One difference is that order is irrelevant. For example, "MMMMd" will return "MMMM d" in the
 * en_US locale, but "d. MMMM" in the de_CH locale.
 *
 * @param utcTimeMillis a UTC timestamp to format (milliseconds from epoch)
 * @param skeleton a date format skeleton
 * @param locale the [CalendarLocale] to use when formatting the given timestamp
 * @param cache a [MutableMap] for caching formatter related results for better performance
 */
internal actual fun formatWithSkeleton(
    utcTimeMillis: Long,
    skeleton: String,
    locale: CalendarLocale,
    cache: MutableMap<String, Any>,
): String {
    val instanceForSkeleton = cache.getOrPut(key = "S:$skeleton${locale.toLanguageTag()}") {
        val instanceForSkeleton = DateFormat.getInstanceForSkeleton(skeleton, locale)
        instanceForSkeleton.setContext(DisplayContext.CAPITALIZATION_FOR_STANDALONE)
        // Note: ICU uses the terms GMT and UTC interchangeably, as it does not handle leap
        // seconds or historical behavior.
        instanceForSkeleton.timeZone = TimeZone.GMT_ZONE
        instanceForSkeleton
    } as DateFormat
    return instanceForSkeleton.format(Date(utcTimeMillis))
}