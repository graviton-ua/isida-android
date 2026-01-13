package com.whoppah.common.resources

enum class CurrencyFormat {
    NO_DECIMAL,
    DECIMAL,
    ALLOW_DECIMAL,
    DASH // Special format that replaces .00 with .-
}

/**
 * An expected interface for a platform-specific number formatter.
 * This abstracts away `DecimalFormat` (JVM) and `NSNumberFormatter` (iOS).
 */
internal expect class KmpNumberFormatter {
    fun format(value: Double): String
    fun getDecimalSeparator(): Char
    fun getCurrencySymbol(): String
}

/**
 * An expected factory function.
 * Implementations MUST handle caching (e.g., using ThreadLocal) to ensure performance.
 */
internal expect fun getKmpNumberFormatter(
    locale: PlatformLocale,
    type: CurrencyFormat,
    currencyCode: String
): KmpNumberFormatter

/**
 * Formats a Double value as a currency string.
 *
 * @param format The desired currency format style.
 * @param locale The locale to use for formatting.
 * @param currencyCode The ISO 4217 currency code (e.g., "EUR", "USD").
 * @param emptyOnNull If true, returns a blank currency string for null input. Otherwise, formats 0.0.
 * @return The formatted currency string.
 */
fun Double?.asCurrency(
    format: CurrencyFormat = CurrencyFormat.ALLOW_DECIMAL,
    locale: PlatformLocale = platformLocaleGetDefault(),
    currencyCode: String = "EUR",
    emptyOnNull: Boolean = false
): String {
    val formatter = getKmpNumberFormatter(locale, format, currencyCode)

    val valueToFormat = this ?: if (emptyOnNull) return "${formatter.getCurrencySymbol()} " else 0.0

    var formattedString = formatter.format(valueToFormat)

    if (format == CurrencyFormat.DASH) {
        val decimalSeparator = formatter.getDecimalSeparator()
        formattedString = formattedString.replace("${decimalSeparator}00", "${decimalSeparator}-")
    }

    return formattedString
}