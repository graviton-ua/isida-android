package ua.graviton.isida.data.parsers.v1

import ua.graviton.isida.data.parsers.PacketParser
import ua.graviton.isida.data.parsers.PacketReader
import ua.graviton.isida.data.protocol.packets.v1.StatusPacketV1

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
        val pvFan = reader.u8()
        val pvFlap = reader.u8()
        val power = reader.u8()
        val fuses = reader.u8()
        val errors = reader.u8()
        val warning = reader.u8()
        val output = reader.u8()
        val dayHour = reader.u8()
        val minSec = reader.u8()
        val spT0 = reader.u16() / 10f
        val spT1 = reader.u16() / 10f
        val spRh0 = reader.u8() / 10f
        val spRh1 = reader.u8() / 10f
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
        val spCO2 = reader.u8() * 20f
        val koffCurr = reader.u8()
        val hysteresis = (reader.u8() and 0x3F) / 10f
        val permission = (reader.u8() and 0xC0) / 64
        val zonaFlap = reader.u8()
        val turnTime = reader.u8()
        val waitCooling = reader.u8()
        val pkoff0 = reader.u8()
        val pkoff1 = reader.u8()
        val ikoff0 = reader.u8()
        val ikoff1 = reader.u8()
        val identif = reader.u8()
        val ip0 = reader.u8()
        val ip1 = reader.u8()
        val ip2 = reader.u8()
        val ip3 = reader.u8()
        val nothing0 = reader.u8()
        val nothing1 = reader.u8()

        StatusPacketV1(
            model = reader.u8(),
            node = reader.u8(),
            pvT0 = reader.u16() / 10f,
            pvT1 = reader.u16() / 10f,
            pvT2 = reader.u16() / 10f,
            pvRh = reader.u8(),
            pvCO2 = reader.u8() * 20f,
            pvTimer = reader.u8(),
            pvFan = reader.u8(),
            pvFlap = reader.u8(),
            power = reader.u8(),
            fuses = reader.u8(),
            errors = reader.u8(),
            warning = reader.u8(),
            output = reader.u8(),
            dayHour = reader.u8(),
            minSec = reader.u8(),
            spT0 = reader.u16() / 10f,
            spT1 = reader.u16() / 10f,
            spRh0 = reader.u8() / 10f,
            spRh1 = reader.u8() / 10f,
            state = reader.u8(),
            extendMode = reader.u8(),
            relayMode = reader.u8(),
            programm = reader.u8(),
            minRun = reader.u8() / 10f,
            maxRun = reader.u8(),
            period = reader.u8(),
            timer0 = reader.u8(),
            timer1 = reader.u8(),
            alarm0 = reader.u8() / 10f,
            alarm1 = reader.u8() / 10f,
            extOn0 = reader.u8() / 10f,
            extOn1 = reader.u8() / 10f,
            extOff0 = reader.u8() / 10f,
            extOff1 = reader.u8() / 10f,
            air0 = reader.u8(),
            air1 = reader.u8(),
            spCO2 = reader.u8() * 20f,
            koffCurr = reader.u8(),
            hysteresis = (reader.u8() and 0x3F) / 10f,
            permission = (reader.u8() and 0xC0) / 64,
            zonaFlap = reader.u8(),
            turnTime = reader.u8(),
            waitCooling = reader.u8(),
            pkoff0 = reader.u8(),
            pkoff1 = reader.u8(),
            ikoff0 = reader.u8(),
            ikoff1 = reader.u8(),
            identif = reader.u8(),
            ip0 = reader.u8(),
            ip1 = reader.u8(),
            ip2 = reader.u8(),
            ip3 = reader.u8(),
            nothing0 = reader.u8(),
            nothing1 = reader.u8(),
        )
    }
}