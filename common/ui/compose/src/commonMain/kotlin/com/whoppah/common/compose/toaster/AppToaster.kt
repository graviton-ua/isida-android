package com.whoppah.common.compose.toaster

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.whoppah.common.compose.theme.WhoppahTheme
import com.whoppah.common.compose.toaster.Toaster.Duration
import com.whoppah.common.compose.toaster.Toaster.ToastType
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

internal object EventBus {
    // Use an UNLIMITED buffer to ensure no events are lost
    // if the UI is not ready to collect them.
    // This is crucial for "fire-and-forget" events.
    private val _events = Channel<Event>(Channel.UNLIMITED)
    val events: Flow<Event> = _events.receiveAsFlow()

    fun sendEvent(event: Event) {
        // trySend is non-suspending and can be called from any
        // thread, which is perfect for ViewModels.
        _events.trySend(event)
    }

    internal data class Event(
        val message: String,
        val duration: Duration,
        val action: SnackbarAction? = null,
        val type: ToastType,
    )
}

/**
 * A data class to hold the action's label (title) and
 * the non-composable callback function to execute.
 *
 */
internal data class SnackbarAction(
    val title: String,
    val onActionPress: () -> Unit // The callback
)


// This is the default implementation that will be injected or used.
object AppToaster : Toaster {

    override fun showToast(
        message: String,
        duration: Duration,
        actionLabel: String?,
        onAction: (() -> Unit)?,
        type: ToastType,
    ) {
        // Only create an action object if BOTH the label and
        // the callback function are provided.
        val snackbarAction = if (actionLabel != null && onAction != null) {
            SnackbarAction(title = actionLabel, onActionPress = onAction)
        } else {
            null
        }

        EventBus.sendEvent(
            EventBus.Event(message = message, duration = duration, action = snackbarAction, type = type)
        )
    }
}

internal data class AppSnackbarVisuals(
    override val message: String,
    override val actionLabel: String?,
    override val duration: SnackbarDuration,
    val type: ToastType,
    override val withDismissAction: Boolean = false
) : SnackbarVisuals

@Suppress("ComposableNaming")
@Composable
fun rememberToaster(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    LaunchedEffect(snackbarHostState, EventBus) {
        EventBus.events.collect { event ->

            val visuals = AppSnackbarVisuals(
                message = event.message,
                actionLabel = event.action?.title,
                duration = event.duration.toSnackbarDuration(),
                type = event.type
            )

            val result = snackbarHostState.showSnackbar(visuals)

            // Check the result of the suspend function
            if (result == SnackbarResult.ActionPerformed) {
                // If the user clicked the action, execute the
                // callback function that we received in the event.
                event.action?.onActionPress?.invoke()
            }
        }
    }
}

// Helper to convert our enum to Material's enum
private fun Duration.toSnackbarDuration(): SnackbarDuration =
    when (this) {
        Duration.SHORT -> SnackbarDuration.Short
        Duration.LONG -> SnackbarDuration.Long
    }

@Composable
fun AppToasterHost(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) = SnackbarHost(
    hostState = snackbarHostState,
    modifier = modifier,
) { data ->
    val visuals = data.visuals as? AppSnackbarVisuals

    val containerColor = when (visuals?.type) {
        ToastType.SUCCESS -> WhoppahTheme.colors.toastSuccessContainer
        ToastType.ERROR -> WhoppahTheme.colors.toastErrorContainer
        else -> WhoppahTheme.colors.toastContainer
    }

    val contentColor = when (visuals?.type) {
        ToastType.SUCCESS -> WhoppahTheme.colors.toastSuccessContent
        ToastType.ERROR -> WhoppahTheme.colors.toastErrorContent
        else -> WhoppahTheme.colors.toastContent
    }

    val actionColor = when (visuals?.type) {
        ToastType.SUCCESS -> WhoppahTheme.colors.toastSuccessAction
        ToastType.ERROR -> WhoppahTheme.colors.toastErrorAction
        else -> WhoppahTheme.colors.toastAction
    }

    Snackbar(
        snackbarData = data,
        actionOnNewLine = true,
        containerColor = containerColor,
        contentColor = contentColor,
        actionColor = actionColor,
        dismissActionContentColor = contentColor
    )
}