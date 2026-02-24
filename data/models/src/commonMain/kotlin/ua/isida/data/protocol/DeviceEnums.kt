package ua.isida.data.protocol

/**
 * Represents the operating mode of the device.
 * Used in [ua.graviton.isida.data.protocol.commands.v1.DeviceModeCommandV1].
 *
 * @property code The byte code representing the mode.
 */
enum class DeviceMode(val code: Int) {
    /** Camera/Device is OFF. */
    DISABLE(0x00),          // "камера ОТКЛЮЧЕНА"

    /** Camera/Device is ON. */
    ENABLE(0x01),           // "камера ВКЛЮЧИНА"

    /** Preparing for COOLING mode. */
    WAITING_COOLING(0x02),  // "подгототка к ОХЛАЖДЕНИЮ"

    /** Preparing for START/ON. */
    WAITING_ON(0x04),       // "подгототка к ЗАПУСКУ"

    /** Horizon mode is ON. */
    HORIZON_ON(0x08),       // "ВКЛЮЧЕН ГОРИЗОНТ"

    /** Horizon has been SET. */
    HORIZON_SET(0x10),      // "ГОРИЗОНТ УСТАНОВЛЕН"

    /** Tray rotation monitoring is ON. */
    TRAY_ROTATION_ON(0x20), // "ВКЛЮЧЕН мониторинг поворота лотков"

    /** Low-speed fan monitoring is ON. */
    FAN_MONITORING_ON(0x40),// "ВКЛЮЧЕН мониторинг тихоходного вентилятора"

    /** Only ROTATION mode. */
    ONLY_ROTATION(0x80),    // "только ПОВОРОТ"
}

/**
 * Represents output bits for controlling external components.
 *
 * @property code The bitmask value for the output.
 */
enum class OutputBit(val code: Int) {
    /** Humidifier output. */
    OUT_Wetting(0x02),  //УВЛАЖНИТЕЛЬ

    /** Air exchange flap output. */
    OUT_Flap(0x04),     //Заслонка воздухообмена

    /** Auxiliary channel output. */
    OUT_Extend(0x08),   //Вспомогательный канал

    /** Tray rotation output. */
    OUT_Trays(0x10),    //Поворот лотков
}

/**
 * Represents extra device modes and monitoring states.
 *
 * @property code The bitmask value for the extra mode.
 */
enum class DeviceModeExtra(val code: Int) {
    // TODO: There is 2 more states need to be added later

    /** Low-speed fan monitoring (data | 0x40). */
    EXTRA_1(0x40),   //"Мониторинг тихоход. вентилятора"    data = data | 0x40

    /** Tray rotation monitoring (data | 0x20). */
    EXTRA_2(0x20),   //"Мониторинг поворота лотков"         data = data | 0x20

    /** Horizontal position of trays (data | 0x08). */
    EXTRA_3(0x08),   //"Горизонтальное положение лотков"    data = data | 0x08

    /** Preparing for COOLING mode (data | 0x02). */
    EXTRA_4(0x02),   //"Режим подгототка к ОХЛАЖДЕНИЮ"      data = data | 0x02
}

/**
 * Represents possible device errors.
 *
 * @property code The error code.
 */
enum class DeviceError(val code: Int) {
    /** Temperature sensor error. */
    ERROR_01(code = 0x01),  //ОШИБКА ДАТЧИКА температуры

    /** Humidity sensor error. */
    ERROR_02(code = 0x02),  //ОШИБКА ДАТЧИКА влажности

    /** Triac breakdown. */
    ERROR_04(code = 0x04),  //ПРОБОЙ СИМИСТОРА

    /** Heater circuit malfunction. */
    ERROR_08(code = 0x08),  //НЕИСПРАВНА цепь НАГРЕВАТЕЛЯ

    /** Triac overheating. */
    ERROR_10(code = 0x10),  //ПЕРЕГРЕВ СИМИСТОРА

    /** CO2 module or Flap error. */
    ERROR_20(code = 0x20),  //ОШИБКА модуля СО2 или FLAP

    /** Hall sensor or Rotation module error. */
    ERROR_40(code = 0x40),  //ОШИБКА модуля Холла или Поворотов
}

/**
 * Represents possible device warnings.
 *
 * @property code The warning code.
 */
enum class DeviceWarning(val code: Int) {
    /** Temperature deviation. */
    WARNING_01(code = 0x01),  //ОТКЛОНЕНИЕ по температуре

    /** Humidity deviation. */
    WARNING_02(code = 0x02),  //ОТКЛОНЕНИЕ по влажности

    /** Sensor substitution occurred. */
    WARNING_04(code = 0x04),  //Произведена подмена датчика

    /** Large temperature drop. */
    WARNING_08(code = 0x08),  //Большой перепад температуры

    /** Incorrect sensor configuration. */
    WARNING_10(code = 0x10),  //Неправильная конфигурация датчиков

    /** Unused warning slot (0x20). */
    WARNING_20(code = 0x20),  //неиспользуется

    /** Unused warning slot (0x40). */
    WARNING_40(code = 0x40),  //неиспользуется
}