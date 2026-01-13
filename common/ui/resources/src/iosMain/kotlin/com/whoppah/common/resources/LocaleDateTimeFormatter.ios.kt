package com.whoppah.common.resources

import kotlinx.datetime.*
import platform.Foundation.*
import kotlin.native.concurrent.ThreadLocal
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/**
 * 1. The Cache is marked @ThreadLocal.
 * This ensures that every thread gets its own unique HashMap.
 * No locks are required because no two threads access the same map.
 */
@ThreadLocal
private object IosFormatterCache {
    val formatters = mutableMapOf<String, NSDateFormatter>()
}

internal actual fun getPlatformDateTimeFormatter(pattern: String, locale: PlatformLocale): LocaleDateTimeFormatter {
    // We return a lightweight wrapper that knows how to look up the cached native formatter
    return IosLocaleDateTimeFormatter(pattern, locale)
}

/**
 * A lightweight wrapper. It doesn't hold the NSDateFormatter directly.
 * It looks it up from the ThreadLocal cache whenever format() is called.
 */
private class IosLocaleDateTimeFormatter(
    private val pattern: String,
    private val locale: PlatformLocale
) : LocaleDateTimeFormatter {

    // Helper to get or create the formatter for the CURRENT thread
    private fun getFormatter(timeZone: TimeZone): NSDateFormatter {
        val key = "$pattern|${locale.localeIdentifier}"

        val formatter = IosFormatterCache.formatters.getOrPut(key) {
            NSDateFormatter().apply {
                this.locale = this@IosLocaleDateTimeFormatter.locale
                this.dateFormat = this@IosLocaleDateTimeFormatter.pattern
            }
        }

        // Update the timezone on the cached instance.
        // This is safe because this instance belongs ONLY to the current thread.
        formatter.timeZone = timeZone.toNSTimeZone()
        return formatter
    }

    @OptIn(ExperimentalTime::class)
    override fun format(instant: Instant, timeZone: TimeZone): String {
        return getFormatter(timeZone).stringFromDate(instant.toNSDate())
    }

    override fun format(localDateTime: LocalDateTime, timeZone: TimeZone): String {
        val instant = localDateTime.toInstant(timeZone)
        return getFormatter(timeZone).stringFromDate(instant.toNSDate())
    }

    override fun format(localDate: LocalDate, timeZone: TimeZone): String {
        val instant = localDate.atStartOfDayIn(timeZone)
        return getFormatter(timeZone).stringFromDate(instant.toNSDate())
    }
}

// Helper: Convert TimeZone to NSTimeZone
private fun TimeZone.toNSTimeZone(): NSTimeZone {
    val nsTimeZone = NSTimeZone.timeZoneWithName(this.id)
    if (nsTimeZone != null) return nsTimeZone
    return NSTimeZone.localTimeZone
}