package ua.graviton.isida.data.db.adapters

import app.cash.sqldelight.ColumnAdapter
import kotlin.time.Instant

internal object InstantColumnAdapter : ColumnAdapter<Instant, Long> {
    override fun decode(databaseValue: Long): Instant {
        return Instant.fromEpochMilliseconds(databaseValue)
    }

    override fun encode(value: Instant): Long {
        return value.toEpochMilliseconds()
    }
}
