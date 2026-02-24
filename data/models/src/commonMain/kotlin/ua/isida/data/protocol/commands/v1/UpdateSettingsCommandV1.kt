package ua.isida.data.protocol.commands.v1

import ua.isida.data.protocol.commands.IsidaCommand
import ua.isida.data.protocol.commands.UpdateSettingCommand

/**
 * Command to update various settings on the device.
 * This includes temperature setpoints, humidity, alarm thresholds, timer configurations, and more.
 *
 * @property spT0 Temperature setpoint for sensor #0 (Dry sensor).
 * @property spT1 Temperature setpoint for sensor #1 (Wet sensor).
 * @property spRh0 Adjustment/Calibration for HIH-5030.
 * @property spRh1 Humidity setpoint for HIH-5030 sensor.
 * @property state Device state (OFF, ON, COOLING, etc.).
 * @property extendMode Extended operation mode (0: Siren, 1: Fan, 2: Force Heat, 3: Force Cool, 4: Force Dry, 5: Duplicate Wetting).
 * @property relayMode Relay operation mode (0: None, 1: Ch[0], 2: Ch[1], 3: Ch[0]&[1]).
 * @property programm Program operation mode.
 * @property minRun Min run time for humidifier pump (pulse control).
 * @property maxRun Max run time for humidifier pump (pulse control).
 * @property period Period for humidifier pump (pulse control).
 * @property timer0 Timer ON state duration (or OFF state, depending on specific logic).
 * @property timer1 Timer OFF state duration (or ON state, depending on specific logic).
 * @property alarm0 Alarm threshold delta 0.
 * @property alarm1 Alarm threshold delta 1.
 * @property extOn0 Offset for turning ON the auxiliary channel (0).
 * @property extOn1 Offset for turning ON the auxiliary channel (1).
 * @property extOff0 Offset for turning OFF the auxiliary channel (0).
 * @property extOff1 Offset for turning OFF the auxiliary channel (1).
 * @property air0 Ventilation pause timer.
 * @property air1 Ventilation work timer (if 0, ventilation is OFF).
 * @property spCO2 Setpoint for CO2 concentration control.
 * @property koffCurr Scale coefficient for triac current (e.g., 150 for AC1010).
 * @property hysteresis Humidifier channel hysteresis (mask 0x03); Enable HIH-5030 (mask 0x40); Enable AM2301 (mask 0x80).
 * @property zonaFlap Zone threshold for flap control.
 * @property turnTime Wait time for tray rotation (in seconds).
 * @property waitCooling Wait time before starting cooling mode.
 * @property pkoff0 Proportional coefficient #0.
 * @property pkoff1 Proportional coefficient #1.
 * @property ikoff0 Integral coefficient #0.
 * @property ikoff1 Integral coefficient #1.
 * @property identif Network identifier/number of the device.
 * @property minFan минимальная скорость вращения вентилятора (маска 0000 1111)
 *  @property nothing1 Unused/Reserved field.
 * @property ip0 IP address byte 0.
 * @property ip1 IP address byte 1.
 * @property ip2 IP address byte 2.
 * @property ip3 IP address byte 3.
 */
data class UpdateSettingsCommandV1(
    // We can duplicate the fields from DataPackageDto here,
    // or pass the entire DataPackageDto if the device expects the full snapshot back.
    val spT0: Float,       // 2 байт ind=20,21 Уставка температуры sp[0].spT->Сухой датчик;
    val spT1: Float,       // 2 байт ind=22,23 Уставка температуры sp[1].spT->Влажный датчик
    val spRh0: Int,      // 1 байт ind=24 ПОДСТРОЙКА HIH-5030
    val spRh1: Int,      // 1 байт ind=25 Уставка влажности Датчик HIH-5030
    val state: Int,         // 1 байт ind=26 состояние камеры (ОТКЛ. ВКЛ. ОХЛАЖДЕНИЕ, и т.д.)
    val extendMode: Int,    // 1 байт ind=27 расширенный режим работы  0-СИРЕНА; 1-ВЕНТ. 2-Форс НАГР. 3-Форс ОХЛЖД. 4-Форс ОСУШ. 5-Дубляж увлажнения
    val relayMode: Int,     // 1 байт ind=28 релейный режим работы  0-НЕТ; 1->по кан.[0] 2->по кан.[1] 3->по кан.[0]&[1]
    val programm: Int,      // 1 байт ind=29 работа по программе
    val minRun: Float,      // 1 байт ind=30 импульсное управление насосом увлажнителя
    val maxRun: Int,        // 1 байт ind=31 импульсное управление насосом увлажнителя
    val period: Int,        // 1 байт ind=32 импульсное управление насосом увлажнителя
    val timer0: Int, val timer1: Int,         // 2 байт ind=33,34 [0]-отключ.состояниe [1]-включ.состояниe
    val alarm0: Float, val alarm1: Float,   // 2 байт ind=35,36 дельта 5 = 0.5 гр.C
    val extOn0: Float, val extOn1: Float,   // 2 байт ind=37,38 смещение для ВКЛ. вспомогательного канала
    val extOff0: Float, val extOff1: Float, // 2 байт ind=39,40 смещение для ОТКЛ. вспомогательного канала
    val air0: Int, val air1: Int,             // 2 байт ind=41,42 таймер проветривания air[0]-пауза; air[1]-работа; если air[1]=0-ОТКЛЮЧЕНО
    val spCO2: Int,       // 1 байт ind=43 опорное значение для управления концетрацией СО2
    val koffCurr: Int,      // 1 байт ind=44 маштабный коэф. по току симистора  (150 для AC1010)
    val hysteresis: Float,  // 1 байт ind=45 гистерезис канала увлажнения маска 0x3F;
    val permission: Int,    // hysteresis -> разрешение использования HIH-5030 маска 0x40; AM2301 маска 0x80;
    val zonality: Int,      // 1 байт ind=46 порог зональности в камере (маска 0xC0; к zonaFlap)
    val flapRestrictions: Int,  // максимальное открытие заслонки (маска 0x3F; к zonaFlap + 37)
    val turnTime: Int,      // 1 байт ind=47 время ожидания прохода лотков в секундах
    val waitCooling: Int,   // 1 байт ind=48 время ожидания начала режима охлаждения
    val pkoff0: Int,        // 1 байт ind=49 пропорциональный коэфф.#0
    val pkoff1: Int,        // 1 байт ind=50 пропорциональный коэфф.#1
    val ikoff0: Int,        // 1 байт ind=51 интегральный коэфф.#0
    val ikoff1: Int,        // 1 байт ind=52 интегральный коэфф.#1
    val identif: Int,       // 1 байт ind=53 сетевой номер прибора
    val minFan: Int,        // 1 байт ind=58 минимальная скорость вращения вентилятора (маска 0000 1111)
    val nothing1: Int,      // 1 байт ind=59;       не используется
    val ip0: Int, val ip1: Int, val ip2: Int, val ip3: Int, // 4 байт ind=54;ind=55;ind=56;ind=57;
) : IsidaCommand.V1, UpdateSettingCommand