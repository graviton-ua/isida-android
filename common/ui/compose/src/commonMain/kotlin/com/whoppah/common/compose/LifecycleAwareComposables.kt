package com.whoppah.common.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * Lifecycle-Aware ViewModel
 *
 * Example:
 * @Composable
 * fun ProductsScreen(
 *     viewModel: ProductsViewModel = hiltViewModel(),
 * ) {
 *     viewModel.observeLifecycle(LocalLifecycleOwner.current.lifecycle)
 * }
 *
 * @HiltViewModel
 * class ProductsViewModel @Inject constructor(
 *     private val productsRepository: ProductsRepository,
 * ) : ViewModel(), DefaultLifecycleObserver {
 *
 *     override fun onResume(owner: LifecycleOwner) {
 *         viewModelScope.launch {
 *             productsRepository.fetchAll()
 *         }
 *     }
 * }
 */
@Composable
fun <LO : LifecycleObserver> LO.observeLifecycle(lifecycle: Lifecycle) {
    DisposableEffect(lifecycle) {
        lifecycle.addObserver(this@observeLifecycle)
        onDispose {
            lifecycle.removeObserver(this@observeLifecycle)
        }
    }
}

private fun nullUnlessStateIsAtLeast(
    state: Lifecycle.State,
    lifecycleOwner: LifecycleOwner,
): Unit? {
    require(state != Lifecycle.State.DESTROYED) {
        "Target state is not allowed to be `Lifecycle.State.DESTROYED` because Compose disposes " +
                "of the composition before `Lifecycle.Event.ON_DESTROY` observers are invoked."
    }

    return if (lifecycleOwner.lifecycle.currentState.isAtLeast(state)) Unit else null
}

fun nullUnlessResumed(
    lifecycleOwner: LifecycleOwner,
): Unit? = nullUnlessStateIsAtLeast(Lifecycle.State.RESUMED, lifecycleOwner)