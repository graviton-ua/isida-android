package com.whoppah.common.compose.input

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import com.whoppah.common.compose.input.DateInputTextFieldState.Error
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
interface DateInputTextFieldState : InputTextFieldState<Error> {
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
    sealed interface Error : InputTextFieldState.Error {
        data object Required : Error {
            @Composable
            override fun asLabel(): String = stringResource(R.string.common_required)
        }

        data object Invalid : Error {
            @Composable
            override fun asLabel(): String = stringResource(R.string.common_error_price_invalid)
        }

        data class Custom(private val onMessage: @Composable () -> String) : Error {
            constructor(message: String) : this(onMessage = { message })

            @Composable
            override fun asLabel(): String = onMessage()
        }
    }

    companion object {
        val Preview = DefaultDateInputTextFieldState(
            fieldState = TextFieldState(),
            errorState = mutableStateOf(null)
        )
    }
}

@Stable
interface DateInputTextFieldStateHelper : InputTextFieldStateHelper<DateInputTextFieldState, Error> {
    fun setDate(date: LocalDate?) = state.setDate(date)

    suspend fun validateOnFly(
        debounce: Long = 500L,
        onValidate: (InputTextFieldStateErrorScope<Error>.(String, DateTimeFormat<LocalDate>) -> Error?)? = null,
    )

    override fun clear() {
        super.clear()
        state.setDate(date = null)
    }
}

@Stable
data class DefaultDateInputTextFieldState(
    override val fieldState: TextFieldState,
    override val errorState: MutableState<Error?>,
    override val enabledState: MutableState<Boolean> = mutableStateOf(true),
    override val dateFormatter: DateTimeFormat<LocalDate> = DateFieldDefaults.dateFormatter,
) : DateInputTextFieldState {

    constructor(
        initialDate: LocalDate? = null,
        initialError: Error? = null,
        initialEnabled: Boolean = true,
        dateFormatter: DateTimeFormat<LocalDate> = DateFieldDefaults.dateFormatter,
    ) : this(
        fieldState = TextFieldState(initialText = initialDate?.format(dateFormatter) ?: ""),
        errorState = mutableStateOf(initialError),
        enabledState = mutableStateOf(initialEnabled),
        dateFormatter = dateFormatter,
    )

    companion object {
        fun Saver(): Saver<DefaultDateInputTextFieldState, Any> = Saver(
            save = {
                with(TextFieldState.Saver) { save(it.fieldState) }
            },
            restore = {
                DefaultDateInputTextFieldState(
                    fieldState = TextFieldState.Saver.restore(it)!!,
                    errorState = mutableStateOf(null)
                )
            }
        )
    }
}

@Stable
class DefaultDateInputTextFieldStateHelper(
    initialDate: LocalDate? = null,
    private val onValidate: InputTextFieldStateErrorScope<Error>.(String, DateTimeFormat<LocalDate>) -> Error? = { text, formatter -> null },
    private val errorScope: InputTextFieldStateErrorScope<Error> = DefaultErrorScope,
) : DateInputTextFieldStateHelper {
    override val state: DateInputTextFieldState = DefaultDateInputTextFieldState(initialDate = initialDate)


    /**
     * Runs the validation logic against the current text and updates the error state.
     *
     * @return `true` if the input is valid, `false` otherwise.
     */
    override fun validate(onValidate: (InputTextFieldStateErrorScope<Error>.(String) -> Error?)?): Error? = with(errorScope) {
        when (onValidate) {
            null -> onValidate(text, state.dateFormatter)
            else -> onValidate(text)
        }.also(::setError)
    }

    @OptIn(FlowPreview::class)
    override suspend fun validateOnFly(
        debounce: Long,
        onValidate: (InputTextFieldStateErrorScope<Error>.(String, DateTimeFormat<LocalDate>) -> Error?)?,
    ) = with(state) {
        snapshotFlow { fieldState.text.toString() }
            .debounce(debounce)
            .distinctUntilChanged()
            .map { text ->
                with(errorScope) {
                    when (onValidate) {
                        null -> onValidate(text, dateFormatter)
                        else -> onValidate(text, dateFormatter)
                    }
                }
            }
            .collectLatest(::setError)
    }

    object DefaultErrorScope : InputTextFieldStateErrorScope<Error> {
        override fun error(onMessage: @Composable (() -> String)): Error = Error.Custom(onMessage)
    }
}