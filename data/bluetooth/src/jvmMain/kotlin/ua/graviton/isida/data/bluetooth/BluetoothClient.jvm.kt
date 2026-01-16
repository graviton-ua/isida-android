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
/**
 * JVM/Desktop implementation of [BluetoothClient] using jSerialComm.
 * Connects to standard Serial ports (COM3, /dev/ttyUSB0, etc.) which are often mapped to Bluetooth SPP devices.
 * @param scope CoroutineScope for managing background IO operations.
 */
class JvmBluetoothDriver(
    private val scope: CoroutineScope
) : BluetoothClient {

    // Internal State
    private val _state = MutableStateFlow(ConnectionState.DISCONNECTED)
    override val state: StateFlow<ConnectionState> = _state.asStateFlow()

    private val _incomingData = MutableSharedFlow<ByteArray>(replay = 0)
    override val incomingData: SharedFlow<ByteArray> = _incomingData.asSharedFlow()

    private var activePort: SerialPort? = null
    private var readJob: Job? = null

    /**
     * Connects to the specified Serial Port.
     * @param address For JVM, this is the port descriptor (e.g., "COM3" or "/dev/tty.Isida").
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

    /**
     * Closes the serial port and cancels reading.
     */
    override suspend fun disconnect() = withContext(Dispatchers.IO) {
        closePort()
        _state.value = ConnectionState.DISCONNECTED
    }

    /**
     * Writes bytes to the open serial port.
     * @param data Data to send.
     */
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
            val buffer = ArrayList<Int>() // Accumulation buffer
            val readBuffer = ByteArray(1024) // Raw read buffer
            val inputStream = port.inputStream

            try {
                while (isActive && port.isOpen) {
                    // This call blocks until at least 1 byte is available
                    // or timeout (if set) occurs.
                    val bytesRead = inputStream.read(readBuffer)

                    if (bytesRead > 0) {
                        for (i in 0 until bytesRead) {
                            val byteInt = readBuffer[i].toInt() and 0xFF // Convert to unsigned int

                            // Logic: Buffer until 0x0A (LF) and 0x0D (CR)
                            if (byteInt == 0x0A) {
                                // Check previous byte logic
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
                            } else {
                                buffer.add(byteInt)
                            }
                        }
                    } else if (bytesRead == -1) {
                        // End of stream
                        break
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