package ua.graviton.isida.ui.contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import ua.graviton.isida.domain.bl.BluetoothConstants
import ua.graviton.isida.ui.scan.intentScanDevices

class ScanForDeviceResultContract : ActivityResultContract<Unit, String?>() {

    override fun createIntent(context: Context, input: Unit): Intent = context.intentScanDevices()

    /**
     *  If enabling Bluetooth succeeds, your activity receives the RESULT_OK result code in the onActivityResult() callback.
     *  If Bluetooth was not enabled due to an error (or the user responded "Deny") then the result code is RESULT_CANCELED.
     */
    override fun parseResult(resultCode: Int, intent: Intent?): String? = when (resultCode) {
        Activity.RESULT_OK -> intent?.getStringExtra(BluetoothConstants.EXTRA_DEVICE_ADDRESS)
        else -> null
    }
}