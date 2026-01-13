package ua.crypto.shared.appinitializers

import dev.zacsweers.metro.Inject
import ua.crypto.core.appinitializers.AppInitializer

@Inject
class AppInitializers(
    private val initializers: Lazy<Set<AppInitializer>>,
) : AppInitializer {
    override fun initialize() {
        for (initializer in initializers.value) {
            initializer.initialize()
        }
    }
}
