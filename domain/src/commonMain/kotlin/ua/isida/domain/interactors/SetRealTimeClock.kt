package ua.isida.domain.interactors

import ua.isida.util.AppCoroutineDispatchers
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import ua.isida.data.protocol.commands.v1.SettingRealTimeClockCommandV1
import ua.isida.data.protocol.packets.ConfirmPacket
import ua.isida.data.protocol.packets.v1.ConfirmPacketV1
import ua.isida.data.serializers.RootEncoder
import ua.isida.data.serializers.v1.SetTableCommandV1Serializer
import ua.isida.data.serializers.v1.SettingRealTimeClockV1Serializer
import ua.isida.domain.ResultInteractor
import ua.isida.domain.bluetooth.DeviceConnectionManager
import kotlin.time.Duration.Companion.seconds

@Inject
class SetRealTimeClock(
    dispatchers: AppCoroutineDispatchers,
    private val manager: DeviceConnectionManager,
) : ResultInteractor<SetRealTimeClock.Params, Result<Unit>>() {
    private val dispatcher = dispatchers.computation

    data class Params(
        val second: Int,
        val minute: Int,
        val hour: Int,
        val day: Int,
        val month: Int,
        val year: Int
    )

    override suspend fun doWork(params: Params): Result<Unit> = withContext(dispatcher) {
        runCatching {
            withTimeout(10.seconds) {
                val responseDeferred = async<ConfirmPacket>(start = CoroutineStart.UNDISPATCHED) {
                    manager.packetStream
                        .filterIsInstance<ConfirmPacket>()
                        .first { packet ->
                            when (packet) {
                                is ConfirmPacketV1 -> packet.commandId == SettingRealTimeClockV1Serializer.commandId
                                else -> false
                            }
                        }
                }

                val command = when (val v = manager.protocolVersion) {
                    1 -> SettingRealTimeClockCommandV1(
                        second = params.second,
                        minute = params.minute,
                        hour = params.hour,
                        day = params.day,
                        month = params.month,
                        year = params.year
                    )
                    else -> throw IllegalStateException("Unsupported protocol version: $v")
                }
                
                val bytes = RootEncoder.serialize(command).getOrThrow()
                manager.sendCommand(bytes)

                responseDeferred.await()
                Unit
            }
        }
    }
}
