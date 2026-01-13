package com.whoppah.common.compose.input

import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.insert

/**
 * An [OutputTransformation] for formatting IBANs by inserting a space every 4 characters.
 *
 * This transformation improves the readability of long IBAN strings in a TextField.
 * For example, an input of "NL91ABNA0417164300" will be displayed as "NL91 ABNA 0417 1643 00".
 *
 * It preserves the correct cursor position by incrementally building the transformed string
 * and letting the [TextFieldBuffer] generate the appropriate `OffsetMapping`.
 */
data object IbanOutputTransformation : OutputTransformation {

    override fun TextFieldBuffer.transformOutput() {
        // We iterate through the original text's indices to find insertion points.
        // A space is required after every 4th character.
        for (i in 1 until originalText.length) {
            if (i % 4 == 0) {
                // We must insert the space at the correct, transformed offset.
                // For example, the space after the 8th character of the original text
                // must be inserted after the 9th character of the transformed text
                // (because one space has already been inserted after the 4th character).
                insert(originalToTransformed(i), " ")
            }
        }
    }

    /**
     * Calculates the corresponding offset in the transformed text (which includes spaces)
     * for a given offset in the original, unformatted text.
     *
     * This implementation replaces an iterative loop with a more efficient O(1)
     * mathematical calculation.
     *
     * @param offset The character offset in the original, unformatted text.
     * @return The corresponding character offset in the transformed, formatted text.
     */
    private fun originalToTransformed(offset: Int): Int {
        // The number of spaces to add is the number of 4-character groups
        // that precede the given offset.
        //
        // The loop in the original implementation added a space for each `i` where
        // `i > 0`, `i < offset`, and `i % 4 == 0`. This is equivalent to calculating
        // the number of multiples of 4 in the range [1, offset - 1].
        // Using integer division, this can be calculated as `(offset - 1) / 4`.
        //
        // Example:
        // - offset = 4: (4 - 1) / 4 = 0 spaces. Transformed offset = 4 + 0 = 4.
        // - offset = 5: (5 - 1) / 4 = 1 space.  Transformed offset = 5 + 1 = 6.
        // - offset = 9: (9 - 1) / 4 = 2 spaces. Transformed offset = 9 + 2 = 11.
        //
        // The final transformed offset is the original offset plus the number of spaces.
        // Note: In Kotlin, integer division `(offset - 1) / 4` correctly results in 0
        // for `offset` values from 0 to 4, so no special bounds checking is needed.
        val spaces = if (offset > 0) (offset - 1) / 4 else 0
        return offset + spaces
    }
}