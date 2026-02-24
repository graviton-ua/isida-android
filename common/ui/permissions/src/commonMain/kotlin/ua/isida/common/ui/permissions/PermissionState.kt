package ua.isida.common.ui.permissions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

/**
 * Types of permissions that can be requested across platforms.
 */
enum class PermissionType {
    STORAGE,
    WRITE_STORAGE,
    LOCATION,
    COARSE_LOCATION,
    BLUETOOTH_SCAN,
    BLUETOOTH_CONNECT,
    REMOTE_NOTIFICATION,
}

/**
 * Represents the current status of a permission.
 */
@Stable
sealed interface PermissionStatus {
    /** Permission is granted by the user. */
    data object Granted : PermissionStatus
    /** Permission is denied. [shouldShowRationale] indicates if we should explain why it's needed. */
    data class Denied(val shouldShowRationale: Boolean) : PermissionStatus
}

/**
 * Extension to check if the status is [PermissionStatus.Granted].
 */
val PermissionStatus.isGranted: Boolean
    get() = this is PermissionStatus.Granted

/**
 * Handle for checking and requesting permissions.
 */
@Stable
interface PermissionState {
    /** List of permissions being managed. */
    val permissions: List<PermissionType>
    /** Current aggregated status of the permissions. */
    val status: PermissionStatus

    /**
     * Triggers the system permission request dialog.
     */
    fun launchPermissionRequest()
}

/**
 * Remembers and provides the [PermissionState] for the given [permissions].
 *
 * @param permissions One or more permissions to manage.
 * @param onPermissionResult Optional callback invoked when the request completes.
 * @return A stable [PermissionState] instance.
 * Example:
 * ```
 * val state = rememberPermissionState(PermissionType.LOCATION)
 * Button(onClick = { state.launchPermissionRequest() }) { ... }
 * ```
 */
@Composable
expect fun rememberPermissionState(
    vararg permissions: PermissionType,
    onPermissionResult: ((Boolean) -> Unit)? = null
): PermissionState