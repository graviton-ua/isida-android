package com.whoppah.common.compose.input

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf

@Stable
class DefaultInputState<T>(
    initValue: T,
    initError: InputState.Error? = null,
    initEnabled: Boolean = true,
) : InputState<T, InputState.Error> {
    override val inputState: MutableState<T> = mutableStateOf(initValue)
    override val errorState: MutableState<InputState.Error?> = mutableStateOf(initError)
    override val enabledState: MutableState<Boolean> = mutableStateOf(initEnabled)
}

@Stable
class DefaultInputStateHelper<T>(
    initValue: T,
    initError: InputState.Error? = null,
    initEnabled: Boolean = true,
) : InputStateHelper<T, InputState<T, InputState.Error>, InputState.Error> {
    override val state: InputState<T, InputState.Error> = DefaultInputState(initValue, initError, initEnabled)
}