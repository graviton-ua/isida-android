package ua.graviton.isida.data.bluetooth

import com.fazecast.jSerialComm.SerialPort
import com.fazecast.jSerialComm.SerialPortDataListener
import com.fazecast.jSerialComm.SerialPortEvent
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    private val _incomingData = MutableSharedFlow<ByteArray>(
        replay = 0,
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    override val incomingData: SharedFlow<ByteArray> = _incomingData.asSharedFlow()

    private var activePort: SerialPort? = null

    // Buffer to hold incoming bytes between different events (since data might arrive split)
    private val parsingBuffer = ArrayList<Int>()

    /**
     * Connects to the specified Serial Port.
     * @param address For JVM, this is the port descriptor (e.g., "COM3" or "/dev/tty.Isida").
     */
    override suspend fun connect(address: DeviceAddress) = withContext(Dispatchers.IO) {
        if (_state.value == ConnectionState.CONNECTED) return@withContext

        // 1. Cleanup previous connection if any
        disconnect()

        _state.value = ConnectionState.CONNECTING

        try {
            // 1. Find the port
            val port = SerialPort.getCommPort(address.value)

            // 2. Configure Port (Standard Bluetooth SPP baud rate is often irrelevant,
            // but 9600 or 115200 is safe default. Set strictly if device requires it).
            port.baudRate = 9600

            // IMPORTANT: Set to NONBLOCKING. 
            // We are relying on the Event Listener, not on inputStream.read() timeouts.
            port.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0)

            val opened = port.openPort()
            if (!opened) {
                _state.value = ConnectionState.ERROR
                // Throw or log specific error
                println("Failed to open port: $address")
                return@withContext
            }

            val listener = object : SerialPortDataListener {
                override fun getListeningEvents(): Int {
                    return SerialPort.LISTENING_EVENT_DATA_RECEIVED or SerialPort.LISTENING_EVENT_PORT_DISCONNECTED
                }

                override fun serialEvent(event: SerialPortEvent) {
                    if (event.eventType == SerialPort.LISTENING_EVENT_PORT_DISCONNECTED) {
                        scope.launch { disconnect() }
                        return
                    }

                    if (event.eventType == SerialPort.LISTENING_EVENT_DATA_RECEIVED) {
                        val newData = event.receivedData
                        if (newData != null && newData.isNotEmpty()) {
                            processIncomingData(newData)
                        }
                    }
                }
            }

            port.addDataListener(listener)
            activePort = port
            _state.value = ConnectionState.CONNECTED
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
     * Processes raw bytes coming from the Event Listener.
     * Maintains the Protocol State Machine (0x55 -> 0x01 ...).
     */
    private fun processIncomingData(data: ByteArray) {
        for (byte in data) {
            val byteInt = byte.toInt() and 0xFF

            if (parsingBuffer.isEmpty()) {
                // Step 1: Wait for 0x55.
                if (byteInt == 0x55) parsingBuffer.add(byteInt)
            } else if (parsingBuffer.size == 1) {
                // Step 2: Check header
                if (byteInt == 0x01) {
                    parsingBuffer.add(byteInt)
                } else if (byteInt == 0x55) {
                    // New start byte, keep just this one
                    parsingBuffer.clear()
                    parsingBuffer.add(byteInt)
                } else {
                    // Invalid, reset
                    parsingBuffer.clear()
                }
            } else {
                parsingBuffer.add(byteInt)
                // Step 3: Check for Footer (CR 0x0D, LF 0x0A)
                if (byteInt == 0x0A) {
                    val size = parsingBuffer.size
                    if (size >= 2 && parsingBuffer[size - 2] == 0x0D) {
                        // Full packet found
                        val packet = parsingBuffer.map { it.toByte() }.toByteArray()

                        // Emit to flow (Need to bridge to Coroutines)
                        scope.launch { _incomingData.emit(packet) }

                        parsingBuffer.clear()
                    }
                }
            }
        }
    }

    private fun closePort() {
        activePort?.let {
            it.removeDataListener() // Stop receiving events
            if (it.isOpen) it.closePort()
        }
        activePort = null
        parsingBuffer.clear() // Reset buffer on disconnect
    }
}