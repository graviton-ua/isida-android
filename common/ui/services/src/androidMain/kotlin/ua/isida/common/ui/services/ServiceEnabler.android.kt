package ua.isida.common.ui.services

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Android implementation of [rememberServiceEnabler].
 * - Bluetooth: Uses [BluetoothAdapter.ACTION_REQUEST_ENABLE] intent.
 * - Location: Uses Google Play Services (GMS) [LocationSettingsRequest] to show a resolution dialog.
 */
@Composable
actual fun rememberServiceEnabler(
    type: ServiceType,
    onEnabled: () -> Unit,
    onDenied: () -> Unit
): ServiceEnabler {
    val context = LocalContext.current

    return when (type) {
        ServiceType.BLUETOOTH -> rememberBluetoothEnablerImpl(onEnabled, onDenied)
        ServiceType.LOCATION -> rememberLocationEnablerImpl(context, onEnabled, onDenied)
    }
}

@Composable
private fun rememberBluetoothEnablerImpl(
    onEnabled: () -> Unit,
    onDenied: () -> Unit
): ServiceEnabler {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            onEnabled()
        } else {
            onDenied()
        }
    }

    return remember(launcher) {
        object : ServiceEnabler {
            override fun requestEnable() {
                val adapter = BluetoothAdapter.getDefaultAdapter()
                if (adapter?.isEnabled == true) {
                    onEnabled()
                } else {
                    try {
                        val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                        launcher.launch(intent)
                    } catch (e: Exception) {
                        onDenied()
                    }
                }
            }
        }
    }
}

@Composable
private fun rememberLocationEnablerImpl(
    context: Context,
    onEnabled: () -> Unit,
    onDenied: () -> Unit
): ServiceEnabler {
    val scope = rememberCoroutineScope()

    // 1. The Launcher to handle the "ResolvableApiException" dialog result
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            onEnabled()
        } else {
            onDenied()
        }
    }

    return remember(context, launcher, scope) {
        object : ServiceEnabler {
            override fun requestEnable() {
                // Quick check: is it already on?
                val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val isGpsOn = lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                        lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

                if (isGpsOn) {
                    onEnabled()
                    return
                }

                // If not, use GMS SettingsClient to show the popup
                scope.launch {
                    try {
                        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()
                        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
                        val client = LocationServices.getSettingsClient(context)

                        // This task will either succeed immediately (if on) or throw exception
                        client.checkLocationSettings(builder.build()).await()
                        onEnabled() // Already satisfied

                    } catch (e: Exception) {
                        if (e is ResolvableApiException) {
                            try {
                                // Show the dialog
                                val intentSenderRequest = IntentSenderRequest.Builder(e.resolution).build()
                                launcher.launch(intentSenderRequest)
                            } catch (sendEx: Exception) {
                                // Fallback: Open general settings if GMS dialog fails
                                openLocationSettings(context)
                            }
                        } else {
                            // Not resolvable (e.g. no GMS) -> Open Settings manually
                            openLocationSettings(context)
                        }
                    }
                }
            }
        }
    }
}

private fun openLocationSettings(context: Context) {
    try {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    } catch (e: Exception) {
        // Ignore
    }
}

private suspend fun <T> Task<T>.await(): T {
    return suspendCancellableCoroutine { continuation ->
        addOnSuccessListener { result ->
            continuation.resume(result)
        }
        addOnFailureListener { exception ->
            continuation.resumeWithException(exception)
        }
        // Handle cancellation
        continuation.invokeOnCancellation {
            // Tasks usually cannot be cancelled easily, but we can ignore the result
        }
    }
}