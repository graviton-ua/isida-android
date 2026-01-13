package com.whoppah.common.resources

import platform.Foundation.*
import kotlin.native.concurrent.ThreadLocal

internal actual class KmpNumberFormatter(private val formatter: NSNumberFormatter) {
    actual fun format(value: Double): String {
        return formatter.stringFromNumber(NSNumber(value)) ?: ""
    }

    actual fun getDecimalSeparator(): Char = formatter.decimalSeparator.firstOrNull() ?: '.'
    actual fun getCurrencySymbol(): String = formatter.currencySymbol
}

private data class FormatterKey(
    val localeId: String,
    val type: CurrencyFormat,
    val currencyCode: String
)

// In Kotlin Native, @ThreadLocal on an object makes it unique per thread.
@ThreadLocal
private object IosCache {
    val map = mutableMapOf<FormatterKey, KmpNumberFormatter>()
}

internal actual fun getKmpNumberFormatter(
    locale: PlatformLocale,
    type: CurrencyFormat,
    currencyCode: String
): KmpNumberFormatter {
    val key = FormatterKey(locale.localeIdentifier, type, currencyCode)

    return IosCache.map.getOrPut(key) {
        createIosFormatter(locale, type, currencyCode)
    }
}

private fun createIosFormatter(
    locale: PlatformLocale,
    type: CurrencyFormat,
    currencyCode: String
): KmpNumberFormatter {
    val formatter = NSNumberFormatter().apply {
        setLocale(locale)
        setNumberStyle(NSNumberFormatterCurrencyStyle)
        setCurrencyCode(currencyCode)
        setUsesGroupingSeparator(true)
        roundingMode = NSNumberFormatterRoundHalfEven

        when (type) {
            CurrencyFormat.NO_DECIMAL -> {
                minimumFractionDigits = 0u
                maximumFractionDigits = 0u
            }

            CurrencyFormat.DECIMAL, CurrencyFormat.DASH -> {
                minimumFractionDigits = 2u
                maximumFractionDigits = 2u
            }

            CurrencyFormat.ALLOW_DECIMAL -> {
                minimumFractionDigits = 0u
                maximumFractionDigits = 2u
            }
        }
    }
    return KmpNumberFormatter(formatter)
}