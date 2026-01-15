package com.whoppah.common.permissions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember

@Composable
actual fun rememberPermissionState(
    vararg permissions: PermissionType,
    onPermissionResult: ((Boolean) -> Unit)?
): PermissionState {
    return remember { JvmPermissionState(permissions.toList()) }
}

@Stable
private class JvmPermissionState(
    override val permissions: List<PermissionType>,
) : PermissionState {

    override val status: PermissionStatus = PermissionStatus.Granted

    override fun launchPermissionRequest() {
        println("JVM: Launching permission request for $permissions (Auto-Granted)")
    }
}