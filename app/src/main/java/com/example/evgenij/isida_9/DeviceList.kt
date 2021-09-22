package com.example.evgenij.isida_9

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

@SuppressLint("NewApi")
class DeviceList : AppCompatActivity() {
    // Member fields
    private var mBtAdapter: BluetoothAdapter? = null
    private var mPairedDevicesArrayAdapter: ArrayAdapter<String>? = null
    private var pairedDevices: Set<BluetoothDevice>? = null
    private var scanButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.device_layout_list)
        Log.d(TAG, "(DeviceList) onCreate()")
        title = "Bluetooth Devices"

        // Set result CANCELED in case the user backs out
        setResult(RESULT_CANCELED)

        // Initialize the button to perform device discovery
        scanButton = findViewById(R.id.button_scan)
        var strScanDevice = intent.getStringExtra("scan_for_devices")
        if (strScanDevice == null) strScanDevice = "SCAN FOR DEVICES"
        scanButton.setText(strScanDevice)
        scanButton.setOnClickListener(View.OnClickListener { doDiscovery() })

        // Initialize array adapters. One for already paired devices
        // and one for newly discovered devices
        val layout_text = intent.getIntExtra("layout_text", R.layout.device_layout_text)
        mPairedDevicesArrayAdapter = ArrayAdapter(this, layout_text)

        // Find and set up the ListView for paired devices
        val pairedListView = findViewById<ListView>(R.id.list_devices)
        pairedListView.adapter = mPairedDevicesArrayAdapter
        pairedListView.onItemClickListener = mDeviceClickListener

        // Register for broadcasts when a device is discovered
        val filter = IntentFilter()
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        filter.addAction(BluetoothDevice.ACTION_FOUND)
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        this.registerReceiver(mReceiver, filter)

        // Get the local Bluetooth adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter()

        // Get a set of currently paired devices
        pairedDevices = mBtAdapter.getBondedDevices()

        // If there are paired devices, add each one to the ArrayAdapter
        if (pairedDevices.size > 0) {
            for (device in pairedDevices) {
                mPairedDevicesArrayAdapter!!.add(device.name + "\n" + device.address)
            }
        } else {
            val noDevices = "No devices found"
            mPairedDevicesArrayAdapter!!.add(noDevices)
        }

        //===================================================================================
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                REQUEST_PERMISSION_CODE
            )

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        } else {
            // Permission has already been granted
        }
        //===================================================================================
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>?,
        grantResults: IntArray
    ) {
        Log.d(TAG, "(DeviceList) onRequestPermissionsResult()")
        when (requestCode) {
            REQUEST_PERMISSION_CODE -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    finish()
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "(DeviceList) onDestroy()")
        // Make sure we're not doing discovery anymore
        if (mBtAdapter != null) mBtAdapter!!.cancelDiscovery()

        // Unregister broadcast listeners
        unregisterReceiver(mReceiver)
        finish()
    }

    // Start device discover with the BluetoothAdapter
    private fun doDiscovery() {
        Log.d(TAG, "(DeviceList) doDiscovery()")

        // Remove all element from the list
        mPairedDevicesArrayAdapter!!.clear()

        // If there are paired devices, add each one to the ArrayAdapter
        if (pairedDevices!!.size > 0) {
            Log.d(TAG, "(DeviceList) pairedDevices.size() > 0")
            for (device in pairedDevices!!) {
                mPairedDevicesArrayAdapter!!.add(
                    """
                        ${device.name}
                        ${device.address}
                        """.trimIndent()
                )
            }
        } else {
            Log.d(TAG, "(DeviceList) pairedDevices.size() = 0")
            var strNoFound = intent.getStringExtra("no_devices_found")
            if (strNoFound == null) strNoFound = "No devices found"
            mPairedDevicesArrayAdapter!!.add(strNoFound)
        }

        // Indicate scanning in the title
        Log.d(TAG, "(DeviceList) Scanning for devices...")
        var strScanning = intent.getStringExtra("scanning")
        if (strScanning == null) strScanning = "Scanning for devices..."
        setProgressBarIndeterminateVisibility(true)
        title = strScanning
        if (mBtAdapter!!.isDiscovering) {
            mBtAdapter!!.cancelDiscovery()
        }

        // Request discover from BluetoothAdapter
        mBtAdapter!!.startDiscovery()
    }

    // The on-click listener for all devices in the ListViews
    private val mDeviceClickListener =
        OnItemClickListener { av, v, arg2, arg3 ->
            Log.d(TAG, "(DeviceList) The on-click listener for all devices in the ListViews.")
            // Cancel discovery because it's costly and we're about to connect
            if (mBtAdapter!!.isDiscovering) mBtAdapter!!.cancelDiscovery()
            var strNoFound = intent.getStringExtra("no_devices_found")
            Log.d(TAG, "(DeviceList) strNoFound = $strNoFound")
            if (strNoFound == null) strNoFound = "No devices found"
            if ((v as TextView).text.toString() != strNoFound) {
                // Get the device MAC address, which is the last 17 chars in the View
                val info = v.text.toString()
                val address = info.substring(info.length - 17)
                Log.d(TAG, "(DeviceList) info = $info")
                Log.d(TAG, "(DeviceList) address = $address")
                // Create the result Intent and include the MAC address
                val intent = Intent()
                intent.putExtra(BluetoothState.EXTRA_DEVICE_ADDRESS, address)

                // Set result and finish this Activity
                setResult(RESULT_OK, intent)
                finish()
            }
        }

    // The BroadcastReceiver that listens for discovered devices and
    // changes the title when discovery is finished
    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            Log.d(TAG, "(DeviceList) Received action: $action")

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND == action) {
                Log.d(TAG, "(DeviceList) DEVICE Found")

                // Get the BluetoothDevice object from the Intent
                val device =
                    intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)

                // If it's already paired, skip it, because it's been listed already
                if (device!!.bondState != BluetoothDevice.BOND_BONDED) {
                    Log.d(TAG, "(DeviceList) It's already paired.")
                    var strNoFound = getIntent().getStringExtra("no_devices_found")
                    if (strNoFound == null) strNoFound = "No devices found"
                    if (mPairedDevicesArrayAdapter!!.getItem(0) == strNoFound) {
                        mPairedDevicesArrayAdapter!!.remove(strNoFound)
                    }
                    mPairedDevicesArrayAdapter!!.add(
                        """
                            ${device.name}
                            ${device.address}
                            """.trimIndent()
                    )
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED == action) {
                Log.d(TAG, "(DeviceList) Discovery started")

                // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED == action) {
                Log.d(TAG, "(DeviceList) Discovery is finished.")
                setProgressBarIndeterminateVisibility(false)
                var strSelectDevice = getIntent().getStringExtra("select_device")
                if (strSelectDevice == null) strSelectDevice = "Select a device to connect"
                title = strSelectDevice
            }
        }
    }
    var scanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            Log.d(TAG, "(DeviceList) onScanResult()")
        }

        override fun onBatchScanResults(results: List<ScanResult>) {
            super.onBatchScanResults(results)
            Log.d(TAG, "(DeviceList) onBatchScanResults()")
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            Log.d(TAG, "(DeviceList) onScanFailed()")
        }
    }

    companion object {
        // Debugging
        private const val TAG = "myLogs"

        //    private static final String TAG = "BluetoothSPP";
        private const val REQUEST_PERMISSION_CODE = 1100
    }
}