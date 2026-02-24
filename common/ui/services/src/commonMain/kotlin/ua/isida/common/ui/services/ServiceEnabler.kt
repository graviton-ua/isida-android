package ua.isida.common.ui.services

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

/**
 * Supported hardware services that can be toggled via the system.
 */
enum class ServiceType {
    /** Bluetooth adapter. */
    BLUETOOTH,
    /** GPS/Location services. */
    LOCATION // GPS
}

/**
 * Handle for requesting a hardware service to be enabled.
 */
@Stable
interface ServiceEnabler {
    /**
     * Initiates the request to enable the service.
     * On Android: Shows a system dialog.
     * On JVM: Might log or do nothing.
     */
    fun requestEnable()
}

/**
 * Remembers and provides a [ServiceEnabler] handle to request enabling a system service.
 *
 * @param type The service to enable (Bluetooth or Location).
 * @param onEnabled Called when the service is successfully enabled (or was already on).
 * @param onDenied Called if the user refuses the dialog or an error occurs.
 * Example:
 * ```
 * val enabler = rememberServiceEnabler(ServiceType.BLUETOOTH, onEnabled = { startScan() })
 * Button(onClick = { enabler.requestEnable() }) { ... }
 * ```
 */
@Composable
expect fun rememberServiceEnabler(
    type: ServiceType,
    onEnabled: () -> Unit,
    onDenied: () -> Unit = {}
): ServiceEnabler