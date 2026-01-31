package com.whoppah.common.compose.input

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import com.whoppah.common.resources.common_required
import com.whoppah.common.resources.error_invalid_pass
import com.whoppah.common.resources.error_invalid_pass_repeat
import org.jetbrains.compose.resources.stringResource
import com.whoppah.common.resources.Res as R

@Stable
interface PassInputTextFieldState : InputTextFieldState<PassInputTextFieldState.Error> {
    sealed interface Error : InputTextFieldState.Error {
        data object Required : Error {
            @Composable
            override fun asLabel(): String = stringResource(R.string.common_required)
        }

        data object Invalid : Error {
            @Composable
            override fun asLabel(): String = stringResource(R.string.error_invalid_pass)
        }

        data object NotMatch : Error {
            @Composable
            override fun asLabel(): String = stringResource(R.string.error_invalid_pass_repeat)
        }

        data class Custom(val message: String) : Error {
            @Composable
            override fun asLabel(): String = message
        }
    }

    companion object {
        val Preview = DefaultPassInputTextFieldState(
            fieldState = TextFieldState(),
            errorState = mutableStateOf(null)
        )
    }
}

@Stable
interface PassInputTextFieldStateHelper : InputTextFieldStateHelper<PassInputTextFieldState, PassInputTextFieldState.Error> {
    fun validate(onValidate: ((String) -> PassInputTextFieldState.Error?)? = null): PassInputTextFieldState.Error?
}

@Stable
data class DefaultPassInputTextFieldState(
    override val fieldState: TextFieldState = TextFieldState(),
    override val errorState: MutableState<PassInputTextFieldState.Error?> = mutableStateOf(null),
    override val enabledState: MutableState<Boolean> = mutableStateOf(true),
) : PassInputTextFieldState {

    constructor(
        initialText: String = "",
        initialError: PassInputTextFieldState.Error? = null,
        initialEnabled: Boolean = true,
    ) : this(
        fieldState = TextFieldState(initialText = initialText),
        errorState = mutableStateOf(initialError),
        enabledState = mutableStateOf(initialEnabled),
    )

    companion object {
        fun Saver(): Saver<DefaultPassInputTextFieldState, Any> = Saver(
            save = {
                with(TextFieldState.Saver) { save(it.fieldState) }
            },
            restore = {
                DefaultPassInputTextFieldState(
                    fieldState = TextFieldState.Saver.restore(it)!!,
                    errorState = mutableStateOf(null)
                )
            }
        )
    }
}

@Stable
class DefaultPassInputTextFieldStateHelper(
    initialText: String = "",
    private val onValidate: (String) -> PassInputTextFieldState.Error? = { null }
) : PassInputTextFieldStateHelper {
    /**
     * The UI-facing state object. Pass this to your `ViewState` data class.
     */
    override val state: PassInputTextFieldState = DefaultPassInputTextFieldState(initialText = initialText)

    /**
     * Runs the validation logic against the current text and updates the error state.
     *
     * @return `true` if the input is valid, `false` otherwise.
     */
    override fun validate(onValidate: ((String) -> PassInputTextFieldState.Error?)?): PassInputTextFieldState.Error? = when (onValidate) {
        null -> onValidate(text)
        else -> onValidate(text)
    }.also(::setError)
}