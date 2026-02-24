@file:OptIn(ExperimentalCoroutinesApi::class)

package ua.isida.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * A Flow operator that converts a cold Flow into a hot StateFlow, caching the last-emitted value.
 * The upstream flow is collected only when there is at least one collector.
 * Unlike `stateIn` with `SharingStarted.WhileSubscribed`, this operator NEVER resets the
 * cached value back to the initial value. It holds the last-emitted value indefinitely
 * within the provided scope.
 *
 * @param scope The CoroutineScope in which the upstream flow is collected.
 * @param initialValue The initial value of the StateFlow.
 * @return A StateFlow that emits updates from the upstream flow and caches the last value.
 */
fun <T> Flow<T>.cacheIn(
    scope: CoroutineScope,
    initialValue: T
): StateFlow<T> {
    // The private, mutable state flow that acts as our cache.
    val cache = MutableStateFlow(initialValue)

    // A reference to the collection job, so we can cancel it when not needed.
    var job: Job? = null

    // This is a SharedFlow that tracks the number of subscribers.
    // It is a powerful but low-level API.
    val subscriptionCount = cache.subscriptionCount

    scope.launch {
        // This will collect emissions from the subscriptionCount flow.
        subscriptionCount.collect { count ->
            if (count > 0) {
                // If there's at least one subscriber, and we don't have an active job,
                // start a new one.
                if (job == null) {
                    job = this@cacheIn
                        .onEach { value -> cache.value = value } // Update cache on each emission
                        .launchIn(scope) // Launch collection in the provided scope
                }
            } else {
                // If there are no subscribers, cancel the existing job.
                job?.cancel()
                job = null
            }
        }
    }

    // Return the read-only StateFlow to the consumer.
    return cache.asStateFlow()
}

/**
 * A Flow operator that converts a cold Flow into a hot StateFlow, caching the last-emitted value
 * and respecting a SharingStarted policy.
 *
 * The upstream flow's collection is controlled by the `started` policy.
 * Unlike `stateIn`, this operator NEVER resets the cached value back to the initial value. It
 * interprets `SharingCommand.STOP_AND_RESET_REPLAY_CACHE` as a simple `STOP`, preserving
 * the last-emitted value indefinitely within the provided scope.
 *
 * @param scope The CoroutineScope in which the upstream flow is collected.
 * @param started The SharingStarted policy that controls when the upstream flow is started and stopped.
 * @param initialValue The initial value of the StateFlow.
 * @return A StateFlow that emits updates from the upstream flow and caches the last value.
 */
fun <T> Flow<T>.cacheIn(
    scope: CoroutineScope,
    started: SharingStarted,
    initialValue: T
): StateFlow<T> {
    // The private, mutable state flow that acts as our persistent cache.
    val cache = MutableStateFlow(initialValue)

    // The command flow is derived from the subscription count of our cache and the provided policy.
    val commandFlow = started.command(cache.subscriptionCount)

    commandFlow
        // Map the SharingCommand to a boolean representing the active state.
        // Crucially, we treat STOP_AND_RESET_REPLAY_CACHE as a simple stop.
        .map { command ->
            when (command) {
                SharingCommand.START -> true
                SharingCommand.STOP, SharingCommand.STOP_AND_RESET_REPLAY_CACHE -> false
            }
        }
        // Prevent restarting the flow if the command is the same as the last one.
        .distinctUntilChanged()
        // Use flatMapLatest to start or stop collecting the upstream flow.
        .flatMapLatest { isActive ->
            if (isActive) {
                // If active, switch to the upstream flow.
                this@cacheIn
            } else {
                // If inactive, switch to an empty flow, effectively stopping collection.
                emptyFlow()
            }
        }
        // For each value from the upstream, update our cache.
        .onEach { value -> cache.value = value }
        // Launch this entire control mechanism in the provided scope.
        .launchIn(scope)

    // Return the read-only StateFlow to the consumer.
    return cache.asStateFlow()
}