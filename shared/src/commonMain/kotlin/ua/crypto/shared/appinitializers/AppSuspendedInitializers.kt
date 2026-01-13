package ua.crypto.shared.appinitializers

import dev.zacsweers.metro.Inject
import ua.crypto.core.appinitializers.AppSuspendedInitializer

@Inject
class AppSuspendedInitializers(
    private val initializers: Lazy<Set<AppSuspendedInitializer>>,
) : AppSuspendedInitializer {
    override suspend fun initialize() {
        for (initializer in initializers.value) {
            initializer.initialize()
        }
    }
}
