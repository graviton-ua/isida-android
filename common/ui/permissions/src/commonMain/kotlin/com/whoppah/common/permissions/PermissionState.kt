package com.whoppah.common.permissions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

/**
 * Your own enum to replace Moko's in common code.
 * Add other permissions here as needed.
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

@Stable
sealed interface PermissionStatus {
    data object Granted : PermissionStatus
    data class Denied(val shouldShowRationale: Boolean) : PermissionStatus
}

val PermissionStatus.isGranted: Boolean
    get() = this is PermissionStatus.Granted

@Stable
interface PermissionState {
    val permissions: List<PermissionType>
    val status: PermissionStatus

    fun launchPermissionRequest()
}

/**
 * The expect function acting as the bridge.
 */
@Composable
expect fun rememberPermissionState(
    vararg permissions: PermissionType,
    onPermissionResult: ((Boolean) -> Unit)? = null
): PermissionState