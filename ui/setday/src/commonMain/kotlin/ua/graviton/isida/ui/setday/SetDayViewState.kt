package ua.graviton.isida.ui.setday

import androidx.compose.runtime.Stable

@Stable
data class SetDayViewState(
    val waitingForData: Boolean = true,
)

sealed interface SetDayViewEvent {
    // Means our command already been successfully sent to device
    object Sent : SetDayViewEvent
}