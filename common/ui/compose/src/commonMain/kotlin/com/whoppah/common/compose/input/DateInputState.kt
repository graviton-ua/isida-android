package com.whoppah.common.compose.input

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import com.whoppah.common.compose.ui.DateFieldDefaults
import com.whoppah.common.resources.common_error_price_invalid
import com.whoppah.common.resources.common_required
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import com.whoppah.common.resources.Res as R

@Stable
interface DateInputState : InputState<DateInputState.Error> {
    val dateFormatter: DateTimeFormat<LocalDate>
    val date: Result<LocalDate>
        get() = runCatching { LocalDate.parse(fieldState.text.toString(), dateFormatter) }
            .onFailure { errorState.value = Error.Invalid }

    @OptIn(ExperimentalTime::class)
    fun setDate(dateMillis: Long?) {
        setDate(dateMillis?.let { Instant.fromEpochMilliseconds(it) }?.toLocalDateTime(TimeZone.currentSystemDefault())?.date)
    }

    fun setDate(date: LocalDate?) {
        fieldState.setTextAndPlaceCursorAtEnd(date?.format(dateFormatter) ?: "")
        errorState.value = null
    }

    @Immutable
    sealed interface Error : InputState.Error {
        data object Required : Error {
            @Composable
            override fun asLabel(): String = stringResource(R.string.common_required)
        }

        data object Invalid : Error {
            @Composable
            override fun asLabel(): String = stringResource(R.string.common_error_price_invalid)
        }

        data class Custom(val message: String) : Error {
            @Composable
            override fun asLabel(): String = message
        }
    }

    companion object {
        val Preview = DefaultDateInputState(
            fieldState = TextFieldState(),
            errorState = mutableStateOf(null)
        )
    }
}

@Stable
interface DateInputStateHelper : InputStateHelper<DateInputState, DateInputState.Error> {
    fun setDate(date: LocalDate?) = state.setDate(date)

    fun validate(onValidate: ((String) -> DateInputState.Error?)?): DateInputState.Error?

    suspend fun validateOnFly(
        debounce: Long = 500L,
        onValidate: ((String, DateTimeFormat<LocalDate>) -> DateInputState.Error?)? = null,
    )

    override fun clear() {
        super.clear()
        state.setDate(date = null)
    }
}

@Stable
data class DefaultDateInputState(
    override val fieldState: TextFieldState,
    override val errorState: MutableState<DateInputState.Error?>,
    override val enabledState: MutableState<Boolean> = mutableStateOf(true),
    override val dateFormatter: DateTimeFormat<LocalDate> = DateFieldDefaults.dateFormatter,
) : DateInputState {

    constructor(
        initialDate: LocalDate? = null,
        initialError: DateInputState.Error? = null,
        initialEnabled: Boolean = true,
        dateFormatter: DateTimeFormat<LocalDate> = DateFieldDefaults.dateFormatter,
    ) : this(
        fieldState = TextFieldState(initialText = initialDate?.format(dateFormatter) ?: ""),
        errorState = mutableStateOf(initialError),
        enabledState = mutableStateOf(initialEnabled),
        dateFormatter = dateFormatter,
    )

    companion object {
        fun Saver(): Saver<DefaultDateInputState, Any> = Saver(
            save = {
                with(TextFieldState.Saver) { save(it.fieldState) }
            },
            restore = {
                DefaultDateInputState(
                    fieldState = TextFieldState.Saver.restore(it)!!,
                    errorState = mutableStateOf(null)
                )
            }
        )
    }
}

@Stable
class DefaultDateInputStateHelper(
    initialDate: LocalDate? = null,
    private val onValidate: (String, DateTimeFormat<LocalDate>) -> DateInputState.Error? = { text, formatter -> null }
) : DateInputStateHelper {
    override val state: DateInputState = DefaultDateInputState(initialDate = initialDate)


    /**
     * Runs the validation logic against the current text and updates the error state.
     *
     * @return `true` if the input is valid, `false` otherwise.
     */
    override fun validate(onValidate: ((String) -> DateInputState.Error?)?): DateInputState.Error? = when (onValidate) {
        null -> onValidate(text, state.dateFormatter)
        else -> onValidate(text)
    }.also(::setError)

    @OptIn(FlowPreview::class)
    override suspend fun validateOnFly(
        debounce: Long,
        onValidate: ((String, DateTimeFormat<LocalDate>) -> DateInputState.Error?)?,
    ) = with(state) {
        snapshotFlow { fieldState.text.toString() }
            .debounce(debounce)
            .distinctUntilChanged()
            .map { text ->
                when (onValidate) {
                    null -> onValidate(text, dateFormatter)
                    else -> onValidate(text, dateFormatter)
                }
            }
            .collectLatest(::setError)
    }
}