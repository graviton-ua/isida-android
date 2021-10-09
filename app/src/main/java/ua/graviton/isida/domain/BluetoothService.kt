package ua.graviton.isida.domain

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.os.Handler
import android.util.Log
import androidx.core.os.bundleOf
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*

@SuppressLint("NewApi")
class BluetoothService(context: Context?, handler: Handler) {
    // Member fields
    private val mAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    private val mHandler: Handler
    private var mSecureAcceptThread: AcceptThread? = null
    private var mConnectThread: ConnectThread? = null
    private var mConnectedThread: ConnectedThread? = null
    private var mState: Int
    private var isAndroid: Boolean = BluetoothState.DEVICE_ANDROID// Give the new state to the Handler so the UI Activity can update

    // Set the current state of the chat connection
    // state : An integer defining the current connection state
    // Return the current connection state.
    @get:Synchronized
    @set:Synchronized
    var state: Int
        get() = mState
        private set(state) {
            Log.d(TAG, "setState() $mState -> $state")
            mState = state

            // Give the new state to the Handler so the UI Activity can update
            mHandler.obtainMessage(BluetoothState.MESSAGE_STATE_CHANGE, state, -1).sendToTarget()
        }

    // Start the chat service. Specifically start AcceptThread to begin a
    // session in listening (server) mode. Called by the Activity onResume()
    @Synchronized
    fun start(isAndroid: Boolean) {
        // Cancel any thread attempting to make a connection
        if (mConnectThread != null) {
            mConnectThread!!.cancel()
            mConnectThread = null
        }
        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
            mConnectedThread!!.cancel()
            mConnectedThread = null
        }
        state = BluetoothState.STATE_LISTEN

        // Start the thread to listen on a BluetoothServerSocket
        if (mSecureAcceptThread == null) {
            mSecureAcceptThread = AcceptThread(isAndroid)
            mSecureAcceptThread!!.start()
            this@BluetoothService.isAndroid = isAndroid
        }
    }

    // Start the ConnectThread to initiate a connection to a remote device
    // device : The BluetoothDevice to connect
    // secure : Socket Security type - Secure (true) , Insecure (false)
    @Synchronized
    fun connect(device: BluetoothDevice) {
        // Cancel any thread attempting to make a connection
        if (mState == BluetoothState.STATE_CONNECTING) {
            if (mConnectThread != null) {
                mConnectThread!!.cancel()
                mConnectThread = null
            }
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
            mConnectedThread!!.cancel()
            mConnectedThread = null
        }

        // Start the thread to connect with the given device
        mConnectThread = ConnectThread(device)
        mConnectThread!!.start()
        state = BluetoothState.STATE_CONNECTING
    }

    /**
     * Start the ConnectedThread to begin managing a Bluetooth connection
     * @param socket  The BluetoothSocket on which the connection was made
     * @param device  The BluetoothDevice that has been connected
     */
    @Synchronized
    fun connected(socket: BluetoothSocket, device: BluetoothDevice, socketType: String?) {
        // Cancel the thread that completed the connection
        if (mConnectThread != null) {
            mConnectThread!!.cancel()
            mConnectThread = null
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
            mConnectedThread!!.cancel()
            mConnectedThread = null
        }

        // Cancel the accept thread because we only want to connect to one device
        if (mSecureAcceptThread != null) {
            mSecureAcceptThread!!.cancel()
            mSecureAcceptThread = null
        }

        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = ConnectedThread(socket, socketType).apply { start() }

        // Send the name of the connected device back to the UI Activity
        val msg = mHandler.obtainMessage(BluetoothState.MESSAGE_DEVICE_NAME)
        msg.data = bundleOf(
            BluetoothState.DEVICE_NAME to device.name,
            BluetoothState.DEVICE_ADDRESS to device.address,
        )
        mHandler.sendMessage(msg)
        state = BluetoothState.STATE_CONNECTED
    }

    // Stop all threads
    @Synchronized
    fun stop() {
        if (mConnectThread != null) {
            mConnectThread!!.cancel()
            mConnectThread = null
        }
        if (mConnectedThread != null) {
            mConnectedThread!!.cancel()
            mConnectedThread = null
        }
        if (mSecureAcceptThread != null) {
            mSecureAcceptThread!!.cancel()
            mSecureAcceptThread!!.kill()
            mSecureAcceptThread = null
        }
        state = BluetoothState.STATE_NONE
    }

    // Write to the ConnectedThread in an unsynchronized manner
    // out : The bytes to write
    fun write(out: ByteArray?) {
        // Create temporary object
        var r: ConnectedThread?
        // Synchronize a copy of the ConnectedThread
        synchronized(this) {
            if (mState != BluetoothState.STATE_CONNECTED) return
            r = mConnectedThread
        }
        // Perform the write unsynchronized
        r!!.write(out)
    }

    // Indicate that the connection attempt failed and notify the UI Activity
    private fun connectionFailed() {
        // Start the service over to restart listening mode
        start(isAndroid)
    }

    // Indicate that the connection was lost and notify the UI Activity
    private fun connectionLost() {
        // Start the service over to restart listening mode
        start(isAndroid)
    }

    // This thread runs while listening for incoming connections. It behaves
    // like a server-side client. It runs until a connection is accepted
    // (or until cancelled)
    private inner class AcceptThread(isAndroid: Boolean) : Thread() {
        // The local server socket
        private var mmServerSocket: BluetoothServerSocket?
        private val mSocketType: String? = null
        var isRunning = true
        override fun run() {
            name = "AcceptThread$mSocketType"
            var socket: BluetoothSocket? = null

            // Listen to the server socket if we're not connected
            while (mState != BluetoothState.STATE_CONNECTED && isRunning) {
                socket = try {
                    // This is a blocking call and will only return on a
                    // successful connection or an exception
                    mmServerSocket!!.accept()
                } catch (e: IOException) {
                    break
                }

                // If a connection was accepted
                if (socket != null) {
                    synchronized(this@BluetoothService) {
                        when (mState) {
                            BluetoothState.STATE_LISTEN, BluetoothState.STATE_CONNECTING ->                                 // Situation normal. Start the connected thread.
                                connected(
                                    socket, socket.remoteDevice,
                                    mSocketType
                                )
                            BluetoothState.STATE_NONE, BluetoothState.STATE_CONNECTED ->                                 // Either not ready or already connected. Terminate new socket.
                                try {
                                    socket.close()
                                } catch (e: IOException) {
                                }
                        }
                    }
                }
            }
        }

        fun cancel() {
            try {
                mmServerSocket!!.close()
                mmServerSocket = null
            } catch (e: IOException) {
            }
        }

        fun kill() {
            isRunning = false
        }

        init {
            var tmp: BluetoothServerSocket? = null

            // Create a new listening server socket
            try {
                tmp = if (isAndroid) mAdapter.listenUsingRfcommWithServiceRecord(
                    NAME_SECURE,
                    UUID_ANDROID_DEVICE
                ) else mAdapter.listenUsingRfcommWithServiceRecord(
                    NAME_SECURE, UUID_OTHER_DEVICE
                )
            } catch (e: IOException) {
            }
            mmServerSocket = tmp
        }
    }

    // This thread runs while attempting to make an outgoing connection
    // with a device. It runs straight through
    // the connection either succeeds or fails
    private inner class ConnectThread(private val mmDevice: BluetoothDevice) : Thread() {
        private val mmSocket: BluetoothSocket?
        private val mSocketType: String? = null
        override fun run() {
            // Always cancel discovery because it will slow down a connection
            mAdapter.cancelDiscovery()

            // Make a connection to the BluetoothSocket
            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                mmSocket!!.connect()
            } catch (e: IOException) {
                // Close the socket
                try {
                    mmSocket!!.close()
                } catch (e2: IOException) {
                }
                connectionFailed()
                return
            }

            // Reset the ConnectThread because we're done
            synchronized(this@BluetoothService) { mConnectThread = null }

            // Start the connected thread
            connected(mmSocket, mmDevice, mSocketType)
        }

        fun cancel() {
            try {
                mmSocket!!.close()
            } catch (e: IOException) {
            }
        }

        init {
            var tmp: BluetoothSocket? = null

            // Get a BluetoothSocket for a connection with the
            // given BluetoothDevice
            try {
                tmp =
                    if (isAndroid) mmDevice.createRfcommSocketToServiceRecord(UUID_ANDROID_DEVICE) else mmDevice.createRfcommSocketToServiceRecord(
                        UUID_OTHER_DEVICE
                    )
            } catch (e: IOException) {
            }
            mmSocket = tmp
        }
    }

    // This thread runs during a connection with a remote device.
    // It handles all incoming and outgoing transmissions.
    private inner class ConnectedThread(
        private val mmSocket: BluetoothSocket,
        socketType: String?
    ) : Thread() {
        private val mmInStream: InputStream?
        private val mmOutStream: OutputStream?
        override fun run() {
            var buffer: ByteArray
            var arr_byte = ArrayList<Int>()

            // Keep listening to the InputStream while connected
            while (true) {
                try {
                    val data = mmInStream!!.read()
                    if (data == 0x0A) {
                    } else if (data == 0x0D) {
                        buffer = ByteArray(arr_byte.size)
                        for (i in arr_byte.indices) {
                            buffer[i] = arr_byte[i].toByte()
                        }
                        // Send the obtained bytes to the UI Activity
                        mHandler.obtainMessage(
                            BluetoothState.MESSAGE_READ, buffer.size, -1, buffer
                        ).sendToTarget()
                        arr_byte = ArrayList()
                    } else {
                        arr_byte.add(data)
                    }
                } catch (e: IOException) {
                    connectionLost()
                    // Start the service over to restart listening mode
                    this@BluetoothService.start(isAndroid)
                    break
                }
            }
        }

        // Write to the connected OutStream.
        // @param buffer  The bytes to write
        fun write(buffer: ByteArray?) {
            try { /*
                byte[] buffer2 = new byte[buffer.length + 2];
                for(int i = 0 ; i < buffer.length ; i++)
                    buffer2[i] = buffer[i];
                buffer2[buffer2.length - 2] = 0x0A;
                buffer2[buffer2.length - 1] = 0x0D;*/
                mmOutStream!!.write(buffer)
                // Share the sent message back to the UI Activity
                mHandler.obtainMessage(
                    BluetoothState.MESSAGE_WRITE, -1, -1, buffer
                ).sendToTarget()
            } catch (e: IOException) {
            }
        }

        fun cancel() {
            try {
                mmSocket.close()
            } catch (e: IOException) {
            }
        }

        init {
            var tmpIn: InputStream? = null
            var tmpOut: OutputStream? = null

            // Get the BluetoothSocket input and output streams
            try {
                tmpIn = mmSocket.inputStream
                tmpOut = mmSocket.outputStream
            } catch (e: IOException) {
            }
            mmInStream = tmpIn
            mmOutStream = tmpOut
        }
    }

    companion object {
        // Debugging
        private const val TAG = "Bluetooth Service"

        // Name for the SDP record when creating server socket
        private const val NAME_SECURE = "Bluetooth Secure"

        // Unique UUID for this application
        private val UUID_ANDROID_DEVICE = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66")
        private val UUID_OTHER_DEVICE = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    }

    // Constructor. Prepares a new BluetoothChat session
    // context : The UI Activity Context
    // handler : A Handler to send messages back to the UI Activity
    init {
        mState = BluetoothState.STATE_NONE
        mHandler = handler
    }
}