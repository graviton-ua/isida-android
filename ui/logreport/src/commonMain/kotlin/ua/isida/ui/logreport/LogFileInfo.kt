package ua.isida.ui.logreport

import androidx.compose.runtime.Immutable
import kotlinx.io.files.Path

@Immutable
internal data class LogFileInfo(
    val path: Path,
    val name: String,
    val sizeBytes: Long,
    val isSelected: Boolean = false
) {
    val sizeFormatted: String
        get() {
            val kb = sizeBytes / 1024.0
            return if (kb < 1024) {
                "${kb.toLong()} KB"
            } else {
                "${(kb / 1024.0).toLong()} MB"
            }
        }
}