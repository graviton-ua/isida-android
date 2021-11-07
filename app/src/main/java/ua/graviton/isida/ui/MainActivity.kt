package ua.graviton.isida.ui

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import ua.graviton.isida.data.bl.model.SendPackageDto
import ua.graviton.isida.domain.bl.BluetoothSPP
import ua.graviton.isida.ui.contracts.ScanForDeviceResultContract

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    private val bluetoothManager: BluetoothManager by lazy { getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager }
    private val bluetoothAdapter: BluetoothAdapter? by lazy { bluetoothManager.adapter }
    private val bt: BluetoothSPP? by lazy { bluetoothAdapter?.let { BluetoothSPP(it) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen()
        setContent {
            IsidaApp(
                onConnectDevice = {
                    // if(bt.getServiceState() == BluetoothState.STATE_CONNECTED) bt.disconnect();
                    scanForDevice.launch(Unit)
                },
                onDisconnectDevice = {}
            )
        }

        bt?.setOnDataReceivedListener(object : BluetoothSPP.OnDataReceivedListener {
            override fun onDataReceived(data: ByteArray, message: String) = viewModel.submitData(data)
        })

        bt?.setBluetoothConnectionListener(object : BluetoothSPP.BluetoothConnectionListener {
            override fun onDeviceDisconnected() {
                viewModel.submitStreamEnd()
            }

            override fun onDeviceConnectionFailed() {
            }

            override fun onDeviceConnected(name: String?, address: String?) {
            }
        })
    }

    private val scanForDevice = registerForActivityResult(ScanForDeviceResultContract()) { address ->
        Timber.d("Selected device: $address | bt: $bt")
        if (address != null) Unit
        // if (resultCode == RESULT_OK) bt.connect(data)
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
//        R.id.menu_device_connect -> {
//            bt.setDeviceTarget(BluetoothState.DEVICE_OTHER)
//            if(bt.getServiceState() == BluetoothState.STATE_CONNECTED) bt.disconnect();
//            val intent = Intent(applicationContext, DeviceList::class.java)
//            startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE)
//            true
//        }
//        R.id.menu_device_disconnect -> {
//            if (bt.serviceState == BluetoothState.STATE_CONNECTED) bt.disconnect()
//            true
//        }
//        R.id.menu_start -> {
//            DeviceModeDialogFragment().show(supportFragmentManager, "device_mode")
//            true
//        }
//        else -> super.onOptionsItemSelected(item)
//    }

    override fun onDestroy() {
        super.onDestroy()
        bt?.stopService()
    }

//    override fun onStart() {
//        super.onStart()
//        if (!bt.isBluetoothEnabled) {
//            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
//            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT)
//        } else {
//            if (!bt.isServiceAvailable) {
//                bt.setupService()
//                bt.startService(BluetoothState.DEVICE_ANDROID)
//                //                setup();
//            }
//        }
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
//            if (resultCode == RESULT_OK) bt.connect(data)
//        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
//            if (resultCode == RESULT_OK) {
//                bt.setupService()
//                bt.startService(BluetoothState.DEVICE_ANDROID)
//                //                setup();
//            } else {
//                Toast.makeText(this, "Bluetooth was not enabled.", Toast.LENGTH_LONG).show()
//                finish()
//            }
//        }
//    }


    //TODO: Should be re-worked!!!
    fun sendCommand(command: SendPackageDto) = bt?.send(command.asByteArray(), false)
}