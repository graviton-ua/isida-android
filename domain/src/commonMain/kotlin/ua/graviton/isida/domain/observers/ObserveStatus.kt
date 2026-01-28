package ua.graviton.isida.domain.observers

import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterIsInstance
import ua.graviton.isida.data.bluetooth.ConnectionState
import ua.graviton.isida.data.protocol.packets.StatusPacket
import ua.graviton.isida.domain.SubjectInteractor
import ua.graviton.isida.domain.bluetooth.DeviceConnectionManager

@Inject
class ObserveStatus(
    private val repo: DeviceConnectionManager,
) : SubjectInteractor<Unit, StatusPacket?>() {

    init {
        invoke(Unit)
    }

    override suspend fun createObservable(params: Unit): Flow<StatusPacket?> {
        return combine(
            repo.packetStream.filterIsInstance<StatusPacket>(),
            repo.connectionState,
        ) { packet, state ->
            if (state != ConnectionState.CONNECTED) return@combine null
            packet
        }
    }
}