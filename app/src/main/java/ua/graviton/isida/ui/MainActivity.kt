package ua.graviton.isida.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint

fun Context.intentMain() = Intent(this, MainActivity::class.java)

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen()
        setContent {
            IsidaApp()
        }
    }


//    override fun onStart() {
//        super.onStart()
//        bindService(intentBLConnectionService(), connection, Context.BIND_NOT_FOREGROUND)
//    }
//
//    override fun onStop() {
//        unbindService(connection)
//        super.onStop()
//    }
//
//    private val connection = object : ServiceConnection {
//        override fun onServiceConnected(name: ComponentName, service: IBinder) {
//            Timber.d("Bluetooth service connected")
//            // We've bound to RecordService, cast the IBinder
//            val binder = service as BluetoothConnectionService.ConnectionBinder
//            lifecycleScope.launchWhenStarted {
//                repeatOnLifecycle(Lifecycle.State.STARTED) {
//                    binder.isConnected().collectLatest { viewModel.setRecording(it) }
//                }
//            }
//        }
//
//        override fun onServiceDisconnected(name: ComponentName) {
//            Timber.d("Bluetooth service disconnected")
//        }
//
//        override fun onBindingDied(name: ComponentName?) {
//            Timber.d("onBindingDied | need rebind connection")
//            unbindService(this)
//            bindService(intentBLConnectionService(), this, Context.BIND_NOT_FOREGROUND)
//        }
//    }

    //    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
//        R.id.menu_start -> {
//            DeviceModeDialogFragment().show(supportFragmentManager, "device_mode")
//            true
//        }
//        else -> super.onOptionsItemSelected(item)
//    }

    //TODO: Should be re-worked!!!
    // fun sendCommand(command: SendPackageDto) = bt?.send(command.asByteArray(), false)
}