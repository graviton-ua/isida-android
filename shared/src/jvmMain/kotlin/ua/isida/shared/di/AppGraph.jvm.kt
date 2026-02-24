package ua.isida.shared.di

import ua.isida.util.AppCoroutineDispatchers
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import ua.isida.base.PlatformConfig
import ua.isida.shared.di.AppGraph

@DependencyGraph(AppScope::class)
interface JvmAppGraph : AppGraph {

    @DependencyGraph.Factory
    fun interface Factory {
        fun create(
            @Provides config: PlatformConfig,
        ): JvmAppGraph
    }


    val appScope: CoroutineScope

    @Provides @SingleIn(AppScope::class)
    fun provideAppScope(): CoroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    @Provides @SingleIn(AppScope::class)
    fun provideCoroutineDispatchers(): AppCoroutineDispatchers = AppCoroutineDispatchers(
        io = Dispatchers.IO,
        computation = Dispatchers.Default,
        main = Dispatchers.Main
    )
}