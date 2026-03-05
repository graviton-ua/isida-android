package ua.isida.common.ui.compose.input

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import org.jetbrains.compose.resources.stringResource
import ua.isida.common.ui.compose.input.TextInputTextFieldState.Error
import ua.isida.common.ui.resources.input_invalid
import ua.isida.common.ui.resources.input_required
import ua.isida.common.ui.resources.Res as R

@Stable
interface TextInputTextFieldState : InputTextFieldState<Error> {
    @Immutable
    interface Error : InputTextFieldState.Error {
        data object Required : Error {
            @Composable
            override fun asLabel(): String = stringResource(R.string.input_required)
        }

        data object Invalid : Error {
            @Composable
            override fun asLabel(): String = stringResource(R.string.input_invalid)
        }

        data class Custom(private val onMessage: @Composable () -> String) : Error {
            constructor(message: String) : this(onMessage = { message })

            @Composable
            override fun asLabel(): String = onMessage()
        }
    }

    companion object {
        val Preview = DefaultTextInputTextFieldState(
            fieldState = TextFieldState(initialText = "Some text"),
            errorState = mutableStateOf(null)
        )
    }
}

@Stable
interface TextInputTextFieldStateHelper : InputTextFieldStateHelper<TextInputTextFieldState, Error> {
    suspend fun validateSuspend(onValidate: (suspend InputTextFieldStateErrorScope<Error>.(String) -> Error?)? = null): Error?
}

@Stable
data class DefaultTextInputTextFieldState(
    override val fieldState: TextFieldState,
    override val errorState: MutableState<Error?> = mutableStateOf(null),
    override val enabledState: MutableState<Boolean> = mutableStateOf(true),
) : TextInputTextFieldState {

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
        /**
         * A Saver for [DefaultTextInputTextFieldState] that handles process death and configuration changes.
         * Note: Validation error state is not saved/restored as it should be re-validated.
         */
        fun Saver(): Saver<DefaultTextInputTextFieldState, Any> = Saver(
            save = {
                with(TextFieldState.Saver) { save(it.fieldState) }
            },
            restore = {
                DefaultTextInputTextFieldState(
                    fieldState = TextFieldState.Saver.restore(it)!!,
                    errorState = mutableStateOf(null)
                )
            }
        )
    }
}

@Stable
class DefaultTextInputTextFieldStateHelper(
    initialText: String = "",
    private val onValidate: InputTextFieldStateErrorScope<Error>.(String) -> Error? = { null },
    private val errorScope: InputTextFieldStateErrorScope<Error> = DefaultErrorScope,
) : TextInputTextFieldStateHelper {
    /**
     * The UI-facing state object. Pass this to your `ViewState` data class.
     */
    override val state: TextInputTextFieldState = DefaultTextInputTextFieldState(initialText = initialText)

    /**
     * Runs the validation logic against the current text and updates the error state.
     *
     * @return `true` if the input is valid, `false` otherwise.
     */
    override fun validate(onValidate: (InputTextFieldStateErrorScope<Error>.(String) -> Error?)?): Error? = with(errorScope) {
        when (onValidate) {
            null -> onValidate(text)
            else -> onValidate(text)
        }.also(::setError)
    }

    override suspend fun validateSuspend(onValidate: (suspend InputTextFieldStateErrorScope<Error>.(String) -> Error?)?): Error? =
        with(errorScope) {
            when (onValidate) {
                null -> onValidate(text)
                else -> onValidate(text)
            }.also(::setError)
        }

    object DefaultErrorScope : InputTextFieldStateErrorScope<Error> {
        override fun error(onMessage: @Composable (() -> String)): Error = Error.Custom(onMessage)
    }
}