package com.whoppah.common.resources

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/**
 * A common interface for formatting kotlinx.datetime objects into strings.
 * Implementations are platform-specific.
 */
interface LocaleDateTimeFormatter {
    /**
     * Formats an [Instant] into a string representation.
     * An [Instant] represents a specific moment in time, and formatting it
     * requires a to resolve it into human-readable components
     * like year, month, day, and hour.
     */
    @OptIn(ExperimentalTime::class)
    fun format(instant: Instant, timeZone: TimeZone): String

    /**
     * Formats a into a string representation.
     * Note: On platforms like iOS, which rely on time-moment-based APIs (NSDate),
     * a TimeZone will be required to resolve the ambiguity of a "local" time.
     * The default system time zone is typically used if not specified.
     */
    fun format(localDateTime: LocalDateTime, timeZone: TimeZone = TimeZone.currentSystemDefault()): String

    /**
     * Formats a into a string representation.
     * Similar to LocalDateTime, a TimeZone is often required on certain platforms
     * to determine the exact moment the "day" begins.
     */
    fun format(localDate: LocalDate, timeZone: TimeZone = TimeZone.currentSystemDefault()): String


    companion object {
        /**
         * Obtains a formatter for a specific pattern and locale.
         *
         * @param pattern The desired.
         * @param locale The platform-specific [PlatformLocale] for localization.
         * @return A cached or newly created instance.
         */
        fun ofPattern(pattern: String, locale: PlatformLocale = platformLocaleGetDefault()): LocaleDateTimeFormatter {
            return getPlatformDateTimeFormatter(pattern, locale)
        }
    }
}

/**
 * Expected factory function.
 * Implementations are responsible for caching if necessary.
 */
internal expect fun getPlatformDateTimeFormatter(pattern: String, locale: PlatformLocale): LocaleDateTimeFormatter

@Composable
fun rememberLocaleDateTimeFormatter(pattern: String, locale: PlatformLocale = defaultPlatformLocale()): LocaleDateTimeFormatter {
    return remember(pattern, locale) { LocaleDateTimeFormatter.ofPattern(pattern, locale) }
}

fun LocalDate.format(formatter: LocaleDateTimeFormatter): String = formatter.format(this)
fun LocalDateTime.format(formatter: LocaleDateTimeFormatter): String = formatter.format(this)