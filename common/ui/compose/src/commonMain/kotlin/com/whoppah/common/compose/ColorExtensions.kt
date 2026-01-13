package com.whoppah.common.compose

import androidx.compose.ui.graphics.Color
import kotlin.math.pow
import kotlin.math.roundToInt

/**
 * Calculates the relative luminance of this color.
 *
 * @return A value between 0.0 (black) and 1.0 (white).
 */
fun Color.calculateLuminance(): Float {
    // This formula is based on the WCAG 2.0 definition for relative luminance:
    // https://www.w3.org/TR/WCAG20/#relativeluminancedef

    val r = this.red
    val g = this.green
    val b = this.blue

    // Convert from sRGB to linear RGB
    val rLinear = if (r <= 0.03928f) r / 12.92f else ((r + 0.055f) / 1.055f).pow(2.4f)
    val gLinear = if (g <= 0.03928f) g / 12.92f else ((g + 0.055f) / 1.055f).pow(2.4f)
    val bLinear = if (b <= 0.03928f) b / 12.92f else ((b + 0.055f) / 1.055f).pow(2.4f)

    // Calculate luminance using the REC. 709 primaries
    return 0.2126f * rLinear + 0.7152f * gLinear + 0.0722f * bLinear
}

/**
 * Calculates the relative luminance of a color represented as an ARGB Int.
 *
 * @return A value between 0.0 (black) and 1.0 (white).
 */
fun Long.calculateLuminance(): Float {
    // Extract R, G, B and normalize to 0.0-1.0
    val r = ((this shr 16) and 0xFF) / 255.0f
    val g = ((this shr 8) and 0xFF) / 255.0f
    val b = (this and 0xFF) / 255.0f

    // Convert from sRGB to linear RGB
    val rLinear = if (r <= 0.03928f) r / 12.92f else ((r + 0.055f) / 1.055f).pow(2.4f)
    val gLinear = if (g <= 0.03928f) g / 12.92f else ((g + 0.055f) / 1.055f).pow(2.4f)
    val bLinear = if (b <= 0.03928f) b / 12.92f else ((b + 0.055f) / 1.055f).pow(2.4f)

    // Calculate luminance
    return 0.2126f * rLinear + 0.7152f * gLinear + 0.0722f * bLinear
}


/**
 * Converts a Compose Color to a standard hex string format #RRGGBB.
 * This function ignores the alpha component of the color.
 *
 * @return The hex string representation (e.g., "#1A73E8").
 */
fun Color.toRgbHexString(): String {
    // Multiply float components by 255 and round to the nearest integer
    val red = (this.red * 255).roundToInt().toHex2()
    val green = (this.green * 255).roundToInt().toHex2()
    val blue = (this.blue * 255).roundToInt().toHex2()
    
    return "#$red$green$blue"
}

/**
 * Converts a Compose Color to a hex string format #AARRGGBB,
 * including the alpha channel.
 *
 * @return The hex string representation with alpha (e.g., "#FF1A73E8").
 */
fun Color.toArgbHexString(): String {
    // Multiply float components by 255 and round to the nearest integer
    val alpha = (this.alpha * 255).roundToInt().toHex2()
    val red = (this.red * 255).roundToInt().toHex2()
    val green = (this.green * 255).roundToInt().toHex2()
    val blue = (this.blue * 255).roundToInt().toHex2()

    return "#$alpha$red$green$blue"
}

/**
 * Helper extension to convert an Int to a 2-digit hex string.
 * Replaces: String.format("%02x", this)
 */
private fun Int.toHex2(): String {
    // 1. toString(16) converts to hex (e.g., 255 -> "ff", 10 -> "a")
    // 2. padStart(2, '0') ensures it's at least 2 chars (e.g., "a" -> "0a")
    return this.toString(16).padStart(2, '0')
}