package com.whoppah.common.compose.input

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf

@Stable
class DefaultInputState<T, E : InputState.Error>(
    initValue: T,
    initError: E? = null,
    initEnabled: Boolean = true,
) : InputState<T, E> {

    override val inputState: MutableState<T> = mutableStateOf(initValue)
    override val errorState: MutableState<E?> = mutableStateOf(initError)
    override val enabledState: MutableState<Boolean> = mutableStateOf(initEnabled)
}

@Stable
class DefaultInputStateHelper<T, E : InputState.Error>(
    initValue: T,
    initError: E? = null,
    initEnabled: Boolean = true,
    private val onValidate: (T) -> E? = { null }
) : InputStateHelper<T, InputState<T, E>, E> {

    override val state: InputState<T, E> = DefaultInputState(initValue, initError, initEnabled)

    override fun validate(onValidate: ((T) -> E?)?): E? = when (onValidate) {
        null -> onValidate(value)
        else -> onValidate(value)
    }.also(::setError)
}