package ua.graviton.isida.domain.interactors

import ua.isida.util.AppCoroutineDispatchers
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.withContext
import ua.graviton.isida.data.protocol.commands.IsidaCommand
import ua.graviton.isida.data.serializers.RootEncoder
import ua.graviton.isida.domain.ResultInteractor
import ua.graviton.isida.domain.bluetooth.DeviceConnectionManager

@Inject
class SendCommand(
    private val dispatchers: AppCoroutineDispatchers,
    private val manager: DeviceConnectionManager,
) : ResultInteractor<SendCommand.Params, Result<Unit>>() {

    override suspend fun doWork(params: Params): Result<Unit> = withContext(dispatchers.io) {
        RootEncoder.serialize(params.command)
            .mapCatching { byteArray -> manager.sendCommand(byteArray) }
    }

    suspend operator fun invoke(command: IsidaCommand) = executeSync(Params(command))

    data class Params(val command: IsidaCommand)
}