package ua.graviton.isida.domain.bl

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Handler
import android.os.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

@SuppressLint("MissingPermission")
class BluetoothSPP(
    scope: CoroutineScope,
    private val adapter: BluetoothAdapter,
) {
    // Listener for Bluetooth Status & Connection
    private var mBluetoothStateListener: BluetoothStateListener? = null
    private var mDataReceivedListener: OnDataReceivedListener? = null
    private var mBluetoothConnectionListener: BluetoothConnectionListener? = null
    private var mAutoConnectionListener: AutoConnectionListener? = null

    //TODO: Rewrite it !!!!!!!!!!!
    @SuppressLint("HandlerLeak")
    private val mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                BluetoothConstants.MESSAGE_WRITE -> Unit
                BluetoothConstants.MESSAGE_READ -> {
                    val readBuf = msg.obj as ByteArray
                    val readMessage = String(readBuf)
                    if (readBuf.isNotEmpty()) mDataReceivedListener?.onDataReceived(readBuf, readMessage)
                }
                BluetoothConstants.MESSAGE_DEVICE_NAME -> {
                    connectedDeviceName = msg.data.getString(BluetoothConstants.DEVICE_NAME)
                    connectedDeviceAddress = msg.data.getString(BluetoothConstants.DEVICE_ADDRESS)
                    mBluetoothConnectionListener?.onDeviceConnected(connectedDeviceName, connectedDeviceAddress)
                    isConnected = true
                }
                BluetoothConstants.MESSAGE_TOAST -> Unit//Toast.makeText(ctx, msg.data.getString(BluetoothState.TOAST), Toast.LENGTH_LONG).show()
            }
        }
    }

    // Member object for the chat services
    private val mChatService: BluetoothService = BluetoothService(adapter, mHandler)

    // Name and Address of the connected device
    var connectedDeviceName: String? = null
        private set
    var connectedDeviceAddress: String? = null
        private set
    var isAutoConnecting = false
        private set
    private var isAutoConnectionEnabled = false
    private var isConnected = false
    private var isConnecting = false
    private var isServiceRunning = false
    private var keyword = ""
    private var isAndroid: Boolean = BluetoothConstants.DEVICE_ANDROID
    private var bcl: BluetoothConnectionListener? = null
    private var c = 0


    val isBluetoothAvailable: Boolean
        @SuppressLint("HardwareIds", "MissingPermission")
        get() = adapter.address != null

    val isBluetoothEnabled: Boolean get() = adapter.isEnabled
    val isServiceAvailable: Boolean get() = true

    //fun startDiscovery(): Boolean = bluetoothAdapter.startDiscovery()
    //val isDiscovery: Boolean get() = bluetoothAdapter.isDiscovering
    //fun cancelDiscovery(): Boolean = bluetoothAdapter.cancelDiscovery()

    val serviceState get() = mChatService.state.value

//    fun startService(isAndroid: Boolean) {
//        mChatService.run {
//            if (state == BluetoothState.STATE_IDLE) {
//                isServiceRunning = true
//                start(isAndroid)
//                this@BluetoothSPP.isAndroid = isAndroid
//            }
//        }
//    }

    init {
        // Start listening for bluetooth connection state updates
        scope.listenForConnectionState(mChatService)
    }

    fun stopService() {
        isServiceRunning = false
        mChatService.stop()
        Handler().postDelayed({
            isServiceRunning = false
            mChatService.stop()
        }, 500)
    }

    fun setDeviceTarget(isAndroid: Boolean) {
        stopService()
        //startService(isAndroid)
        this@BluetoothSPP.isAndroid = isAndroid
    }


    fun stopAutoConnect() {
        isAutoConnectionEnabled = false
    }

    fun connect(data: Intent?) {
        val address = data?.extras?.getString(BluetoothConstants.EXTRA_DEVICE_ADDRESS) ?: return
        val device = adapter.getRemoteDevice(address) ?: return
        mChatService.connect(device)
    }

    fun connect(address: String?) {
        val device = adapter.getRemoteDevice(address) ?: return
        mChatService.connect(device)
    }

    fun disconnect() {
        isServiceRunning = false
        mChatService.stop()
    }

    fun setBluetoothStateListener(listener: BluetoothStateListener?) {
        mBluetoothStateListener = listener
    }

    fun setOnDataReceivedListener(listener: OnDataReceivedListener?) {
        mDataReceivedListener = listener
    }

    fun setBluetoothConnectionListener(listener: BluetoothConnectionListener?) {
        mBluetoothConnectionListener = listener
    }

    fun setAutoConnectionListener(listener: AutoConnectionListener?) {
        mAutoConnectionListener = listener
    }

    fun send(data: ByteArray, CRLF: Boolean) {
        mChatService.run {
            if (state.value == BluetoothState.CONNECTED) {
                if (CRLF) {
                    val data2 = ByteArray(data.size + 2)
                    for (i in data.indices) data2[i] = data[i]
                    data2[data2.size - 2] = 0x0D
                    data2[data2.size - 1] = 0x0A
                    write(data2)
                } else {
                    write(data)
                }
            }
        }
    }

    fun send(data: String, CRLF: Boolean) {
        mChatService.run {
            if (state.value == BluetoothState.CONNECTED) {
                val final = data.let { if (CRLF) it + "\r\n" else it }
                write(final.toByteArray())
            }
        }
    }

    private val pairedDeviceName: List<String> get() = adapter.bondedDevices.map { it.name }
    private val pairedDeviceAddress: List<String> get() = adapter.bondedDevices.map { it.address }

    private fun autoConnect(keywordName: String) {
        if (!isAutoConnectionEnabled) {
            keyword = keywordName
            isAutoConnectionEnabled = true
            isAutoConnecting = true
            if (mAutoConnectionListener != null) mAutoConnectionListener!!.onAutoConnectionStarted()
            val arr_filter_address = ArrayList<String?>()
            val arr_filter_name = ArrayList<String?>()
            val arr_name = pairedDeviceName
            val arr_address = pairedDeviceAddress
            for (i in arr_name.indices) {
                if (arr_name[i].contains(keywordName)) {
                    arr_filter_address.add(arr_address[i])
                    arr_filter_name.add(arr_name[i])
                }
            }
            bcl = object : BluetoothConnectionListener {
                override fun onDeviceConnected(name: String?, address: String?) {
                    bcl = null
                    isAutoConnecting = false
                }

                override fun onDeviceDisconnected() {}
                override fun onDeviceConnectionFailed() {
                    Timber.e("Failed")
                    if (isServiceRunning) {
                        if (isAutoConnectionEnabled) {
                            c++
                            if (c >= arr_filter_address.size) c = 0
                            connect(arr_filter_address[c])
                            Timber.e("Connect")
                            if (mAutoConnectionListener != null) mAutoConnectionListener!!.onNewConnection(
                                arr_filter_name[c], arr_filter_address[c]
                            )
                        } else {
                            bcl = null
                            isAutoConnecting = false
                        }
                    }
                }
            }
            setBluetoothConnectionListener(bcl)
            c = 0
            if (mAutoConnectionListener != null) mAutoConnectionListener!!.onNewConnection(
                arr_name[c],
                arr_address[c]
            )
            if (arr_filter_address.size > 0) connect(arr_filter_address[c]) else Timber.e("Device name mismatch")
            //Toast.makeText(mContext, "Device name mismatch", Toast.LENGTH_SHORT).show()
        }
    }

    private fun CoroutineScope.listenForConnectionState(service: BluetoothService) = launch(Dispatchers.Default) {
        service.state.collectLatest {
            mBluetoothStateListener?.onServiceStateChanged(it)
            if (isConnected && it != BluetoothState.CONNECTED) {
                mBluetoothConnectionListener?.onDeviceDisconnected()
                if (isAutoConnectionEnabled) {
                    isAutoConnectionEnabled = false
                    autoConnect(keyword)
                }
                isConnected = false
                connectedDeviceName = null
                connectedDeviceAddress = null
            }
            if (!isConnecting && it == BluetoothState.CONNECTING) {
                isConnecting = true
            } else if (isConnecting) {
                if (it != BluetoothState.CONNECTED) {
                    mBluetoothConnectionListener?.onDeviceConnectionFailed()
                }
                isConnecting = false
            }
        }
    }


    interface BluetoothStateListener {
        fun onServiceStateChanged(state: BluetoothState)
    }

    interface OnDataReceivedListener {
        fun onDataReceived(data: ByteArray, message: String)
    }

    interface BluetoothConnectionListener {
        fun onDeviceConnected(name: String?, address: String?)
        fun onDeviceDisconnected()
        fun onDeviceConnectionFailed()
    }

    interface AutoConnectionListener {
        fun onAutoConnectionStarted()
        fun onNewConnection(name: String?, address: String?)
    }
}