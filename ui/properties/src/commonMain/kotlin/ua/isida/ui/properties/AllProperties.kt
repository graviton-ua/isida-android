package ua.isida.ui.properties

import androidx.compose.runtime.Stable
import ua.isida.common.ui.compose.input.NumberInputTextFieldState
import ua.isida.common.ui.resources.*
import org.jetbrains.compose.resources.stringResource
import ua.isida.ui.properties.types.NumberInputTextFieldDeviceProperty
import ua.isida.ui.properties.types.RadioListDeviceProperty
import ua.isida.ui.properties.types.SliderDeviceProperty

//-------------------------- spT0 ------------------------------
@Stable
class DevicePropertySpT0(value: Float? = null) : NumberInputTextFieldDeviceProperty<Float>(
    initValue = value, allowDecimals = true,
    title = { stringResource(Res.string.prop_spT0_lb) + " °C" },
    description = {
        stringResource(
            Res.string.input_info_limit_min_max,
            "28.0", "45.0"
        )
    },
    onValidate = { value ->
        val floatValue = value.toFloatOrNull()
        when {
            floatValue == null -> NumberInputTextFieldState.Error.Invalid
            floatValue < 28f -> NumberInputTextFieldState.Error.CantBeLessThen("30.0")
            floatValue > 45f -> NumberInputTextFieldState.Error.CantBeMoreThen("45.0")
            else -> null
        }
    },
)

//-------------------------- spT1 ------------------------------
@Stable
class DevicePropertySpT1(value: Float? = null) : NumberInputTextFieldDeviceProperty<Float>(
    initValue = value, allowDecimals = true,
    title = { stringResource(Res.string.prop_spT1_lb) + " °C" },
    description = {
        stringResource(
            Res.string.input_info_limit_min_max,
            "20.0", "40.0"
        )
    },
    onValidate = { value ->
        val floatValue = value.toFloatOrNull()
        when {
            floatValue == null -> NumberInputTextFieldState.Error.Invalid
            floatValue < 20f -> NumberInputTextFieldState.Error.CantBeLessThen("20.0")
            floatValue > 40f -> NumberInputTextFieldState.Error.CantBeMoreThen("40.0")
            else -> null
        }
    },
)

//-------------------------- Permission (маска 0xC0) ------------------------------
@Stable
class DevicePropertyPermission(value: Int? = null) : RadioListDeviceProperty<Int>(
    initValue = value,        // нужно применить маску 0xC0 !!!!!!!!!!
    title = { stringResource(Res.string.prop_Rh_lb) },
    list = listOf(0, 1, 2),
    listItemTitleMap = { modeIndex ->
        val id = when (modeIndex) {
            0 -> Res.string.no
            1 -> Res.string.prop_Rh1_lb
            2 -> Res.string.prop_Rh2_lb
            else -> null
        }
        if (id != null) stringResource(id) else "Unknown"
    },
)

//-------------------------- spRh0 ------------------------------
@Stable
class DevicePropertySpRh0(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_spRh0_lb) + " %" },
    description = {
        stringResource(
            Res.string.input_info_limit_min_max,
            "-10.0", "10.0"
        )
    },
    onValidate = { text -> // Переименовал в text для ясности
        val numericValue = text.toFloatOrNull()
        when {
            numericValue == null -> NumberInputTextFieldState.Error.Required
            numericValue < -10f -> NumberInputTextFieldState.Error.CantBeLessThen("-10.0")
            numericValue > 10f -> NumberInputTextFieldState.Error.CantBeMoreThen("10.0")
            else -> null // Если всё в порядке — возвращаем null (ошибки нет)
        }
    },
)

//-------------------------- spRh1 ------------------------------
@Stable
class DevicePropertySpRh1(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value, allowDecimals = true,
    title = { stringResource(Res.string.prop_spRh1_lb) + " %" },
    description = {
        stringResource(
            Res.string.input_info_limit_min_max,
            "10.0", "80.0"
        )
    },
    onValidate = { text -> // Переименовал в text для ясности
        val numericValue = text.toFloatOrNull()
        when {
            numericValue == null -> NumberInputTextFieldState.Error.Required
            numericValue < 10f -> NumberInputTextFieldState.Error.CantBeLessThen("10.0")
            numericValue > 80f -> NumberInputTextFieldState.Error.CantBeMoreThen("80.0")
            else -> null // Если всё в порядке — возвращаем null (ошибки нет)
        }
    },
)

//-------------------------- ExtendMode ------------------------------
@Stable
class DevicePropertyExtendMode(value: Int? = null) : RadioListDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_extMode_lb) },
    list = listOf(0, 1, 2, 3, 4, 5),
    listItemTitleMap = { modeIndex ->
        val id = when (modeIndex) {
            0 -> Res.string.prop_extMode0
            1 -> Res.string.prop_extMode1
            2 -> Res.string.prop_extMode2
            3 -> Res.string.prop_extMode3
            4 -> Res.string.prop_extMode4
            5 -> Res.string.prop_extMode5
            else -> null
        }
        if (id != null) stringResource(id) else "Unknown"
    },
)

//-------------------------- RelayMode ------------------------------
@Stable
class DevicePropertyRelayMode(value: Int? = null) : RadioListDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_relMode_lb) },
    list = listOf(0, 1, 2, 3, 4),
    listItemTitleMap = { modeIndex ->
        val id = when (modeIndex) {
            0 -> Res.string.prop_relMode0
            1 -> Res.string.prop_relMode1
            2 -> Res.string.prop_relMode2
            3 -> Res.string.prop_relMode3
            4 -> Res.string.prop_relMode4
            else -> null
        }
        if (id != null) stringResource(id) else "Unknown"
    },
)

//-------------------------- Program ------------------------------
@Stable
class DevicePropertyProgram(value: Int? = null) : RadioListDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_program_lb) },
    list = listOf(0, 1, 2, 3, 4),
    listItemTitleMap = {
        when (it) {
            0 -> stringResource(Res.string.no)
            else -> {
                val label = stringResource(Res.string.prop_program_lb)
                "$label $it"
            }
        }
    },
)


//-------------------------- MinRun ------------------------------
@Stable
class DevicePropertyMinRun(value: Float? = null) : NumberInputTextFieldDeviceProperty<Float>(
    initValue = value, allowDecimals = true,
    title = { stringResource(Res.string.prop_minImpulse_lb) + stringResource(Res.string.dimen_sec) },
    description = {
        stringResource(
            Res.string.input_info_limit_min_max,
            "0.1", "10.0"
        )
    },
    onValidate = { text -> // Переименовал в text для ясности
        val numericValue = text.toFloatOrNull()
        when {
            numericValue == null -> NumberInputTextFieldState.Error.Required
            numericValue < 0.1 -> NumberInputTextFieldState.Error.CantBeLessThen("0.1")
            numericValue > 10f -> NumberInputTextFieldState.Error.CantBeMoreThen("10.0")
            else -> null // Если всё в порядке — возвращаем null (ошибки нет)
        }
    },
)

//-------------------------- MaxRun ------------------------------
@Stable
class DevicePropertyMaxRun(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_maxImpulse_lb) + stringResource(Res.string.dimen_sec) },
    description = {
        stringResource(
            Res.string.input_info_limit_min_max,
            "1", "100"
        )
    },
    onValidate = { text -> // Переименовал в text для ясности
        val numericValue = text.toIntOrNull()
        when {
            numericValue == null -> NumberInputTextFieldState.Error.Required
            numericValue < 1 -> NumberInputTextFieldState.Error.CantBeLessThen("1")
            numericValue > 100 -> NumberInputTextFieldState.Error.CantBeMoreThen("100")
            else -> null // Если всё в порядке — возвращаем null (ошибки нет)
        }
    },
)

//-------------------------- Period ------------------------------
@Stable
class DevicePropertyPeriod(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_repeatTime_lb) + stringResource(Res.string.dimen_sec) },
    description = {
        stringResource(
            Res.string.input_info_limit_min_max,
            "30", "240"
        )
    },
    onValidate = { text -> // Переименовал в text для ясности
        val numericValue = text.toIntOrNull()
        when {
            numericValue == null -> NumberInputTextFieldState.Error.Required
            numericValue < 30 -> NumberInputTextFieldState.Error.CantBeLessThen("30")
            numericValue > 255 -> NumberInputTextFieldState.Error.CantBeMoreThen("255")
            else -> null // Если всё в порядке — возвращаем null (ошибки нет)
        }
    },
)

//-------------------------- TurnOff ------------------------------
@Stable
class DevicePropertyTurnOff(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_turnOff_lb) + stringResource(Res.string.dimen_min) },
    description = {
        stringResource(
            Res.string.input_info_limit_min_max,
            "10", "255"
        )
    },
    onValidate = { text -> // Переименовал в text для ясности
        val numericValue = text.toIntOrNull()
        when {
            numericValue == null -> NumberInputTextFieldState.Error.Required
            numericValue < 10 -> NumberInputTextFieldState.Error.CantBeLessThen("10")
            numericValue > 255 -> NumberInputTextFieldState.Error.CantBeMoreThen("255")
            else -> null // Если всё в порядке — возвращаем null (ошибки нет)
        }
    },
)

//-------------------------- TurnOn ------------------------------
@Stable
class DevicePropertyTurnOn(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_turnOn_lb) + stringResource(Res.string.dimen_min) },
    description = {
        stringResource(
            Res.string.input_info_limit_min_max,
            "0", "255"
        )
    },
    onValidate = { text -> // Переименовал в text для ясности
        val numericValue = text.toIntOrNull()
        when {
            numericValue == null -> NumberInputTextFieldState.Error.Required
            numericValue < 0 -> NumberInputTextFieldState.Error.CantBeLessThen("0")
            numericValue > 255 -> NumberInputTextFieldState.Error.CantBeMoreThen("255")
            else -> null // Если всё в порядке — возвращаем null (ошибки нет)
        }
    },
)

//-------------------------- Alarm0 ------------------------------
@Stable
class DevicePropertyAlarm0(value: Float? = null) : NumberInputTextFieldDeviceProperty<Float>(
    initValue = value, allowDecimals = true,
    title = { stringResource(Res.string.prop_alarm0_lb) + " °C" },
    description = {
        stringResource(
            Res.string.input_info_limit_min_max,
            "0.2", "25.5"
        )
    },
    onValidate = { text -> // Переименовал в text для ясности
        val numericValue = text.toFloatOrNull()
        when {
            numericValue == null -> NumberInputTextFieldState.Error.Required
            numericValue < 0.2 -> NumberInputTextFieldState.Error.CantBeLessThen("0.2")
            numericValue > 25.5 -> NumberInputTextFieldState.Error.CantBeMoreThen("25.5")
            else -> null // Если всё в порядке — возвращаем null (ошибки нет)
        }
    },
)

//-------------------------- Alarm1 ------------------------------
@Stable
class DevicePropertyAlarm1(value: Float? = null) : NumberInputTextFieldDeviceProperty<Float>(
    initValue = value, allowDecimals = true,
    title = { stringResource(Res.string.prop_alarm1_lb) },
    description = {
        stringResource(
            Res.string.input_info_limit_min_max,
            "0.5", "25.5"
        )
    },
    onValidate = { text -> // Переименовал в text для ясности
        val numericValue = text.toFloatOrNull()
        when {
            numericValue == null -> NumberInputTextFieldState.Error.Required
            numericValue < 0.5 -> NumberInputTextFieldState.Error.CantBeLessThen("0.5")
            numericValue > 25.5 -> NumberInputTextFieldState.Error.CantBeMoreThen("25.5")
            else -> null // Если всё в порядке — возвращаем null (ошибки нет)
        }
    },
)

//-------------------------- ExtOn0 ------------------------------
@Stable
class DevicePropertyExtOn0(value: Float? = null) : NumberInputTextFieldDeviceProperty<Float>(
    initValue = value, allowDecimals = true,
    title = { stringResource(Res.string.prop_extOn0_lb) + " °C" },
    description = {
        stringResource(
            Res.string.input_info_limit_min_max,
            "0.2", "25.5"
        )
    },
    onValidate = { text -> // Переименовал в text для ясности
        val numericValue = text.toFloatOrNull()
        when {
            numericValue == null -> NumberInputTextFieldState.Error.Required
            numericValue < 0.2 -> NumberInputTextFieldState.Error.CantBeLessThen("0.2")
            numericValue > 25.5 -> NumberInputTextFieldState.Error.CantBeMoreThen("25.5")
            else -> null // Если всё в порядке — возвращаем null (ошибки нет)
        }
    },
)

//-------------------------- ExtOn1 ------------------------------
@Stable
class DevicePropertyExtOn1(value: Float? = null) : NumberInputTextFieldDeviceProperty<Float>(
    initValue = value, allowDecimals = true,
    title = { stringResource(Res.string.prop_extOn1_lb) + " °C" },
    description = {
        stringResource(
            Res.string.input_info_limit_min_max,
            "0.2", "25.5"
        )
    },
    onValidate = { text -> // Переименовал в text для ясности
        val numericValue = text.toFloatOrNull()
        when {
            numericValue == null -> NumberInputTextFieldState.Error.Required
            numericValue < 0.2 -> NumberInputTextFieldState.Error.CantBeLessThen("0.2")
            numericValue > 25.5 -> NumberInputTextFieldState.Error.CantBeMoreThen("25.5")
            else -> null // Если всё в порядке — возвращаем null (ошибки нет)
        }
    },
)

//-------------------------- ExtOff0 ------------------------------
@Stable
class DevicePropertyExtOff0(value: Float? = null) : NumberInputTextFieldDeviceProperty<Float>(
    initValue = value, allowDecimals = true,
    title = { stringResource(Res.string.prop_extOff0_lb) + " °C" },
    description = {
        stringResource(
            Res.string.input_info_limit_min_max,
            "0.1", "15.0"
        )
    },
    onValidate = { text -> // Переименовал в text для ясности
        val numericValue = text.toFloatOrNull()
        when {
            numericValue == null -> NumberInputTextFieldState.Error.Required
            numericValue < 0.1 -> NumberInputTextFieldState.Error.CantBeLessThen("0.1")
            numericValue > 15.0 -> NumberInputTextFieldState.Error.CantBeMoreThen("15.0")
            else -> null // Если всё в порядке — возвращаем null (ошибки нет)
        }
    },
)

//-------------------------- ExtOff1 ------------------------------
@Stable
class DevicePropertyExtOff1(value: Float? = null) : NumberInputTextFieldDeviceProperty<Float>(
    initValue = value, allowDecimals = true,
    title = { stringResource(Res.string.prop_extOff1_lb) + " °C" },
    description = {
        stringResource(
            Res.string.input_info_limit_min_max,
            "0.1", "15.0"
        )
    },
    onValidate = { text -> // Переименовал в text для ясности
        val numericValue = text.toFloatOrNull()
        when {
            numericValue == null -> NumberInputTextFieldState.Error.Required
            numericValue < 0.1 -> NumberInputTextFieldState.Error.CantBeLessThen("0.1")
            numericValue > 15.0 -> NumberInputTextFieldState.Error.CantBeMoreThen("15.0")
            else -> null // Если всё в порядке — возвращаем null (ошибки нет)
        }
    },
)

//-------------------------- Air0 ------------------------------
@Stable
class DevicePropertyAir0(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_air0_lb) + stringResource(Res.string.dimen_min) },
    description = {
        stringResource(
            Res.string.input_info_limit_min_max,
            "1", "255"
        )
    },
    onValidate = { text -> // Переименовал в text для ясности
        val numericValue = text.toIntOrNull()
        when {
            numericValue == null -> NumberInputTextFieldState.Error.Required
            numericValue < 1 -> NumberInputTextFieldState.Error.CantBeLessThen("1")
            numericValue > 255 -> NumberInputTextFieldState.Error.CantBeMoreThen("255")
            else -> null // Если всё в порядке — возвращаем null (ошибки нет)
        }
    },
)

//-------------------------- Air1 ------------------------------
@Stable
class DevicePropertyAir1(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_air1_lb) + stringResource(Res.string.dimen_sec) },
    description = {
        stringResource(
            Res.string.input_info_limit_min_max,
            "0", "255"
        )
    },
    onValidate = { text -> // Переименовал в text для ясности
        val numericValue = text.toIntOrNull()
        when {
            numericValue == null -> NumberInputTextFieldState.Error.Required
            numericValue < 0 -> NumberInputTextFieldState.Error.CantBeLessThen("0")
            numericValue > 255 -> NumberInputTextFieldState.Error.CantBeMoreThen("255")
            else -> null // Если всё в порядке — возвращаем null (ошибки нет)
        }
    },
)

//-------------------------- SpCO2 ------------------------------
@Stable
class DevicePropertySpCO2(value: Int? = null) : SliderDeviceProperty<Int>(
    initValue = (value ?: 20),
    min = 1000, max = 5000, increment = 500f,
    title = { stringResource(Res.string.prop_CO2_lb) + " ppm." },
)

//-------------------------- KoffCurr ------------------------------
@Stable
class DevicePropertyKoffCurr(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_koffCurr_lb) },
    description = {
        stringResource(
            Res.string.input_info_limit_min_max,
            "0", "255"
        )
    },
    onValidate = { text -> // Переименовал в text для ясности
        val numericValue = text.toIntOrNull()
        when {
            numericValue == null -> NumberInputTextFieldState.Error.Required
            numericValue < 0 -> NumberInputTextFieldState.Error.CantBeLessThen("0")
            numericValue > 255 -> NumberInputTextFieldState.Error.CantBeMoreThen("255")
            else -> null // Если всё в порядке — возвращаем null (ошибки нет)
        }
    },
)

//-------------------------- Hysteresis (маска 0x3F) ------------------------------
@Stable
class DevicePropertyHysteresis(value: Float? = null) : SliderDeviceProperty<Float>(
    initValue = (value ?: 0.2f),
    min = 0.2f, max = 3.0f, increment = 0.1f,
    title = { stringResource(Res.string.prop_Hysteresis_lb) + " °C" },
)

//-------------------------- TurnTime ------------------------------
@Stable
class DevicePropertyTurnTime(value: Int? = null) : SliderDeviceProperty<Int>(
    initValue = value,
    min = 1, max = 4, increment = 1.0f,
    title = { stringResource(Res.string.prop_turnTime_lb) + stringResource(Res.string.dimen_min) },
)

//------------------------ TurnPermission ----------------------------
@Stable
class DevicePropertyTurnPermission(value: Int? = null) : RadioListDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_turnPermission_lb) },
    list = listOf(1, 0),
    listItemTitleMap = { modeIndex ->
        when (modeIndex) {
            1 -> stringResource(Res.string.prop_turnPermission_on)
            0 -> stringResource(Res.string.prop_turnPermission_off)
            else -> ""
        }
    },
)

//-------------------------- Zonality ----------------------------
@Stable
class DevicePropertyZonality(value: Int? = null) : SliderDeviceProperty<Int>(
    initValue = value,
    min = 1, max = 3, increment = 1.0f,
    title = { stringResource(Res.string.prop_zonelity_lb) + " °C" },
)

//-------------------------- Flap restrictions ----------------------------
@Stable
class DevicePropertyFlapRestrictions(value: Int? = null) : SliderDeviceProperty<Int>(
    initValue = value,
    min = 40, max = 100, increment = 10.0f,
    title = { stringResource(Res.string.prop_flapRestr_lb) + " %" },
)

//----------------- Opening the Flap for the current day ------------------
@Stable
class DevicePropertyFlapProgramDay(value: Int? = null) : SliderDeviceProperty<Int>(
    initValue = value,
    min = 0, max = 40, increment = 10.0f,
    title = { stringResource(Res.string.prop_flapProg_lb) + " %" },
)

//-------------------------- WaitCooling -------------------------
@Stable
class DevicePropertyWaitCooling(value: Int? = null) : SliderDeviceProperty<Int>(
    initValue = value,
    min = 4, max = 17, increment = 1.0f,
    title = { stringResource(Res.string.prop_waitCooling_lb) + stringResource(Res.string.dimen_min) },
)

//-------------------------- Pkoff0 ------------------------------
@Stable
class DevicePropertyPkoff0(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_pkoff0_lb) },
    description = {
        stringResource(
            Res.string.input_info_limit_min_max,
            "1", "100"
        )
    },
    onValidate = { text -> // Переименовал в text для ясности
        val numericValue = text.toIntOrNull()
        when {
            numericValue == null -> NumberInputTextFieldState.Error.Required
            numericValue < 1 -> NumberInputTextFieldState.Error.CantBeLessThen("1")
            numericValue > 100 -> NumberInputTextFieldState.Error.CantBeMoreThen("100")
            else -> null // Если всё в порядке — возвращаем null (ошибки нет)
        }
    },
)

//-------------------------- Pkoff1 ------------------------------
@Stable
class DevicePropertyPkoff1(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_pkoff1_lb) },
    description = {
        stringResource(
            Res.string.input_info_limit_min_max,
            "1", "100"
        )
    },
    onValidate = { text -> // Переименовал в text для ясности
        val numericValue = text.toIntOrNull()
        when {
            numericValue == null -> NumberInputTextFieldState.Error.Required
            numericValue < 1 -> NumberInputTextFieldState.Error.CantBeLessThen("1")
            numericValue > 100 -> NumberInputTextFieldState.Error.CantBeMoreThen("100")
            else -> null // Если всё в порядке — возвращаем null (ошибки нет)
        }
    },
)

//-------------------------- Ikoff0 ------------------------------
@Stable
class DevicePropertyIkoff0(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_ikoff0_lb) },
    description = {
        stringResource(
            Res.string.input_info_limit_min_max,
            "0", "100"
        )
    },
    onValidate = { text -> // Переименовал в text для ясности
        val numericValue = text.toIntOrNull()
        when {
            numericValue == null -> NumberInputTextFieldState.Error.Required
            numericValue < 0 -> NumberInputTextFieldState.Error.CantBeLessThen("0")
            numericValue > 100 -> NumberInputTextFieldState.Error.CantBeMoreThen("100")
            else -> null // Если всё в порядке — возвращаем null (ошибки нет)
        }
    },
)

//-------------------------- Ikoff1 ------------------------------
@Stable
class DevicePropertyIkoff1(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_ikoff1_lb) },
    description = {
        stringResource(
            Res.string.input_info_limit_min_max,
            "0", "100"
        )
    },
    onValidate = { text -> // Переименовал в text для ясности
        val numericValue = text.toIntOrNull()
        when {
            numericValue == null -> NumberInputTextFieldState.Error.Required
            numericValue < 0 -> NumberInputTextFieldState.Error.CantBeLessThen("0")
            numericValue > 100 -> NumberInputTextFieldState.Error.CantBeMoreThen("100")
            else -> null // Если всё в порядке — возвращаем null (ошибки нет)
        }
    },
)

//-------------------------- Identif ------------------------------
@Stable
class DevicePropertyIdentif(value: Int? = null) : NumberInputTextFieldDeviceProperty<Int>(
    initValue = value,
    title = { stringResource(Res.string.prop_identif_lb) },
    description = {
        stringResource(
            Res.string.input_info_limit_min_max,
            "0", "100"
        )
    },
    onValidate = { text -> // Переименовал в text для ясности
        val numericValue = text.toIntOrNull()
        when {
            numericValue == null -> NumberInputTextFieldState.Error.Required
            numericValue < 0 -> NumberInputTextFieldState.Error.CantBeLessThen("0")
            numericValue > 100 -> NumberInputTextFieldState.Error.CantBeMoreThen("100")
            else -> null // Если всё в порядке — возвращаем null (ошибки нет)
        }
    },
)

//-------------------------- MinFan ------------------------------
@Stable
class DevicePropertyMinFan(value: Int? = null) : SliderDeviceProperty<Int>(
    initValue = value,
    min = 60, max = 900, increment = 60.0f,
    title = { stringResource(Res.string.prop_minFan_lb) + stringResource(Res.string.dimen_speed) },
)