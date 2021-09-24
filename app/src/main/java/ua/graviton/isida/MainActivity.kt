package ua.graviton.isida

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import ua.graviton.isida.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding by viewBinding(ActivityMainBinding::bind)

    private var bt: BluetoothSPP? = null
    var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)

        val longDataRX = ShortArray(8)
        val tvTemp0 = findViewById<TextView>(R.id.tvTemp0)
        val tvTempU0 = findViewById<TextView>(R.id.tvTempU0)
        val tvTempName1 = findViewById<TextView>(R.id.tvTempName1)
        val tvTemp1 = findViewById<TextView>(R.id.tvTemp1)
        val tvTempU1 = findViewById<TextView>(R.id.tvTempU1)
        val tvTemp2 = findViewById<TextView>(R.id.tvTemp2)
        val tvTemp3 = findViewById<TextView>(R.id.tvTemp3)
        val tvTimer = findViewById<TextView>(R.id.tvTimer)
        val tvTimerU = findViewById<TextView>(R.id.tvTimerU)
        val tvCount = findViewById<TextView>(R.id.tvCount)
        val tvFlap = findViewById<TextView>(R.id.tvFlap)
        val tvFlapU = findViewById<TextView>(R.id.tvFlapU)
        val tvCoTwo = findViewById<TextView>(R.id.tvCoTwo)
        val tvStatus = findViewById<TextView>(R.id.tvStatus)

        binding.tvCellMain.visibility = View.INVISIBLE
        binding.tblMain.visibility = View.INVISIBLE
        binding.barButton.visibility = View.INVISIBLE


        bt = BluetoothSPP(this)

        if (!bt!!.isBluetoothAvailable) {
            tvStatus.setText(R.string.bluetooth_is_not_available)
            Toast.makeText(applicationContext, "Bluetooth is not available", Toast.LENGTH_LONG).show()
//            finish();
        }

        bt!!.setOnDataReceivedListener(object : BluetoothSPP.OnDataReceivedListener {
            override fun onDataReceived(data: ByteArray, message: String) {
                var i: Int = 0
                var indrx: Int
                val dataRX = ShortArray(data.size)

                if (data.size > 10) {
                    i = 0
                    // убираем отрицательные значения ------------------------------------------------
                    while (i < data.size) {
                        if (data[i] < 0) dataRX[i] = (data[i] + 255).toShort() else dataRX[i] =
                            data[i].toShort()
                        i++
                    }
                    indrx = 0
                    // расчитываем 8 byte int16_t начиная с dataRX[1]
                    i = 1
                    while (i < 17) {
                        longDataRX[indrx] = (1 + dataRX[i + 1] + dataRX[i] * 256).toShort()
                        indrx++
                        i = i + 2
                    }
                    DataHolder.setTemp0(longDataRX[0])
                    DataHolder.setTemp1(longDataRX[1])
                    DataHolder.setTemp2(longDataRX[2])
                    DataHolder.setTemp3(longDataRX[3])
                    DataHolder.setRh(longDataRX[4])
                    DataHolder.setCoTwo(longDataRX[5])
                    DataHolder.setTimer(dataRX[18])
                    DataHolder.setCount(dataRX[19])
                    DataHolder.setFlap(dataRX[20])
                    // -----------ID камеры -----------------------------------------
                    binding.tvCellMain.text = getString(R.string.CellNum, dataRX[0])
                    // ---------- pvT[0] СУХОЙ --------------------------------------
                    tvTemp0.setText(((longDataRX[0]).toFloat() / 10).toString());
                    //-- u0 --
                    //tvTempU0.setText ??
                    // ---------- pvT[1] ВЛАЖНЫЙ ------------------------------------
                    if (longDataRX[4] == 0.toShort()) {
                        tvTemp1.setText(((longDataRX[1]).toFloat() / 10).toString());
                        //-- u1 --
                        //tvTempU1.setText ??
                        // ---------- pvT[2] КОНТРОЛЬНЫЙ --------------------------------
                        if (longDataRX[2] > 800) tvTemp2.visibility = View.INVISIBLE
                        else tvTemp2.setText(((longDataRX[2]).toFloat() / 10).toString());
                    } else {
                        // ------------ pvRH датчик относительной влажности ----------------
                        tvTemp1.setText(((longDataRX[4]).toFloat() / 10).toString());
                        tvTempName1.text = "Отн.Влаж."
                        //-- u1 --
                        //tvTempU1.setText ??
                        // ---------- pvT[2] КОНТРОЛЬНЫЙ --------------------------------
                        if (longDataRX[1] > 800 && longDataRX[2] > 800) tvTemp2.visibility =
                            View.INVISIBLE
                        else if (longDataRX[2] > 800) {
                            tvTemp2.setText(((longDataRX[1]).toFloat() / 10).toString())
                        } else if (longDataRX[1] > 800) {
                            tvTemp2.setText(((longDataRX[2]).toFloat() / 10).toString())
                        } else {
                            tvTemp2.setText((((longDataRX[1] + longDataRX[2]) / 2).toFloat() / 10).toString())
                        }
                    }
                    // ---------- pvT[3] СКОРЛУПА --------------------------------------
                    if (longDataRX[3] > 800) tvTemp3.visibility = View.INVISIBLE
                    else tvTemp3.setText(((longDataRX[3]).toFloat() / 10).toString());
                    // ---------- CO2 углекислый газ ------------------------------------
                    if (longDataRX[5] > 0) tvCoTwo.setText((longDataRX[2]).toString())
                    else tvCoTwo.visibility = View.INVISIBLE
                    // ---------- pvTimer значение таймера ------------------------------
                    tvTimer.setText(dataRX[18].toString())
                    //-- tu --
                    //tvTimerU.setText ??
                    // ---------- pvCount значение счетчика проходов поворота --------
                    tvCount.setText(dataRX[19].toString())
                    // ---------- pvFlap положение заслонки -----------------------------
                    tvFlap.setText(dataRX[20].toString())
                    //-- fu --
                    //tvFlapU.setText ??
                }
            }
        })

        val my_dev: BluetoothSPP.BluetoothConnectionListener = object : BluetoothSPP.BluetoothConnectionListener {
            override fun onDeviceDisconnected() {
                tvStatus.setText(R.string.status_not_connect)
                menu!!.clear()
                menuInflater.inflate(R.menu.menu_connection, menu)
            }

            override fun onDeviceConnectionFailed() {
                tvStatus.setText(R.string.status_connection_failed)
            }

            override fun onDeviceConnected(name: String?, address: String?) {
                tvStatus.text = getString(R.string.status_connected_to, name)
                binding.tvCellMain.visibility = View.VISIBLE
                binding.tblMain.visibility = View.VISIBLE
                binding.barButton.visibility = View.VISIBLE
                menu!!.clear()
                menuInflater.inflate(R.menu.menu_disconnection, menu)
            }
        }
        bt!!.setBluetoothConnectionListener(my_dev)

        //-------------------------------------------------
        //-------------------------------------------------
        binding.btnStart.setOnClickListener { startActivity(intentRun(longDataRX)) }
        binding.btnProp.setOnClickListener { startActivity(Intent(this, PropActivity::class.java)) }
        binding.btnInfo.setOnClickListener { startActivity(Intent(this, InfoActivity::class.java)) }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_connection, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.menu_device_connect) {
            bt?.setDeviceTarget(BluetoothState.DEVICE_OTHER)
            /*
			if(bt.getServiceState() == BluetoothState.STATE_CONNECTED)
    			bt.disconnect();*/
            val intent = Intent(applicationContext, DeviceList::class.java)
            startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE)
        } else {
            if (bt?.serviceState == BluetoothState.STATE_CONNECTED) bt?.disconnect()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        bt?.stopService()
    }

    override fun onStart() {
        super.onStart()
        if (!bt!!.isBluetoothEnabled) {
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT)
        } else {
            if (!bt!!.isServiceAvailable) {
                bt?.setupService()
                bt?.startService(BluetoothState.DEVICE_ANDROID)
                //                setup();
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == RESULT_OK) bt?.connect(data)
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                bt?.setupService()
                bt?.startService(BluetoothState.DEVICE_ANDROID)
                //                setup();
            } else {
                Toast.makeText(this, "Bluetooth was not enabled.", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
}