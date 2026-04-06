package ua.isida.shared.di

import dev.zacsweers.metrox.viewmodel.ViewModelGraph
import ua.isida.shared.AppInitializers
import ua.isida.util.AppCoroutineDispatchers

interface AppGraph : ViewModelGraph {
    val initializers: AppInitializers

    val dispatchers: AppCoroutineDispatchers
}