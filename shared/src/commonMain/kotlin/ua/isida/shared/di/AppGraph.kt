package ua.isida.shared.di

import androidx.lifecycle.ViewModelProvider
import ua.isida.metrox.viewmodel.ViewModelGraph
import ua.isida.util.AppCoroutineDispatchers
import ua.isida.shared.AppInitializers

interface AppGraph : ViewModelGraph.Factory {
    val initializers: AppInitializers

    val viewModelFactory: ViewModelProvider.Factory

    val dispatchers: AppCoroutineDispatchers
}