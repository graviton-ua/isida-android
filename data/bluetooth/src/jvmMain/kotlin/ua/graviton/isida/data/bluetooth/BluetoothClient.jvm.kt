package ua.graviton.isida.data.bluetooth

import com.fazecast.jSerialComm.SerialPort
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.IOException

@Inject
@ContributesBinding(AppScope::class)
class JvmBluetoothDriver(
    private val scope: CoroutineScope
) : BluetoothClient {

    // Internal State
    private val _state = MutableStateFlow(ConnectionState.DISCONNECTED)
    override val state: StateFlow<ConnectionState> = _state.asStateFlow()

    private val _incomingData = MutableSharedFlow<ByteArray>(replay = 0)
    override val incomingData: Flow<ByteArray> = _incomingData.asSharedFlow()

    private var activePort: SerialPort? = null
    private var readJob: Job? = null

    /**
     * @param address For JVM Serial, this is effectively the Port Descriptor
     * (e.g., "COM3" on Windows or "/dev/tty.Isida" on Mac/Linux).
     */
    override suspend fun connect(address: DeviceAddress) = withContext(Dispatchers.IO) {
        if (_state.value == ConnectionState.CONNECTED) return@withContext

        _state.value = ConnectionState.CONNECTING

        try {
            // 1. Find the port
            val port = SerialPort.getCommPort(address.value)

            // 2. Configure Port (Standard Bluetooth SPP baud rate is often irrelevant,
            // but 9600 or 115200 is safe default. Set strictly if device requires it).
            port.baudRate = 9600

            // 3. Open Port
            // TIMEOUT_READ_BLOCKING is crucial so our coroutine loop waits for data
            port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 1000, 0)

            val opened = port.openPort()
            if (!opened) {
                _state.value = ConnectionState.ERROR
                // Throw or log specific error
                println("Failed to open port: $address")
                return@withContext
            }

            activePort = port
            _state.value = ConnectionState.CONNECTED

            // 4. Start Reading Loop
            startReading(port)

        } catch (e: Exception) {
            e.printStackTrace()
            closePort()
            _state.value = ConnectionState.ERROR
        }
    }

    override suspend fun disconnect() = withContext(Dispatchers.IO) {
        closePort()
        _state.value = ConnectionState.DISCONNECTED
    }

    override suspend fun send(data: ByteArray) = withContext(Dispatchers.IO) {
        val port = activePort ?: return@withContext
        if (!port.isOpen) {
            _state.value = ConnectionState.DISCONNECTED
            return@withContext
        }

        try {
            // Write bytes to the output stream
            val bytesWritten = port.writeBytes(data, data.size)
            if (bytesWritten == -1) throw IOException("Write failed")
        } catch (e: Exception) {
            e.printStackTrace()
            // If write fails, the connection is likely dead
            disconnect()
        }
    }

    /**
     * Starts a coroutine that continuously reads from the InputStream.
     * It uses [Dispatchers.IO] to handle blocking calls efficiently.
     */
    private fun startReading(port: SerialPort) {
        readJob?.cancel()
        readJob = scope.launch(Dispatchers.IO) {
            val buffer = ByteArray(1024) // Adjust buffer size as needed
            val inputStream = port.inputStream

            try {
                while (isActive && port.isOpen) {
                    // This call blocks until at least 1 byte is available
                    // or timeout (if set) occurs.
                    val available = inputStream.available()
                    if (available > 0) {
                        // Read exactly what is available to avoid blocking unnecessarily
                        val bytesRead = inputStream.read(buffer, 0, minOf(buffer.size, available))

                        if (bytesRead > 0) {
                            // Copy only the valid bytes
                            val actualData = buffer.copyOf(bytesRead)
                            _incomingData.emit(actualData)
                        } else if (bytesRead == -1) {
                            // End of stream
                            break
                        }
                    } else {
                        // Small delay to prevent CPU spinning if implementation
                        // doesn't block perfectly on .available()
                        delay(10)
                    }
                }
            } catch (e: IOException) {
                // Connection lost
                e.printStackTrace()
            } finally {
                // If loop exits, ensure we clean up if we aren't already disconnected manually
                if (_state.value == ConnectionState.CONNECTED) {
                    withContext(Dispatchers.Main) { disconnect() }
                }
            }
        }
    }

    private fun closePort() {
        readJob?.cancel()
        readJob = null

        activePort?.let {
            if (it.isOpen) it.closePort()
        }
        activePort = null
    }
}