package ua.graviton.isida.shared.di

import androidx.lifecycle.ViewModelProvider
import com.whoppah.metrox.viewmodel.ViewModelGraph
import com.whoppah.util.AppCoroutineDispatchers
import ua.graviton.isida.shared.AppInitializers

interface AppGraph : ViewModelGraph.Factory {
    val initializers: AppInitializers

    val viewModelFactory: ViewModelProvider.Factory

    val dispatchers: AppCoroutineDispatchers
}