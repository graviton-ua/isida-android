package com.whoppah.common.compose.pickers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import platform.Foundation.NSLocale
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.currentLocale

/**
 * Represents a Locale for the calendar.
 */
actual typealias CalendarLocale = NSLocale

/** Returns the default [CalendarLocale]. */
@Composable
@ReadOnlyComposable
internal actual fun defaultLocale(): CalendarLocale = NSLocale.currentLocale

/** Returns a string representation of an integer for the given Locale. */
internal actual fun Int.toLocalString(
    minDigits: Int,
    maxDigits: Int,
    isGroupingUsed: Boolean,
    locale: CalendarLocale?,
): String {
    val formatter = NSNumberFormatter().apply {
        this.minimumIntegerDigits = minDigits.toULong()
        this.maximumIntegerDigits = maxDigits.toULong()
        this.usesGroupingSeparator = isGroupingUsed
        this.locale = locale ?: NSLocale.currentLocale
    }
    return formatter.stringFromNumber(NSNumber(int = this)) ?: this.toString()
}