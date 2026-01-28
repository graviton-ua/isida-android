@file:Suppress("SimplifiableCallChain")

package ua.graviton.isida.ui.home.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whoppah.common.compose.theme.IsidaColor
import com.whoppah.common.resources.*
import com.whoppah.metrox.viewmodel.ViewModelKey
import com.whoppah.metrox.viewmodel.ViewModelScope
import com.whoppah.util.ObservableLoadingCounter
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import ua.graviton.isida.data.models.DataPackageDto
import ua.graviton.isida.domain.IsidaCommands
import ua.graviton.isida.domain.observers.ObserveDeviceData
import ua.graviton.isida.ui.home.stats.StatsItem.Title.ComposableString.Companion.composableString

@Inject
@ViewModelKey(StatsViewModel::class)
@ContributesIntoMap(ViewModelScope::class)
/**
 * ViewModel для экрана статистики.
 * Отвечает за наблюдение за данными устройства и преобразование их в список [StatsItem]
 * с использованием DSL [buildStats].
 */
class StatsViewModel(
    observeDeviceData: ObserveDeviceData,
) : ViewModel() {
    private val loadingState = ObservableLoadingCounter()

    /**
     * Поток состояния пользовательского интерфейса.
     * Объединяет последние данные устройства с состоянием загрузки.
     * Если данные равны null (еще не получены), возвращается к [PlaceholderStats] для отображения структуры.
     */
    val state: StateFlow<StatsViewState> = combine(
        observeDeviceData.flow, loadingState.observable
    ) { data, loading ->
        val deviceBgColor = when {
            data != null && data.fuses + data.errors + data.warning > 0 -> IsidaColor.Red500
            data != null && data.state == 1 -> IsidaColor.Green500
            data != null && data.state == 2 -> IsidaColor.Yellow500
            else -> null
        }

        // Создает модель состояния экрана
        StatsViewState(
            titleDeviceId = data?.node,
            titleDeviceBackgroundColor = deviceBgColor,
            items = data?.toItems() ?: emptyList(),
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = StatsViewState.Empty,
    )
}

/**
 * Статический список элементов, используемый, когда реальные данные недоступны.
 * Отражает структуру [toItems], но со всеми значениями, установленными в null.
 * Это гарантирует, что пользовательский интерфейс отображает правильные метки и макет еще до прибытия первого пакета данных.
 */
internal val PlaceholderStats = buildStats {
    item<Float>(Res.string.pv_t0_label, null, null)
    item<Float>(Res.string.pv_t1_label, null, null)
    item<Float>(Res.string.pv_t2_label, null)
    item<Int>(Res.string.cotwo, null)
    item<Int>(Res.string.timer, null, null)
    item<Int>(Res.string.power, null)
    item<Int>(Res.string.flap, null)
    mapStringResource<Int>(Res.string.fuses, null)
    mapStringResource<Int>(Res.string.errors, null)
    mapStringResource<Int>(Res.string.warnings, null)
    mapStringResource<Int>(Res.string.state, null)
    mapString<Int>(Res.string.extendMode, null) { null }
    mapStringResource<Int>(Res.string.programm, null)
}

/**
 * Преобразует [DataPackageDto] в список элементов [StatsItem].
 * Использует DSL [buildStats] для определения макета и логики для каждой строки.
 * Здесь происходит сопоставление сырых байтов с представлением в пользовательском интерфейсе.
 */
private fun DataPackageDto.toItems(): List<StatsItem> = buildStats {
    header(
        title = composableString(node) { stringResource(Res.string.CellNum, node) },
        style = {
            backgroundColor = if (state == 0x80) IsidaColor.Yellow500 else if (state > 0) IsidaColor.Green500 else null
        }
    )

    header(
        title = composableString { stringResource(Res.string.titleSensor) }
    )

    // *****--------------------------- Status -------------------------------*****
    item(
        title = composableString { stringResource(Res.string.titleState) },
        content = composableString(state) {
            val value = state and 0x7E
            val result = mutableListOf<StringResource>()
            // Проверяем каждый предохранитель по его битовой маске
            if (value and 0x02 != 0) result.add(Res.string.state_2)
            if (value and 0x04 != 0) result.add(Res.string.state_4)
            if (value and 0x08 != 0) result.add(Res.string.state_8)
            if (value and 0x10 != 0) result.add(Res.string.state_10)
            if (value and 0x20 != 0) result.add(Res.string.state_20)
            if (value and 0x40 != 0) result.add(Res.string.state_40)
            // Если ни один бит не поднят, возвращаем список с ресурсом "нет/норма"
            // if (result.isEmpty()) {
            //     result.add(Res.string.no)
            // }
            result.map { stringResource(it) }.joinToString(separator = "\n")
        },
    )

    // *****--------------------------- T0 -------------------------------*****
    item(
        title = composableString { stringResource(Res.string.pv_t0_label) },
        // value = if (pvT0 > 80) null else pvT0,
        // target = spT0,
        content = composableString(pvT0, spT0) {
            val valueFormatted = if (pvT0 > 80) "--.-" else String.format("%.1f", pvT0)
            "$valueFormatted °C [$spT0 °C]"
        },
        style = {
            val st = state and IsidaCommands.DeviceMode.ENABLE.code
            val value = if (pvT0 > 80) null else pvT0
            if(st !=0) {
                backgroundColor = when {
                    value == null -> null
                    value >= spT0 + alarm0 -> IsidaColor.Red100
                    value <= spT0 - alarm0 -> IsidaColor.Blue100
                    else -> IsidaColor.Green100
                }
            }
        },
    )

    // *****--------------------------- T1 / RH -------------------------------*****
    item(
        // title = if (pvRh != 0) Res.string.pv_rh_label else Res.string.pv_t1_label,
        title = composableString {
            stringResource(if (pvRh != 0) Res.string.pv_rh_label else Res.string.pv_t1_label)
        },
        content = composableString(pvT1, spT1, pvRh) {
            if (pvRh > 10){
                val value = if (pvRh > 100) 100 else pvRh
                "$value % [$spRh1 %]"
            } else {
                val valueFormatted = if (pvT0 > 80) "--.-" else String.format("%.1f", pvT1)
                "$valueFormatted °C [$spT1 °C]"
            }
        },
        style = {
            val st = state and IsidaCommands.DeviceMode.ENABLE.code
            val value = if (pvT1 > 80) null else pvT1
            if(st !=0) {
                backgroundColor = when {
                    value == null -> null
                    value >= spT1 + alarm1 -> IsidaColor.Red100
                    value <= spT1 - alarm1 -> IsidaColor.Blue100
                    else -> IsidaColor.Green100
                }
            }
        },
    )

    // *****--------------------------- T2 -------------------------------*****
    item(
        title = composableString { stringResource(Res.string.pv_t2_label) },
        content = composableString(pvT2) {
            val value = if (pvT2 > 80) null else pvT2
            if (value != null) {
                val valueFormatted = String.format("%.1f", value)
                "$valueFormatted °C"
            } else ""
        },
        style = {
            val st = warning and IsidaCommands.Warning.WARNING_08.code
            backgroundColor = if(st !=0) IsidaColor.Red100
            else null
        },
    )

    // *****--------------------------- CO2 -------------------------------*****
    item(
        title = composableString { stringResource(Res.string.cotwo) },
        content = composableString(pvCO2) {
            val value = if (pvCO2 < 100) null else pvCO2
            value?.let { "$it ppm" } ?: ""
        }
    )

    header(
        title = composableString { stringResource(Res.string.titleStstus) }
    )
    // *****--------------------------- State -------------------------------*****
    item(
        title = composableString(state) {
            // stringResource(Res.string.state)
            val str = stringResource(Res.string.state)
            // Настраиваем формат: префикс "0x" и минимальная длина 2 символа
            val myFormat = HexFormat {
                number {
                    prefix = "0x"
                    minLength = 2
                    removeLeadingZeros = true
                    upperCase = true // Чтобы получить 'D' вместо 'd'
                }
            }
            "$str:  ${state.toHexString(myFormat)}"
        },
        content = composableString(state) {
            // 1. Определяем режим
            val mode = when (state) {
                state or IsidaCommands.DeviceMode.ENABLE.code -> IsidaCommands.DeviceMode.ENABLE
                state or IsidaCommands.DeviceMode.WAITING_COOLING.code -> IsidaCommands.DeviceMode.ENABLE
                state or IsidaCommands.DeviceMode.WAITING_ON.code -> IsidaCommands.DeviceMode.ENABLE
                state or IsidaCommands.DeviceMode.HORIZON_ON.code -> IsidaCommands.DeviceMode.ENABLE
                state or IsidaCommands.DeviceMode.HORIZON_SET.code -> IsidaCommands.DeviceMode.ENABLE
                state or IsidaCommands.DeviceMode.TRAY_ROTATION_ON.code -> IsidaCommands.DeviceMode.ENABLE
                state or IsidaCommands.DeviceMode.FAN_MONITORING_ON.code -> IsidaCommands.DeviceMode.ENABLE
                state or IsidaCommands.DeviceMode.ONLY_ROTATION.code -> IsidaCommands.DeviceMode.ONLY_ROTATION
                else -> IsidaCommands.DeviceMode.DISABLE
            }

            // 2. Собираем экстра-флаги
            val extras = if (mode == IsidaCommands.DeviceMode.ENABLE) {
                val result = mutableListOf<IsidaCommands.DeviceModeExtra>()
                if (state == (state or IsidaCommands.DeviceModeExtra.EXTRA_1.code)) result.add(IsidaCommands.DeviceModeExtra.EXTRA_1)
                if (state == (state or IsidaCommands.DeviceModeExtra.EXTRA_2.code)) result.add(IsidaCommands.DeviceModeExtra.EXTRA_2)
                if (state == (state or IsidaCommands.DeviceModeExtra.EXTRA_3.code)) result.add(IsidaCommands.DeviceModeExtra.EXTRA_3)
                if (state == (state or IsidaCommands.DeviceModeExtra.EXTRA_4.code)) result.add(IsidaCommands.DeviceModeExtra.EXTRA_4)
                result
            } else {
                emptyList()
            }

            // 3. Выбираем ресурс (сохраняем в переменную)
            val resource = when (mode) {
                IsidaCommands.DeviceMode.DISABLE -> Res.string.device_mode_disabled
                IsidaCommands.DeviceMode.ONLY_ROTATION -> Res.string.device_mode_turn
                else -> when {
                    extras.contains(IsidaCommands.DeviceModeExtra.EXTRA_3) -> Res.string.state_10
                    extras.contains(IsidaCommands.DeviceModeExtra.EXTRA_4) -> Res.string.state_2
                    else -> Res.string.device_mode_enabled
                }
            }

            // 4. Оборачиваем в список для соответствия новой сигнатуре функции
            stringResource(resource)
        },
        style = {
            backgroundColor =
                if (state == IsidaCommands.DeviceMode.DISABLE.code) IsidaColor.BlueGrey100
                else if ((state and IsidaCommands.DeviceMode.ENABLE.code)==IsidaCommands.DeviceMode.ENABLE.code) IsidaColor.Green500
                else if ((state and IsidaCommands.DeviceMode.ONLY_ROTATION.code)==IsidaCommands.DeviceMode.ONLY_ROTATION.code) IsidaColor.Yellow500
                else IsidaColor.Red100
        },
    )

    // *****--------------------------- Door -------------------------------*****
    item(
        //title = Res.string.door,
        title = composableString { stringResource(Res.string.door) },
        content = composableString(fuses) {
            val value = fuses and 0x10
            val resource = if (value != 0) Res.string.doorOpen else Res.string.doorClose
            stringResource(resource)
        },
        style = {
            val value = fuses and 0x10
            if (value != 0 && state > 0 && state < 0x80) {
                backgroundColor = IsidaColor.Yellow900
                valueColor = IsidaColor.Red900
            }
        },
    )

    // *****--------------------------- Program -------------------------------*****
    item(
        title = composableString { stringResource(Res.string.programm) },
        content = composableString(programm) {
            val resource = when (programm) {
                0 -> Res.string.no
                1 -> Res.string.chickens
                2 -> Res.string.ducklings
                3 -> Res.string.ducklings
                4 -> Res.string.quail
                else -> null
            }
            resource?.let { stringResource(resource) } ?: ""
        }
    )

    header(
        title = composableString { stringResource(Res.string.titleControl) }
    )
    // *****--------------------------- Power -------------------------------*****
    item(
        title = composableString { stringResource(Res.string.heater) },
        content = composableString(power) {
            val str = stringResource(Res.string.power)
            "$str:  $power %"
        },
        style = {
            if (power != 0) {
                valueColor = IsidaColor.Red900
                backgroundColor = IsidaColor.Yellow900
            }
        },
    )

    // *****--------------------------- Wetting -------------------------------*****
    item(
        title = composableString { stringResource(Res.string.outWetting) },
        content = composableString(output) {
            val value = output and IsidaCommands.OutputBit.OUT_Wetting.code
            if (value != 0) stringResource(Res.string.wetOn) else stringResource(Res.string.wetOff)
        },
        style = {
            val value = output and IsidaCommands.OutputBit.OUT_Wetting.code
            if (value != 0) backgroundColor = IsidaColor.Blue100
        },
    )

    // *****--------------------------- Flap -------------------------------*****
    item(
        title = composableString { stringResource(Res.string.outFlap) },
        content = composableString(output, pvFlap) {
            val value = output and IsidaCommands.OutputBit.OUT_Flap.code
            val str = if (value != 0) stringResource(Res.string.flapOpen) else stringResource(Res.string.flapClose)
            "$str  $pvFlap %"
        },
        style = {
            val value = output and IsidaCommands.OutputBit.OUT_Flap.code
            if (value != 0) backgroundColor = IsidaColor.Green100
        },
    )

    // *****--------------------------- Extend -------------------------------*****
    item(
        title = composableString { stringResource(Res.string.outExtend) },
        content = composableString(extendMode) {
            val resource = when (extendMode) {
                0 -> Res.string.extendSiren
                1 -> Res.string.extendVentilation
                2 -> Res.string.extendForcedHeating
                3 -> Res.string.extendForcedCooling
                4 -> Res.string.extendForcedDehumid
                5 -> Res.string.extendWetting
                else -> null
            }
            resource?.let { stringResource(resource) } ?: ""
        },
        style = {
            val value = output and IsidaCommands.OutputBit.OUT_Extend.code
            if (value != 0) backgroundColor = IsidaColor.Green500
        },
    )

    // *****--------------------------- Trays -------------------------------*****
    item(
        title = composableString { stringResource(Res.string.outTrays) },
        content = composableString(output, pvTimer, timer0) {
            val value = output and IsidaCommands.OutputBit.OUT_Trays.code
            "$pvTimer min [$timer0]"
        },
        style = {
            val value = output and IsidaCommands.OutputBit.OUT_Trays.code
            if (value != 0) backgroundColor = IsidaColor.Green500
        },
    )

    header(
        title = composableString { stringResource(Res.string.titleErrors) }
    )
    // *****--------------------------- Fuses -------------------------------*****
    item(
        title = composableString(fuses) {
            val str = stringResource(Res.string.fuses)
            // Настраиваем формат: префикс "0x" и минимальная длина 2 символа
            val myFormat = HexFormat {
                number {
                    prefix = "0x"
                    minLength = 2
                    removeLeadingZeros = true
                    upperCase = true // Чтобы получить 'D' вместо 'd'
                }
            }
            "$str:  ${fuses.toHexString(myFormat)}"
        },
        content = composableString(fuses) {
            val value = fuses and 0x6F
            val result = mutableListOf<StringResource>()
            // Проверяем каждый предохранитель по его битовой маске
            if (value and 0x01 != 0) result.add(Res.string.fuses_0)
            if (value and 0x02 != 0) result.add(Res.string.fuses_1)
            if (value and 0x04 != 0) result.add(Res.string.fuses_2)
            if (value and 0x08 != 0) result.add(Res.string.fuses_3)
            if (value and 0x20 != 0) result.add(Res.string.fuses_5)
            if (value and 0x40 != 0) result.add(Res.string.fuses_6)

            // Если ни один бит не поднят, возвращаем список с ресурсом "нет/норма"
            if (result.isEmpty()) {
                result.add(Res.string.no)
            }
            result.map { stringResource(it) }.joinToString(separator = "\n")
        },
        style = {
            val value = fuses and 0x6F
            if (value != 0) {
                backgroundColor = IsidaColor.Red900
                valueColor = IsidaColor.Yellow900
            }
        },
    )

    // *****--------------------------- Errors -------------------------------*****
    item(
        title = composableString(errors) {
            val str = stringResource(Res.string.errors)
            // Настраиваем формат: префикс "0x" и минимальная длина 2 символа
            val myFormat = HexFormat {
                number {
                    prefix = "0x"
                    minLength = 2
                    removeLeadingZeros = true
                    upperCase = true // Чтобы получить 'D' вместо 'd'
                }
            }
            "$str:  ${errors.toHexString(myFormat)}"
        },
        content = composableString(errors) {
            val value = errors // предполагаем, что это Int
            val result = mutableListOf<StringResource>()

            // Проверка битовых флагов
            if (value and IsidaCommands.Errors.ERROR_01.code != 0) result.add(Res.string.error_01)
            if (value and IsidaCommands.Errors.ERROR_02.code != 0) result.add(Res.string.error_02)
            if (value and IsidaCommands.Errors.ERROR_04.code != 0) result.add(Res.string.error_04)
            if (value and IsidaCommands.Errors.ERROR_08.code != 0) result.add(Res.string.error_08)
            if (value and IsidaCommands.Errors.ERROR_10.code != 0) result.add(Res.string.error_10)
            if (value and IsidaCommands.Errors.ERROR_20.code != 0) result.add(Res.string.error_20)
            if (value and IsidaCommands.Errors.ERROR_40.code != 0) result.add(Res.string.error_40)
            // Если список пуст, можно добавить "Нет ошибок"
            if (result.isEmpty()) result.add(Res.string.no)
            result.map { stringResource(it) }.joinToString(separator = "\n")
        },
        style = {
            val value = errors // предполагаем, что это Int
            if (value != 0) {
                backgroundColor = IsidaColor.Red500
                valueColor = IsidaColor.Yellow900
            }
        },
    )

    // *****--------------------------- Warnings -------------------------------*****
    item(
        title = composableString(warning) {
            val str = stringResource(Res.string.warnings)
            // Настраиваем формат: префикс "0x" и минимальная длина 2 символа
            val myFormat = HexFormat {
                number {
                    prefix = "0x"
                    minLength = 2
                    removeLeadingZeros = true
                    upperCase = true // Чтобы получить 'D' вместо 'd'
                }
            }
            "$str:  ${warning.toHexString(myFormat)}"
        },
        content = composableString(warning) {
            val value = warning
            val result = mutableListOf<StringResource>()

            // Используем битовое "И" (and), чтобы проверить каждый флаг независимо
            if (value and IsidaCommands.Warning.WARNING_01.code != 0) result.add(Res.string.warning_01)
            if (value and IsidaCommands.Warning.WARNING_02.code != 0) result.add(Res.string.warning_02)
            if (value and IsidaCommands.Warning.WARNING_04.code != 0) result.add(Res.string.warning_04)
            if (value and IsidaCommands.Warning.WARNING_08.code != 0) result.add(Res.string.warning_08)
            if (value and IsidaCommands.Warning.WARNING_10.code != 0) result.add(Res.string.warning_10)
            // if (value and IsidaCommands.Warning.WARNING_20.code != 0) result.add(Res.string.warning_20)

            // Если активных предупреждений нет, возвращаем "Нет"
            if (result.isEmpty()) {
                result.add(Res.string.no)
            }
            result.map { stringResource(it) }.joinToString(separator = "\n")
        },
        style = {
            val value = warning
            if (value != 0) {
                backgroundColor = IsidaColor.Yellow500
                valueColor = IsidaColor.Red900
            }
        },
    )
}
