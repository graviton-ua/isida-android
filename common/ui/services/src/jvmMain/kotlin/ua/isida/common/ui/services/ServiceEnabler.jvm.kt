package ua.isida.common.ui.services

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import ua.isida.common.ui.services.ServiceEnabler
import ua.isida.common.ui.services.ServiceType

/**
 * JVM/Desktop implementation of [rememberServiceEnabler].
 * Since Desktop platforms don't have a standardized cross-platform API for toggling system services, 
 * this implementation assumes the hardware is present/manual and immediately calls [onEnabled].
 */
@Composable
actual fun rememberServiceEnabler(
    type: ServiceType,
    onEnabled: () -> Unit,
    onDenied: () -> Unit
): ServiceEnabler {
    return remember {
        object : ServiceEnabler {
            override fun requestEnable() {
                // On Desktop, we cannot easily invoke a system dialog to enable Bluetooth/GPS.
                // We just proceed and let the scanner fail if hardware is missing.
                println("JVM: $type enable requested. Assuming manual user action or hardware presence.")
                onEnabled()
            }
        }
    }
}