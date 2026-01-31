package ua.graviton.isida.domain

enum class DeviceMode(val code: Int) {
    DISABLE(0x00),          // "камера ОТКЛЮЧЕНА"
    ENABLE(0x01),           // "камера ВКЛЮЧИНА"
    WAITING_COOLING(0x02),  // "подгототка к ОХЛАЖДЕНИЮ"
    WAITING_ON(0x04),       // "подгототка к ЗАПУСКУ"
    HORIZON_ON(0x08),       // "ВКЛЮЧЕН ГОРИЗОНТ"
    HORIZON_SET(0x10),      // "ГОРИЗОНТ УСТАНОВЛЕН"
    TRAY_ROTATION_ON(0x20), // "ВКЛЮЧЕН мониторинг поворота лотков"
    FAN_MONITORING_ON(0x40),// "ВКЛЮЧЕН мониторинг тихоходного вентилятора"
    ONLY_ROTATION(0x80),    // "только ПОВОРОТ"
}

enum class OutputBit(val code: Int) {
    OUT_Wetting(0x02),  //УВЛАЖНИТЕЛЬ
    OUT_Flap(0x04),     //Заслонка воздухообмена
    OUT_Extend(0x08),   //Вспомогательный канал
    OUT_Trays(0x10),    //Поворот лотков
}

enum class DeviceModeExtra(val code: Int) {
    // TODO: There is 2 more states need to be added later
    EXTRA_1(0x40),   //"Мониторинг тихоход. вентилятора"    data = data | 0x40
    EXTRA_2(0x20),   //"Мониторинг поворота лотков"         data = data | 0x20
    EXTRA_3(0x08),   //"Горизонтальное положение лотков"    data = data | 0x08
    EXTRA_4(0x02),   //"Режим подгототка к ОХЛАЖДЕНИЮ"      data = data | 0x02
}

enum class Errors(val code: Int) {
    ERROR_01(code = 0x01),  //ОШИБКА ДАТЧИКА температуры
    ERROR_02(code = 0x02),  //ОШИБКА ДАТЧИКА влажности
    ERROR_04(code = 0x04),  //ПРОБОЙ СИМИСТОРА
    ERROR_08(code = 0x08),  //НЕИСПРАВНА цепь НАГРЕВАТЕЛЯ
    ERROR_10(code = 0x10),  //ПЕРЕГРЕВ СИМИСТОРА
    ERROR_20(code = 0x20),  //ОШИБКА модуля СО2 или FLAP
    ERROR_40(code = 0x40),  //ОШИБКА модуля Холла или Поворотов
}

enum class Warning(val code: Int) {
    WARNING_01(code = 0x01),  //ОТКЛОНЕНИЕ по температуре
    WARNING_02(code = 0x02),  //ОТКЛОНЕНИЕ по влажности
    WARNING_04(code = 0x04),  //Произведена подмена датчика
    WARNING_08(code = 0x08),  //Большой перепад температуры
    WARNING_10(code = 0x10),  //Неправильная конфигурация датчиков
    WARNING_20(code = 0x20),  //неиспользуется
    WARNING_40(code = 0x40),  //неиспользуется
}