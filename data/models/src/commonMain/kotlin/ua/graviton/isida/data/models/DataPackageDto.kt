@file:OptIn(ExperimentalUnsignedTypes::class)

package ua.graviton.isida.data.models

import kotlin.Int

data class DataPackageDto(
    val model: Int = 0,       // 1 байт ind=0  модель прибора
    val node: Int = 0,        // 1 байт ind=1  сетевой номер прибора
    val pvT0: Float = 0f,     // 2 байт ind=2,3  значения датчика температуры #0
    val pvT1: Float = 0f,     // 2 байт ind=4,5  значения датчикa температуры #1
    val pvT2: Float = 0f,     // 2 байт ind=6,7  значения датчикa температуры #2
    val pvRh: Int = 0,        // 1 байт ind=8  значение датчика относительной влажности
    val pvCO2: Int = 0,       // 1 байт ind=9  значения датчика CO2 (max 250*20=5000; min 20*20=400)
    val pvTimer: Int = 0,     // 1 байт ind=10 значение таймера до начала поворота лотков
    val pvFan: Int = 0,       // 1 байт ind=11 скорость вращения тихоходного вентилятора
    val pvFlap: Int = 0,      // 1 байт ind=12 положение заслонки
    val power: Int = 0,       // 1 байт ind=13 мощность подаваемая на тены
    val fuses: Int = 0,       // 1 байт ind=14 короткие замыкания
    val errors: Int = 0,      // 1 байт ind=15 ошибки
    val warning: Int = 0,     // 1 байт ind=16 предупреждения
    val yearMonth: Int = 0,   // 1 байт ind=17 YYMM = 2405
    val dayHour: Int = 0,     // 1 байт ind=18 DDHH = 2209
    val minSec: Int = 0,      // 1 байт ind=19 MMSS = 0728
    // ------------------ ИТОГО 20 bytes -------------------------------
    val spT0: Float = 0f,       // 2 байт ind=20,21 Уставка температуры sp[0].spT->Сухой датчик;
    val spT1: Float = 0f,       // 2 байт ind=22,23 Уставка температуры sp[1].spT->Влажный датчик
    val spRh0: Float = 0f,      // 1 байт ind=24 ПОДСТРОЙКА HIH-5030
    val spRh1: Float = 0f,      // 1 байт ind=25 Уставка влажности Датчик HIH-5030
    val state: Int = 0,         // 1 байт ind=26 состояние камеры (ОТКЛ. ВКЛ. ОХЛАЖДЕНИЕ, и т.д.)
    val extendMode: Int = 0,    // 1 байт ind=27 расширенный режим работы  0-СИРЕНА; 1-ВЕНТ. 2-Форс НАГР. 3-Форс ОХЛЖД. 4-Форс ОСУШ. 5-Дубляж увлажнения
    val relayMode: Int = 0,     // 1 байт ind=28 релейный режим работы  0-НЕТ; 1->по кан.[0] 2->по кан.[1] 3->по кан.[0]&[1]
    val programm: Int = 0,      // 1 байт ind=29 работа по программе
    val minRun: Int = 0,        // 1 байт ind=30 импульсное управление насосом увлажнителя
    val maxRun: Int = 0,        // 1 байт ind=31 импульсное управление насосом увлажнителя
    val period: Int = 0,        // 1 байт ind=32 импульсное управление насосом увлажнителя
    val timer0: Int = 0, val timer1: Int = 0,         // 2 байт ind=33,34 [0]-отключ.состояниe [1]-включ.состояниe
    val alarm0: Float = 0f, val alarm1: Float = 0f,   // 2 байт ind=35,36 дельта 5 = 0.5 гр.C
    val extOn0: Float = 0f, val extOn1: Float = 0f,   // 2 байт ind=37,38 смещение для ВКЛ. вспомогательного канала
    val extOff0: Float = 0f, val extOff1: Float = 0f, // 2 байт ind=39,40 смещение для ОТКЛ. вспомогательного канала
    val air0: Int = 0, val air1: Int = 0,             // 2 байт ind=41,42 таймер проветривания air[0]-пауза; air[1]-работа; если air[1]=0-ОТКЛЮЧЕНО
    val spCO2: Int = 0,         // 1 байт ind=43 опорное значение для управления концетрацией СО2
    val koffCurr: Int = 0,      // 1 байт ind=44 маштабный коэф. по току симистора  (150 для AC1010)
    val hysteresis: Int = 0,    // 1 байт ind=45 гистерезис канала увлажнения маска 0x03; разрешение использования HIH-5030 маска 0x40; AM2301 маска 0x80;
    val zonaFlap: Int = 0,      // 1 байт ind=46 порог зональности в камере
    val turnTime: Int = 0,      // 1 байт ind=47 время ожидания прохода лотков в секундах
    val waitCooling: Int = 0,   // 1 байт ind=48 время ожидания начала режима охлаждения
    val pkoff0: Int = 0,        // 1 байт ind=49 пропорциональный коэфф.#0
    val pkoff1: Int = 0,        // 1 байт ind=50 пропорциональный коэфф.#1
    val ikoff0: Int = 0,        // 1 байт ind=51 интегральный коэфф.#0
    val ikoff1: Int = 0,        // 1 байт ind=52 интегральный коэфф.#1
    val identif: Int = 0,       // 1 байт ind=53 сетевой номер прибора
    val ip0: Int = 0, val ip1: Int = 0, val ip2: Int = 0, val ip3: Int = 0, // 4 байт ind=54;ind=55;ind=56;ind=57;
    val nothing0: Int = 0,      // 1 байт ind=58;       не используется ! YYMM = 2405
    val nothing1: Int = 0,      // 1 байт ind=59;       не используется ! DDHH = 2209
    // ------------------ ИТОГО 40 bytes -------------------------------
) {
    companion object {
        fun parseData(bytes: ByteArray): DataPackageDto {
            require(bytes.size == 60) { "Input bytes are incorrect" }
            val ubytes = bytes.toUByteArray()
            return DataPackageDto(
                model = ubytes[0].toInt(),
                node = ubytes[1].toInt(),
                pvT0 = ubytes.read2BytesAsInt(2).toFloat() / 10,
                pvT1 = ubytes.read2BytesAsInt(4).toFloat() / 10,
                pvT2 = ubytes.read2BytesAsInt(6).toFloat() / 10,
                pvRh = ubytes[8].toInt(),
                pvCO2 = ubytes[9].toInt(),        // 1 байт ind=9  значения датчика CO2 (max 250*20=5000; min 20*20=400)
                pvTimer = ubytes[10].toInt(),     // 1 байт ind=10 значение таймера до начала поворота лотков
                pvFan = ubytes[11].toInt(),       // 1 байт ind=11 скорость вращения тихоходного вентилятора
                pvFlap = ubytes[12].toInt(),      // 1 байт ind=12 положение заслонки
                power = ubytes[13].toInt(),       // 1 байт ind=13 мощность подаваемая на тены
                fuses = ubytes[14].toInt(),       // 1 байт ind=14 короткие замыкания
                errors = ubytes[15].toInt(),      // 1 байт ind=15 ошибки
                warning = ubytes[16].toInt(),     // 1 байт ind=16 предупреждения
                yearMonth = ubytes[17].toInt(),   // 1 байт ind=17 YYMM = 2405
                dayHour = ubytes[18].toInt(),     // 1 байт ind=18 DDHH = 2209
                minSec = ubytes[19].toInt(),      // 1 байт ind=19 MMSS = 0728
                spT0 = ubytes.read2BytesAsInt(20).toFloat() / 10,
                spT1 = ubytes.read2BytesAsInt(22).toFloat() / 10,
                spRh0 = ubytes[24].toFloat() / 10,
                spRh1 = ubytes[25].toFloat() / 10,
                state = ubytes[26].toInt(),
                extendMode = ubytes[27].toInt(),
                relayMode = ubytes[28].toInt(),
                programm = ubytes[29].toInt(),
                minRun = ubytes[30].toInt(),
                maxRun = ubytes[31].toInt(),
                period = ubytes[32].toInt(),
                timer0 = ubytes[33].toInt(), timer1 = ubytes[34].toInt(),
                alarm0 = ubytes[35].toInt().toFloat() / 10,
                alarm1 = ubytes[36].toInt().toFloat() / 10,
                extOn0 = ubytes[37].toInt().toFloat() / 10,
                extOn1 = ubytes[38].toInt().toFloat() / 10,
                extOff0 = ubytes[39].toInt().toFloat() / 10,
                extOff1 = ubytes[40].toInt().toFloat() / 10,
                air0 = ubytes[41].toInt(), air1 = ubytes[42].toInt(),
                spCO2 = ubytes[43].toInt(),
                koffCurr = ubytes[44].toInt(),
                hysteresis = ubytes[45].toInt(),
                zonaFlap = ubytes[46].toInt(),      // 1 байт ind=46 порог зональности в камере
                turnTime = ubytes[47].toInt(),      // 1 байт ind=47 время ожидания прохода лотков в секундах
                waitCooling = ubytes[48].toInt(),   // 1 байт ind=48 время ожидания начала режима охлаждения
                pkoff0 = ubytes[49].toInt(),        // 1 байт ind=49 пропорциональный коэфф.#0
                pkoff1 = ubytes[50].toInt(),        // 1 байт ind=50 пропорциональный коэфф.#1
                ikoff0 = ubytes[51].toInt(),        // 1 байт ind=51 интегральный коэфф.#0
                ikoff1 = ubytes[52].toInt(),        // 1 байт ind=52 интегральный коэфф.#1
                identif = ubytes[53].toInt(),       // 1 байт ind=53 сетевой номер прибора
                ip0 = ubytes[54].toInt(),
                ip1 = ubytes[55].toInt(),
                ip2 = ubytes[56].toInt(),
                ip3 = ubytes[57].toInt(),
                nothing0 = ubytes[58].toInt(),      // 1 байт ind=58;       не используется
                nothing1 = ubytes[59].toInt(),      // 1 байт ind=59;       не используется
            )
        }

        val TestData = DataPackageDto()
    }
}

private fun UByteArray.read2BytesAsInt(index: Int): Int = this[index].toInt() + this[index + 1].toInt() * 256
