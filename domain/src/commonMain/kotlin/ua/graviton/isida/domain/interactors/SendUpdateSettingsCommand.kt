package ua.graviton.isida.domain.interactors

import com.whoppah.util.AppCoroutineDispatchers
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.withContext
import ua.graviton.isida.data.protocol.commands.v1.UpdateSettingsCommandV1
import ua.graviton.isida.data.protocol.packets.StatusPacket
import ua.graviton.isida.data.protocol.packets.v1.StatusPacketV1
import ua.graviton.isida.data.serializers.RootEncoder
import ua.graviton.isida.domain.ResultInteractor
import ua.graviton.isida.domain.bluetooth.DeviceConnectionManager
import ua.graviton.isida.domain.models.DeviceProperty

@Inject
class SendUpdateSettingsCommand(
    private val dispatchers: AppCoroutineDispatchers,
    private val manager: DeviceConnectionManager,
) : ResultInteractor<SendUpdateSettingsCommand.Params, Result<Unit>>() {

    override suspend fun doWork(params: Params): Result<Unit> = withContext(dispatchers.io) {
        val props = params.props
        val snapshot = when (params.deviceDataSnapshot) {
            is StatusPacketV1 -> params.deviceDataSnapshot
            else -> throw IllegalArgumentException("Unsupported packet type")
        }

        val command = UpdateSettingsCommandV1(
            spT0 = props.findIsInstance<DeviceProperty.SpT0>()?.value ?: snapshot.spT0,
            spT1 = props.findIsInstance<DeviceProperty.SpT1>()?.value ?: snapshot.spT1,
            spRh0 = props.findIsInstance<DeviceProperty.SpRh0>()?.value ?: snapshot.spRh0,
            spRh1 = props.findIsInstance<DeviceProperty.SpRh1>()?.value ?: snapshot.spRh1,
            pkoff0 = props.findIsInstance<DeviceProperty.Pkoff0>()?.value ?: snapshot.pkoff0,
            pkoff1 = props.findIsInstance<DeviceProperty.Pkoff1>()?.value ?: snapshot.pkoff1,
            ikoff0 = props.findIsInstance<DeviceProperty.Ikoff0>()?.value ?: snapshot.ikoff0,
            ikoff1 = props.findIsInstance<DeviceProperty.Ikoff1>()?.value ?: snapshot.ikoff1,
            minRun = props.findIsInstance<DeviceProperty.MinRun>()?.value ?: snapshot.minRun,
            maxRun = props.findIsInstance<DeviceProperty.MaxRun>()?.value ?: snapshot.maxRun,
            period = props.findIsInstance<DeviceProperty.Period>()?.value ?: snapshot.period,
            timer0 = props.findIsInstance<DeviceProperty.Timer0>()?.value ?: snapshot.timer0,
            timer1 = props.findIsInstance<DeviceProperty.Timer1>()?.value ?: snapshot.timer1,
            alarm0 = props.findIsInstance<DeviceProperty.Alarm0>()?.value ?: snapshot.alarm0,
            alarm1 = props.findIsInstance<DeviceProperty.Alarm1>()?.value ?: snapshot.alarm1,
            extOn0 = props.findIsInstance<DeviceProperty.ExtOn0>()?.value ?: snapshot.extOn0,
            extOn1 = props.findIsInstance<DeviceProperty.ExtOn1>()?.value ?: snapshot.extOn1,
            extOff0 = props.findIsInstance<DeviceProperty.ExtOff0>()?.value ?: snapshot.extOff0,
            extOff1 = props.findIsInstance<DeviceProperty.ExtOff1>()?.value ?: snapshot.extOff1,
            air0 = props.findIsInstance<DeviceProperty.Air0>()?.value ?: snapshot.air0,
            air1 = props.findIsInstance<DeviceProperty.Air1>()?.value ?: snapshot.air1,
            spCO2 = props.findIsInstance<DeviceProperty.SpCO2>()?.value ?: snapshot.spCO2,
            deviceNumber = props.findIsInstance<DeviceProperty.DeviceNumber>()?.value ?: snapshot.node,
            state = props.findIsInstance<DeviceProperty.State>()?.value ?: snapshot.state,
            extendMode = props.findIsInstance<DeviceProperty.ExtendMode>()?.value ?: snapshot.extendMode,
            relayMode = props.findIsInstance<DeviceProperty.RelayMode>()?.value ?: snapshot.relayMode,
            programm = props.findIsInstance<DeviceProperty.Program>()?.value ?: snapshot.programm,
            hysteresis = props.findIsInstance<DeviceProperty.Hysteresis>()?.value ?: snapshot.hysteresis,
            turnTime = props.findIsInstance<DeviceProperty.TurnTime>()?.value ?: snapshot.turnTime,
        )

        RootEncoder.serialize(command)
            .mapCatching { byteArray -> manager.sendCommand(byteArray) }
    }

    suspend operator fun invoke(
        deviceNumber: Int,
        deviceDataSnapshot: StatusPacket,
        props: List<DeviceProperty<*>>,
    ) = executeSync(Params(deviceNumber, deviceDataSnapshot, props))

    suspend operator fun invoke(
        deviceNumber: Int,
        deviceDataSnapshot: StatusPacket,
        vararg props: DeviceProperty<*>,
    ) = executeSync(Params(deviceNumber, deviceDataSnapshot, props.toList()))

    data class Params(
        val deviceNumber: Int,
        val deviceDataSnapshot: StatusPacket,
        val props: List<DeviceProperty<*>>,
    )
}

private inline fun <reified R> Collection<*>.findIsInstance(): R? = filterIsInstance<R>().firstOrNull()