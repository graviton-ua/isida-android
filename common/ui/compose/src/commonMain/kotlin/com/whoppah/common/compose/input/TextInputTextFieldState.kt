package com.whoppah.common.compose.input

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import com.whoppah.common.resources.*
import org.jetbrains.compose.resources.stringResource
import com.whoppah.common.resources.Res as R

@Stable
interface TextInputTextFieldState : InputTextFieldState<TextInputTextFieldState.Error> {
    @Immutable
    interface Error : InputTextFieldState.Error {
        data object Required : Error {
            @Composable
            override fun asLabel(): String = stringResource(R.string.common_error_price_required)
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
        val Preview = DefaultTextInputTextFieldState(
            fieldState = TextFieldState(initialText = "Some text"),
            errorState = mutableStateOf(null)
        )
    }
}

@Stable
interface TextInputTextFieldStateHelper : InputTextFieldStateHelper<TextInputTextFieldState, TextInputTextFieldState.Error> {
    fun validate(onValidate: ((String) -> TextInputTextFieldState.Error?)? = null): TextInputTextFieldState.Error?
    suspend fun validateSuspend(onValidate: (suspend (String) -> TextInputTextFieldState.Error?)? = null): TextInputTextFieldState.Error?

    companion object {
        val Required: (String) -> TextInputTextFieldState.Error? = {
            when {
                it.isBlank() -> TextInputTextFieldState.Error.Required
                else -> null
            }
        }
    }
}

@Stable
data class DefaultTextInputTextFieldState(
    override val fieldState: TextFieldState,
    override val errorState: MutableState<TextInputTextFieldState.Error?> = mutableStateOf(null),
    override val enabledState: MutableState<Boolean> = mutableStateOf(true),
) : TextInputTextFieldState {

    constructor(
        initialText: String = "",
        initialError: TextInputTextFieldState.Error? = null,
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
    private val onValidate: (String) -> TextInputTextFieldState.Error? = { null }
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
    override fun validate(onValidate: ((String) -> TextInputTextFieldState.Error?)?): TextInputTextFieldState.Error? = when (onValidate) {
        null -> onValidate(text)
        else -> onValidate(text)
    }.also(::setError)

    override suspend fun validateSuspend(onValidate: (suspend (String) -> TextInputTextFieldState.Error?)?): TextInputTextFieldState.Error? = when (onValidate) {
        null -> onValidate(text)
        else -> onValidate(text)
    }.also(::setError)
}

@Stable
class UserNameInputTextFieldStateHelper(
    initialText: String = "",
    private val onValidate: (String) -> TextInputTextFieldState.Error? = {
        when {
            it.isBlank() -> TextInputTextFieldState.Error.Required
            it.containWhoppah() -> Error.ContainWhoppah
            it.containEmail() -> Error.ContainEmail
            else -> null
        }
    }
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
    override fun validate(onValidate: ((String) -> TextInputTextFieldState.Error?)?): TextInputTextFieldState.Error? = when (onValidate) {
        null -> onValidate(text)
        else -> onValidate(text)
    }.also(::setError)

    override suspend fun validateSuspend(onValidate: (suspend (String) -> TextInputTextFieldState.Error?)?): TextInputTextFieldState.Error? = when (onValidate) {
        null -> onValidate(text)
        else -> onValidate(text)
    }.also(::setError)


    private sealed interface Error : TextInputTextFieldState.Error {
        data object ContainWhoppah : Error {
            @Composable
            override fun asLabel(): String = stringResource(R.string.register_block_whoppah)
        }

        data object ContainEmail : Error {
            @Composable
            override fun asLabel(): String = stringResource(R.string.common_register_block_email)
        }
    }
}