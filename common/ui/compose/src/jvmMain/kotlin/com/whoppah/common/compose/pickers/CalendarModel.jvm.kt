package com.whoppah.common.compose.pickers

import androidx.compose.material3.CalendarLocale
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/** Returns a [CalendarModel] to be used by the date picker. */
internal actual fun createCalendarModel(locale: CalendarLocale): CalendarModel = LegacyCalendarModelImpl(locale)

/**
 * [WORKAROUND] Formats a UTC timestamp using java.time, approximating skeleton behavior.
 *
 * This implementation avoids the ICU4J dependency but has limitations:
 * - It only handles a predefined set of common skeletons.
 * - Custom patterns (like "MMMMd") will NOT reorder elements for different locales.
 * For example, "MMMM d" will not become "d. MMMM" automatically.
 *
 * @param utcTimeMillis a UTC timestamp to format (milliseconds from epoch)
 * @param skeleton a date format skeleton (e.g., "yMMMM", "MMMMd")
 * @param locale the [CalendarLocale] to use when formatting the given timestamp
 * @param cache a [MutableMap] for caching formatter related results for better performance
 */
internal actual fun formatWithSkeleton(
    utcTimeMillis: Long,
    skeleton: String,
    locale: CalendarLocale,
    cache: MutableMap<String, Any>,
): String {
    val formatter = cache.getOrPut(key = "S:$skeleton${locale.toLanguageTag()}") {
        getFormatterForSkeleton(skeleton, locale)
    } as DateTimeFormatter

    val zonedDateTime = Instant.ofEpochMilli(utcTimeMillis).atZone(ZoneOffset.UTC)
    return formatter.format(zonedDateTime)
}

/**
 * Helper function to select a DateTimeFormatter that approximates the given skeleton.
 */
private fun getFormatterForSkeleton(skeleton: String, locale: CalendarLocale): DateTimeFormatter {
    // This `when` block maps common skeletons to the best-available java.time formatter.
    // This is where you can add more mappings if your app uses other skeletons.
    return when (skeleton) {
        // For skeletons that map well to a fixed pattern.
        // WARNING: These will not reorder based on locale.
        "yMMMM" -> DateTimeFormatter.ofPattern("MMMM yyyy", locale)
        "MMMMd" -> DateTimeFormatter.ofPattern("MMMM d", locale)
        "y" -> DateTimeFormatter.ofPattern("yyyy", locale)

        // For skeletons that map well to a standard localized format.
        // This is preferred as it respects locale conventions.
        "yMMMEd" -> DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(locale)
        "yMMMd" -> DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(locale)

        // A sensible fallback for any other unhandled skeletons.
        else -> DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(locale)
    }
}