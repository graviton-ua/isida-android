package com.whoppah.common.resources

import kotlinx.datetime.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.time.toJavaInstant
import java.time.format.DateTimeFormatter as JavaDateTimeFormatter

/**
 * Thread-safe cache for the JVM.
 * ConcurrentHashMap is strictly thread-safe and non-blocking for reads.
 */
private val jvmFormatterCache = ConcurrentHashMap<String, LocaleDateTimeFormatter>()

internal actual fun getPlatformDateTimeFormatter(pattern: String, locale: PlatformLocale): LocaleDateTimeFormatter {
    val key = "$pattern-${locale.toLanguageTag()}"
    return jvmFormatterCache.getOrPut(key) {
        JvmLocaleDateTimeFormatter(pattern, locale)
    }
}

/**
 * Wrapper around java.time.format.DateTimeFormatter.
 * Java's DateTimeFormatter is immutable and thread-safe, so we can hold a reference to it safely.
 */
private class JvmLocaleDateTimeFormatter(
    pattern: String,
    locale: PlatformLocale,
) : LocaleDateTimeFormatter {

    private val javaFormatter: JavaDateTimeFormatter = JavaDateTimeFormatter.ofPattern(pattern, locale)

    @OptIn(ExperimentalTime::class)
    override fun format(instant: Instant, timeZone: TimeZone): String {
        // Convert Kotlin Instant -> Java Instant -> ZonedDateTime
        val zonedDateTime = instant.toJavaInstant().atZone(timeZone.toJavaZoneId())
        return javaFormatter.format(zonedDateTime)
    }

    override fun format(localDateTime: LocalDateTime, timeZone: TimeZone): String {
        // Java's LocalDateTime is timezone-agnostic, simply format it.
        return javaFormatter.format(localDateTime.toJavaLocalDateTime())
    }

    override fun format(localDate: LocalDate, timeZone: TimeZone): String {
        return javaFormatter.format(localDate.toJavaLocalDate())
    }
}