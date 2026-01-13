package com.whoppah.common.compose.input

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
 */
class DateInputTransformation(
    private val limit: Int,
) : InputTransformation {

    // Override keyboardOptions to suggest the correct keyboard type to the system.
    override val keyboardOptions: KeyboardOptions
        get() = KeyboardOptions(keyboardType = KeyboardType.Number)

    override fun TextFieldBuffer.transformInput() {
        val newText = asCharSequence()

        // If the proposed text is not a valid price prefix, reject the entire change.
        if (!isValidPricePrefix(newText) || length > limit) {
            revertAllChanges()
        }
    }

    private fun isValidPricePrefix(text: CharSequence): Boolean {
        if (text.isEmpty()) return true

        return text.all { it.isDigit() }
    }
}

val DefaultDateInputTransformation = DateInputTransformation(limit = 8)