package ua.graviton.isida.ui.home.program

import androidx.compose.runtime.Immutable

@Immutable
data class ProgramViewState(
    val deviceConnected: Boolean = false,
    val selectedTable: Int = 1,
    val isLoading: Boolean = false,
    val table: Table? = null,
    val showResetDialog: Boolean = false,
    val availablePresets: List<ProgramPreset> = emptyList(),
)
