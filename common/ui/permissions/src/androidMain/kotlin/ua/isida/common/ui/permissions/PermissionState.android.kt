package ua.isida.common.ui.permissions

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.bluetooth.BLUETOOTH_CONNECT
import dev.icerock.moko.permissions.bluetooth.BLUETOOTH_SCAN
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import dev.icerock.moko.permissions.location.COARSE_LOCATION
import dev.icerock.moko.permissions.location.LOCATION
import dev.icerock.moko.permissions.notifications.REMOTE_NOTIFICATION
import dev.icerock.moko.permissions.storage.STORAGE
import dev.icerock.moko.permissions.storage.WRITE_STORAGE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Android implementation of [rememberPermissionState] using Moko Permissions.
 * Automatically refreshes status when the activity is resumed.
 */
@Composable
actual fun rememberPermissionState(
    vararg permissions: PermissionType,
    onPermissionResult: ((Boolean) -> Unit)?
): PermissionState {
    // Convert all requested types to Moko permissions
    val mokoPermissions = remember(permissions) {
        permissions.map { it.toMokoPermission() }
    }

    val factory = rememberPermissionsControllerFactory()
    val controller = remember(factory) { factory.createPermissionsController() }
    val scope = rememberCoroutineScope()

    BindEffect(controller)

    val permissionState = remember(mokoPermissions, controller, scope) {
        MokoPermissionState(
            mokoPermissions = mokoPermissions,
            permissions = permissions.toList(),
            controller = controller,
            scope = scope,
            onPermissionResult = onPermissionResult
        )
    }

    // Refresh on resume (e.g. coming back from settings)
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(permissionState, lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                permissionState.refreshStatus()
            }
        }
        lifecycle.addObserver(observer)
        onDispose { lifecycle.removeObserver(observer) }
    }

    return permissionState
}

/**
 * Private custom implementation of BindEffect to fix the "ContextThemeWrapper" crash.
 * This replaces dev.icerock.moko.permissions.compose.BindEffect for this file only.
 */
@Composable
private fun BindEffect(permissionsController: PermissionsController) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val activity = LocalActivity.current as? ComponentActivity

    LaunchedEffect(permissionsController, lifecycleOwner, activity) {
        if (activity != null) {
            permissionsController.bind(activity)
        } else {
            // Log warning instead of crashing
            System.err.println("PermissionState: Could not find ComponentActivity. Permissions may not work.")
        }
    }
}

/**
 * Wrapper for Moko's [PermissionsController] that implements our common [PermissionState].
 */
@Stable
private class MokoPermissionState(
    private val mokoPermissions: List<Permission>,
    override val permissions: List<PermissionType>,
    private val controller: PermissionsController,
    private val scope: CoroutineScope,
    private val onPermissionResult: ((Boolean) -> Unit)?
) : PermissionState {

    override var status: PermissionStatus by mutableStateOf(PermissionStatus.Denied(false))
        private set

    init {
        refreshStatus()
    }

    override fun launchPermissionRequest() {
        scope.launch {
            try {
                // Request permissions sequentially.
                // If one fails, the catch block triggers and the process stops.
                mokoPermissions.forEach { permission ->
                    controller.providePermission(permission)
                }
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

    /**
     * Re-checks the permission status from the controller.
     */
    fun refreshStatus() {
        scope.launch {
            // Check if ALL permissions in the list are granted
            val allGranted = mokoPermissions.all { controller.isPermissionGranted(it) }

            if (allGranted) {
                status = PermissionStatus.Granted
            } else {
                // If currently Granted but system says no, revoke it.
                if (status is PermissionStatus.Granted) {
                    status = PermissionStatus.Denied(false)
                }
            }
        }
    }
}

private fun PermissionType.toMokoPermission(): Permission {
    return when (this) {
        PermissionType.STORAGE -> Permission.STORAGE
        PermissionType.WRITE_STORAGE -> Permission.WRITE_STORAGE
        PermissionType.LOCATION -> Permission.LOCATION
        PermissionType.COARSE_LOCATION -> Permission.COARSE_LOCATION
        PermissionType.BLUETOOTH_SCAN -> Permission.BLUETOOTH_SCAN
        PermissionType.BLUETOOTH_CONNECT -> Permission.BLUETOOTH_CONNECT
        PermissionType.REMOTE_NOTIFICATION -> Permission.REMOTE_NOTIFICATION
    }
}