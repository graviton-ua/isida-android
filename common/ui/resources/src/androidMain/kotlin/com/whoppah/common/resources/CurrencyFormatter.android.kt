package com.whoppah.common.resources

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

internal actual class KmpNumberFormatter(private val formatter: DecimalFormat) {
    actual fun format(value: Double): String = formatter.format(value)
    actual fun getDecimalSeparator(): Char = formatter.decimalFormatSymbols.decimalSeparator
    actual fun getCurrencySymbol(): String = formatter.currency?.symbol ?: ""
}

// Data class for cache key to ensure correct equals/hashCode
private data class FormatterKey(
    val localeLanguage: String, // Storing minimal info needed for key
    val type: CurrencyFormat,
    val currencyCode: String
)

// ThreadLocal ensures each thread has its own MutableMap.
// No synchronization/locking needed.
private val threadLocalCache = object : ThreadLocal<MutableMap<FormatterKey, KmpNumberFormatter>>() {
    override fun initialValue(): MutableMap<FormatterKey, KmpNumberFormatter> = mutableMapOf()
}

internal actual fun getKmpNumberFormatter(
    locale: PlatformLocale,
    type: CurrencyFormat,
    currencyCode: String
): KmpNumberFormatter {
    val key = FormatterKey(locale.language, type, currencyCode)

    // Get the map specific to THIS thread
    val cache = threadLocalCache.get()!!

    return cache.getOrPut(key) {
        createAndroidFormatter(locale, type, currencyCode)
    }
}

private fun createAndroidFormatter(
    locale: PlatformLocale,
    type: CurrencyFormat,
    currencyCode: String
): KmpNumberFormatter {
    val format = (NumberFormat.getCurrencyInstance(Locale(locale.language)) as DecimalFormat).apply {
        currency = Currency.getInstance(currencyCode)
        isGroupingUsed = true
        roundingMode = RoundingMode.HALF_EVEN

        when (type) {
            CurrencyFormat.NO_DECIMAL -> {
                minimumFractionDigits = 0
                maximumFractionDigits = 0
            }

            CurrencyFormat.DECIMAL, CurrencyFormat.DASH -> {
                minimumFractionDigits = 2
                maximumFractionDigits = 2
            }

            CurrencyFormat.ALLOW_DECIMAL -> {
                minimumFractionDigits = 0
                maximumFractionDigits = 2
            }
        }
    }
    return KmpNumberFormatter(format)
}