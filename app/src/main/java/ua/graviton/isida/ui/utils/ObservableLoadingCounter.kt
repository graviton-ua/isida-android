package ua.graviton.isida.ui.utils

import kotlinx.coroutines.flow.*
import ua.graviton.isida.domain.InvokeStatus
import java.util.concurrent.atomic.AtomicInteger

class ObservableLoadingCounter {
    private val count = AtomicInteger()
    private val loadingState = MutableStateFlow(count.get())

    val observable: Flow<Boolean>
        get() = loadingState.map { it > 0 }.distinctUntilChanged()

    fun addLoader() {
        loadingState.value = count.incrementAndGet()
    }

    fun removeLoader() {
        loadingState.value = count.decrementAndGet()
    }
}

suspend fun Flow<InvokeStatus>.collectInto(counter: ObservableLoadingCounter) {
    return onStart { counter.addLoader() }
        .onCompletion { counter.removeLoader() }
        .collect()
}