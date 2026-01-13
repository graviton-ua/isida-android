package com.whoppah.common.permissions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember

@Composable
actual fun rememberPermissionState(
    permission: PermissionType,
    onPermissionResult: ((Boolean) -> Unit)?
): PermissionState {
    return remember { JvmPermissionState(permission) }
}

@Stable
private class JvmPermissionState(
    override val permission: PermissionType
) : PermissionState {

    // Default to Granted for Desktop previews
    override val status: PermissionStatus = PermissionStatus.Granted

    override fun launchPermissionRequest() {
        // No-op on JVM
        println("JVM: Launching permission request for $permission (Auto-Granted)")
    }
}