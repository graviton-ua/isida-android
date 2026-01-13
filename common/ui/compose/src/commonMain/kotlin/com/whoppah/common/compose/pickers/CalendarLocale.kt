package com.whoppah.common.compose.pickers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

/**
 * Represents a Locale for the calendar. This locale will be used when formatting dates, determining
 * the input format, and more.
 *
 * Note: For JVM based platforms, this would be equivalent to [java.util.Locale].
 */
expect class CalendarLocale

/**
 * Returns the default [CalendarLocale].
 *
 * Note: For JVM based platforms, this would be equivalent to [java.util.Locale.getDefault].
 */
@Composable @ReadOnlyComposable internal expect fun defaultLocale(): CalendarLocale

/**
 * Returns a string representation of an integer for the current Locale.
 *
 * @param minDigits sets the minimum number of digits allowed in the integer portion of a number. If
 *   the minDigits value is greater than the [maxDigits] value, then [maxDigits] will also be set to
 *   this value.
 * @param maxDigits sets the maximum number of digits allowed in the integer portion of a number. If
 *   this maxDigits value is less than the [minDigits] value, then [minDigits] will also be set to
 *   this value.
 * @param isGroupingUsed set whether or not grouping will be used when formatting into a local
 *   string. By default, this value is false, which eliminates any use of delimiters when formatting
 *   the integer.
 * @param locale an optional [CalendarLocale] that will be used to format the integer in a given
 *   locale. If `null` (default), the default locale will be used.
 */
internal expect fun Int.toLocalString(
    minDigits: Int = 1,
    maxDigits: Int = 40,
    isGroupingUsed: Boolean = false,
    locale: CalendarLocale? = null,
): String