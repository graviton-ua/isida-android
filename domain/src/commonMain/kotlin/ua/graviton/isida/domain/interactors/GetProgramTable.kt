package ua.graviton.isida.domain.interactors

import ua.isida.util.AppCoroutineDispatchers
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import ua.graviton.isida.data.protocol.commands.RequestTableCommand
import ua.graviton.isida.data.protocol.commands.v1.RequestTableCommandV1
import ua.graviton.isida.data.protocol.packets.TablePacket
import ua.graviton.isida.data.serializers.RootEncoder
import ua.graviton.isida.domain.ResultInteractor
import ua.graviton.isida.domain.bluetooth.DeviceConnectionManager
import kotlin.time.Duration.Companion.seconds

@Inject
class GetProgramTable(
    dispatchers: AppCoroutineDispatchers,
    private val manager: DeviceConnectionManager,
) : ResultInteractor<Int, Result<TablePacket>>() {
    private val dispatcher = dispatchers.computation

    override suspend fun doWork(params: Int): Result<TablePacket> = withContext(dispatcher) {
        runCatching {
            withTimeout(10.seconds) {
                // 1. Start listener UNDISPATCHED. This ensures the Flow subscription 
                // happens IMMEDIATELY on this thread before we move to the next line.
                val responseDeferred = async(start = CoroutineStart.UNDISPATCHED) {
                    manager.packetStream
                        .filterIsInstance<TablePacket>()
                        .first()
                }

                // 2. Now we are 100% sure we are listening, send the command.
                val v = manager.protocolVersion
                val command: RequestTableCommand = when (v) {
                    1 -> RequestTableCommandV1(number = params)
                    else -> throw IllegalStateException("Unsupported protocol version: $v")
                }
                val bytes = RootEncoder.serialize(command).getOrThrow()
                manager.sendCommand(bytes)

                // 3. Await the response.
                responseDeferred.await()
            }
        }
    }

    suspend fun byNumber(number: Int) = executeSync(number)
}