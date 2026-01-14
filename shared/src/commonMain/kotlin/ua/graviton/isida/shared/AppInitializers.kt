package ua.graviton.isida.shared

import com.whoppah.appinitializers.AppInitializer
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn

@Inject
@SingleIn(AppScope::class)
class AppInitializers(
    private val initializers: Set<@JvmSuppressWildcards AppInitializer>,
) {
    fun init() {
        for (initializer in initializers) initializer.init()
    }
}