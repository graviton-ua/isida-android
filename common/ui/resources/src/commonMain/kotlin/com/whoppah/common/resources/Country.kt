package com.whoppah.common.resources

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmStatic

// We declare an expected function. Each platform will provide its own implementation.
internal expect fun getCountryDisplayName(countryCode: String): String

@Serializable
enum class Country(
    val code: String,
    val phoneCodeInt: Int
) {
    AT("AT", 43),   // Austria
    BE("BE", 32),   // Belgium
    CZ("CZ", 420),  // Czech Republic
    DE("DE", 49),   // Germany
    DK("DK", 45),   // Denmark
    ES("ES", 34),   // Spain
    FR("FR", 33),   // France
    HU("HU", 36),   // Hungary
    IT("IT", 39),   // Italy
    LU("LU", 352),  // Luxembourg
    MC("MC", 377),  // Monaco
    NL("NL", 31),   // Netherlands
    PL("PL", 48),   // Poland
    PT("PT", 351),  // Portugal
    RO("RO", 40),   // Romania
    SE("SE", 46),   // Sweden
    SI("SI", 386),  // Slovenia
    SK("SK", 421),  // Slovakia
    CH("CH", 41),   // Switzerland
    GB("GB", 44);   // United Kingdom


    val phoneCode: String = "+$phoneCodeInt"
    val displayName: String get() = getCountryDisplayName(code)
    val flagISOCode: String = code.asFlag()

    fun getDisplayName(locale: PlatformLocale): String = getCountryDisplayName(code)


    companion object {
        @JvmStatic fun findByCountryCode(code: CharSequence?): Country? = findByCountryCode(code?.toString())
        @JvmStatic fun findByCountryCode(code: String?): Country? = entries.firstOrNull { it.code.equals(code, ignoreCase = true) }
        @JvmStatic fun findByPhoneCode(code: Int): Country? = entries.firstOrNull { it.phoneCodeInt == code }
        @JvmStatic fun findByLocale(locale: PlatformLocale): Country? = entries.firstOrNull { it.code.equals(locale.countryCode, ignoreCase = true) }
    }

    /**
     * Converts a two-letter ISO 3166-1 alpha-2 country code into a flag emoji.
     * This implementation is KMP-compatible and uses only the Kotlin standard library.
     */
    private fun String.asFlag(): String {
        // A = 0x1F1E6, B = 0x1F1E7, ... Z = 0x1F1FF
        val flagOffset = 0x1F1E6
        val asciiOffset = 'A'.code

        // Ensure the code is two uppercase letters
        if (this.length != 2 || this[0] !in 'A'..'Z' || this[1] !in 'A'..'Z') {
            return "🏳️" // Return a default flag for invalid codes
        }

        val firstChar = this[0].code - asciiOffset + flagOffset
        val secondChar = this[1].code - asciiOffset + flagOffset

        return buildString {
            appendCodePoint(firstChar)
            appendCodePoint(secondChar)
        }
    }

    /**
     * Extension to handle 32-bit Unicode Code Points in Common Kotlin.
     * Java has this built-in, but Common Kotlin StringBuilder does not.
     */
    private fun StringBuilder.appendCodePoint(codePoint: Int) {
        if (codePoint in 0..0xFFFF) {
            // Fits in a standard 16-bit Char (BMP)
            append(codePoint.toChar())
        } else {
            // Calculates Surrogate Pair for Emoji (Supplementary Plane)
            // See: https://en.wikipedia.org/wiki/UTF-16#U+10000_to_U+10FFFF
            val code = codePoint - 0x10000
            val high = (code shr 10) + 0xD800
            val low = (code and 0x3FF) + 0xDC00
            append(high.toChar())
            append(low.toChar())
        }
    }
}