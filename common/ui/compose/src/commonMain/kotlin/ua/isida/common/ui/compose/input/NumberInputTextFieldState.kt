package ua.isida.common.ui.compose.input

import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import org.jetbrains.compose.resources.stringResource
import ua.isida.common.ui.compose.input.NumberInputTextFieldState.Error
import ua.isida.common.ui.resources.input_cant_be_less_than
import ua.isida.common.ui.resources.input_cant_be_more_than
import ua.isida.common.ui.resources.input_invalid
import ua.isida.common.ui.resources.input_required
import ua.isida.common.ui.resources.Res as R

@Stable
interface NumberInputTextFieldState : InputTextFieldState<Error> {

    val valueAsInt: Int?
    val valueAsFloat: Float?

    @Immutable
    sealed interface Error : InputTextFieldState.Error {
        data object Required : Error {
            @Composable
            override fun asLabel(): String = stringResource(R.string.input_required)
        }

        data object Invalid : Error {
            @Composable
            override fun asLabel(): String = stringResource(R.string.input_invalid)
        }

        data class CantBeLessThen(val value: String) : Error {
            @Composable
            override fun asLabel(): String = stringResource(R.string.input_cant_be_less_than, value)
        }

        data class CantBeMoreThen(val value: String) : Error {
            @Composable
            override fun asLabel(): String = stringResource(R.string.input_cant_be_more_than, value)
        }

        data class Custom(private val onMessage: @Composable () -> String) : Error {
            constructor(message: String) : this(onMessage = { message })

            @Composable
            override fun asLabel(): String = onMessage()
        }
    }

    companion object {
        fun preview(text: String = "", error: Error? = null) = DefaultNumberInputTextFieldState(
            fieldState = TextFieldState(initialText = text),
            errorState = mutableStateOf(error),
        )

        val Preview = DefaultNumberInputTextFieldState(
            fieldState = TextFieldState(initialText = "100"),
            errorState = mutableStateOf(null),
        )
        val PreviewError = DefaultNumberInputTextFieldState(
            fieldState = TextFieldState(initialText = "100"),
            errorState = mutableStateOf(Error.Required),
        )
    }
}

@Stable
interface NumberInputTextFieldStateHelper : InputTextFieldStateHelper<NumberInputTextFieldState, Error> {
    fun <T : Number> setValue(value: T?)
}

@Stable
data class DefaultNumberInputTextFieldState(
    override val fieldState: TextFieldState,
    override val errorState: MutableState<Error?>,
    override val enabledState: MutableState<Boolean> = mutableStateOf(true),
) : NumberInputTextFieldState {

    override val valueAsInt: Int?
        get() = fieldState.text.toString().toIntOrNull()
    override val valueAsFloat: Float?
        get() = fieldState.text.toString().toFloatOrNull()

    constructor(
        initialText: String = "",
        initialError: Error? = null,
        initialEnabled: Boolean = true,
    ) : this(
        fieldState = TextFieldState(initialText = initialText),
        errorState = mutableStateOf(initialError),
        enabledState = mutableStateOf(initialEnabled),
    )

    companion object {
        fun Saver(): Saver<DefaultNumberInputTextFieldState, Any> = Saver(
            save = {
                with(TextFieldState.Saver) { save(it.fieldState) }
            },
            restore = {
                DefaultNumberInputTextFieldState(
                    fieldState = TextFieldState.Saver.restore(it)!!,
                    errorState = mutableStateOf(null)
                )
            }
        )
    }
}

@Stable
class DefaultNumberInputTextFieldStateHelper(
    initialText: String = "",
    private val onValidate: InputTextFieldStateErrorScope<Error>.(String) -> Error? = { null },
    private val errorScope: InputTextFieldStateErrorScope<Error> = DefaultErrorScope,
) : NumberInputTextFieldStateHelper {

    constructor(
        initValue: Number?,
        onValidate: InputTextFieldStateErrorScope<Error>.(String) -> Error? = { null },
        errorScope: InputTextFieldStateErrorScope<Error> = DefaultErrorScope,
    ) : this(initialText = initValue?.toString() ?: "", onValidate = onValidate, errorScope = errorScope)


    override val state: NumberInputTextFieldState = DefaultNumberInputTextFieldState(initialText = initialText)

    override fun <T : Number> setValue(value: T?) = setText(value?.toString() ?: "")

    fun edit(block: TextFieldBuffer.() -> Unit) = with(state) { fieldState.edit(block) }

    fun update(block: (String) -> String) = with(state) { fieldState.setTextAndPlaceCursorAtEnd(block(fieldState.text.toString())) }

    /**
     * Runs the validation logic against the current text and updates the error state.
     *
     * @return `true` if the input is valid, `false` otherwise.
     */
    override fun validate(
        onValidate: (InputTextFieldStateErrorScope<Error>.(String) -> Error?)?
    ): Error? = with(errorScope) {
        when (onValidate) {
            null -> onValidate(text)
            else -> onValidate(text)
        }.also(::setError)
    }

    override suspend fun validateOnInputUpdate() {
        snapshotFlow { state.fieldState.text }
            .distinctUntilChanged()
            .map { text -> with(errorScope) { onValidate(text.toString()) } }
            .collectLatest { state.errorState.value = null }
    }

    object DefaultErrorScope : InputTextFieldStateErrorScope<Error> {
        override fun error(onMessage: @Composable (() -> String)): Error = Error.Custom(onMessage)
    }
}