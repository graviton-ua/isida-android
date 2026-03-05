package ua.isida.data.parsers.v1

import ua.isida.data.parsers.PacketParser
import ua.isida.data.parsers.PacketReader
import ua.isida.data.protocol.packets.v1.StatusPacketV1

object StatusPacketV1Parser : PacketParser<StatusPacketV1> {
    override val version: Int = 1       // [0x01]
    override val length: Int = 60       // [0x3C, 0x00]
    override val commandId: Int = 77    // [0x4D]

    override fun parsePayload(reader: PacketReader): Result<StatusPacketV1> = Result.runCatching {
        require(reader.data.size == length) { "Data size must be $length but was ${reader.data.size}" }
        val model = reader.u8()
        val node = reader.u8()
        val pvT0 = reader.u16() / 10f
        val pvT1 = reader.u16() / 10f
        val pvT2 = reader.u16() / 10f
        val pvRh = reader.u8()
        val pvCO2 = reader.u8() * 20f
        val pvTimer = reader.u8()
        val pvFan = reader.u8() * 60
        val pvFlap = reader.u8()
        val power = reader.u8()
        val fuses = reader.u8()
        val errors = reader.u8()
        val warning = reader.u8()
        val output = reader.u8()
        val currentTime = reader.u16()
        val spT0 = reader.u16() / 10f
        val spT1 = reader.u16() / 10f
        val spRh0 = reader.s8()
        val spRh1 = reader.u8()
        val state = reader.u8()
        val extendMode = reader.u8()
        val relayMode = reader.u8()
        val programm = reader.u8()
        val minRun = reader.u8() / 10f
        val maxRun = reader.u8()
        val period = reader.u8()
        val timer0 = reader.u8()
        val timer1 = reader.u8()
        val alarm0 = reader.u8() / 10f
        val alarm1 = reader.u8() / 10f
        val extOn0 = reader.u8() / 10f
        val extOn1 = reader.u8() / 10f
        val extOff0 = reader.u8() / 10f
        val extOff1 = reader.u8() / 10f
        val air0 = reader.u8()
        val air1 = reader.u8()
        val spCO2 = reader.u8() * 20
        val koffCurr = reader.u8()

        val rawHysteresis = reader.u8()
        val hysteresis = (rawHysteresis and 0x1F) / 10f
        val permission = (rawHysteresis and 0xC0) shr 6

        val zonaFlap = reader.u8()
        val flapRestrictions = (zonaFlap and 0x3F) + 37
        val zonality = (zonaFlap and 0xC0) shr 6

        val turnTime = reader.u8() / 60
        val waitCooling = reader.u8() / 15
        val pkoff0 = reader.u8()
        val pkoff1 = reader.u8()
        val ikoff0 = reader.u8()
        val ikoff1 = reader.u8()
        val identif = reader.u8()
        val minFan = reader.u8() * 60
        val nothing1 = reader.u8()
        val ip0 = reader.u8()
        val ip1 = reader.u8()
        val ip2 = reader.u8()
        val ip3 = reader.u8()

        StatusPacketV1(
            model = model,
            node = node,
            pvT0 = pvT0,
            pvT1 = pvT1,
            pvT2 = pvT2,
            pvRh = pvRh,
            pvCO2 = pvCO2,
            pvTimer = pvTimer,
            pvFan = pvFan,
            pvFlap = pvFlap,
            power = power,
            fuses = fuses,
            errors = errors,
            warning = warning,
            output = output,
            currentTime = currentTime,
            spT0 = spT0,
            spT1 = spT1,
            spRh0 = spRh0,
            spRh1 = spRh1,
            state = state,
            extendMode = extendMode,
            relayMode = relayMode,
            programm = programm,
            minRun = minRun,
            maxRun = maxRun,
            period = period,
            timer0 = timer0,
            timer1 = timer1,
            alarm0 = alarm0,
            alarm1 = alarm1,
            extOn0 = extOn0,
            extOn1 = extOn1,
            extOff0 = extOff0,
            extOff1 = extOff1,
            air0 = air0,
            air1 = air1,
            spCO2 = spCO2,
            koffCurr = koffCurr,
            hysteresis = hysteresis,
            permission = permission,
            zonality = zonality,
            flapRestrictions = flapRestrictions,
            turnTime = turnTime,
            waitCooling = waitCooling,
            pkoff0 = pkoff0,
            pkoff1 = pkoff1,
            ikoff0 = ikoff0,
            ikoff1 = ikoff1,
            identif = identif,
            minFan = minFan,
            nothing1 = nothing1,
            ip0 = ip0,
            ip1 = ip1,
            ip2 = ip2,
            ip3 = ip3,
        )
    }
}