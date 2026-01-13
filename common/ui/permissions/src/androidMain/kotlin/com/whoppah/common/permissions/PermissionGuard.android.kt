package com.whoppah.common.permissions

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
actual fun rememberWriteStorageAction(onAction: () -> Unit): () -> Unit {
    // Logic: Android Q (10) and above do not need permission for DownloadManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        return onAction
    }

    // Logic: Android 9 and below need explicit permission
    val permissionState = rememberPermissionState(PermissionType.WRITE_STORAGE) { success ->
        if (success) onAction()
    }

    return remember(permissionState, onAction) {
        {
            if (permissionState.status == PermissionStatus.Granted) {
                onAction()
            } else {
                permissionState.launchPermissionRequest()
            }
        }
    }
}