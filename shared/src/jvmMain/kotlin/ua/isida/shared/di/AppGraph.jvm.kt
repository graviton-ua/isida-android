package ua.isida.shared.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.io.files.Path
import ua.isida.base.PlatformConfig
import ua.isida.util.AppCoroutineDispatchers
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
        override val logsPath: Path = Path(System.getProperty("user.home"), "Isida", "logs")
    }

    @Provides @SingleIn(AppScope::class)
    fun provideFileSharer(impl: ua.isida.util.JvmFileSharer): FileSharer = impl
}