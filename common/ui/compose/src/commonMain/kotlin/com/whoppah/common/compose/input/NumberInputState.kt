package com.whoppah.common.compose.input

import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import com.whoppah.common.resources.common_error_price_invalid
import com.whoppah.common.resources.common_error_price_less_than
import com.whoppah.common.resources.common_error_price_more_than
import com.whoppah.common.resources.common_error_price_required
import org.jetbrains.compose.resources.stringResource
import com.whoppah.common.resources.Res as R

@Stable
interface NumberInputState : InputState<NumberInputState.Error> {

    val valueAsInt: Int?
    val valueAsDouble: Double?

    @Immutable
    sealed interface Error : InputState.Error {
        data object Required : Error {
            @Composable
            override fun asLabel(): String = stringResource(R.string.common_error_price_required)
        }

        data object Invalid : Error {
            @Composable
            override fun asLabel(): String = stringResource(R.string.common_error_price_invalid)
        }

        data class CantBeLessThen(val value: String) : Error {
            @Composable
            override fun asLabel(): String = stringResource(R.string.common_error_price_less_than, value)
        }

        data class CantBeMoreThen(val value: String) : Error {
            @Composable
            override fun asLabel(): String = stringResource(R.string.common_error_price_more_than, value)
        }

        data class Custom(val message: String) : Error {
            @Composable
            override fun asLabel(): String = message
        }
    }

    companion object {
        fun preview(text: String = "", error: Error? = null) = DefaultNumberInputState(
            fieldState = TextFieldState(initialText = text),
            errorState = mutableStateOf(error),
        )

        val Preview = DefaultNumberInputState(
            fieldState = TextFieldState(initialText = "100"),
            errorState = mutableStateOf(null),
        )
        val PreviewError = DefaultNumberInputState(
            fieldState = TextFieldState(initialText = "100"),
            errorState = mutableStateOf(Error.Required),
        )
    }
}

@Stable
interface NumberInputStateHelper : InputStateHelper<NumberInputState, NumberInputState.Error> {
    fun setValue(value: Int?)

    fun validate(onValidate: ((String) -> NumberInputState.Error?)? = null): NumberInputState.Error?
}

@Stable
data class DefaultNumberInputState(
    override val fieldState: TextFieldState,
    override val errorState: MutableState<NumberInputState.Error?>,
    override val enabledState: MutableState<Boolean> = mutableStateOf(true),
) : NumberInputState {

    override val valueAsInt: Int?
        get() = fieldState.text.toString().toIntOrNull()
    override val valueAsDouble: Double?
        get() = fieldState.text.toString().toDoubleOrNull()

    constructor(
        initialText: String = "",
        initialError: NumberInputState.Error? = null,
        initialEnabled: Boolean = true,
    ) : this(
        fieldState = TextFieldState(initialText = initialText),
        errorState = mutableStateOf(initialError),
        enabledState = mutableStateOf(initialEnabled),
    )

    companion object {
        fun Saver(): Saver<DefaultNumberInputState, Any> = Saver(
            save = {
                with(TextFieldState.Saver) { save(it.fieldState) }
            },
            restore = {
                DefaultNumberInputState(
                    fieldState = TextFieldState.Saver.restore(it)!!,
                    errorState = mutableStateOf(null)
                )
            }
        )
    }
}

@Stable
class DefaultNumberInputStateHelper(
    initialText: String = "",
    private val onValidate: (String) -> NumberInputState.Error? = { null }
) : NumberInputStateHelper {

    constructor(
        initValue: Int?,
        onValidate: (String) -> NumberInputState.Error? = { null },
    ) : this(initialText = initValue?.toString() ?: "", onValidate = onValidate)

    constructor(
        initValue: Double?,
        onValidate: (String) -> NumberInputState.Error? = { null },
    ) : this(initialText = initValue?.toString() ?: "", onValidate = onValidate)


    override val state: NumberInputState = DefaultNumberInputState(initialText = initialText)


    override fun setValue(value: Int?) = setText(value?.toString() ?: "")


    fun edit(block: TextFieldBuffer.() -> Unit) = with(state) { fieldState.edit(block) }

    fun update(block: (String) -> String) = with(state) { fieldState.setTextAndPlaceCursorAtEnd(block(fieldState.text.toString())) }

    /**
     * Runs the validation logic against the current text and updates the error state.
     *
     * @return `true` if the input is valid, `false` otherwise.
     */
    override fun validate(onValidate: ((String) -> NumberInputState.Error?)?): NumberInputState.Error? = when (onValidate) {
        null -> onValidate(text)
        else -> onValidate(text)
    }.also(::setError)
}