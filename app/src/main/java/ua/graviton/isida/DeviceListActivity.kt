package ua.graviton.isida

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import timber.log.Timber
import ua.graviton.isida.databinding.ActivityDeviceListBinding

class DeviceListActivity : AppCompatActivity(R.layout.activity_device_list) {
    private val binding by viewBinding(ActivityDeviceListBinding::bind)

    private val bt by lazy { BluetoothSPP(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!bt.isBluetoothAvailable) {
            Toast.makeText(this, R.string.bluetooth_is_not_available, Toast.LENGTH_LONG).show()
            finish()
        }

        bt.setOnDataReceivedListener(object : BluetoothSPP.OnDataReceivedListener {
            override fun onDataReceived(data: ByteArray, message: String) {
                Timber.i("Length : " + data.size)
                Timber.i("Message : $message")
            }
        })

        val btnConnect = findViewById<Button>(R.id.btnConnect)
        btnConnect.setOnClickListener {
            if (bt.serviceState == BluetoothState.STATE_CONNECTED) {
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