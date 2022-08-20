package ua.graviton.isida.data.bl.model

import timber.log.Timber

data class DataPackageDto(
    val cellId: Int = 0,                        // 1байт[0]     сетевой номер прибора
    val pvT0: Float = 0f, val pvT1: Float = 0f, // 4байт[2;5]   значения 1,2 датчиков температуры
    val pvT2: Float = 0f, val pvT3: Float = 0f, // 4байт[6;9]   значения 3,4 датчиков температуры
    val pvRh: Float = 0f,       // 2байт[10;11]  значение датчика относительной влажности
    val pvCO2_1: Int = 0,       // 2байт[12;13] значения датчика CO2
    val pvCO2_2: Int = 0,       // 2байт[14;15] значения датчика CO2
    val pvCO2_3: Int = 0,       // 2байт[16;17] значения датчика CO2
    val pvTimer: Int = 0,       // 1байт[18]    значение таймера
    val pvTmrCount: Int = 0,    // 1байт[19]    значение счетчика проходов поворота
    val pvFlap: Int = 0,        // 1байт[20]    положение заслонки
    val power: Int = 0, val fuses: Int = 0,     // 2байт[21;22] мощьность подаваемая на тены и короткие замыкания
    val errors: Int = 0, val warning: Int = 0,  // 2байт[23;24] ошибки и предупреждения
    val cost0: Int = 0, val cost1: Int = 0,     // 2байт[25;26] затраты ресурсов
    val date: Int = 0, val hours: Int = 0,      // 2байт[27;28] счетчики суток и часов
    val other0: Int = 0,                        // 1байт[29] прочее

    val spT0: Float = 0f,       // 2байт[30;31] Уставка температуры sp[0].spT->Сухой датчик;
    val spT1: Float = 0f,       // 2байт[32;33] Уставка температуры sp[1].spT->Влажный датчик
    val spRh0: Float = 0f, val spRh1: Float = 0f, // 4байт[34;37] sp[0].spRH->ПОДСТРОЙКА HIH-5030; sp[1].spRH->Уставка влажности Датчик HIH-5030
    val K0: Int = 0, val K1: Int = 0,       // 4байт[38;41] пропорциональный коэфф.
    val Ti0: Int = 0, val Ti1: Int = 0,     // 4байт[42;45] интегральный коэфф.
    val minRun: Int = 0,        // 2байт[46;47] импульсное управление насосом увлажнителя
    val maxRun: Int = 0,        // 2байт[48;49] импульсное управление насосом увлажнителя
    val period: Int = 0,        // 2байт[50;51] импульсное управление насосом увлажнителя
    val timeOut: Int = 0,       // 2байт[52;53] время ожидания начала режима охлаждения
    val energyMeter: Int = 0,   // 2байт[54;55] счетчик элктрической энергии
    val timer0: Int = 0, val timer1: Int = 0,         // 2байт[56;57] [0]-отключ.состояниe [1]-включ.состояниe
    val alarm0: Float = 0f, val alarm1: Float = 0f,   // 2байт[58;59] дельта 5 = 0.5 гр.C
    val extOn0: Float = 0f, val extOn1: Float = 0f,   // 2байт[60;61] смещение для ВКЛ. вспомогательного канала
    val extOff0: Float = 0f, val extOff1: Float = 0f, // 2байт[62;63] смещение для ОТКЛ. вспомогательного канала
    val air0: Int = 0, val air1: Int = 0,             // 2байт[64;65] таймер проветривания air[0]-пауза; air[1]-работа; если air[1]=0-ОТКЛЮЧЕНО
    val spCO2: Int = 0,         // 1байт[66]    опорное значение для управления концетрацией СО2
    val deviceNumber: Int = 0,  // 1байт[67]    сетевой номер прибора
    val state: Int = 0,         // 1байт[68]    состояние камеры (ОТКЛ. ВКЛ. ОХЛАЖДЕНИЕ, и т.д.)
    val extendMode: Int = 0,    // 1байт[69]    расширенный режим работы  0-СИРЕНА; 1-ВЕНТ. 2-Форс НАГР. 3-Форс ОХЛЖД. 4-Форс ОСУШ. 5-Дубляж увлажнения
    val relayMode: Int = 0,     // 1байт[70]    релейный режим работы  0-НЕТ; 1->по кан.[0] 2->по кан.[1] 3->по кан.[0]&[1]
    val programm: Int = 0,      // 1байт[71]    работа по программе
    val hysteresis: Int = 0,    // 1байт[72]    Гистерезис канала увлажнения
    val forceHeat: Float = 0f,  // 1байт[73]    Форсированный нагрев 5 = 0.5 грд.С.
    val turnTime: Int = 0,      // 1байт[74]    время ожидания прохода лотков в секундах
    val hihEnable: Int = 0,     // 1байт[75]    разрешение использования датчика влажности
    val kOffCurr: Int = 0,      // 1байт[76]    маштабный коэф. по току симистора  (160 для AC1010 или 80 для другого)
    val coolOn: Int = 0,        // 1байт[77]    порог включения вентилятора обдува сисмистора coolOn=80~>65 грд.С.
    val coolOff: Int = 0,       // 1байт[78]    порог отключения вентилятора обдува сисмистора
    val zonality: Int = 0,      // 1байт[79]    порог зональности в камере
) {
    companion object {
        fun parseData(bytes: ByteArray): DataPackageDto {
            Timber.d("package size: ${bytes.size}")
            require(bytes.size == 80) { "Input bytes are incorrect" }
            val ubytes = bytes.toUByteArray()
            return DataPackageDto(
                cellId = ubytes[0].toInt(),
                pvT0 = ubytes.read2BytesAsInt(2).toFloat() / 10,
                pvT1 = ubytes.read2BytesAsInt(4).toFloat() / 10,
                pvT2 = ubytes.read2BytesAsInt(6).toFloat() / 10,
                pvT3 = ubytes.read2BytesAsInt(8).toFloat() / 10,
                pvRh = ubytes.read2BytesAsInt(10).toFloat() / 10,
                pvCO2_1 = ubytes.read2BytesAsInt(12),
                pvCO2_2 = ubytes.read2BytesAsInt(14),
                pvCO2_3 = ubytes.read2BytesAsInt(16),
                pvTimer = ubytes[18].toInt(),
                pvTmrCount = ubytes[19].toInt(),
                pvFlap = ubytes[20].toInt(),
                power = ubytes[21].toInt(), fuses = ubytes[22].toInt(),
                errors = ubytes[23].toInt(), warning = ubytes[24].toInt(),
                cost0 = ubytes[25].toInt(), cost1 = ubytes[26].toInt(),
                date = ubytes[27].toInt(), hours = ubytes[28].toInt(),
                spT0 = ubytes.read2BytesAsInt(30).toFloat() / 10,
                spT1 = ubytes.read2BytesAsInt(32).toFloat() / 10,
                spRh0 = ubytes.read2BytesAsInt(34).toFloat() / 10,
                spRh1 = ubytes.read2BytesAsInt(36).toFloat() / 10,
                K0 = ubytes.read2BytesAsInt(38), K1 = ubytes.read2BytesAsInt(40),
                Ti0 = ubytes.read2BytesAsInt(42), Ti1 = ubytes.read2BytesAsInt(44),
                minRun = ubytes.read2BytesAsInt(46),
                maxRun = ubytes.read2BytesAsInt(48),
                period = ubytes.read2BytesAsInt(50),
                timeOut = ubytes.read2BytesAsInt(52),
                energyMeter = ubytes.read2BytesAsInt(54),
                timer0  = ubytes[56].toInt(), timer1 = ubytes[57].toInt(),
                alarm0  = ubytes[58].toInt().toFloat() / 10,
                alarm1  = ubytes[59].toInt().toFloat() / 10,
                extOn0  = ubytes[60].toInt().toFloat() / 10,
                extOn1 = ubytes[61].toInt().toFloat() / 10,
                extOff0 = ubytes[62].toInt().toFloat() / 10,
                extOff1 = ubytes[63].toInt().toFloat() / 10,
                air0 = ubytes[64].toInt(), air1 = ubytes[65].toInt(),
                spCO2 = ubytes[66].toInt(),
                deviceNumber = ubytes[67].toInt(),
                state = ubytes[68].toInt(),
                extendMode = ubytes[69].toInt(),
                relayMode = ubytes[70].toInt(),
                programm = ubytes[71].toInt(),
                hysteresis = ubytes[72].toInt(),
                forceHeat = ubytes[73].toInt().toFloat() / 10,
                turnTime = ubytes[74].toInt(),
                hihEnable = ubytes[75].toInt(),
                kOffCurr = ubytes[76].toInt(),
                coolOn = ubytes[77].toInt(),
                coolOff = ubytes[78].toInt(),
                zonality = ubytes[79].toInt(),
            )
        }

        val TestData = DataPackageDto()
    }
}

private fun UByteArray.read2BytesAsInt(index: Int): Int = this[index].toInt() + this[index + 1].toInt() * 256
