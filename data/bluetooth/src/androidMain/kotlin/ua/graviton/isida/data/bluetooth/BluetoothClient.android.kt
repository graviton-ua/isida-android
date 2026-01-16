package ua.graviton.isida.data.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import co.touchlab.kermit.Logger
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID

@SuppressLint("MissingPermission")
@Inject
@ContributesBinding(AppScope::class)
/**
 * Android implementation of [BluetoothClient] using standard Android Bluetooth APIs.
 * Connects via RFCOMM socket (SPP).
 * @param adapter The system BluetoothAdapter.
 * @param scope Application-bound scope for background operations.
 */
class AndroidBluetoothClient(
    private val adapter: BluetoothAdapter?,
    private val scope: CoroutineScope, // Provide an Application-bound scope
) : BluetoothClient {
    private val logger by lazy { Logger.withTag("AndroidBluetoothClient") }

    // Standard SPP UUID
    private val uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    private val _state = MutableStateFlow(ConnectionState.DISCONNECTED)
    override val state: StateFlow<ConnectionState> = _state.asStateFlow()

    // SharedFlow to broadcast data to multiple subscribers if needed
    private val _incomingData = MutableSharedFlow<ByteArray>(
        replay = 0,
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.SUSPEND
    )
    override val incomingData: Flow<ByteArray> = _incomingData.asSharedFlow()

    private var socket: BluetoothSocket? = null
    private var outputStream: OutputStream? = null

    // Job to track the active reading loop
    private var readJob: Job? = null

    /**
     * Connects to the given Android Bluetooth device.
     * Uses an insecure RFCOMM socket to maximize compatibility.
     * @param address MAC address of the device.
     */
    override suspend fun connect(address: DeviceAddress) = withContext(Dispatchers.IO) {
        if (adapter == null) {
            logger.e { "BluetoothAdapter is null" }
            _state.value = ConnectionState.ERROR
            return@withContext
        }

        try {
            disconnect() // Ensure clean slate
            _state.value = ConnectionState.CONNECTING

            val device = adapter.getRemoteDevice(address.value)
            // Use createInsecure to avoid pairing dialog loop issues on some industrial devices
            val tmpSocket = device.createInsecureRfcommSocketToServiceRecord(uuid)

            logger.d { "Connecting to ${address.value}..." }
            tmpSocket.connect() // Blocking call

            socket = tmpSocket
            outputStream = tmpSocket.outputStream

            _state.value = ConnectionState.CONNECTED
            logger.d { "Connected" }

            // Start reading loop
            startReading(tmpSocket.inputStream)

        } catch (e: IOException) {
            logger.e(e) { "Connection failed" }
            disconnect()
            _state.value = ConnectionState.ERROR
        }
    }

    /**
     * Disconnects the socket and cancels the reading job.
     */
    override suspend fun disconnect(): Unit = withContext(Dispatchers.IO) {
        logger.d { "Disconnecting..." }
        readJob?.cancelAndJoin()
        try {
            socket?.close()
        } catch (e: Exception) {
            logger.w(e) { "Error closing socket" }
        } finally {
            socket = null
            outputStream = null
            _state.value = ConnectionState.DISCONNECTED
        }
    }

    /**
     * Writes bytes to the Bluetooth socket.
     * @param data Data to send.
     */
    override suspend fun send(data: ByteArray) = withContext(Dispatchers.IO) {
        if (_state.value != ConnectionState.CONNECTED) {
            logger.w { "Attempted to send data while not connected" }
            return@withContext
        }

        try {
            outputStream?.write(data)
            outputStream?.flush() // Important for immediate transmission
            logger.d { "Sent: ${data.toHexString()}" } // Use your existing extension
        } catch (e: IOException) {
            logger.e(e) { "Write failed" }
            disconnect()
        }
    }

    private fun startReading(inputStream: InputStream) {
        readJob = scope.launch(Dispatchers.IO) {
            val buffer = ArrayList<Int>()
            val readBuffer = ByteArray(1024)

            // Replicating your original logic: Buffer until 0x0A (LF) and 0x0D (CR)
            try {
                while (isActive) {
                    val bytesRead = inputStream.read(readBuffer) // Blocking read
                    if (bytesRead == -1) throw IOException("EOF")

                    for (i in 0 until bytesRead) {
                        val byteInt = readBuffer[i].toInt() and 0xFF

                        when (byteInt) {
                            0x0A -> { // LF
                                // Check previous byte logic from your original code
                                // Logic: 0x0D then 0x0A means end of message
                                if (buffer.isNotEmpty() && buffer.last() == 0x0D) {
                                    // Add the LF
                                    buffer.add(byteInt)

                                    // Emit packet
                                    val packet = buffer.map { it.toByte() }.toByteArray()
                                    _incomingData.emit(packet)

                                    buffer.clear()
                                } else {
                                    buffer.add(byteInt)
                                }
                            }

                            else -> {
                                buffer.add(byteInt)
                            }
                        }
                    }
                }
            } catch (e: IOException) {
                logger.w(e) { "Read loop error" }
                if (isActive) {
                    disconnect()
                }
            }
        }
    }

    // Helper for logging
    private fun ByteArray.toHexString() = joinToString(" ") { "%02x".format(it) }
}