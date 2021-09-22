package com.example.evgenij.isida_9

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private val EXTRA_MESSAGE = "com.example.evgenij.isida_9.MESSAGE"
    private var bt: BluetoothSPP? = null
    private val TAG = "myLogs"
    var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "MainActivity.onCreate()")
        val longDataRX = ShortArray(8)
        val tvCell = findViewById<TextView>(R.id.tvCellMain)
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

        val tblMain = findViewById<TableLayout>(R.id.tblMain)
        val barButton = findViewById<LinearLayout>(R.id.barButton)
        tvCell.visibility = View.INVISIBLE
        tblMain.visibility = View.INVISIBLE
        barButton.visibility = View.INVISIBLE


        bt = BluetoothSPP(this)

        if (!bt!!.isBluetoothAvailable) {
            tvStatus.setText(R.string.bluetooth_is_not_available)
            Toast.makeText(applicationContext, "Bluetooth is not available", Toast.LENGTH_LONG)
                .show()
//            finish();
        }

        bt!!.setOnDataReceivedListener(object : OnDataReceivedListener() {
            @SuppressLint("SetTextI18n")
            fun onDataReceived(data: ByteArray, message: String?) {
                var i: Int = 0
                var indrx: Int
                val dataRX = ShortArray(data.size)

                Log.d(TAG, "(MainActivity) data.size = $data.size")
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
                    tvCell.text = getString(R.string.CellNum, dataRX[0])
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
        }
        )

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
                    tvCell.visibility = View.VISIBLE
                    tblMain.visibility = View.VISIBLE
                    barButton.visibility = View.VISIBLE
                    menu!!.clear()
                    menuInflater.inflate(R.menu.menu_disconnection, menu)
                }
            }
        Log.d(TAG, "MainActivity.BluetoothSPP.BluetoothConnectionListener() = $my_dev")
        bt!!.setBluetoothConnectionListener(my_dev)

        //-------------------------------------------------
        //-------------------------------------------------
        val btnStart = findViewById<Button>(R.id.btnStart)
        btnStart.setOnClickListener { view: View? ->
            val ranintent = Intent(this, RunActivity::class.java)
            ranintent.putExtra(EXTRA_MESSAGE, longDataRX)
            startActivity(ranintent)
        }

        val btnProp = findViewById<Button>(R.id.btnProp)
        btnProp.setOnClickListener { view: View? ->
            startActivity(
                Intent(this, PropActivity::class.java)
            )
        }

        val btnInfo = findViewById<Button>(R.id.btnInfo)
        btnInfo.setOnClickListener { view: View? ->
            startActivity(
                Intent(this, InfoActivity::class.java)
            )
        }
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
            if (bt?.serviceState === BluetoothState.STATE_CONNECTED) bt?.disconnect()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "MainActivity.onDestroy()")
        bt?.stopService()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "MainActivity.onStart()")
        if (!bt!!.isBluetoothEnabled) {
            Log.d(TAG, "intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)")
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT)
        } else {
            if (!bt!!.isServiceAvailable) {
                bt?.setupService()
                Log.d(TAG, "bt.startService(BluetoothState.DEVICE_ANDROID)")
                bt?.startService(BluetoothState.DEVICE_ANDROID)
                //                setup();
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(
            TAG,
            "MainActivity.onActivityResult() requestCode = $requestCode, resultCode = $resultCode"
        )
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