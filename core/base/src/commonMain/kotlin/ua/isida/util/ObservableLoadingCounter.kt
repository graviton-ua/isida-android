package ua.isida.util

import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.updateAndGet
import kotlinx.coroutines.flow.*

class ObservableLoadingCounter {
    private val count = atomic(0)
    private val loadingState = MutableStateFlow(count.value)

    val loaders = loadingState.asStateFlow()
    val observable: Flow<Boolean>
        get() = loadingState.map { it > 0 }.distinctUntilChanged()

    fun addLoader() {
        loadingState.value = count.incrementAndGet()
    }

    fun removeLoader() {
        loadingState.value = count.decrementAndGet()
    }

    fun clear() {
        loadingState.value = count.updateAndGet { 0 }
    }
}