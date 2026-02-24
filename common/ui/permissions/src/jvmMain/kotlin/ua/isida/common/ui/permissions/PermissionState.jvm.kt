package ua.isida.common.ui.permissions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember

/**
 * JVM/Desktop implementation of [rememberPermissionState].
 * Since Desktop platforms don't have a standardized runtime permission system for these types, 
 * it defaults to [PermissionStatus.Granted].
 */
@Composable
actual fun rememberPermissionState(
    vararg permissions: PermissionType,
    onPermissionResult: ((Boolean) -> Unit)?
): PermissionState {
    return remember { JvmPermissionState(permissions.toList()) }
}

/**
 * Emulated permission state for JVM that auto-grants all requests.
 */
@Stable
private class JvmPermissionState(
    override val permissions: List<PermissionType>,
) : PermissionState {

    override val status: PermissionStatus = PermissionStatus.Granted

    override fun launchPermissionRequest() {
        println("JVM: Launching permission request for $permissions (Auto-Granted)")
    }
}