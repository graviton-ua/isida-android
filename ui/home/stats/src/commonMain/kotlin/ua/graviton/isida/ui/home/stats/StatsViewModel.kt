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
import ua.graviton.isida.data.models.DataPackageDto
import ua.graviton.isida.domain.IsidaCommands
import ua.graviton.isida.domain.observers.ObserveDeviceData
import org.jetbrains.compose.resources.StringResource

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
            items = data?.toItems() ?: PlaceholderStats,
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
    header(title = Res.string.titleSensor)
    // *****--------------------------- T0 -------------------------------*****
    item(
        title = Res.string.pv_t0_label,
        value = if (pvT0 > 80) null else pvT0,
        target = spT0,
        valueColor = { value, target ->
            when {
                value == null || target == null -> null
                value > target -> IsidaColor.Red900
                value < target -> IsidaColor.Indigo800
                else -> null
            }
        },
    )

    // *****--------------------------- T1 / RH -------------------------------*****
    item(
        title = if (pvRh != 0) Res.string.pv_rh_label else Res.string.pv_t1_label,
        value = if (pvT1 > 80) null else pvT1,
        target = spT1,
        valueColor = { value, target ->
            when {
                value == null || target == null -> null
                value > target -> IsidaColor.Red900
                value < target -> IsidaColor.Indigo800
                else -> null
            }
        },
    )

    // T2
    item(
        title = Res.string.pv_t2_label,
        value = if (pvT2 > 80) null else pvT2,
    )

    // CO2
    item(
        title = Res.string.cotwo,
        value = if (pvCO2 < 400) null else pvCO2,
    )

    header(title = Res.string.titleStstus)
    // *****--------------------------- State -------------------------------*****
    mapStringResources(
        title = Res.string.state,
        value = state,
        backgroundColor = { value, _ ->
            when (value) {
                IsidaCommands.DeviceMode.DISABLE.code -> IsidaColor.BlueGrey100
                IsidaCommands.DeviceMode.ENABLE.code -> IsidaColor.Green500
                IsidaCommands.DeviceMode.ONLY_ROTATION.code -> IsidaColor.Yellow500
                else -> IsidaColor.Yellow500
            }
        },
        mapper = { value ->
            // 1. Определяем режим
            val mode = when (value) {
                value or IsidaCommands.DeviceMode.ENABLE.code -> IsidaCommands.DeviceMode.ENABLE
                value or IsidaCommands.DeviceMode.WAITING_COOLING.code -> IsidaCommands.DeviceMode.ENABLE
                value or IsidaCommands.DeviceMode.WAITING_ON.code -> IsidaCommands.DeviceMode.ENABLE
                value or IsidaCommands.DeviceMode.HORIZON_ON.code -> IsidaCommands.DeviceMode.ENABLE
                value or IsidaCommands.DeviceMode.HORIZON_SET.code -> IsidaCommands.DeviceMode.ENABLE
                value or IsidaCommands.DeviceMode.TRAY_ROTATION_ON.code -> IsidaCommands.DeviceMode.ENABLE
                value or IsidaCommands.DeviceMode.FAN_MONITORING_ON.code -> IsidaCommands.DeviceMode.ENABLE
                value or IsidaCommands.DeviceMode.ONLY_ROTATION.code -> IsidaCommands.DeviceMode.ONLY_ROTATION
                else -> IsidaCommands.DeviceMode.DISABLE
            }

            // 2. Собираем экстра-флаги
            val extras = if (mode == IsidaCommands.DeviceMode.ENABLE) {
                val result = mutableListOf<IsidaCommands.DeviceModeExtra>()
                if (value == value or IsidaCommands.DeviceModeExtra.EXTRA_1.code) result.add(IsidaCommands.DeviceModeExtra.EXTRA_1)
                if (value == value or IsidaCommands.DeviceModeExtra.EXTRA_2.code) result.add(IsidaCommands.DeviceModeExtra.EXTRA_2)
                if (value == value or IsidaCommands.DeviceModeExtra.EXTRA_3.code) result.add(IsidaCommands.DeviceModeExtra.EXTRA_3)
                if (value == value or IsidaCommands.DeviceModeExtra.EXTRA_4.code) result.add(IsidaCommands.DeviceModeExtra.EXTRA_4)
                result
            } else {
                emptyList()
            }

            // 3. Выбираем ресурс (сохраняем в переменную)
            val resource = when (mode) {
                IsidaCommands.DeviceMode.DISABLE -> Res.string.device_mode_disabled
                IsidaCommands.DeviceMode.ONLY_ROTATION -> Res.string.device_mode_turn
                else -> when {
                    extras.contains(IsidaCommands.DeviceModeExtra.EXTRA_3) -> Res.string.device_mode_extra_3
                    extras.contains(IsidaCommands.DeviceModeExtra.EXTRA_4) -> Res.string.device_mode_extra_4
                    else -> Res.string.device_mode_enabled
                }
            }

            // 4. Оборачиваем в список для соответствия новой сигнатуре функции
            listOf(resource)
        },
    )

    // *****--------------------------- Extend Mode (Raw String) -------------------------------*****
    mapStringResources(
        title = Res.string.extendMode,
        value = extendMode,
        mapper = { value ->
            val resource = when (value) {
                0 -> Res.string.extendSiren
                1 -> Res.string.extendVentilation
                2 -> Res.string.extendForcedHeating
                3 -> Res.string.extendForcedCooling
                4 -> Res.string.extendForcedDehumid
                5 -> Res.string.extendWetting
                else -> null
            }
            if (resource != null) listOf(resource) else emptyList()
        },
    )

    // *****--------------------------- Program -------------------------------*****
    mapStringResources(
        title = Res.string.programm,
        value = programm,
        mapper = { value ->
            val resource = when (value) {
                0 -> Res.string.no
                1 -> Res.string.chickens
                2 -> Res.string.ducklings
                3 -> Res.string.ducklings
                4 -> Res.string.quail
                else -> null
            }

            // Оборачиваем найденный ресурс в список.
            // Если ничего не найдено (null), возвращаем пустой список.
            if (resource != null) listOf(resource) else emptyList()
        },
    )

    header(title = Res.string.titleControl)
    // *****--------------------------- Timer -------------------------------*****
    item(
        title = Res.string.timer,
        value = pvTimer,
        target = timer0,
    )

    // *****--------------------------- Power -------------------------------*****
    item(
        title = Res.string.power,
        value = power,
        valueColor = { value, _ ->
            if (value != null && value != 0) IsidaColor.Red900
            else null
        },
    )

    // *****--------------------------- Flap -------------------------------*****
    item(
        title = Res.string.flap,
        value = pvFlap,
    )

    header(title = Res.string.titleErrors)

    // *****--------------------------- Fuses -------------------------------*****
    mapStringResources(
        title = Res.string.fuses,
        value = fuses,
        backgroundColor = { value, _ ->
            if (value != null && value != 0) IsidaColor.Red500
            else null
        },
        valueColor = { value, _ ->
            if (value != null && value != 0) IsidaColor.Yellow900
            else null
        },
        mapper = { value ->
            val result = mutableListOf<StringResource>()

            // Проверяем каждый предохранитель по его битовой маске
            if (value and 1 != 0) result.add(Res.string.fuses_0)
            if (value and 2 != 0) result.add(Res.string.fuses_1)
            if (value and 4 != 0) result.add(Res.string.fuses_2)
            if (value and 8 != 0) result.add(Res.string.fuses_3)

            // Если ни один бит не поднят, возвращаем список с ресурсом "нет/норма"
            if (result.isEmpty()) {
                result.add(Res.string.no)
            }

            result
        },
    )

    // *****--------------------------- Errors -------------------------------*****
    mapStringResources(
        title = Res.string.errors,
        value = errors, // предполагаем, что это Int
        backgroundColor = { value, _ ->
            if (value != null && value != 0) IsidaColor.Red500
            else null
        },
        valueColor = { value, _ ->
            if (value != null && value != 0) IsidaColor.Yellow900
            else null
        },
        mapper = { value ->
            val result = mutableListOf<StringResource>()

            // Проверка битовых флагов
            if (value and 1 != 0) result.add(Res.string.error_01)
            if (value and 2 != 0) result.add(Res.string.error_02)
            if (value and 4 != 0) result.add(Res.string.error_04)
            if (value and 8 != 0) result.add(Res.string.error_08)

            // Если список пуст, можно добавить "Нет ошибок"
            if (result.isEmpty()) result.add(Res.string.no)

            result // Возвращаем список
        },
    )

    // *****--------------------------- Warnings -------------------------------*****
    mapStringResources(
        title = Res.string.warnings,
        value = warning,
        backgroundColor = { value, _ ->
            if (value != null && value != 0) IsidaColor.Yellow500
            else null
        },
        valueColor = { value, _ ->
            if (value != null && value != 0) IsidaColor.Red900
            else null
        },
        mapper = { value ->
            val result = mutableListOf<StringResource>()

            // Используем битовое "И" (and), чтобы проверить каждый флаг независимо
            if (value and 1 != 0) result.add(Res.string.warning_01)
            if (value and 2 != 0) result.add(Res.string.warning_02)
            if (value and 4 != 0) result.add(Res.string.warning_04)
            if (value and 8 != 0) result.add(Res.string.warning_08)

            // Если активных предупреждений нет, возвращаем "Нет"
            if (result.isEmpty()) {
                result.add(Res.string.no)
            }

            result // Возвращаем накопленный список
        },
    )




}
