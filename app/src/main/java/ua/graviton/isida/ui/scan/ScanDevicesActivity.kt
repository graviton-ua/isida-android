package ua.graviton.isida.ui.scan

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.*
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import ua.graviton.isida.domain.bl.BluetoothState
import ua.graviton.isida.ui.contracts.EnableBluetoothResultContract
import ua.graviton.isida.ui.theme.IsidaTheme


fun Context.intentScanDevices() = Intent(this, ScanDevicesActivity::class.java)

@AndroidEntryPoint
class ScanDevicesActivity : AppCompatActivity() {
    private val viewModel: ScanDevicesViewModel by viewModels()

    private val bluetoothManager: BluetoothManager by lazy { getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager }
    private val bluetoothAdapter: BluetoothAdapter? by lazy { bluetoothManager.adapter }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Set result CANCELED in case the user backs out
        setResult(RESULT_CANCELED)

        // Register for broadcasts when a device is discovered
        val filter = IntentFilter().apply {
            addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
            addAction(BluetoothDevice.ACTION_FOUND)
            addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        }
        registerReceiver(deviceListReceiver, filter)

        // If there are paired devices, add each one to the ArrayAdapter
        with(bluetoothAdapter) {
            if (this != null && bondedDevices.isNotEmpty()) {
                viewModel.submitAction(ScanDevicesAction.AlreadyPairedDevices(bondedDevices.toList()))
            }
        }

        setContent {
            IsidaTheme {
                val systemBarUiController = rememberSystemUiController()
                systemBarUiController.setStatusBarColor(Color.Transparent, darkIcons = true)

                ScanDevicesScreen(
                    navigateUp = { onBackPressed() },
                    onStartScanClicked = { onScanClick(bluetoothAdapter) },
                    onStopScanClicked = { bluetoothAdapter?.cancelDiscovery() },
                    onDeviceClicked = {
                        // Cancel discovery because it's costly and we're about to connect
                        bluetoothAdapter?.cancelDiscovery()

                        // Create the result Intent and include the MAC address
                        val intent = Intent().apply { putExtra(BluetoothState.EXTRA_DEVICE_ADDRESS, it.address) }

                        // Set result and finish this Activity
                        setResult(RESULT_OK, intent)
                        finish()
                    }
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CHECK_SETTINGS -> when (resultCode) {
                RESULT_OK -> onScanClick(bluetoothAdapter)
                else -> Unit
            }
        }
    }

    override fun onDestroy() {
        // Make sure we're not doing discovery anymore
        bluetoothAdapter?.cancelDiscovery()
        // Unregister broadcast listeners
        unregisterReceiver(deviceListReceiver)
        super.onDestroy()
    }


    private fun onScanClick(adapter: BluetoothAdapter?) {
        if (adapter == null) return

        if (!adapter.isEnabled) {
            enableBluetooth.launch(Unit)
            return
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            return
        }

        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!isGpsEnabled) {
            //startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), MY_REQUEST_CODE);
            val locationRequest: LocationRequest = LocationRequest.create()
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
            builder.setAlwaysShow(true) //this displays dialog box like Google Maps with two buttons - OK and NO,THANKS
            val task: Task<LocationSettingsResponse> = LocationServices.getSettingsClient(this).checkLocationSettings(builder.build())

            task.addOnSuccessListener {
                // All location settings are satisfied. The client can initialize location requests here.
                //onScanClick(bluetoothAdapter)
            }
            task.addOnFailureListener {
                Timber.w(it)
                if (it is ApiException)
                    when (it.statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->
                            // Location settings are not satisfied. But could be fixed by showing the user a dialog.
                            try {
                                // Cast to a resolvable exception.
                                val resolvable = it as ResolvableApiException
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                resolvable.startResolutionForResult(this@ScanDevicesActivity, REQUEST_CHECK_SETTINGS)
                            } catch (e: IntentSender.SendIntentException) {
                                // Ignore the error.
                            } catch (e: ClassCastException) {
                                // Ignore, should be an impossible error.
                            }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> Unit
                    }
            }
            return
        }

        doDiscovery(adapter)
    }

    private val enableBluetooth = registerForActivityResult(EnableBluetoothResultContract()) { enabled ->
        if (enabled) onScanClick(bluetoothAdapter)
    }

    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { enabled ->
        if (enabled) onScanClick(bluetoothAdapter)
    }


    // Start device discover with the BluetoothAdapter
    private fun doDiscovery(adapter: BluetoothAdapter) {
        Timber.d("doDiscovery")

        // Remove all element from the list
        viewModel.submitAction(ScanDevicesAction.ClearDeviceList)

        // If there are paired devices, add each one to the ArrayAdapter
        if (adapter.bondedDevices.isNotEmpty()) {
            viewModel.submitAction(ScanDevicesAction.AlreadyPairedDevices(adapter.bondedDevices.toList()))
        }

        if (adapter.isDiscovering) adapter.cancelDiscovery()

        // Request discover from BluetoothAdapter
        adapter.startDiscovery().also { Timber.d("startDiscovery: $it") }
    }

    // The BroadcastReceiver that listens for discovered devices and
    // changes the title when discovery is finished
    private val deviceListReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    Timber.d("Device Found")

                    // Get the BluetoothDevice object from the Intent
                    val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)

                    // If it's already paired, skip it, because it's been listed already
                    if (device == null || device.bondState == BluetoothDevice.BOND_BONDED) return

                    // Submit it as possible paring option
                    viewModel.submitAction(ScanDevicesAction.DeviceFound(device))
                }

                BluetoothAdapter.ACTION_DISCOVERY_STARTED -> {
                    Timber.d("Discovery started")
                    viewModel.submitAction(ScanDevicesAction.ScanningStarted)
                }

                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    Timber.d("Discovery finished")
                    viewModel.submitAction(ScanDevicesAction.ScanningStopped)
                }
            }
        }
    }

    companion object {
        private const val REQUEST_CHECK_SETTINGS = 113
    }
}