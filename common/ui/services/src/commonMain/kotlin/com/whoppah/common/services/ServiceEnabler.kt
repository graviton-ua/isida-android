package com.whoppah.common.services

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

enum class ServiceType {
    BLUETOOTH,
    LOCATION // GPS
}

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
 * Returns a handle to request enabling a system service.
 *
 * @param type The service to enable (Bluetooth or Location).
 * @param onEnabled Called when the service is successfully enabled (or was already on).
 * @param onDenied Called if the user refuses the dialog or an error occurs.
 */
@Composable
expect fun rememberServiceEnabler(
    type: ServiceType,
    onEnabled: () -> Unit,
    onDenied: () -> Unit = {}
): ServiceEnabler