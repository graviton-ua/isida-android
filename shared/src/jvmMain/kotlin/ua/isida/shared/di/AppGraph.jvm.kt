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
import ua.isida.util.FileSharer
import ua.isida.util.PathProvider

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

    @Provides @SingleIn(AppScope::class)
    fun providePathProvider(): PathProvider = object : PathProvider {
        override val filesPath: String = "."
    }

    @Provides @SingleIn(AppScope::class)
    fun provideFileSharer(impl: ua.isida.util.JvmFileSharer): FileSharer = impl
}