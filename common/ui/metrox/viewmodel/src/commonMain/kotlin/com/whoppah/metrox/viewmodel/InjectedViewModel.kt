package com.whoppah.metrox.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel

val LocalMetroViewModelFactory = staticCompositionLocalOf<ViewModelProvider.Factory?> { null }

@Composable
inline fun <reified VM : ViewModel> injectedViewModel(
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    },
    key: String? = null,
    factory: ViewModelProvider.Factory? = LocalMetroViewModelFactory.current,
): VM {
    return viewModel<VM>(
        viewModelStoreOwner = viewModelStoreOwner,
        key = key,
        factory = factory,
    )
}

@Composable
inline fun <reified VM : ViewModel, reified VMF> injectedViewModel(
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    },
    key: String? = null,
    factory: ViewModelProvider.Factory? = LocalMetroViewModelFactory.current,
    noinline creationCallback: CreationExtras.(VMF) -> VM,
): VM {
    return viewModel<VM>(
        viewModelStoreOwner = viewModelStoreOwner,
        key = key,
        factory = factory,
        extras = viewModelStoreOwner.run {
            if (this is HasDefaultViewModelProviderFactory) {
                this.defaultViewModelCreationExtras.withCreationCallback(creationCallback)
            } else {
                CreationExtras.Empty.withCreationCallback(creationCallback)
            }
        },
    )
}