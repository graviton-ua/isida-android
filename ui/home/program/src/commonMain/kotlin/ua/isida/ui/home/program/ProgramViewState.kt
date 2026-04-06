package ua.isida.ui.home.program

import androidx.compose.runtime.Immutable

@Immutable
internal data class ProgramViewState(
    val deviceConnected: Boolean = false,
    val selectedTable: Int = 1,
    val isLoading: Boolean = false,
    val table: Table? = null,
    val showResetDialog: Boolean = false,
    val showClockDialog: Boolean = false,
)
