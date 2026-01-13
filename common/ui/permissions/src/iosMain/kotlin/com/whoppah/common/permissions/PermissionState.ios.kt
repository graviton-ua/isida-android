package com.whoppah.common.permissions

import androidx.compose.runtime.*
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.camera.CAMERA
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import dev.icerock.moko.permissions.notifications.REMOTE_NOTIFICATION
import dev.icerock.moko.permissions.storage.STORAGE
import dev.icerock.moko.permissions.storage.WRITE_STORAGE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// On iOS, we might not have LifecycleEventObserver easily available in common without
// extra dependencies, so we skip the auto-refresh-on-resume logic or implement it differently.
// The core request logic remains the same.

@Composable
actual fun rememberPermissionState(
    permission: PermissionType,
    onPermissionResult: ((Boolean) -> Unit)?
): PermissionState {
    val mokoPermission = permission.toMokoPermission()
    val factory = rememberPermissionsControllerFactory()
    val controller = remember(factory) { factory.createPermissionsController() }
    val scope = rememberCoroutineScope()

    BindEffect(controller)

    return remember(mokoPermission, controller, scope) {
        IosMokoPermissionState(mokoPermission, permission, controller, scope, onPermissionResult)
    }
}

@Stable
private class IosMokoPermissionState(
    private val mokoPermission: Permission,
    override val permission: PermissionType,
    private val controller: PermissionsController,
    private val scope: CoroutineScope,
    private val onPermissionResult: ((Boolean) -> Unit)?
) : PermissionState {

    override var status: PermissionStatus by mutableStateOf(PermissionStatus.Denied(false))
        private set

    init {
        checkStatus()
    }

    private fun checkStatus() {
        scope.launch {
            if (controller.isPermissionGranted(mokoPermission)) {
                status = PermissionStatus.Granted
            }
        }
    }

    override fun launchPermissionRequest() {
        scope.launch {
            try {
                controller.providePermission(mokoPermission)
                status = PermissionStatus.Granted
                onPermissionResult?.invoke(true)
            } catch (e: DeniedAlwaysException) {
                status = PermissionStatus.Denied(shouldShowRationale = false)
                onPermissionResult?.invoke(false)
            } catch (e: DeniedException) {
                status = PermissionStatus.Denied(shouldShowRationale = true)
                onPermissionResult?.invoke(false)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

private fun PermissionType.toMokoPermission(): Permission {
    return when (this) {
        PermissionType.CAMERA -> Permission.CAMERA
        //PermissionType.GALLERY -> Permission.GALLERY
        PermissionType.STORAGE -> Permission.STORAGE
        PermissionType.WRITE_STORAGE -> Permission.WRITE_STORAGE
        //PermissionType.LOCATION -> Permission.LOCATION
        //PermissionType.COARSE_LOCATION -> Permission.COARSE_LOCATION
        //PermissionType.BLUETOOTH_LE -> Permission.BLUETOOTH_LE
        PermissionType.REMOTE_NOTIFICATION -> Permission.REMOTE_NOTIFICATION
        //PermissionType.RECORD_AUDIO -> Permission.RECORD_AUDIO
    }
}