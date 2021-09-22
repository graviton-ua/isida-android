package com.example.evgenij.isida_9

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast

class DeviceListActivity : AppCompatActivity() {
    var bt: BluetoothSPP? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_list)

        bt = BluetoothSPP(this)

        if (!bt.isBluetoothAvailable()) {
            Toast.makeText(
                applicationContext,
                R.string.bluetooth_is_not_available,
                Toast.LENGTH_LONG
            ).show()
            finish()
        }

        bt.setOnDataReceivedListener(object : OnDataReceivedListener() {
            fun onDataReceived(data: ByteArray, message: String) {
                Log.i("Check", "Length : " + data.size)
                Log.i("Check", "Message : $message")
            }
        })

        val btnConnect = findViewById<Button>(R.id.btnConnect)
        btnConnect.setOnClickListener {
            if (bt.getServiceState() === BluetoothState.STATE_CONNECTED) {
                bt.disconnect()
            } else {
                val intent = Intent(this@DeviceListActivity, DeviceList::class.java)
                intent.putExtra("bluetooth_devices", "Bluetooth devices")
                intent.putExtra("no_devices_found", "No device")
                intent.putExtra("scanning", "Scanning")
                intent.putExtra("scan_for_devices", "Search")
                intent.putExtra("select_device", "Select")
                intent.putExtra("layout_list", R.layout.device_layout_list)
                intent.putExtra("layout_text", R.layout.device_layout_text)
                startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE)
            }
        }
    }
}