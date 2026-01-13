package com.whoppah.common.resources

import kotlinx.datetime.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.time.toJavaInstant
import java.time.format.DateTimeFormatter as JavaDateTimeFormatter

// 1. Thread-safe cache for Android
private val androidFormatterCache = ConcurrentHashMap<String, LocaleDateTimeFormatter>()

internal actual fun getPlatformDateTimeFormatter(pattern: String, locale: PlatformLocale): LocaleDateTimeFormatter {
    val key = "$pattern-${locale.toLanguageTag()}"
    return androidFormatterCache.getOrPut(key) {
        AndroidLocaleDateTimeFormatter(pattern, locale)
    }
}

/**
 * The JVM/Android implementation of the multiplatform date formatter.
 * It wraps an instance of `java.time.format.DateTimeFormatter`.
 */
private class AndroidLocaleDateTimeFormatter(
    pattern: String,
    locale: PlatformLocale,
) : LocaleDateTimeFormatter {

    // Java DateTimeFormatter is immutable and thread-safe.
    private val javaFormatter: JavaDateTimeFormatter = JavaDateTimeFormatter.ofPattern(pattern, locale)

    @OptIn(ExperimentalTime::class)
    override fun format(instant: Instant, timeZone: TimeZone): String {
        val zonedDateTime = instant.toJavaInstant().atZone(timeZone.toJavaZoneId())
        return javaFormatter.format(zonedDateTime)
    }

    override fun format(localDateTime: LocalDateTime, timeZone: TimeZone): String {
        return javaFormatter.format(localDateTime.toJavaLocalDateTime())
    }

    override fun format(localDate: LocalDate, timeZone: TimeZone): String {
        return javaFormatter.format(localDate.toJavaLocalDate())
    }
}