package ua.isida.common.ui.compose.input

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.ui.text.input.KeyboardType

/**
 * An [InputTransformation] that filters text input to allow only valid price characters.
 * It can be configured to allow or disallow a decimal separator.
 *
 * This transformation validates the entire proposed text after each change to ensure
 * correctness (e.g., preventing multiple decimal points).
 *
 * @param allowDecimals If true, a single decimal separator ('.' or ',') is permitted.
 *                      If false, only digits are allowed.
 */
class PriceInputTransformation(
    private val allowDecimals: Boolean
) : InputTransformation {

    private val decimalSeparators = setOf('.', ',')

    // Override keyboardOptions to suggest the correct keyboard type to the system.
    override val keyboardOptions: KeyboardOptions
        get() = KeyboardOptions(
            keyboardType = if (allowDecimals) KeyboardType.Decimal else KeyboardType.Number
        )

    override fun TextFieldBuffer.transformInput() {
        val newText = asCharSequence()

        // If the proposed text is not a valid price prefix, reject the entire change.
        if (!isValidPricePrefix(newText)) {
            revertAllChanges()
        }
    }

    private fun isValidPricePrefix(text: CharSequence): Boolean {
        if (text.isEmpty()) return true

        val contentToCheck = if (text.first() == '-') text.substring(1) else text

        if (!allowDecimals) {
            return contentToCheck.all { it.isDigit() }
        }

        // If decimals are allowed:
        // 1. Count the number of decimal separators. More than one is invalid.
        val separatorCount = contentToCheck.count { it in decimalSeparators }
        if (separatorCount > 1) {
            return false
        }

        // 2. Ensure all other characters are digits.
        return contentToCheck.all { it.isDigit() || it in decimalSeparators }
    }
}