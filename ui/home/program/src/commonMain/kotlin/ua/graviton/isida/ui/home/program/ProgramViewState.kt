package ua.graviton.isida.ui.home.program

import androidx.compose.runtime.Immutable

@Immutable
data class ProgramViewState(
    val isLoading: Boolean = false,
    val items: List<ProgramItem> = emptyList(),
) {

    @Immutable
    data class ProgramItem(
        val day: Int,
        val t0: Float,
        val t1: Float,
        val rh: Int,
        val flp: Int,
        val tr: Int,
        val cl: Int,
    )

    companion object {
        val Empty = ProgramViewState()
    }
}
