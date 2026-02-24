package ua.graviton.isida.domain.interactors

import ua.isida.util.AppCoroutineDispatchers
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import ua.graviton.isida.data.protocol.commands.SetTableCommand
import ua.graviton.isida.data.protocol.commands.v1.SetTableCommandV1
import ua.graviton.isida.data.protocol.packets.ConfirmPacket
import ua.graviton.isida.data.protocol.packets.TablePacket
import ua.graviton.isida.data.protocol.packets.v1.ConfirmPacketV1
import ua.graviton.isida.data.protocol.packets.v1.TablePacketV1
import ua.graviton.isida.data.serializers.RootEncoder
import ua.graviton.isida.data.serializers.v1.SetTableCommandV1Serializer
import ua.graviton.isida.domain.ResultInteractor
import ua.graviton.isida.domain.bluetooth.DeviceConnectionManager
import kotlin.time.Duration.Companion.seconds

@Inject
class UpdateProgramTable(
    dispatchers: AppCoroutineDispatchers,
    private val manager: DeviceConnectionManager,
) : ResultInteractor<UpdateProgramTable.Params, Result<Unit>>() {
    private val dispatcher = dispatchers.computation

    override suspend fun doWork(params: Params): Result<Unit> = withContext(dispatcher) {
        runCatching {
            withTimeout(10.seconds) {
                // 1. Start listener UNDISPATCHED. This ensures the Flow subscription
                // happens IMMEDIATELY on this thread before we move to the next line.
                val responseDeferred = async(start = CoroutineStart.UNDISPATCHED) {
                    manager.packetStream
                        .filterIsInstance<ConfirmPacket>()
                        .first { packet ->
                            when (packet) {
                                is ConfirmPacketV1 -> packet.commandId == SetTableCommandV1Serializer.commandId
                                else -> false
                            }
                        }
                }

                val command: SetTableCommand = when (params.table) {
                    is TablePacketV1 -> SetTableCommandV1(number = params.number, days = params.table.days)
                    else -> throw IllegalStateException("Unsupported table type: ${params.table::class}")
                }
                val bytes = RootEncoder.serialize(command).getOrThrow()
                manager.sendCommand(bytes)

                // 3. Await the response.
                responseDeferred.await()
                Unit
            }
        }
    }

    data class Params(val number: Int, val table: TablePacket)
}