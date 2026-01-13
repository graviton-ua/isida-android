package ua.crypto.shared.serviceinitializers

import dev.zacsweers.metro.Inject
import ua.crypto.core.settings.TraderPreferences
import ua.crypto.domain.services.ServiceInitializer

@Inject
class TraderServiceInitializers(
    private val initializers: Lazy<Set<ServiceInitializer>>,
    private val prefs: TraderPreferences,
) {
    fun start() {
        val disabled = prefs.disabledServices.getNotSuspended()
        initializers.value.filterNot { disabled.contains(it::class.simpleName!!) }.forEach { it.start() }
    }

    fun stop() = initializers.value.forEach { it.stop() }
}

@Inject
class SyncServiceInitializers(
    private val initializers: Lazy<Set<ServiceInitializer>>,
) {
    fun start() = initializers.value.forEach { it.start() }

    fun stop() = initializers.value.forEach { it.stop() }
}