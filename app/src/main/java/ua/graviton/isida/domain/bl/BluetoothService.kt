package ua.graviton.isida.domain.bl

import android.bluetooth.*
import android.os.Handler
import androidx.core.os.bundleOf
import timber.log.Timber
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*

class BluetoothService(
    private val adapter: BluetoothAdapter,
    private val handler: Handler,
) {
    // Member fields
    private var mConnectThread: ConnectThread? = null
    private var mConnectedThread: ConnectedThread? = null
    private var mState: Int = BluetoothState.STATE_IDLE

    // Set the current state of the chat connection
    // state : An integer defining the current connection state
    // Return the current connection state.
    @get:Synchronized
    @set:Synchronized
    var state: Int
        get() = mState
        private set(state) {
            Timber.d("setState() $mState -> $state")
            mState = state

            // Give the new state to the Handler so the UI Activity can update
            handler.obtainMessage(BluetoothState.MESSAGE_STATE_CHANGE, state, -1).sendToTarget()
        }


    // Start the ConnectThread to initiate a connection to a remote device
    // device : The BluetoothDevice to connect
    // secure : Socket Security type - Secure (true) , Insecure (false)
    @Synchronized
    fun connect(device: BluetoothDevice) {
        // Cancel any thread attempting to make a connection
        if (mState == BluetoothState.STATE_CONNECTING) {
            mConnectThread?.cancel()
            mConnectThread = null
        }

        // Cancel any thread currently running a connection
        mConnectedThread?.cancel()
        mConnectedThread = null

        // Start the thread to connect with the given device
        mConnectThread = ConnectThread(device).apply { start() }
        state = BluetoothState.STATE_CONNECTING
    }

    /**
     * Start the ConnectedThread to begin managing a Bluetooth connection
     * @param socket  The BluetoothSocket on which the connection was made
     * @param device  The BluetoothDevice that has been connected
     */
    @Synchronized
    fun connected(socket: BluetoothSocket, device: BluetoothDevice) {
        // Cancel the thread that completed the connection
        mConnectThread?.cancel()
        mConnectThread = null

        // Cancel any thread currently running a connection
        mConnectedThread?.cancel()
        mConnectedThread = null

        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = ConnectedThread(socket).apply { start() }

        // Send the name of the connected device back to the UI Activity
        val msg = handler.obtainMessage(BluetoothState.MESSAGE_DEVICE_NAME)
        msg.data = bundleOf(
            BluetoothState.DEVICE_NAME to device.name,
            BluetoothState.DEVICE_ADDRESS to device.address,
        )
        handler.sendMessage(msg)
        state = BluetoothState.STATE_CONNECTED
    }

    // Stop all threads
    @Synchronized
    fun stop() {
        mConnectThread?.cancel()
        mConnectThread = null

        mConnectedThread?.cancel()
        mConnectedThread = null

        state = BluetoothState.STATE_IDLE
    }

    // Write to the ConnectedThread in an unsynchronized manner
    // out : The bytes to write
    fun write(out: ByteArray) {
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
    private fun connectionFailed() = Unit

    // Indicate that the connection was lost and notify the UI Activity
    private fun connectionLost() = Unit


    // This thread runs while attempting to make an outgoing connection
    // with a device. It runs straight through
    // the connection either succeeds or fails
    private inner class ConnectThread(private val device: BluetoothDevice) : Thread() {

        private val mmSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
            device.createRfcommSocketToServiceRecord(UUID_ISIDA_DEVICE)
        }

        override fun run() {
            // Always cancel discovery because it will slow down a connection
            adapter.cancelDiscovery()

            // Make a connection to the BluetoothSocket
            mmSocket?.let { socket ->
                try {
                    // Connect to the remote device through the socket. This call blocks
                    // until it succeeds or throws an exception.
                    socket.connect()

                    // The connection attempt succeeded. Perform work associated with
                    // the connection in a separate thread.
                    connected(socket, device)
                } catch (e: IOException) {
                    Timber.w(e, "Could not connect the client socket")
                    connectionFailed()
                }
            }

            // Reset the ConnectThread because we're done
            synchronized(this@BluetoothService) { mConnectThread = null }
        }

        fun cancel() {
            try {
                mmSocket?.close()
            } catch (e: IOException) {
                Timber.w(e, "Could not close the client socket")
            }
        }
    }

    // This thread runs during a connection with a remote device.
    // It handles all incoming and outgoing transmissions.
    private inner class ConnectedThread(private val mmSocket: BluetoothSocket) : Thread() {

        private val mmInStream: InputStream = mmSocket.inputStream
        private val mmOutStream: OutputStream = mmSocket.outputStream

        override fun run() {
            var buffer: ByteArray
            var arr_byte = ArrayList<Int>()

            // Keep listening to the InputStream until an exception occurs.
            while (true) {
                try {
                    when (val data = mmInStream.read()) {
                        0x0A -> Unit    // Do nothing
                        0x0D -> {
                            buffer = ByteArray(arr_byte.size)
                            for (i in arr_byte.indices) {
                                buffer[i] = arr_byte[i].toByte()
                            }
                            // Send the obtained bytes to the UI Activity
                            handler.obtainMessage(BluetoothState.MESSAGE_READ, buffer.size, -1, buffer).sendToTarget()
                            arr_byte = ArrayList()
                        }
                        else -> arr_byte.add(data)
                    }
                } catch (e: IOException) {
                    connectionLost()
                    break
                }
            }
        }

        // Call this from the main activity to send data to the remote device.
        fun write(bytes: ByteArray) {
            try { /*
                byte[] buffer2 = new byte[buffer.length + 2];
                for(int i = 0 ; i < buffer.length ; i++)
                    buffer2[i] = buffer[i];
                buffer2[buffer2.length - 2] = 0x0A;
                buffer2[buffer2.length - 1] = 0x0D;*/
                mmOutStream.write(bytes)
            } catch (e: IOException) {
                Timber.w(e, "Error occurred when sending data")

                // Send a failure message back to the activity.
                val writeErrorMsg = handler.obtainMessage(BluetoothState.MESSAGE_TOAST)
                writeErrorMsg.data = bundleOf("toast" to "Couldn't send data to the other device")
                handler.sendMessage(writeErrorMsg)
                return
            }

            // Share the sent message back to the UI Activity
            handler.obtainMessage(BluetoothState.MESSAGE_WRITE, -1, -1, bytes).sendToTarget()
        }

        // Call this method from the main activity to shut down the connection.
        fun cancel() {
            try {
                mmSocket.close()
            } catch (e: IOException) {
                Timber.w(e, "Could not close the connect socket")
            }
        }
    }


    companion object {
        // Unique UUID for this application
        private val UUID_ISIDA_DEVICE = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66")
        private val UUID_OTHER_DEVICE = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    }
}
