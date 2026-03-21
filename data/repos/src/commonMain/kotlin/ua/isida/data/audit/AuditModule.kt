package ua.isida.data.audit

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import ua.isida.util.AuditLogger

@ContributesTo(AppScope::class)
interface AuditModule {
    /**
     * Binds FileAuditLogger implementation to AuditLogger interface.
     */
    @Provides
    @SingleIn(AppScope::class)
    fun provideAuditLogger(impl: FileAuditLogger): AuditLogger = impl
}
