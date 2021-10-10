package ua.graviton.isida.ui

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commitNow
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ua.graviton.isida.R
import ua.graviton.isida.databinding.ActivityMainBinding
import ua.graviton.isida.domain.BluetoothSPP
import ua.graviton.isida.domain.BluetoothState
import ua.graviton.isida.ui.prop.PropFragment
import ua.graviton.isida.ui.report.ReportFragment
import ua.graviton.isida.ui.stats.StatsFragment
import kotlin.reflect.KClass

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding by viewBinding(ActivityMainBinding::bind)
    private val viewModel: MainViewModel by viewModels()

    private val bt by lazy { BluetoothSPP(this) }
    var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)

        if (!bt.isBluetoothAvailable) {
            binding.tvStatus.setText(R.string.bluetooth_is_not_available)
            Toast.makeText(applicationContext, "Bluetooth is not available", Toast.LENGTH_LONG)
                .show()
//            finish();
        }

        bt.setOnDataReceivedListener(object : BluetoothSPP.OnDataReceivedListener {
            override fun onDataReceived(data: ByteArray, message: String) =
                viewModel.submitData(data)
        })

        bt.setBluetoothConnectionListener(object : BluetoothSPP.BluetoothConnectionListener {
            override fun onDeviceDisconnected() {
                binding.tvStatus.setText(R.string.status_not_connect)
                menu?.clear()
                menuInflater.inflate(R.menu.menu_connection, menu)
            }

            override fun onDeviceConnectionFailed() {
                binding.tvStatus.setText(R.string.status_connection_failed)
            }

            override fun onDeviceConnected(name: String?, address: String?) {
                binding.tvStatus.text = getString(R.string.status_connected_to, name)
                menu?.clear()
                menuInflater.inflate(R.menu.menu_disconnection, menu)
            }
        })

        binding.bottomBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_bottom_stats -> open(Screen.Stats)
                R.id.action_bottom_prop -> open(Screen.Prop)
                R.id.action_bottom_report -> open(Screen.Report)
                else -> false
            }
        }

        goToStats()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.menu_connection, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.menu_device_connect) {
            bt.setDeviceTarget(BluetoothState.DEVICE_OTHER)
            /*
			if(bt.getServiceState() == BluetoothState.STATE_CONNECTED)
    			bt.disconnect();*/
            val intent = Intent(applicationContext, DeviceList::class.java)
            startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE)
        } else {
            if (bt.serviceState == BluetoothState.STATE_CONNECTED) bt.disconnect()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        bt.stopService()
    }

    override fun onStart() {
        super.onStart()
        if (!bt.isBluetoothEnabled) {
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT)
        } else {
            if (!bt.isServiceAvailable) {
                bt.setupService()
                bt.startService(BluetoothState.DEVICE_ANDROID)
                //                setup();
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == RESULT_OK) bt.connect(data)
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                bt.setupService()
                bt.startService(BluetoothState.DEVICE_ANDROID)
                //                setup();
            } else {
                Toast.makeText(this, "Bluetooth was not enabled.", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }


    private fun goToStats() = with(binding.bottomBar) { selectedItemId = R.id.action_bottom_stats }
    private fun goToProp() = with(binding.bottomBar) { selectedItemId = R.id.action_bottom_prop }
    private fun goToReport() =
        with(binding.bottomBar) { selectedItemId = R.id.action_bottom_report }

    private fun open(screen: Screen): Boolean {
        supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val currentFragment = supportFragmentManager.findFragmentById(R.id.container)

        if (currentFragment?.let { it::class } == screen.clazz()) return true

        val existingFragment = supportFragmentManager.findFragmentByTag(screen.tag)
        val createdFragment = if (existingFragment == null) screen.createFragment() else null
        supportFragmentManager.commitNow {
            if (currentFragment != null) {
                detach(currentFragment)
            }
            if (existingFragment != null) {
                attach(existingFragment)
            }
            if (createdFragment != null) {
                add(R.id.container, createdFragment, screen.tag)
            }
        }
        return true
    }

    sealed interface Screen {
        val tag: String
        fun clazz(): KClass<out Fragment>
        fun createFragment(): Fragment

        object Stats : Screen {
            override val tag: String get() = "stats"
            override fun clazz(): KClass<out Fragment> = StatsFragment::class
            override fun createFragment(): Fragment = StatsFragment.newInstance()
        }

        object Prop : Screen {
            override val tag: String get() = "prop"
            override fun clazz(): KClass<out Fragment> = PropFragment::class
            override fun createFragment(): Fragment = PropFragment.newInstance()
        }

        object Report : Screen {
            override val tag: String get() = "report"
            override fun clazz(): KClass<out Fragment> = ReportFragment::class
            override fun createFragment(): Fragment = ReportFragment.newInstance()
        }
    }
}