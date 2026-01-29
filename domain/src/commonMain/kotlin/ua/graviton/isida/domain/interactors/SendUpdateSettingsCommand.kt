package ua.graviton.isida.domain.interactors

import com.whoppah.extensions.asByteArray
import com.whoppah.util.AppCoroutineDispatchers
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.withContext
import ua.graviton.isida.data.protocol.packets.StatusPacket
import ua.graviton.isida.data.serializers.RootEncoder
import ua.graviton.isida.domain.ResultInteractor
import ua.graviton.isida.domain.bluetooth.DeviceConnectionManager
import ua.graviton.isida.domain.findIsInstance
import ua.graviton.isida.domain.models.DeviceProperty

@Inject
class SendUpdateSettingsCommand(
    private val dispatchers: AppCoroutineDispatchers,
    private val manager: DeviceConnectionManager,
) : ResultInteractor<SendUpdateSettingsCommand.Params, Result<Unit>>() {

    override suspend fun doWork(params: Params): Result<Unit> = withContext(dispatchers.io) {
        val de

        val spT0 = ((props.findIsInstance<DeviceProperty.SpT0>()?.value ?: deviceDataSnapshot.spT0) * 10).toInt().toShort().asByteArray()
        val spT1 = ((props.findIsInstance<DeviceProperty.SpT1>()?.value ?: deviceDataSnapshot.spT1) * 10).toInt().toShort().asByteArray()
        val spRh0 = ((props.findIsInstance<DeviceProperty.SpRh0>()?.value ?: deviceDataSnapshot.spRh0) * 10).toInt().toShort().asByteArray()
        val spRh1 = ((props.findIsInstance<DeviceProperty.SpRh1>()?.value ?: deviceDataSnapshot.spRh1) * 10).toInt().toShort().asByteArray()
        val k0 = (props.findIsInstance<DeviceProperty.K0>()?.value ?: deviceDataSnapshot.pkoff0).toShort().asByteArray()
        val k1 = (props.findIsInstance<DeviceProperty.K1>()?.value ?: deviceDataSnapshot.pkoff1).toShort().asByteArray()
        val ti0 = (props.findIsInstance<DeviceProperty.Ti0>()?.value ?: deviceDataSnapshot.ikoff0).toShort().asByteArray()
        val ti1 = (props.findIsInstance<DeviceProperty.Ti1>()?.value ?: deviceDataSnapshot.ikoff1).toShort().asByteArray()
        val minRun = (props.findIsInstance<DeviceProperty.MinRun>()?.value ?: deviceDataSnapshot.minRun).toShort().asByteArray()
        val maxRun = (props.findIsInstance<DeviceProperty.MaxRun>()?.value ?: deviceDataSnapshot.maxRun).toShort().asByteArray()
        val period = (props.findIsInstance<DeviceProperty.Period>()?.value ?: deviceDataSnapshot.period).toShort().asByteArray()

        val timer0 = (props.findIsInstance<DeviceProperty.Timer0>()?.value ?: deviceDataSnapshot.timer0).toByte()
        val timer1 = (props.findIsInstance<DeviceProperty.Timer1>()?.value ?: deviceDataSnapshot.timer1).toByte()
        val alarm0 = ((props.findIsInstance<DeviceProperty.Alarm0>()?.value ?: deviceDataSnapshot.alarm0) * 10).toInt().toByte()
        val alarm1 = ((props.findIsInstance<DeviceProperty.Alarm1>()?.value ?: deviceDataSnapshot.alarm1) * 10).toInt().toByte()
        val extOn0 = ((props.findIsInstance<DeviceProperty.ExtOn0>()?.value ?: deviceDataSnapshot.extOn0) * 10).toInt().toByte()
        val extOn1 = ((props.findIsInstance<DeviceProperty.ExtOn1>()?.value ?: deviceDataSnapshot.extOn1) * 10).toInt().toByte()
        val extOff0 = ((props.findIsInstance<DeviceProperty.ExtOff0>()?.value ?: deviceDataSnapshot.extOff0) * 10).toInt().toByte()
        val extOff1 = ((props.findIsInstance<DeviceProperty.ExtOff1>()?.value ?: deviceDataSnapshot.extOff1) * 10).toInt().toByte()
        val air0 = (props.findIsInstance<DeviceProperty.Air0>()?.value ?: deviceDataSnapshot.air0).toByte()
        val air1 = (props.findIsInstance<DeviceProperty.Air1>()?.value ?: deviceDataSnapshot.air1).toByte()
        val spCO2 = (props.findIsInstance<DeviceProperty.SpCO2>()?.value ?: deviceDataSnapshot.spCO2).toByte()
        val newDeviceNumber = (props.findIsInstance<DeviceProperty.DeviceNumber>()?.value ?: deviceDataSnapshot.node).toByte()
        val state = (props.findIsInstance<DeviceProperty.State>()?.value ?: deviceDataSnapshot.state).toByte()
        val extendMode = (props.findIsInstance<DeviceProperty.ExtendMode>()?.value ?: deviceDataSnapshot.extendMode).toByte()
        val relayMode = (props.findIsInstance<DeviceProperty.RelayMode>()?.value ?: deviceDataSnapshot.relayMode).toByte()
        val programm = (props.findIsInstance<DeviceProperty.Program>()?.value ?: deviceDataSnapshot.programm).toByte()
        val hysteresis = (props.findIsInstance<DeviceProperty.Hysteresis>()?.value ?: deviceDataSnapshot.hysteresis).toByte()
        val turnTime = (props.findIsInstance<DeviceProperty.TurnTime>()?.value ?: deviceDataSnapshot.turnTime).toByte()


        RootEncoder.serialize(params.command)
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