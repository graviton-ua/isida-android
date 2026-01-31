package com.whoppah.common.compose.input

import androidx.compose.runtime.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged

@Stable
interface InputState<T, E : InputState.Error> {
    val inputState: MutableState<T>
    val errorState: MutableState<E?>
    val enabledState: MutableState<Boolean>

    val isError: Boolean
        get() = errorState.value != null

    @Immutable
    interface Error {
        @Composable fun asLabel(): String
    }
}

@Stable
interface InputStateHelper<T, S : InputState<T, E>, E : InputState.Error> {
    val state: S

    val value: T
        get() = state.inputState.value
    val valueAsFlow: Flow<T>
        get() = snapshotFlow { state.inputState.value }

    val error: E?
        get() = state.errorState.value
    val errorFlow: Flow<E?>
        get() = snapshotFlow { state.errorState.value }


    fun setValue(value: T) = with(state) { inputState.value = value; errorState.value = null }
    fun setError(error: E?) = with(state) { errorState.value = error }
    fun clearError() = setError(null)

    fun setEnabled(enabled: Boolean) = with(state.enabledState) { value = enabled }

    fun validate(onValidate: ((T) -> E?)? = null): E?

    open suspend fun validateOnInputUpdate() = Unit

    suspend fun clearErrorOnInputUpdate() {
        valueAsFlow.distinctUntilChanged()
            .collectLatest { state.errorState.value = null }
    }
}