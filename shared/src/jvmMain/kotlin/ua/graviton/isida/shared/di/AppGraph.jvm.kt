package ua.graviton.isida.shared.di

import com.whoppah.base.PlatformConfig
import com.whoppah.util.AppCoroutineDispatchers
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.Dispatchers

@DependencyGraph(AppScope::class)
interface JvmAppGraph : AppGraph {

    @DependencyGraph.Factory
    fun interface Factory {
        fun create(
            @Provides config: PlatformConfig,
        ): JvmAppGraph
    }


    //fun inject(target: SpecialFirebaseMessagingService)


    @Provides @SingleIn(AppScope::class)
    fun provideCoroutineDispatchers(): AppCoroutineDispatchers = AppCoroutineDispatchers(
        io = Dispatchers.IO,
        computation = Dispatchers.Default,
        main = Dispatchers.Main
    )
}