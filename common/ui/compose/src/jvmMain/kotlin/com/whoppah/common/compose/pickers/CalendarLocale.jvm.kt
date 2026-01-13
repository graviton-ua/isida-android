package com.whoppah.common.compose.pickers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import java.text.NumberFormat
import java.util.Locale
import java.util.WeakHashMap

/**
 * Represents a Locale for the calendar. This locale will be used when formatting dates, determining
 * the input format, and more.
 */
actual typealias CalendarLocale = Locale

@Composable @ReadOnlyComposable
internal actual fun defaultLocale(): CalendarLocale = Locale.getDefault()

/** Returns a string representation of an integer for the given Locale. */
internal actual fun Int.toLocalString(
    minDigits: Int,
    maxDigits: Int,
    isGroupingUsed: Boolean,
    locale: CalendarLocale?,
): String {
    return getCachedDateTimeFormatter(
        minDigits = minDigits,
        maxDigits = maxDigits,
        isGroupingUsed = isGroupingUsed,
        locale = locale ?: Locale.getDefault(),
    )
        .format(this)
}

private val cachedFormatters = WeakHashMap<String, NumberFormat>()

private fun getCachedDateTimeFormatter(
    minDigits: Int,
    maxDigits: Int,
    isGroupingUsed: Boolean,
    locale: CalendarLocale,
): NumberFormat {
    // Note: Using Locale.getDefault() as a best effort to obtain a unique key and keeping this
    // function non-composable.
    val key = "$minDigits.$maxDigits.$isGroupingUsed.${locale.toLanguageTag()}"
    return cachedFormatters.getOrPut(key) {
        NumberFormat.getIntegerInstance(/* inLocale= */ locale).apply {
            this.isGroupingUsed = isGroupingUsed
            this.minimumIntegerDigits = minDigits
            this.maximumIntegerDigits = maxDigits
        }
    }
}