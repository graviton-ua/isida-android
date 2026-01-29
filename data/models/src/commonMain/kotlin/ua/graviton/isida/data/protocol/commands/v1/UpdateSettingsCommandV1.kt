package ua.graviton.isida.data.protocol.commands.v1

import ua.graviton.isida.data.protocol.commands.IsidaCommand
import ua.graviton.isida.data.protocol.commands.UpdateSettingCommand

data class UpdateSettingsCommandV1(
    // We can duplicate the fields from DataPackageDto here,
    // or pass the entire DataPackageDto if the device expects the full snapshot back.
    val spT0: Float,       // 2 байт ind=20,21 Уставка температуры sp[0].spT->Сухой датчик;
    val spT1: Float,       // 2 байт ind=22,23 Уставка температуры sp[1].spT->Влажный датчик
    val spRh0: Float,      // 1 байт ind=24 ПОДСТРОЙКА HIH-5030
    val spRh1: Float,      // 1 байт ind=25 Уставка влажности Датчик HIH-5030
    val state: Int,         // 1 байт ind=26 состояние камеры (ОТКЛ. ВКЛ. ОХЛАЖДЕНИЕ, и т.д.)
    val extendMode: Int,    // 1 байт ind=27 расширенный режим работы  0-СИРЕНА; 1-ВЕНТ. 2-Форс НАГР. 3-Форс ОХЛЖД. 4-Форс ОСУШ. 5-Дубляж увлажнения
    val relayMode: Int,     // 1 байт ind=28 релейный режим работы  0-НЕТ; 1->по кан.[0] 2->по кан.[1] 3->по кан.[0]&[1]
    val programm: Int,      // 1 байт ind=29 работа по программе
    val minRun: Int,        // 1 байт ind=30 импульсное управление насосом увлажнителя
    val maxRun: Int,        // 1 байт ind=31 импульсное управление насосом увлажнителя
    val period: Int,        // 1 байт ind=32 импульсное управление насосом увлажнителя
    val timer0: Int, val timer1: Int,         // 2 байт ind=33,34 [0]-отключ.состояниe [1]-включ.состояниe
    val alarm0: Float, val alarm1: Float,   // 2 байт ind=35,36 дельта 5 = 0.5 гр.C
    val extOn0: Float, val extOn1: Float,   // 2 байт ind=37,38 смещение для ВКЛ. вспомогательного канала
    val extOff0: Float, val extOff1: Float, // 2 байт ind=39,40 смещение для ОТКЛ. вспомогательного канала
    val air0: Int, val air1: Int,             // 2 байт ind=41,42 таймер проветривания air[0]-пауза; air[1]-работа; если air[1]=0-ОТКЛЮЧЕНО
    val spCO2: Int,         // 1 байт ind=43 опорное значение для управления концетрацией СО2
    val koffCurr: Int,      // 1 байт ind=44 маштабный коэф. по току симистора  (150 для AC1010)
    val hysteresis: Int,    // 1 байт ind=45 гистерезис канала увлажнения маска 0x03; разрешение использования HIH-5030 маска 0x40; AM2301 маска 0x80;
    val zonaFlap: Int,      // 1 байт ind=46 порог зональности в камере
    val turnTime: Int,      // 1 байт ind=47 время ожидания прохода лотков в секундах
    val waitCooling: Int,   // 1 байт ind=48 время ожидания начала режима охлаждения
    val pkoff0: Int,        // 1 байт ind=49 пропорциональный коэфф.#0
    val pkoff1: Int,        // 1 байт ind=50 пропорциональный коэфф.#1
    val ikoff0: Int,        // 1 байт ind=51 интегральный коэфф.#0
    val ikoff1: Int,        // 1 байт ind=52 интегральный коэфф.#1
    val identif: Int,       // 1 байт ind=53 сетевой номер прибора
    val ip0: Int, val ip1: Int, val ip2: Int, val ip3: Int, // 4 байт ind=54;ind=55;ind=56;ind=57;
    val nothing0: Int,      // 1 байт ind=58;       не используется
    val nothing1: Int,      // 1 байт ind=59;       не используется
) : IsidaCommand.V1, UpdateSettingCommand