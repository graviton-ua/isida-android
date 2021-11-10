package ua.graviton.isida.ui.contracts

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class EnableBluetoothResultContract : ActivityResultContract<Unit, Boolean>() {

    override fun createIntent(context: Context, input: Unit): Intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)

    /**
     *  If enabling Bluetooth succeeds, your activity receives the RESULT_OK result code in the onActivityResult() callback.
     *  If Bluetooth was not enabled due to an error (or the user responded "Deny") then the result code is RESULT_CANCELED.
     */
    override fun parseResult(resultCode: Int, intent: Intent?): Boolean = resultCode == Activity.RESULT_OK
}