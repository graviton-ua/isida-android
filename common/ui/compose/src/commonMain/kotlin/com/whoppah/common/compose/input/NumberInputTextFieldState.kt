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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import org.jetbrains.compose.resources.stringResource
import com.whoppah.common.resources.Res as R

@Stable
interface NumberInputTextFieldState : InputTextFieldState<NumberInputTextFieldState.Error> {

    val valueAsInt: Int?
    val valueAsFloat: Float?

    @Immutable
    sealed interface Error : InputTextFieldState.Error {
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
interface NumberInputTextFieldStateHelper : InputTextFieldStateHelper<NumberInputTextFieldState, NumberInputTextFieldState.Error> {
    fun <T : Number> setValue(value: T?)

    fun validate(onValidate: ((String) -> NumberInputTextFieldState.Error?)? = null): NumberInputTextFieldState.Error?
}

@Stable
data class DefaultNumberInputTextFieldState(
    override val fieldState: TextFieldState,
    override val errorState: MutableState<NumberInputTextFieldState.Error?>,
    override val enabledState: MutableState<Boolean> = mutableStateOf(true),
) : NumberInputTextFieldState {

    override val valueAsInt: Int?
        get() = fieldState.text.toString().toIntOrNull()
    override val valueAsFloat: Float?
        get() = fieldState.text.toString().toFloatOrNull()

    constructor(
        initialText: String = "",
        initialError: NumberInputTextFieldState.Error? = null,
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
    private val onValidate: (String) -> NumberInputTextFieldState.Error? = { null }
) : NumberInputTextFieldStateHelper {

    constructor(
        initValue: Number?,
        onValidate: (String) -> NumberInputTextFieldState.Error? = { null },
    ) : this(initialText = initValue?.toString() ?: "", onValidate = onValidate)


    override val state: NumberInputTextFieldState = DefaultNumberInputTextFieldState(initialText = initialText)

    override fun <T : Number> setValue(value: T?) = setText(value?.toString() ?: "")

    fun edit(block: TextFieldBuffer.() -> Unit) = with(state) { fieldState.edit(block) }

    fun update(block: (String) -> String) = with(state) { fieldState.setTextAndPlaceCursorAtEnd(block(fieldState.text.toString())) }

    /**
     * Runs the validation logic against the current text and updates the error state.
     *
     * @return `true` if the input is valid, `false` otherwise.
     */
    override fun validate(onValidate: ((String) -> NumberInputTextFieldState.Error?)?): NumberInputTextFieldState.Error? = when (onValidate) {
        null -> onValidate(text)
        else -> onValidate(text)
    }.also(::setError)

    override suspend fun validateOnInputUpdate() {
        snapshotFlow { state.fieldState.text }
            .distinctUntilChanged()
            .map { text -> onValidate(text.toString()) }
            .collectLatest { state.errorState.value = null }
    }
}