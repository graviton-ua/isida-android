package com.whoppah.common.compose.input

import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.insert

/**
 * An [OutputTransformation] for formatting text with a mask, such as "###-###".
 *
 * This transformation applies a mask to user input, inserting the mask's special characters
 * (e.g., hyphens) as the user types.
 * For example, with a mask of "###-###" and an input of "123456", the output will be "123-456".
 *
 * @param mask The mask pattern to apply.
 * @param placeholder The character in the mask that represents user-typed input.
 */
data class MaskedOutputTransformation(
    private val mask: String = "###-###",
    private val placeholder: Char = '#',
) : OutputTransformation {

    override fun TextFieldBuffer.transformOutput() {
        var originalTextIndex = 0
        var maskIndex = 0

        // We continue as long as there are characters in the original text to process
        // and we haven't exhausted the mask.
        while (originalTextIndex < originalText.length && maskIndex < mask.length) {
            // If the current character in the mask is not a placeholder,
            // it's a literal character (like '-') that needs to be inserted.
            if (mask[maskIndex] != placeholder) {
                // We calculate the correct position in the transformed text to insert the literal.
                // This position is the current mask index.
                insert(maskIndex, mask[maskIndex].toString())
                // We only advance the mask index, as we haven't consumed a character
                // from the original text yet.
                maskIndex++
            } else {
                // If the mask character is a placeholder, it means this position is for
                // a user-typed character. We don't need to do anything here because the
                // character from originalText is already in place. We just advance both indices.
                originalTextIndex++
                maskIndex++
            }
        }
    }
}