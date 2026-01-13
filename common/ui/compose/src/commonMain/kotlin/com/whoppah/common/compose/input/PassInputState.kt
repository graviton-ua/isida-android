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
interface PassInputState : InputState<PassInputState.Error> {
    sealed interface Error : InputState.Error {
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
        val Preview = DefaultPassInputState(
            fieldState = TextFieldState(),
            errorState = mutableStateOf(null)
        )
    }
}

@Stable
interface PassInputStateHelper : InputStateHelper<PassInputState, PassInputState.Error> {
    fun validate(onValidate: ((String) -> PassInputState.Error?)? = null): PassInputState.Error?
}

@Stable
data class DefaultPassInputState(
    override val fieldState: TextFieldState = TextFieldState(),
    override val errorState: MutableState<PassInputState.Error?> = mutableStateOf(null),
    override val enabledState: MutableState<Boolean> = mutableStateOf(true),
) : PassInputState {

    constructor(
        initialText: String = "",
        initialError: PassInputState.Error? = null,
        initialEnabled: Boolean = true,
    ) : this(
        fieldState = TextFieldState(initialText = initialText),
        errorState = mutableStateOf(initialError),
        enabledState = mutableStateOf(initialEnabled),
    )

    companion object {
        fun Saver(): Saver<DefaultPassInputState, Any> = Saver(
            save = {
                with(TextFieldState.Saver) { save(it.fieldState) }
            },
            restore = {
                DefaultPassInputState(
                    fieldState = TextFieldState.Saver.restore(it)!!,
                    errorState = mutableStateOf(null)
                )
            }
        )
    }
}

@Stable
class DefaultPassInputStateHelper(
    initialText: String = "",
    private val onValidate: (String) -> PassInputState.Error? = { null }
) : PassInputStateHelper {
    /**
     * The UI-facing state object. Pass this to your `ViewState` data class.
     */
    override val state: PassInputState = DefaultPassInputState(initialText = initialText)

    /**
     * Runs the validation logic against the current text and updates the error state.
     *
     * @return `true` if the input is valid, `false` otherwise.
     */
    override fun validate(onValidate: ((String) -> PassInputState.Error?)?): PassInputState.Error? = when (onValidate) {
        null -> onValidate(text)
        else -> onValidate(text)
    }.also(::setError)
}