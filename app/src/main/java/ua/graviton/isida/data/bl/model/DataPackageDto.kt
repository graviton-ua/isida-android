package ua.graviton.isida.data.bl.model

import timber.log.Timber
import ua.graviton.isida.data.db.entities.DeviceDataEntity

data class DataPackageDto(
    val cellId: Int = 0,                        // 1байт[0]     сетевой номер прибора
    val pvT0: Float = 0f, val pvT1: Float = 0f, // 4байт[1;4]   значения 1,2 датчиков температуры
    val pvT2: Float = 0f, val pvT3: Float = 0f, // 4байт[5;8]   значения 3,4 датчиков температуры
    val pvRh: Float = 0f,       // 2байт[9;10]  значение датчика относительной влажности
    val pvCO2_1: Int = 0,       // 2байт[11;12] значения датчика CO2
    val pvCO2_2: Int = 0,       // 2байт[13;14] значения датчика CO2
    val pvCO2_3: Int = 0,       // 2байт[15;16] значения датчика CO2
    val pvTimer: Int = 0,       // 1байт[17]    значение таймера
    val pvTmrCount: Int = 0,    // 1байт[18]    значение счетчика проходов поворота
    val pvFlap: Int = 0,        // 1байт[19]    положение заслонки
    val power: Int = 0, val fuses: Int = 0,     // 2байт[20;21] мощьность подаваемая на тены и короткие замыкания
    val errors: Int = 0, val warning: Int = 0,  // 2байт[22;23] ошибки и предупреждения
    val cost0: Int = 0, val cost1: Int = 0,     // 2байт[24;25] затраты ресурсов
    val date: Int = 0, val hours: Int = 0,      // 2байт[26;27] счетчики суток и часов

    val spT0: Int = 0, val spT1: Int = 0,   // 4байт[28;31] Уставка температуры sp[0].spT->Сухой датчик; sp[1].spT->Влажный датчик
    val spRh0: Int = 0, val spRh1: Int = 0, // 4байт[32;35] sp[0].spRH->ПОДСТРОЙКА HIH-5030; sp[1].spRH->Уставка влажности Датчик HIH-5030
    val K0: Int = 0, val K1: Int = 0,       // 4байт[36;39] пропорциональный коэфф.
    val Ti0: Int = 0, val Ti1: Int = 0,     // 4байт[40;43] интегральный коэфф.
    val minRun: Int = 0,        // 2байт[44;45] импульсное управление насосом увлажнителя
    val maxRun: Int = 0,        // 2байт[46;47] импульсное управление насосом увлажнителя
    val period: Int = 0,        // 2байт[48;49] импульсное управление насосом увлажнителя
    val timeOut: Int = 0,       // 2байт[50;51] время ожидания начала режима охлаждения
    val energyMeter: Int = 0,   // 2байт[52;53] счетчик элктрической энергии
    val timer0: Int = 0, val timer1: Int = 0,   // 2байт[54;55] [0]-отключ.состояниe [1]-включ.состояниe
    val alarm0: Int = 0, val alarm1: Int = 0,   // 2байт[56;57] дельта 5 = 0.5 гр.C
    val extOn0: Int = 0, val extOn1: Int = 0,   // 2байт[58;59] смещение для ВКЛ. вспомогательного канала
    val extOff0: Int = 0, val extOff1: Int = 0, // 2байт[60;61] смещение для ОТКЛ. вспомогательного канала
    val air0: Int = 0, val air1: Int = 0,       // 2байт[62;63] таймер проветривания air[0]-пауза; air[1]-работа; если air[1]=0-ОТКЛЮЧЕНО
    val spCO2: Int = 0,         // 1байт[64]    опорное значение для управления концетрацией СО2
    val deviceNumber: Int = 0,  // 1байт[65]    сетевой номер прибора
    val state: Int = 0,         // 1байт[66]    состояние камеры (ОТКЛ. ВКЛ. ОХЛАЖДЕНИЕ, и т.д.)
    val extendMode: Int = 0,    // 1байт[67]    расширенный режим работы  0-СИРЕНА; 1-ВЕНТ. 2-Форс НАГР. 3-Форс ОХЛЖД. 4-Форс ОСУШ. 5-Дубляж увлажнения
    val relayMode: Int = 0,     // 1байт[68]    релейный режим работы  0-НЕТ; 1->по кан.[0] 2->по кан.[1] 3->по кан.[0]&[1]
    val programm: Int = 0,      // 1байт[69]    работа по программе
    val hysteresis: Int = 0,    // 1байт[70]    Гистерезис канала увлажнения
    val forceHeat: Int = 0,     // 1байт[71]    Форсированный нагрев 5 = 0.5 грд.С.
    val turnTime: Int = 0,      // 1байт[72]    время ожидания прохода лотков в секундах
    val hihEnable: Int = 0,     // 1байт[73]    разрешение использования датчика влажности
    val kOffCurr: Int = 0,      // 1байт[74]    маштабный коэф. по току симистора  (160 для AC1010 или 80 для другого)
    val coolOn: Int = 0,        // 1байт[75]    порог включения вентилятора обдува сисмистора coolOn=80~>65 грд.С.
    val coolOff: Int = 0,       // 1байт[76]    порог отключения вентилятора обдува сисмистора
    val zonality: Int = 0,      // 1байт[77]    порог зональности в камере
) {
    companion object {
        fun parseData(bytes: ByteArray): DataPackageDto {
            Timber.d("package size: ${bytes.size}")
            require(bytes.size == 78) { "Input bytes are incorrect" }
            val ubytes = bytes.toUByteArray()
            Timber.d("package raw and ubytes:\n${bytes.asList()}\n$ubytes")
            return DataPackageDto(
                cellId = ubytes[0].toInt(),
                pvT0 = ubytes.read2BytesAsInt(1).toFloat() / 10,
                pvT1 = ubytes.read2BytesAsInt(3).toFloat() / 10,
                pvT2 = ubytes.read2BytesAsInt(5).toFloat() / 10,
                pvT3 = ubytes.read2BytesAsInt(7).toFloat() / 10,
                pvRh = ubytes.read2BytesAsInt(9).toFloat() / 10,
                pvCO2_1 = ubytes.read2BytesAsInt(11),
                pvCO2_2 = ubytes.read2BytesAsInt(13),
                pvCO2_3 = ubytes.read2BytesAsInt(15),
                pvTimer = ubytes[17].toInt(),
                pvTmrCount = ubytes[18].toInt(),
                pvFlap = ubytes[19].toInt(),
                power = ubytes[20].toInt(), fuses = ubytes[21].toInt(),
                errors = ubytes[22].toInt(), warning = ubytes[23].toInt(),
                cost0 = ubytes[24].toInt(), cost1 = ubytes[25].toInt(),
                date = ubytes[26].toInt(), hours = ubytes[27].toInt(),

                // More data if needed should be placed here
                //spT0 = ubytes.read2BytesAsInt(28), spT1 = ubytes.read2BytesAsInt(30),
                //spRh0 = ubytes.read2BytesAsInt(32), spRh1 = ubytes.read2BytesAsInt(34),
                //K0 = ubytes.read2BytesAsInt(36), K1 = ubytes.read2BytesAsInt(38),
            )
        }
    }
}

private fun UByteArray.read2BytesAsInt(index: Int): Int = this[index].toInt() * 256 + this[index + 1].toInt()
// ---------- pvT[1] ВЛАЖНЫЙ ------------------------------------
//if (longDataRX[1] == 0.toShort()) {
//binding.tvTemp1.text = (longDataRX[1].toFloat() / 10).toString()
//temp1 = longDataRX[1].toFloat() / 10

// ---------- pvT[2] КОНТРОЛЬНЫЙ --------------------------------
//if (longDataRX[2] > 800) binding.tvTemp2.visibility = View.INVISIBLE
//else binding.tvTemp2.text = (longDataRX[2].toFloat() / 10).toString()
//temp2 = longDataRX[2].toFloat() / 10
//} else {
// ------------ pvRH датчик относительной влажности ----------------
//binding.tvTemp1.text = (longDataRX[4].toFloat() / 10).toString()
//binding.tvTempName1.text = "Отн.Влаж."
//rh = longDataRX[4].toFloat() / 10

// ---------- pvT[2] КОНТРОЛЬНЫЙ --------------------------------
//if (longDataRX[1] > 800 && longDataRX[2] > 800) binding.tvTemp2.visibility = View.INVISIBLE
//else if (longDataRX[2] > 800) {
//    binding.tvTemp2.text = (longDataRX[1].toFloat() / 10).toString()
//} else if (longDataRX[1] > 800) {
//    binding.tvTemp2.text = (longDataRX[2].toFloat() / 10).toString()
//} else {
//    binding.tvTemp2.text = (((longDataRX[1] + longDataRX[2]) / 2).toFloat() / 10).toString()
//}
//}

// ---------- pvT[3] СКОРЛУПА --------------------------------------
//if (longDataRX[3] > 800) binding.tvTemp3.visibility = View.INVISIBLE
//else binding.tvTemp3.text = (longDataRX[3].toFloat() / 10).toString()
//val temp3 = longDataRX[3].toFloat() / 10

// ---------- CO2 углекислый газ ------------------------------------
//if (longDataRX[5] > 0) binding.tvCoTwo.text = longDataRX[2].toString()
//else binding.tvCoTwo.visibility = View.INVISIBLE
//val coTwo = longDataRX[5]

//val timer = dataRX[18]   // pvTimer значение таймера
//val count = dataRX[19]   // pvCount значение счетчика проходов поворота
//val flap = dataRX[20]    // pvFlap положение заслонки