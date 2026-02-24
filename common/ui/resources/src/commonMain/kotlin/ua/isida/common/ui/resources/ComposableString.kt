package ua.isida.common.ui.resources

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable

@Immutable
class ComposableString(
    val key: Any?,
    val text: @Composable () -> String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ComposableString) return false
        return key == other.key
    }

    override fun hashCode(): Int = key.hashCode()

    companion object {
        fun composableString(key: Any?, text: @Composable () -> String) = ComposableString(key, text)
        fun composableString(vararg keys: Any?, text: @Composable () -> String) = ComposableString(keys.toList(), text)
    }
}