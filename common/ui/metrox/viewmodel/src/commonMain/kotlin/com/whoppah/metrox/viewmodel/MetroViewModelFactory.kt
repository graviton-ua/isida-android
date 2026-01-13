package com.whoppah.metrox.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import co.touchlab.kermit.Logger
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlin.reflect.KClass
import kotlin.time.measureTimedValue

@ContributesBinding(AppScope::class)
@Inject
class MetroViewModelFactory(
    val graphFactory: ViewModelGraph.Factory,
) : ViewModelProvider.Factory {
    private val logger by lazy { Logger.withTag("MetroViewModelFactory") }

    companion object {
        /** Creation extra key for the callbacks that create @AssistedInject-annotated ViewModels.  */
        val CREATION_CALLBACK_KEY = object : CreationExtras.Key<CreationExtras.(Any) -> ViewModel> {}
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
        val viewModelGraph = measureTimedValue {
            graphFactory.createViewModelGraph(extras)
        }.also { logger.d { "Created ViewModelGraph in ${it.duration} for ${modelClass.simpleName ?: ""}" } }.value

        //logger.d { "All injected ViewModels:\n${viewModelGraph.viewModelProviders.keys.joinToString(separator = ", ") { it.simpleName ?: "Unknown class" }}" }
        //logger.d { "All injected assisted ViewModels:\n${viewModelGraph.viewModelAssistedProviders.keys.joinToString(separator = ", ") { it.simpleName ?: "Unknown class" }}" }

        return viewModelGraph.viewModelProviders[modelClass]?.invoke() as T?
            ?: run {
                // The callback is meant to use an existing factory and provide the remaining
                // assisted dependencies to complete the creation of the ViewModel.
                val callback = extras[CREATION_CALLBACK_KEY] ?: return@run null
                val viewModelFactory = viewModelGraph.viewModelAssistedProviders[modelClass] ?: return@run null
                with(extras) {
                    callback(viewModelFactory.invoke()) as? T
                }
            }
            ?: throw IllegalArgumentException("Unknown model class $modelClass")
    }
}